# Analysis

## Handling sensitive data

Currently sensitive information, such as passwords, server IP addresses, ... are hard-coded and stored in plain text. If sensitive data must be saved, it must be encrypted first. The best option would be to use a tool for securely managing secrets and encrypting, for example, HashiCorp Vault.

## Automation

Artefact building and method of deployment are done manually as described in [README.md](README.md) file. The whole process (configuration, software provisioning and application deployment) can be fully automated. To solve the problem of environment drifting in release pipeline Infrastructure as Code (IaC) approach should be used. Continuous configuration automation (CCA) tools can be thought of as an extension of traditional IaC frameworks. Notable CCA tools: Ansible, SaltStack, Terraform.

## Database

For simplicity, PostgreSQL database service is currently deployed without persistent storage (Ephemeral) and, therefore, is not production ready. Any data stored will be lost upon pod destruction. Data persistence on Kubernetes is achieved with Persistent Volumes which provide a plugin model for storage in Kubernetes where how storage is provided is completed abstracted from how it is consumed. For production, PostgreSQL replicated database service with persistent storage (highly available with automated failover and backup) should be used.

## Pipeline

Current GitlabCI and Jenkins pipeline streamline the very simplified process of build, tag and release for only one environment - TEST. The production TEST stages should include

- Test and Analysis (running unit tests and code quality analysis for using SonarQube or equivalent)
- Deploy (As soon as the latest Docker image is built and pushed to Docker registry a new deployment based on this most recent image is rolled out on the Test environment. This stage will wait until the deployment is fully rolled out and ready.)
- (Optional) Smoke Tests and Module Integration Test (For some implementations it might be useful to implement smoke test and module integration tests. These tests can be executed after the successful rollout of a new deployment.)
- Tag Docker Image (After the successful rollout (and optional smoke and module integration tests), the image is tagged with the current build version.)
- Create GIT Release Tag (Finally, the current GIT commit is tagged with the current image build version.)

The next steps can include transporting immutable images across the various stages (INT, PROD) or even across the different clusters.

## Software Versioning

Versioning is essential in application development. It must be possible to relate every deployment to one unique SCM commit to:

- Verify that the correct version with all its desired features is deployed
- Reproduce errors or problems by analysing the code of the exact SCM commit
- Rolling out one specific (stable) version of the application to other environments

To keep it clear and straightforward semantic versioning is used which is also used for versioning the Libraries in software development. For Continuous microservices release cycles, on the other hand, rely heavily on these time-based parameters due to their much shorter lifetime and could almost renounce semantic versioning entirely. Though it is recommended to keep a semantic version for microservices to indicate major and minor releases and track microservice development from a top view.

Example: 20170717.111749-master-1.2.3

Additional tagging should also be applied after successful rollouts to INT / PROD in GIT.
