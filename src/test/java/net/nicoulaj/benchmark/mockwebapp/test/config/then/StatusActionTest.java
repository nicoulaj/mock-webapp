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
package net.nicoulaj.benchmark.mockwebapp.test.config.then;

import com.meterware.httpunit.GetMethodWebRequest;
import net.nicoulaj.benchmark.mockwebapp.config.then.StatusAction;
import net.nicoulaj.benchmark.mockwebapp.test.AbstractMockWebAppTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Tests for {@link net.nicoulaj.benchmark.mockwebapp.config.then.StatusAction}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class StatusActionTest extends AbstractMockWebAppTest {

    /**
     * Provide a list of coherent HTTP status codes.
     * <p/>
     * Can be used by tests method with a (int statusCode) signature.
     *
     * @return an {@link java.util.Iterator} of HTTP status codes as single argument.
     */
    @DataProvider
    public Iterator<Object[]> statusCodesDataProvider() {
        final List<Object[]> params = new ArrayList<Object[]>();
        for (int i : new int[]{
                HttpServletResponse.SC_CONTINUE,
                HttpServletResponse.SC_SWITCHING_PROTOCOLS,
                HttpServletResponse.SC_OK,
                HttpServletResponse.SC_CREATED,
                HttpServletResponse.SC_ACCEPTED,
                HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION,
                HttpServletResponse.SC_NO_CONTENT,
                HttpServletResponse.SC_RESET_CONTENT,
                HttpServletResponse.SC_PARTIAL_CONTENT,
                HttpServletResponse.SC_MULTIPLE_CHOICES,
                HttpServletResponse.SC_MOVED_PERMANENTLY,
                HttpServletResponse.SC_MOVED_TEMPORARILY,
                HttpServletResponse.SC_FOUND,
                HttpServletResponse.SC_SEE_OTHER,
                HttpServletResponse.SC_NOT_MODIFIED,
                HttpServletResponse.SC_USE_PROXY,
                HttpServletResponse.SC_TEMPORARY_REDIRECT,
                HttpServletResponse.SC_BAD_REQUEST,
                HttpServletResponse.SC_UNAUTHORIZED,
                HttpServletResponse.SC_PAYMENT_REQUIRED,
                HttpServletResponse.SC_FORBIDDEN,
                HttpServletResponse.SC_NOT_FOUND,
                HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                HttpServletResponse.SC_NOT_ACCEPTABLE,
                HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED,
                HttpServletResponse.SC_REQUEST_TIMEOUT,
                HttpServletResponse.SC_CONFLICT,
                HttpServletResponse.SC_GONE,
                HttpServletResponse.SC_LENGTH_REQUIRED,
                HttpServletResponse.SC_PRECONDITION_FAILED,
                HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,
                HttpServletResponse.SC_REQUEST_URI_TOO_LONG,
                HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE,
                HttpServletResponse.SC_EXPECTATION_FAILED,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                HttpServletResponse.SC_NOT_IMPLEMENTED,
                HttpServletResponse.SC_BAD_GATEWAY,
                HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                HttpServletResponse.SC_GATEWAY_TIMEOUT,
                HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED
        })
            params.add(new Object[]{i});
        return params.iterator();
    }

    /**
     * Assert the HTTP status code set on HTTP responses is the one defined.
     *
     * @param statusCode the HTTP status code to use for the test.
     * @throws java.io.IOException      should never happen.
     * @throws org.xml.sax.SAXException should never happen.
     */
    @Test(dataProvider = "statusCodesDataProvider")
    public void statusCodeShouldBeRightOne(int statusCode) throws IOException, SAXException {

        final StatusAction stmt = new StatusAction();
        stmt.statusCode = statusCode;

        final GetMethodWebRequest webRequest = new GetMethodWebRequest("http://localhost/test");
        final HttpServletResponse response = getResponse(webRequest);
        stmt.process(getRequest(webRequest), response);

        // FIXME No way to get the status code, even using reflection ? WTF ?
    }
}
