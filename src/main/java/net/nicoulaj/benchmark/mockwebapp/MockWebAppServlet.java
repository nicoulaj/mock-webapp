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
package net.nicoulaj.benchmark.mockwebapp;

import net.nicoulaj.benchmark.mockwebapp.config.MockWebAppConfig;
import org.apache.commons.vfs.FileChangeEvent;
import org.apache.commons.vfs.FileListener;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileMonitor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * {@link HttpServlet} that defers requests handling to a {@link MockWebAppConfig}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class MockWebAppServlet extends HttpServlet {

    /**
     * The name of property used to define the path to the configuration file to use.
     */
    public static final String MOCK_WEB_APP_CONF_PROPERTY = "mock-web-app-conf";

    /**
     * The {@link MockWebAppConfig} currently in use.
     */
    protected MockWebAppConfig mockWebAppConfig;

    /**
     * The file monitor used to detect changes in the configuration file.
     */
    protected DefaultFileMonitor fileMonitor;

    /**
     * Initialize the servlet.
     * <p/>
     * Loads the {@link #mockWebAppConfig} by looking up the {@link #MOCK_WEB_APP_CONF_PROPERTY} as a system property or init parameter,
     * and sets up a listener on the file changes.
     *
     * @param config the {@link ServletConfig}, optionally with a {@link #MOCK_WEB_APP_CONF_PROPERTY} parameter.
     * @throws ServletException if the {@link #MOCK_WEB_APP_CONF_PROPERTY} was neither defined as a system property nor an init parameter.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Locate the config file parameter.
        String configFilePath = System.getProperty(MOCK_WEB_APP_CONF_PROPERTY);
        if (configFilePath == null) configFilePath = config.getInitParameter(MOCK_WEB_APP_CONF_PROPERTY);
        if (configFilePath == null) throw new ServletException("No mock web app config file defined. Please define one using the '" + MOCK_WEB_APP_CONF_PROPERTY + "' system property or servlet init parameter.");

        // Setup the config file changes listener.
        try {
            fileMonitor = new DefaultFileMonitor(new ConfigFileListener());
            fileMonitor.addFile(VFS.getManager().resolveFile(new File("."), configFilePath));
            fileMonitor.start();
        } catch (Exception e) {
            getServletContext().log("Failed setting up config file changes listener, config file changes will not be taken into account", e);
        }

        // Load the config from the file.
        final File configFile = new File(configFilePath);
        try {
            mockWebAppConfig = MockWebAppConfig.Parser.parseConfig(configFile);
            getServletContext().log("Loaded config file " + configFilePath);
        } catch (Exception e) {
            getServletContext().log("Failed loading config, please replace it with a valid one", e);
        }
    }

    /**
     * Prepare the servlet for stopping service.
     * <p/>
     * Disposes the config file changes monitor.
     */
    @Override
    public void destroy() {
        if (fileMonitor != null) fileMonitor.stop();
        super.destroy();
    }

    /**
     * Handle a GET request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * Handle a HEAD request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * Handle a POST request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * Handle a PUT request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * Handle a DELETE request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * Handle a OPTIONS request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * Handle a TRACE request.
     * <p/>
     * Defers the request to {@link #mockWebAppConfig} when possible.
     *
     * @param req  the client request.
     * @param resp the servlet response.
     * @throws IOException      if an input or output error occured while handling the request.
     * @throws ServletException if the request could not be handled.
     */
    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (mockWebAppConfig != null) mockWebAppConfig.process(req, resp);
        else super.doDelete(req, resp);
    }

    /**
     * {@link FileListener} handling the mock webapp config file changes.
     *
     * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
     * @see MockWebAppServlet#init(javax.servlet.ServletConfig)
     * @see MockWebAppServlet#fileMonitor
     * @since 1.0.0
     */
    protected class ConfigFileListener implements FileListener {

        /**
         * Handle a file creation event.
         * <p/>
         * Defers the event to {@link #fileChanged(org.apache.commons.vfs.FileChangeEvent)}.
         *
         * @param fileChangeEvent the event to handle.
         */
        public void fileCreated(FileChangeEvent fileChangeEvent) {
            fileChanged(fileChangeEvent);
        }

        /**
         * Handle a file deletion event.
         * <p/>
         * Unsets {@link MockWebAppServlet#mockWebAppConfig}.
         *
         * @param fileChangeEvent the event to handle.
         */
        public void fileDeleted(FileChangeEvent fileChangeEvent) {
            getServletContext().log("Config file deleted");
            mockWebAppConfig = null;
        }

        /**
         * Handle a file change event.
         * <p/>
         * Updates {@link MockWebAppServlet#mockWebAppConfig}.
         *
         * @param fileChangeEvent the event to handle.
         */
        public void fileChanged(FileChangeEvent fileChangeEvent) {
            try {
                final File configFile = new File(fileChangeEvent.getFile().getURL().getFile());
                mockWebAppConfig = MockWebAppConfig.Parser.parseConfig(configFile);
                getServletContext().log("Updated config from " + configFile.getPath());
            } catch (Exception e) {
                getServletContext().log("Failed updating config", e);
            }
        }
    }
}
