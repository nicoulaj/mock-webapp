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
package net.nicoulaj.benchmark.mockwebapp.test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import net.nicoulaj.benchmark.mockwebapp.MockWebAppServlet;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import static org.testng.Assert.assertNotNull;


/**
 * Tests for {@link MockWebAppServlet}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class ServletTest extends AbstractMockWebAppTest {

    /**
     * Assert starting the servlet without positioning the configuration file parameter throws
     * an exception.
     *
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test(expectedExceptions = HttpInternalErrorException.class,
          expectedExceptionsMessageRegExp = ".*No mock web app config file defined.*")
    public void noConfigDeclaredShouldThrowException() throws IOException, SAXException {
        servletRunner.registerServlet("", MockWebAppServlet.class.getName());
        servletRunner.newClient().getResponse(new GetMethodWebRequest("http://localhost/"));
    }

    /**
     * Assert starting the servlet with a valid configuration file does not throw any exception.
     *
     * @param file the config file to use.
     * @throws java.io.IOException      should never happen.
     * @throws org.xml.sax.SAXException should never happen.
     */
    @Test(dataProvider = "validConfigFilesDataProvider")
    public void validSetupShouldNotThrowException(File file) throws IOException, SAXException {

        final Hashtable<String, String> initParams = new Hashtable<String, String>();
        initParams.put(MockWebAppServlet.MOCK_WEB_APP_CONF_PROPERTY, file.getAbsolutePath());

        servletRunner.registerServlet("", MockWebAppServlet.class.getName(), initParams);
        final WebRequest request = new GetMethodWebRequest("http://localhost/");
        final WebResponse response = servletRunner.newClient().getResponse(request);

        assertNotNull(response, "No response received");
    }
}
