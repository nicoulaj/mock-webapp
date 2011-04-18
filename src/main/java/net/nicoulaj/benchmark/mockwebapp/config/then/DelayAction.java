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
 * A {@link DelayAction} introduces a specified amount of latency to serve the response.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DelayAction implements ThenStatement {

    /**
     * The time to wait before processing each request (ms).
     */
    @XmlValue
    public int time;

    /**
     * Process an HTTP request/response.
     * <p/>
     * Sleeps for {@link #time}.
     *
     * @param req  the {@link HttpServletRequest} to process.
     * @param resp the {@link HttpServletResponse} to use.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            req.getSession().getServletContext().log("Failed sleeping for " + time + " milliseconds", e);
        }
    }
}
