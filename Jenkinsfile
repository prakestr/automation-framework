pipeline {
    agent any

    stages {
        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    // Start the Selenium Standalone Chrome container
                    bat 'docker run -d -p 4444:4444 selenium/standalone-chrome:latest'
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
            // Stop the Selenium Standalone Chrome container
            bat "docker stop \$(docker ps -q --filter ancestor=selenium/standalone-chrome:latest)"

            // Clean up any stopped containers and unused images/networks/volumes
            bat "docker system prune -af"
        }
    }

}
