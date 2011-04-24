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
import net.nicoulaj.benchmark.mockwebapp.config.when.RemoteAddressCondition;
import net.nicoulaj.benchmark.mockwebapp.test.AbstractMockWebAppTest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link net.nicoulaj.benchmark.mockwebapp.config.when.RemoteAddressCondition}.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public class RemoteAddressConditionTest extends AbstractMockWebAppTest {

    /**
     * Assert a request with a right remote address is matched.
     *
     * @throws java.io.IOException should never happen.
     */
    @Test
    public void rightRemoteAddressShouldMatch() throws IOException {
        final HttpServletRequest req = getRequest(new GetMethodWebRequest("http://localhost/test"));
        final RemoteAddressCondition stmt = new RemoteAddressCondition();
        stmt.address = req.getRemoteAddr();
        assertTrue(stmt.matches(req), "Request with remote address '" + stmt.address + "' should have been matched");
    }

    /**
     * Assert a request with a wrong remote address is not matched.
     *
     * @throws java.io.IOException should never happen.
     */
    @Test
    public void wrongRemoteAddressShouldNotMatch() throws IOException {
        final RemoteAddressCondition stmt = new RemoteAddressCondition();
        stmt.address = "209.85.227.99"; // This is a Google DNS server address
        assertFalse(stmt.matches(getRequest(new GetMethodWebRequest("http://localhost/test"))), "Request with remote address '" + stmt.address + "' should not have been matched");
    }
}
