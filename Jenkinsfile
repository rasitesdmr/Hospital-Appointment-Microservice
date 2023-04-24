pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'rasitesdmr', url: 'https://github.com/rasitesdmr/SpringBoot-Microservice-ApiGateway-Jwt.git']])
            }
        }

        stage('Build Maven') {
            agent {
                docker {
                    image 'maven:3.8.5-openjdk-17'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                dir('api-gateway') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('appointment-service') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('client') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('eureka-server') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('excel-service') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('hospital-service') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('jwt-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage("Docker Build and Push Images"){
            steps{
                script{
                    withCredentials([usernameColonPassword(credentialsId: 'rasitesdmr1486', variable: 'rasitesdmr1486')]) {
                        def packageNames = ['api-gateway', 'appointment-service', 'client','eureka-server','excel-service','hospital-service','jwt-service']
                        packageNames.each { packageName ->
                            def imageName = "rasitesdmr1486/rabyd-${packageName}:latest"
                            dir("${packageName}") {
                                sh "docker build -t ${imageName} ."
                            }
                            withDockerRegistry(credentialsId: 'rasitesdmr1486', toolName: 'rasitesdmr1486', url: "") {
                                sh "docker tag ${imageName} ${imageName}"
                                sh "docker push ${imageName}"
                            }
                        }
                    }
                }
            }
        }
        stage("Docker Compose"){
           steps{
                 sh 'docker-compose pull'
                 sh 'docker-compose up -d'
                 sh 'docker rmi $(docker images -f "dangling=true" -q)'
           }
        }

    }
}
