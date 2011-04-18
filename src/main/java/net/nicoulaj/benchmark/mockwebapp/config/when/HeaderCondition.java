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
import javax.xml.bind.annotation.*;
import java.util.regex.Pattern;

/**
 * A {@link HeaderCondition} matches requests against a specified
 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5.3">HTTP header</a> presence or content.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HeaderCondition implements WhenStatement {

    /**
     * The name of the HTTP request header to check.
     */
    @XmlAttribute
    public String name;

    /**
     * The pattern to match the header content against.
     * <p/>
     * Can be null.
     *
     * @see #matches(javax.servlet.http.HttpServletRequest)
     */
    @XmlValue
    public String pattern;

    /**
     * The compiled {@link #pattern}.
     */
    @XmlTransient
    protected Pattern compiledPattern;

    /**
     * Test whether the given {@link HttpServletRequest} matches this statement.
     *
     * @param req the {@link HttpServletRequest} to process.
     * @return true if:
     *         <ul>
     *         <li>No {@link #pattern} was given and the header is present.
     *         <li>A {@link #pattern} was given and the header is present and matches the pattern.
     *         </ul>
     */
    public boolean matches(HttpServletRequest req) {
        final String headerValue = req.getHeader(name);
        if (headerValue != null) {
            if (pattern != null) {
                if (compiledPattern == null) compiledPattern = Pattern.compile(pattern);
                return compiledPattern.matcher(headerValue).matches();
            } else {
                return true;
            }
        }
        return false;
    }
}
