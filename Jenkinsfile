pipeline {
    agent any

    environment {
        AWS_REGION = 'eu-west-2'
        ECR_REPO_NAME = 'devops-flask-demo'
        IMAGE_TAG = "${GIT_COMMIT}"
        IMAGE_TAG_LATEST = "latest"
        AWS_ACCOUNT_ID = '654654168319' // your AWS account ID
        ECR_LOGIN_URL = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Checkout the latest code
                    checkout scm
                }
            }
        }

        stage('Login to AWS ECR') {
            steps {
                script {
                    // Load external Groovy script and call the login function
                    load 'scripts/script.groovy'
                    loginToECR(AWS_REGION, ECR_LOGIN_URL)
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the external script
                    buildDockerImage(ECR_REPO_NAME, IMAGE_TAG, IMAGE_TAG_LATEST)
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
                    // Push the Docker image to ECR
                    pushDockerImage(ECR_REPO_NAME, IMAGE_TAG, IMAGE_TAG_LATEST, ECR_LOGIN_URL)
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Clean up local Docker images to save space
                    cleanupDockerImages(ECR_REPO_NAME, IMAGE_TAG, IMAGE_TAG_LATEST, ECR_LOGIN_URL)
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }

        success {
            echo 'Docker image pushed to ECR successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}
