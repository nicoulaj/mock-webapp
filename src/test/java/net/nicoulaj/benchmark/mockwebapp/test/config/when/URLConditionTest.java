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
import net.nicoulaj.benchmark.mockwebapp.config.when.URLCondition;
import net.nicoulaj.benchmark.mockwebapp.test.AbstractMockWebAppTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link URLCondition}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class URLConditionTest extends AbstractMockWebAppTest {

    /**
     * Assert a request with a right URL is matched.
     *
     * @throws IOException should never happen.
     */
    @Test
    public void rightURLShouldMatch() throws IOException {
        final URLCondition stmt = new URLCondition();
        stmt.pattern = "/.*";
        assertTrue(stmt.matches(getRequest(new GetMethodWebRequest("http://localhost/test"))));
    }

    /**
     * Assert a request with a wrong URL is not matched.
     *
     * @throws IOException should never happen.
     */
    @Test
    public void wrongURLShouldNotMatch() throws IOException {
        final URLCondition stmt = new URLCondition();
        stmt.pattern = "/context/.*";
        assertFalse(stmt.matches(getRequest(new GetMethodWebRequest("http://localhost/test"))));
    }

    /**
     * Assert validating a {@link URLCondition} without URL pattern throws an error.
     *
     * @throws Throwable should always happen.
     */
    @Test(expectedExceptions = Throwable.class,
          expectedExceptionsMessageRegExp = "The URL pattern must be specified")
    public void patternShouldBeDefined() throws Throwable {
        new URLCondition().validate();
    }
}
