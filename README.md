# Flask CI/CD Demo Application

This project is a minimal Python Flask API designed to demonstrate DevOps CI/CD practices using Docker, Jenkins, and AWS ECR.
The application itself is intentionally simple so the focus remains on containerization, pipeline automation, and image delivery.

Project Overview

The application exposes two HTTP endpoints and is containerized using Docker.
A Jenkins pipeline is responsible for building the Docker image and pushing it to Amazon Elastic Container Registry (ECR).

This project is used as a foundation for:

Docker image creation

Jenkins-based CI pipelines

AWS ECR image publishing

Kubernetes deployment in later stages