#!/bin/bash
#Garder java 8 tant que Jenkins n'est pas compatible plus haut
export JAVA_HOME="/home/alex/dev/sdkman/candidates/java/8u161-oracle"
export MAVEN_HOME="/home/alex/dev/sdkman/candidates/maven/current"
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
export JENKINS_HOME="/home/alex/dev/jenkins/home/"
java -jar jenkins.war
