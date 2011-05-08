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
 * A {@link MethodCondition} matches requests against their <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html">method</a>.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MethodCondition implements WhenStatement {

    /**
     * The supported <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9">HTTP methods</a>.
     */
    public static enum HTTPMethod {

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2">OPTIONS HTTP method</a>.
         */
        OPTIONS,

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.3">GET HTTP method</a>.
         */
        GET,

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.4">HEAD HTTP method</a>.
         */
        HEAD,

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5">POST HTTP method</a>.
         */
        POST,

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.6">PUT HTTP method</a>.
         */
        PUT,

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.7">DELETE HTTP method</a>.
         */
        DELETE,

        /**
         * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.8">TRACE HTTP method</a>.
         */
        TRACE
    }

    /**
     * The {@link HTTPMethod} to check.
     */
    @XmlValue
    public HTTPMethod method;

    /**
     * Assert this {@link MethodCondition} is valid.
     * <p/>
     * Checks everything that cannot be enforced through the XML schema.
     *
     * @throws Throwable if an element of the {@link MethodCondition} is invalid.
     */
    public void validate() throws Throwable {
        assert method != null : "The HTTP method is missing";
    }

    /**
     * Test whether the given {@link HttpServletRequest} matches this statement.
     *
     * @param req the {@link HttpServletRequest} to process.
     * @return true if the request method is {@link #method}.
     */
    public boolean matches(HttpServletRequest req) {
        return HTTPMethod.valueOf(req.getMethod()).equals(method);
    }
}
