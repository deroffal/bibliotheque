#!/bin/bash
pid=`ps aux | grep 'jenkins.war' | awk '{print $2}'`
kill -9 $pid
