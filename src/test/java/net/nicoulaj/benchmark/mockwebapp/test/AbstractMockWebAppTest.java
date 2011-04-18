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
package net.nicoulaj.benchmark.mockwebapp.test;

import com.meterware.httpunit.WebRequest;
import com.meterware.servletunit.ServletRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Base class for Mock web app tests.
 * <p/>
 * Contains constants, common purpose data providers, and convenience methods for servlets emulation.
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @since 1.0.0
 */
public abstract class AbstractMockWebAppTest {

    /**
     * The path to the test resources directory.
     */
    public static final String TEST_RESOURCES_DIR = "src/test/resources/net/nicoulaj/benchmark/mockwebapp/test";

    /**
     * The path to the test configs directory.
     */
    public static final String TEST_CONFIGS_DIR = TEST_RESOURCES_DIR + "/configs";

    /**
     * The path to the valid test configs directory.
     */
    public static final String VALID_TEST_CONFIGS_DIR = TEST_CONFIGS_DIR + "/valid";

    /**
     * The path to the invalid test configs directory.
     */
    public static final String INVALID_TEST_CONFIGS_DIR = TEST_CONFIGS_DIR + "/invalid";

    /**
     * The {@link ServletRunner} used by test methods.
     */
    protected ServletRunner servletRunner;

    /**
     * Initialize {@link #servletRunner}.
     */
    @BeforeMethod
    public void initServletRunner() {
        servletRunner = new ServletRunner();
    }

    /**
     * Dispose {@link #servletRunner}.
     */
    @AfterMethod
    public void disposeServletRunnner() {
        servletRunner.shutDown();
        servletRunner = null;
    }

    /**
     * Build an {@link HttpServletRequest}.
     *
     * @param webRequest the {@link WebRequest} to use for building the {@link HttpServletRequest}.
     * @return an instance of {@link HttpServletRequest}.
     * @throws java.io.IOException if the request could not be build.
     */
    protected HttpServletRequest getRequest(WebRequest webRequest) throws IOException {
        return servletRunner.newClient().newInvocation(webRequest).getRequest();
    }

    /**
     * Build an {@link HttpServletResponse}.
     *
     * @param webRequest the {@link WebRequest} to use for building the {@link HttpServletResponse}.
     * @return an instance of {@link HttpServletResponse}.
     * @throws java.io.IOException if the request could not be build.
     */
    protected HttpServletResponse getResponse(WebRequest webRequest) throws IOException {
        return servletRunner.newClient().newInvocation(webRequest).getResponse();
    }

    /**
     * Provide a list of valid config files.
     * <p/>
     * Can be used by tests method with a (File validConfigFile) signature.
     *
     * @return an {@link Iterator} of valid config files as single argument.
     */
    @DataProvider
    public Iterator<Object[]> validConfigFilesDataProvider() {
        final List<Object[]> params = new ArrayList<Object[]>();
        for (File file : new File(VALID_TEST_CONFIGS_DIR).listFiles()) params.add(new Object[]{file});
        return params.iterator();
    }

    /**
     * Provide a list of invalid config files.
     * <p/>
     * Can be used by tests method with a (File invalidConfigFile) signature.
     *
     * @return an {@link Iterator} of invalid config files as single argument.
     */
    @DataProvider
    public Iterator<Object[]> invalidConfigFilesDataProvider() {
        final List<Object[]> params = new ArrayList<Object[]>();
        for (File file : new File(INVALID_TEST_CONFIGS_DIR).listFiles()) params.add(new Object[]{file});
        return params.iterator();
    }
}
