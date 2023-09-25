#!/bin/sh

DOCKER_GROUP_ID=$(getent group docker | cut -d: -f3)
echo "$DOCKER_GROUP_ID"
# sync docker group id
docker build \
  --build-arg DOCKER_GROUP_ID="$DOCKER_GROUP_ID" \
  -t jenkins_aws .

docker run --privileged --name jenkins_local \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -p 9000:8080 -p 50000:50000 \
  jenkins_aws

sh start.sh