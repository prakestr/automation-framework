pipeline {
    agent any

    stages {
            stage('Start Selenium Standalone Chrome') {
                steps {
                    script {
                        // Clean up any previous Docker containers that might be using the port
                        sh 'docker rm -f $(docker ps -qa --filter "ancestor=selenium/standalone-chrome:latest") || true'

                        // Start the Selenium Standalone Chrome container
                        sh 'docker run -d -p 4444:4444 selenium/standalone-chrome:latest'
                    }
                }
            }

        stage('Build and Test') {
            steps {
                // Replace 'sh' with 'bat' for Windows compatibility
                bat 'mvn clean test'
            }
        }

        stage('Prepare Allure Results') {
            steps {
                // Adjusted for Windows command line
                bat 'if exist allure-results move allure-results target'
            }
        }

        stage('Generate and Publish Reports') {
            steps {
                // Replace 'sh' with 'bat' for Windows compatibility
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
                // Correct syntax to stop Docker containers on Windows
                bat 'docker stop %docker ps -q --filter "ancestor=selenium/standalone-chrome:latest"%'
                bat 'docker system prune -f'
            }
    }

}
