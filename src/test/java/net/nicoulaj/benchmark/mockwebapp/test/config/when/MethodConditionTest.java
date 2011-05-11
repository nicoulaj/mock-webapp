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
package net.nicoulaj.benchmark.mockwebapp.test.config.when;

import com.meterware.httpunit.*;
import net.nicoulaj.benchmark.mockwebapp.config.when.MethodCondition;
import net.nicoulaj.benchmark.mockwebapp.test.AbstractMockWebAppTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.nicoulaj.benchmark.mockwebapp.config.when.MethodCondition.HTTPMethod;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link MethodCondition}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class MethodConditionTest extends AbstractMockWebAppTest {

    /**
     * Provide a list of matching (HTTP method, web request).
     * <p/>
     * Can be used by tests method with a (MethodCondition.HTTPMethod method, WebRequest request) signature.
     *
     * @return an {@link Iterator} of tuples.
     */
    @DataProvider
    public Iterator<Object[]> matchingMethodsRequestsDataProvider() {
        final List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{HTTPMethod.GET, new GetMethodWebRequest("http://localhost/test")});
        params.add(new Object[]{HTTPMethod.POST, new PostMethodWebRequest("http://localhost/test")});
        params.add(new Object[]{HTTPMethod.PUT, new PutMethodWebRequest("http://localhost/test", new ByteArrayInputStream("test".getBytes()), "text/plain")});
        params.add(new Object[]{HTTPMethod.HEAD, new HeadMethodWebRequest("http://localhost/test")});
        return params.iterator();
    }

    /**
     * Assert that a request with thr right method is matched.
     *
     * @param method  the HTTP method to match against.
     * @param request the request to use for the test.
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test(dataProvider = "matchingMethodsRequestsDataProvider")
    public void rightMethodShouldMatch(final MethodCondition.HTTPMethod method, final WebRequest request) throws IOException, SAXException {
        final MethodCondition methodCondition = new MethodCondition();
        methodCondition.method = method;
        assertTrue(methodCondition.matches(getRequest(request)), "A '" + methodCondition.method + "' condition should match a '" + request.getMethod() + "' request");
    }

    /**
     * Provide a list of non-matching (HTTP method, web request).
     * <p/>
     * Can be used by tests method with a (MethodCondition.HTTPMethod method, WebRequest request) signature.
     *
     * @return an {@link Iterator} of tuples.
     */
    @DataProvider
    public Iterator<Object[]> nonMatchingMethodsRequestsDataProvider() {
        final WebRequest getRequest = new GetMethodWebRequest("http://localhost/test");
        final WebRequest postRequest = new PostMethodWebRequest("http://localhost/test");
        final WebRequest putRequest = new PutMethodWebRequest("http://localhost/test", new ByteArrayInputStream("test".getBytes()), "text/plain");
        final WebRequest headRequest = new HeadMethodWebRequest("http://localhost/test");
        final List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{HTTPMethod.GET, postRequest});
        params.add(new Object[]{HTTPMethod.GET, putRequest});
        params.add(new Object[]{HTTPMethod.GET, headRequest});
        params.add(new Object[]{HTTPMethod.POST, getRequest});
        params.add(new Object[]{HTTPMethod.POST, putRequest});
        params.add(new Object[]{HTTPMethod.POST, headRequest});
        params.add(new Object[]{HTTPMethod.PUT, getRequest});
        params.add(new Object[]{HTTPMethod.PUT, postRequest});
        params.add(new Object[]{HTTPMethod.PUT, headRequest});
        params.add(new Object[]{HTTPMethod.HEAD, getRequest});
        params.add(new Object[]{HTTPMethod.HEAD, postRequest});
        params.add(new Object[]{HTTPMethod.HEAD, putRequest});
        return params.iterator();
    }

    /**
     * Assert that a request with thr wrong method is not matched.
     *
     * @param method  the HTTP method to match against.
     * @param request the request to use for the test.
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test(dataProvider = "nonMatchingMethodsRequestsDataProvider")
    public void wrongMethodShouldNotMatch(final MethodCondition.HTTPMethod method, final WebRequest request) throws IOException, SAXException {
        final MethodCondition methodCondition = new MethodCondition();
        methodCondition.method = method;
        assertFalse(methodCondition.matches(getRequest(request)), "A '" + methodCondition.method + "' condition should not match a '" + request.getMethod() + "' request");
    }

    /**
     * Assert validating a {@link MethodCondition} without HTTP method name throws an error.
     *
     * @throws Throwable should always happen.
     */
    @Test(expectedExceptions = Throwable.class,
          expectedExceptionsMessageRegExp = "The HTTP method is missing")
    public void methodShouldBeDefined() throws Throwable {
        new MethodCondition().validate();
    }
}
