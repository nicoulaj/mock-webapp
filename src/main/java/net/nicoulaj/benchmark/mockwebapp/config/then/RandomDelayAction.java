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
import javax.xml.bind.annotation.XmlElement;
import java.util.Random;

/**
 * A {@link RandomDelayAction} introduces a random amount of latency to serve the response.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RandomDelayAction implements ThenStatement {

    /**
     * The {@link Random} number generator.
     */
    protected static final Random RANDOM = new Random();

    /**
     * The latency lower bound (ms).
     */
    @XmlElement(required = true, nillable = false)
    public int min;

    /**
     * The latency upper bound (ms).
     */
    @XmlElement(required = true, nillable = false)
    public int max;

    /**
     * Assert this {@link RandomDelayAction} is valid.
     * <p/>
     * Checks everything that cannot be enforced through the XML schema.
     *
     * @throws Throwable if an element of the {@link RandomDelayAction} is invalid.
     */
    public void validate() throws Throwable {
        assert min > 0 : "The delay lower bound cannot be negative";
        assert max > 0 : "The delay upper bound cannot be negative";
        assert max >= min : "The delay upper bound must be superior to the delay lower bound";
    }

    /**
     * Process an HTTP request/response.
     * <p/>
     * Sleeps for an amount of time in [{@link #min},{@link #max}].
     *
     * @param req  the {@link HttpServletRequest} to process.
     * @param resp the {@link HttpServletResponse} to use.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        final int time = min + RANDOM.nextInt(max - min);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            req.getSession().getServletContext().log("Failed sleeping for " + time + " milliseconds", e);
        }
    }
}
