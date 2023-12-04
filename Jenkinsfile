pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Prepare Allure Results') {
            steps {
                // Move or copy the allure-results folder to the target directory
                sh 'mv allure-results target/'
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
