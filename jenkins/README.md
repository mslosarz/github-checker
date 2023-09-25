# Jenkins local
1. Execute script `./create.sh` - It'll create jenkins locally
2. Navigate to: `http://localhost:9000/`
3. Perform initial setup:
    - Navigate to: `Dashboard > Manage Jenkins > Tools`
    - Setup JDK 17 (`/usr/local/jdk-17.0.2/`): 
        ![jdk_setup.png](jdk_setup.png)
    - Setup Maven:
        ![maven_setup.png](mvn_setup.png)
    - Setup AWS credentials
        ![img.png](aws_creds_setup.png)
4. Project setup:
    - Create pipeline
    ![create_pipeline.png](create_pipeline.png)
    - Setup SCM
    ![scm_config.png](scm_config.png)
    - Repository URL: `https://github.com/mslosarz/github-checker.git`
    - Script Path: `app/Jenkinsfile`