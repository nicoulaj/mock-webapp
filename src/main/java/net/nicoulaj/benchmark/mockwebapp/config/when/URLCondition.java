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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import java.util.regex.Pattern;

/**
 * A {@link URLCondition} matches requests against their URL.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class URLCondition implements WhenStatement {

    /**
     * The Java-style pattern to match requests URL against.
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
     * @return true if the request URL matches {@link #pattern}.
     */
    public boolean matches(HttpServletRequest req) {
        if (compiledPattern == null) compiledPattern = Pattern.compile(pattern);
        return compiledPattern.matcher(req.getRequestURI()).matches();
    }
}
