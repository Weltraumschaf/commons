# Commons Config

This module contains utilities and helpers for configurations.

## ReloadingPropertiesConfiguration

This  properties  based  configuration  reloads  automatically  if  thefile  has
changed. So you need not to  restart an application due to configuration change.
The implementation is bsed on this [blog article][origin-idea].

Example:

    final class MyAppConfig extends ReloadingPropertiesConfiguration {

        public MyAppConfig(final Path file) {
            super(file);
        }

        public String getServiceUrl() {
            return getRequiredProperty("service.url");
        }

        public boolean getShouldStartSlow() {
            return getFlag("start-slow", false);
        }

        public int getHttpPort(final int defaultPort) {
            return getInteger("myapp.http.port", defaultPort);
        }
    }

[origin-idea]:  http://www.javacodegeeks.com/2014/10/dead-simple-configuration.html
