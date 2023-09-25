FROM jenkins/jenkins:lts
ARG DOCKER_GROUP_ID

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

USER root
# Install docker client
RUN groupadd -g $DOCKER_GROUP_ID -f docker \
    && gpasswd -a jenkins docker \
    && echo "$DOCKER_GROUP_ID"
RUN apt-get update && curl -sSL https://get.docker.com/ | sh

# Install zip
RUN apt-get update \
    && apt-get install -y zip \
    && apt-get clean

# install plugins
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

# Install AWS-CLI
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install

#install JDK
RUN cd /usr/local \
    && curl "https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-x64_bin.tar.gz" -o jdk17.tar.gz \
    && tar -zxvf jdk17.tar.gz

USER jenkins