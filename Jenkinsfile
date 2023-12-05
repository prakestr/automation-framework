pipeline {
    agent any

    environment {
        CONTAINER_ID = ""
    }

    stages {
        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    CONTAINER_ID = bat(script: 'docker run -d -p 4444:4444 selenium/standalone-chrome:latest', returnStdout: true).trim()
                    echo "Started Selenium container with ID: ${CONTAINER_ID}"
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    // Run tests and ignore failures to allow the pipeline to continue
                    def mvnResult = bat(script: 'mvn clean test -Dmaven.test.failure.ignore=true', returnStatus: true)
                    // Set the build result based on the outcome of the Maven command
                    if (mvnResult == 0) {
                        currentBuild.result = 'SUCCESS'
                    } else {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }


        stage('Prepare Allure Results') {
            steps {
                bat 'if exist allure-results move allure-results target'
            }
        }

        stage('Generate and Publish Reports') {
            steps {
                bat 'mvn allure:report'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site/allure-maven-plugin',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report',
                    reportTitles: 'The Allure Report'
                ])
            }
        }
    }

    post {
        always {
            script {
                if (CONTAINER_ID) {
                    bat(script: "docker stop ${CONTAINER_ID}", returnStatus: true)
                    bat(script: "docker rm ${CONTAINER_ID}", returnStatus: true)
                }
            }
        }
    }
}
