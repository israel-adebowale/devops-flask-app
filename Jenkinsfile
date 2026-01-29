pipeline {
    agent any

    environment {
        AWS_REGION = 'eu-west-2'
        ECR_REPO_NAME = 'devops-flask-demo'
        IMAGE_TAG = "${GIT_COMMIT}"
        IMAGE_TAG_LATEST = "latest"
        AWS_ACCOUNT_ID = '654654168319'
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
                    // Log in to AWS ECR using AWS CLI
                    withAWS(credentials: 'jenkins_aws_key_ID') {
                        sh """
                            aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_LOGIN_URL}
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh """
                        docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG} .
                        docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG}
                        docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG_LATEST}
                    """
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
                    // Push Docker image to ECR
                    sh """
                        docker push ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG}
                        docker push ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG_LATEST}
                    """
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Optionally clean up local Docker images to save space
                    sh """
                        docker rmi ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG}
                        docker rmi ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG_LATEST}
                    """
                }
            }
        }
    }

    post {
        always {
            // Cleanup or any final actions
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
