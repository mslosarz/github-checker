FROM jenkins/jenkins:lts

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

USER root


# Install zip
RUN apt-get update \
    && apt-get install -y zip \
    && apt-get clean

RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt
# Install AWS-CLI
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install

USER jenkins