pipeline {
    agent any

    stages {
        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    // Start the Selenium Standalone Chrome container and retrieve the container ID
                    def containerId = bat(script: 'docker run -d -p 4444:4444 selenium/standalone-chrome:latest', returnStdout: true).trim()
                    echo "Started container with ID: ${containerId}"
                    // Set the container ID as an environment variable to use in later stages
                    env.SELENIUM_CONTAINER_ID = containerId
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
                // Stop and remove the Selenium container using the stored container ID
                if (env.SELENIUM_CONTAINER_ID) {
                    bat(script: "docker stop ${env.SELENIUM_CONTAINER_ID}", returnStatus: true)
                    bat(script: "docker rm ${env.SELENIUM_CONTAINER_ID}", returnStatus: true)
                }
                // Ensure any stopped containers are cleaned up regardless of ID
                bat(script: "docker ps -a -q --filter 'ancestor=selenium/standalone-chrome:latest' | ForEach-Object -Process { docker stop $_; docker rm $_; }", returnStatus: true)
            }
        }
    }
}
