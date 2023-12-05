pipeline {
    agent any

    environment {
        CONTAINER_ID = ''
    }

    stages {
        stage('Clean Up') {
            steps {
                script {
                    // Stop and remove any existing containers that might be using the 4444 port
                    bat(script: 'docker ps -q --filter "ancestor=selenium/standalone-chrome:latest" | for /f %i in (\'more\') do docker stop %i', returnStatus: true)
                    bat(script: 'docker ps -aq --filter "ancestor=selenium/standalone-chrome:latest" | for /f %i in (\'more\') do docker rm %i', returnStatus: true)
                }
            }
        }

        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    // Now we can start a new Selenium container safely
                    CONTAINER_ID = bat(script: 'docker run -d -p 4444:4444 selenium/standalone-chrome:latest', returnStdout: true).trim()
                    echo "Started Selenium container with ID: ${CONTAINER_ID}"
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    def mvnResult = bat(script: 'mvn clean test', returnStatus: true)
                    if (mvnResult != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Prepare Allure Results') {
            steps {
                bat 'if exist allure-results move allure-results target/'
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
                if (env.CONTAINER_ID) {
                    bat(script: "docker stop ${CONTAINER_ID}", returnStatus: true)
                    bat(script: "docker rm ${CONTAINER_ID}", returnStatus: true)
                }
            }
        }
    }
}
