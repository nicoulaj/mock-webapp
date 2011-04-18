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

import com.meterware.httpunit.GetMethodWebRequest;
import net.nicoulaj.benchmark.mockwebapp.config.when.HeaderCondition;
import net.nicoulaj.benchmark.mockwebapp.test.AbstractMockWebAppTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link HeaderCondition}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class HeaderConditionTest extends AbstractMockWebAppTest {

    /**
     * Assert a request with the right header and the right pattern is matched.
     *
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test
    public void rightHeaderWithRightPatternShouldMatch() throws IOException, SAXException {

        final HeaderCondition stmt = new HeaderCondition();
        stmt.name = "Accept";
        stmt.pattern = "text/html";

        GetMethodWebRequest webRequest = new GetMethodWebRequest("http://localhost/test");
        webRequest.setHeaderField("Accept", "text/html");
        assertTrue(stmt.matches(getRequest(webRequest)));
    }

    /**
     * Assert a request with the right header but the wrong pattern is not matched.
     *
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test
    public void rightHeaderWithWrongPatternShouldNotMatch() throws IOException, SAXException {

        final HeaderCondition stmt = new HeaderCondition();
        stmt.name = "Accept";
        stmt.pattern = "text/html";

        GetMethodWebRequest webRequest = new GetMethodWebRequest("http://localhost/test");
        webRequest.setHeaderField("Accept", "text/*");
        assertFalse(stmt.matches(getRequest(webRequest)));
    }

    /**
     * Assert a request with the right header is matched when no pattern is defined.
     *
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test
    public void rightHeaderWithNoPatternRequestShouldMatch() throws IOException, SAXException {

        final HeaderCondition stmt = new HeaderCondition();
        stmt.name = "Accept";

        GetMethodWebRequest webRequest = new GetMethodWebRequest("http://localhost/test");
        webRequest.setHeaderField("Accept", "text/html");
        assertTrue(stmt.matches(getRequest(webRequest)));
    }

    /**
     * Assert a request with the wrong header is not matched when no pattern is defined.
     *
     * @throws IOException  should never happen.
     * @throws SAXException should never happen.
     */
    @Test
    public void wrongHeaderWithNoPatternRequestShouldMatch() throws IOException, SAXException {

        final HeaderCondition stmt = new HeaderCondition();
        stmt.name = "Accept";

        GetMethodWebRequest webRequest = new GetMethodWebRequest("http://localhost/test");
        assertFalse(stmt.matches(getRequest(webRequest)));
    }
}
