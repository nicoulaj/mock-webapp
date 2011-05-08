/*
 * Copyright (c) 2011 Julien Nicoulaud <julien.nicoulaud@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nicoulaj.benchmark.mockwebapp.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * A {@link MockWebAppConfig} describes the behaviour of a mock web application.
 * <p/>
 * It is a set of {@link Mapping}s defining how HTTP requests should be handled.
 * It is XML serializable/deserializable (see {@link MockWebAppConfig.Parser}).
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @see net.nicoulaj.benchmark.mockwebapp.MockWebAppServlet
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mock-web-app")
public class MockWebAppConfig implements ConfigFragment {

    /**
     * Strategies for requests matching.
     *
     * @see MockWebAppConfig#matchingStrategy
     */
    public static enum MatchingStrategy {

        /**
         * Trigger only the first matching {@link Mapping}.
         */
        first,

        /**
         * Trigger all matching {@link Mapping}s.
         */
        all
    }

    /**
     * Defines the strategy for triggering matched {@link Mapping}s for each request.
     */
    @XmlElement(required = false, nillable = false, defaultValue = "all")
    public MatchingStrategy matchingStrategy = MatchingStrategy.all;

    /**
     * The list of {@link Mapping}s to matches requests against.
     *
     * @see #process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @XmlElement(name = "mapping", required = true, nillable = false)
    public List<Mapping> mappings;

    /**
     * Process a request and associated response.
     * <p/>
     * Triggers {@link #mappings} as defined by the {@link #matchingStrategy}.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @see net.nicoulaj.benchmark.mockwebapp.MockWebAppServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        for (Mapping mapping : mappings) {
            if (mapping.matches(req)) {
                mapping.process(req, resp);
                if (MatchingStrategy.first.equals(matchingStrategy)) return;
            }
        }
    }

    /**
     * Assert this {@link MockWebAppConfig} is valid.
     * <p/>
     * Checks everything that cannot be enforced through the XML schema.
     *
     * @throws Exception if an element of the {@link MockWebAppConfig} is invalid.
     */
    public void validate() throws Throwable {
        assert mappings != null && !mappings.isEmpty() : "At least one mapping should be declared";
        for (Mapping mapping : mappings) mapping.validate();
    }

    /**
     * {@link MockWebAppConfig} Java<->XML binding.
     *
     * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
     * @since 1.0.0
     */
    public static class Parser {

        /**
         * The path to the XSD schema file used to validate the XML config files.
         *
         * @see #initUnmarshaller()
         */
        public static final String XSD_SCHEMA_PATH = "/mock-web-app.xsd";

        /**
         * The path to the XSD schema file used to validate the XML config files, for running tests.
         * <p/>
         * FIXME Find a way to get rid of this.
         *
         * @see #initUnmarshaller()
         */
        public static final String XSD_SCHEMA_TESTS_PATH = "file:target/schemas/mock-web-app.xsd";

        /**
         * The JAXB {@link Unmarshaller} used to parse XML config files.
         */
        protected static Unmarshaller unmarshaller;

        /**
         * Parse a config file.
         *
         * @param file the file to parse.
         * @return the corresponding {@link MockWebAppConfig}
         * @throws Exception if the file could not be read, the JAXB unmarshaller
         *                   could not be initialized or the file was not valid.
         */
        public static MockWebAppConfig parseConfig(File file) throws Exception {

            // Check the given file exists.
            if (file == null || !file.exists() || !file.isFile()) {
                throw new Exception("The config file could not be read");
            }

            // Initialize the Java XML binding unmarshaller if needed.
            if (unmarshaller == null) {
                try {
                    initUnmarshaller();
                } catch (Exception e) {
                    throw new Exception("Failed initializing configuration unmarshaller", e);
                }
            }

            // Unmarshall the config file.
            MockWebAppConfig config;
            try {
                config = (MockWebAppConfig) unmarshaller.unmarshal(file);
            } catch (Exception e) {
                throw new Exception("Failed parsing configuration file", e);
            }

            // Validate the config.
            try {
                config.validate();
            } catch (Throwable t) {
                throw new Exception("Failed validating configuration file", t);
            }

            return config;
        }

        /**
         * Initialize the JAXB {@link Unmarshaller} {@link #unmarshaller}.
         *
         * @throws Exception if the unmarshaller could not be initialized or the XSD schema file could not be loaded.
         */
        protected static void initUnmarshaller() throws Exception {

            // Create the JAXB unmarshaller.
            synchronized (JAXBContext.class) {
                unmarshaller = JAXBContext.newInstance(MockWebAppConfig.class).createUnmarshaller();
            }

            // Load the XSD schema.
            try {
                URL resource = MockWebAppConfig.class.getClassLoader().getResource(XSD_SCHEMA_PATH);
                if (resource == null) resource = new URL(XSD_SCHEMA_TESTS_PATH);
                unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(resource));
            } catch (Exception e) {
                throw new Exception("Failed loading XSD schema file", e);
            }
        }
    }
}
