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
package net.nicoulaj.benchmark.mockwebapp.config.when;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * A {@link net.nicoulaj.benchmark.mockwebapp.config.when.RemoteAddressCondition} matches requests against the remote IP address.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteAddressCondition implements WhenStatement {

    /**
     * The IP address to match requests URL against.
     */
    @XmlValue
    public String address;

    /**
     * Assert this {@link RemoteAddressCondition} is valid.
     * <p/>
     * Checks everything that cannot be enforced through the XML schema.
     *
     * @throws Throwable if an element of the {@link RemoteAddressCondition} is invalid.
     */
    public void validate() throws Throwable {
        assert address != null && address.length() > 0 : "The remote address must be specified";
    }

    /**
     * Test whether the given {@link javax.servlet.http.HttpServletRequest} matches this statement.
     *
     * @param req the {@link javax.servlet.http.HttpServletRequest} to process.
     * @return true if the request remote IP address matches {@link #address}.
     */
    public boolean matches(HttpServletRequest req) {
        return req.getRemoteAddr().equals(address);
    }
}
