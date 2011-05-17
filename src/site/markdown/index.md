**mock web application for performance or integration testing.**

Sometimes you need to test how the architecture around your application (frontal server, SSO system, etc)
behaves in particular situations, that can be hard to reproduce.

This mock web application can easily be setup to behave as you need, through a single configuration file:

    <mock-web-app>

      <!-- Requests on /slow are served in 5 seconds -->
      <mapping>
        <when>
          <url>/slow</url>
        </when>
        <then>
          <delay>5000</delay>
        </then>
      </mapping>

      <!-- POST requests on /bug causes Internal Server Error -->
      <mapping>
        <when>
          <url>/bug</url>
          <method>POST</method>
        </when>
        <then>
          <status>500</status>
        </then>
      </mapping>

    </mock-web-app>


How to use ?
------------

1. [Download it](usage/1-download.html)
2. [Configure it](usage/2-configure.html)
3. [Deploy it](usage/3-deploy.html)
