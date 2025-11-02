#!/bin/bash

# export CATALINA_BASE=${CATALINA_BASE:'/usr/local/tomcat'}
export CATALINA_BASE='/usr/local/tomcat'

export PROMETHEUS_PLUGIN_HOME=${PROMETHEUS_PLUGIN_HOME:-'/opt/prometheus/jmx_prometheus_javaagent'}
mkdir -p $PROMETHEUS_PLUGIN_HOME



# export JMX_EXPORTER_DESIRED_VERSION=${JMX_EXPORTER_DESIRED_VERSION:-"1.0.1"}
export JMX_EXPORTER_DESIRED_VERSION="1.0.1"
export JMX_EXPORTER_DWNLD_LINK="https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/${JMX_EXPORTER_DESIRED_VERSION}/jmx_prometheus_javaagent-${JMX_EXPORTER_DESIRED_VERSION}.jar"

curl -LO "${JMX_EXPORTER_DWNLD_LINK}"
ls -alh jmx_prometheus_javaagent-${JMX_EXPORTER_DESIRED_VERSION}.jar
mv jmx_prometheus_javaagent-${JMX_EXPORTER_DESIRED_VERSION}.jar ${PROMETHEUS_PLUGIN_HOME}

mkdir -p ${CATALINA_BASE}/bin
ls -alh ${CATALINA_BASE}/bin

echo "CATALINA_OPTS=\"-javaagent:${PROMETHEUS_PLUGIN_HOME}/jmx_prometheus_javaagent-${JMX_EXPORTER_DESIRED_VERSION}.jar=8088:$PROMETHEUS_PLUGIN_HOME/config.yml\"" | tee -a ${CATALINA_BASE}/bin/setenv.sh
chmod +x ${CATALINA_BASE}/bin/setenv.sh
ls -alh ${CATALINA_BASE}/bin/setenv.sh

# https://github.com/prometheus/jmx_exporter/blob/1.1.0/examples/tomcat.yml


curl -L https://raw.githubusercontent.com/prometheus/jmx_exporter/refs/tags/${JMX_EXPORTER_DESIRED_VERSION}/example_configs/tomcat.yml | tee $PROMETHEUS_PLUGIN_HOME/config.yml


