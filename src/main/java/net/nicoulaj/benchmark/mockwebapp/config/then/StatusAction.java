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
package net.nicoulaj.benchmark.mockwebapp.config.then;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * A {@link StatusAction} sets the response HTTP status code.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusAction implements ThenStatement {

    /**
     * The <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html#sec6.1.1">HTTP status code</a>.
     */
    @XmlValue
    public int statusCode;

    /**
     * Assert this {@link StatusAction} is valid.
     * <p/>
     * Checks everything that cannot be enforced through the XML schema.
     *
     * @throws Throwable if an element of the {@link StatusAction} is invalid.
     */
    public void validate() throws Throwable {
        assert statusCode > 0 : "The HTTP status code cannot be negative";
    }

    /**
     * Process an HTTP request/response.
     * <p/>
     * Sets the response status to {@link #statusCode}
     *
     * @param req  the {@link javax.servlet.http.HttpServletRequest} to process.
     * @param resp the {@link javax.servlet.http.HttpServletResponse} to use.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(statusCode);
    }
}
