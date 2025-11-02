ARG BASE_IMG=''
ARG BASE_TAG=''

FROM ${BASE_IMG}:${BASE_TAG}

ARG CATALINA_BASE=${CATALINA_BASE:'/usr/local/tomcat'}
ARG PROMETHEUS_PLUGIN_HOME='/opt/prometheus/jmx_prometheus_javaagent'

ADD ./setup.prometheus.exporter.sh .