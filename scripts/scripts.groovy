// scripts.groovy

def loginToECR(AWS_REGION, ECR_LOGIN_URL) {
    sh """
        aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_LOGIN_URL}
    """
}

def buildDockerImage(ECR_REPO_NAME, IMAGE_TAG, IMAGE_TAG_LATEST) {
    sh """
        docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG} .
        docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG}
        docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG_LATEST}
    """
}

def pushDockerImage(ECR_REPO_NAME, IMAGE_TAG, IMAGE_TAG_LATEST, ECR_LOGIN_URL) {
    sh """
        docker push ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG}
        docker push ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG_LATEST}
    """
}

def cleanupDockerImages(ECR_REPO_NAME, IMAGE_TAG, IMAGE_TAG_LATEST, ECR_LOGIN_URL) {
    sh """
        docker rmi ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG}
        docker rmi ${ECR_LOGIN_URL}/${ECR_REPO_NAME}:${IMAGE_TAG_LATEST}
    """
}
