pipeline {
    agent any

    stages {
        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    // Start the Selenium Standalone Chrome container
                    docker.image('selenium/standalone-chrome:latest').withRun('-p 4444:4444') { c ->
                        // Proceed with the next stages while the container is running
                        stage('Build and Test') {
                            steps {
                                sh 'mvn clean test'
                            }
                        }

                        stage('Prepare Allure Results') {
                            steps {
                                // Ensure allure-results directory exists before moving
                                sh 'if exist allure-results (move allure-results target)'
                            }
                        }

                        stage('Generate and Publish Reports') {
                            steps {
                                sh 'mvn allure:report'
                                publishHTML(target: [
                                    reportDir: 'target/site/allure-maven-plugin',
                                    reportFiles: 'index.html',
                                    reportName: 'Allure Report'
                                ])
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up any stopped containers and unused images/networks/volumes
            sh 'docker system prune -f'
        }
    }

