# github-checker

# Directory structure

- `jenkins` - holds all stuff connected with CI/CD (jenkins localhost)
- `infra` - it has swagger api + cloudformation definition 
  - cfn should be split into the stacks representing layers like, networking, balancing, application
- `app` - the code of the challenge
  - to run immediately:
    - `mvn clean package && java -jar target/github-checker-0.0.1-SNAPSHOT.jar --spring.webflux.base-path=/test --github.key=<your github key>`
  - github key can be also placed in the application.properties
  - to test it:
    - `curl -v http://localhost:8080/test/user/mslosarz/repositories`

# Ops setup
1. Navigate to `jenkins/Readme.md` and perform all described steps
2. Run pipeline
##3. Setup your github key in the proper aws secret (<project-name>-github-key)
   ###1. The tasks won't start as the secret version will be missing
4. call via API GW