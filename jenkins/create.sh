#!/bin/sh

docker build -t jenkins_aws .

docker run --name jenkins_local -v jenkins_home:/var/jenkins_home -p 9000:8080 -p 50000:50000 jenkins_aws

sh start.sh