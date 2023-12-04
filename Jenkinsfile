pipeline {
    agent any

    environment {
        // Define the environment variable that will hold the container ID
        CONTAINER_ID = ""
    }

    stages {
        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    // Start the Selenium Standalone Chrome container and capture the container ID
                    CONTAINER_ID = bat(script: 'docker run -d -p 4444:4444 selenium/standalone-chrome:latest', returnStdout: true).trim()
                    echo "Started Selenium container with ID: ${CONTAINER_ID}"
                }
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
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
                publishHTML(target: [
                    reportDir: 'target/site/allure-maven-plugin',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report'
                ])
            }
        }
    }

     post {
            always {
                script {
                    // Use the captured container ID to stop and remove the container
                    if (CONTAINER_ID) {
                        bat(script: "docker stop ${CONTAINER_ID}", returnStatus: true)
                        bat(script: "docker rm ${CONTAINER_ID}", returnStatus: true)
                    }
        }
    }
}
