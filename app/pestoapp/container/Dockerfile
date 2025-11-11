ARG BASE_IMG='maven'
ARG BASE_TAG='4.0.0-rc-4-eclipse-temurin-21-noble'

FROM ${BASE_IMG}:${BASE_TAG} AS app_build

RUN mkdir -p /root/.m2/
# ---
# And in the seettings.xml, the settings you need for your app's maven build
ADD container/m2.settings.xml /root/.m2/settings.xml
ADD . /opt/src
RUN ls -alh /opt/src
RUN ls -alh /root/.m2/settings.xml
RUN cd /opt/src && mvn clean package


ARG BASE_IMG='tomcat'
ARG BASE_TAG='10-jdk21-temurin-noble'

# FROM ${BASE_IMG}:${BASE_TAG} AS release
FROM tomcat:10-jdk21-temurin-noble AS release

COPY --from=app_build /opt/src/target/pestoapp.war /usr/local/tomcat/webapps/

ARG CATALINA_BASE=${CATALINA_BASE:-'/usr/local/tomcat'}
ARG PROMETHEUS_PLUGIN_HOME='/opt/prometheus/jmx_prometheus_javaagent'

ADD container/setup.prometheus.exporter.sh .

# ARG http_proxy=http://ip_addr_of_your_http_proxy:port_nb_of_your_http_proxy
# ARG https_proxy=http://ip_addr_of_your_https_proxy:port_nb_of_your_https_proxy
# ARG no_proxy=*.myacme.com,*.someinternalservice.io,localhost,internal,local,127.0.0.1

# ENV http_proxy=http://ip_addr_of_your_http_proxy:port_nb_of_your_http_proxy
# ENV https_proxy=http://ip_addr_of_your_https_proxy:port_nb_of_your_https_proxy
# ENV no_proxy=*.myacme.com,*.someinternalservice.io,localhost,internal,local,127.0.0.1


RUN chmod +x ./setup.prometheus.exporter.sh && ./setup.prometheus.exporter.sh
