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
package net.nicoulaj.benchmark.mockwebapp.config;

import net.nicoulaj.benchmark.mockwebapp.config.then.DelayAction;
import net.nicoulaj.benchmark.mockwebapp.config.then.RandomDelayAction;
import net.nicoulaj.benchmark.mockwebapp.config.then.ThenStatement;
import net.nicoulaj.benchmark.mockwebapp.config.when.HeaderCondition;
import net.nicoulaj.benchmark.mockwebapp.config.when.MethodCondition;
import net.nicoulaj.benchmark.mockwebapp.config.when.URLCondition;
import net.nicoulaj.benchmark.mockwebapp.config.when.WhenStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A {@link Mapping} is:
 * <ul>
 * <li>a set of {@link WhenStatement}s defining what requests should be matched</li>
 * <li>a set of {@link ThenStatement}s defining how matching requests should be processed</li>
 * </ul>
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Mapping {

    /**
     * The list of {@link WhenStatement}s defining what requests should be matched.
     *
     * @see net.nicoulaj.benchmark.mockwebapp.config.when
     */
    @XmlElementWrapper(name = "when")
    @XmlElements({
                         @XmlElement(name = "url", type = URLCondition.class),
                         @XmlElement(name = "header", type = HeaderCondition.class),
                         @XmlElement(name = "method", type = MethodCondition.class)
                 })
    public List<WhenStatement> whenStatements;

    /**
     * The list of {@link ThenStatement}s defining how matching requests should be processed.
     *
     * @see net.nicoulaj.benchmark.mockwebapp.config.then
     */
    @XmlElementWrapper(name = "then")
    @XmlElements({
                         @XmlElement(name = "delay", type = DelayAction.class),
                         @XmlElement(name = "random-delay", type = RandomDelayAction.class)
                 })
    public List<ThenStatement> thenStatements;

    /**
     * Test whether the given {@link HttpServletRequest} matches this mapping.
     *
     * @param req the {@link HttpServletRequest} to test.
     * @return true if if the request matches all {@link WhenStatement}s in {@link #whenStatements}.
     */
    public boolean matches(HttpServletRequest req) {
        for (WhenStatement matcher : whenStatements) if (!matcher.matches(req)) return false;
        return true;
    }

    /**
     * Process the given request.
     * <p/>
     * Calls {@link ThenStatement#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)} for
     * each {@link ThenStatement} in {@link #thenStatements}.
     *
     * @param req  the {@link HttpServletRequest} to process.
     * @param resp the {@link HttpServletResponse} to use.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        for (ThenStatement processor : thenStatements) processor.process(req, resp);
    }
}
