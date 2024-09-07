pipeline {
    agent {
        kubernetes {
            yaml '''
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: docker
                image: docker:27.2.0-alpine3.20
                command:
                - cat
                tty: true
                volumeMounts:
                - name: docker-socket
                  mountPath: "/var/run/docker.sock"
              volumes:
              - name: docker-socket
                hostPath:
                  path: "/var/run/docker.sock"
            '''
        }
    }
    environment {
        DOCKER_HUB_REPO = 'henhen7/be08-4th-2team' // -> ${도커허브 아이디}/{레포지토리 이름}
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}" // Jenkins에서 자동으로 생성
    }
    stages {
        // GitHub에서 소스 코드 체크아웃
        stage('Checkout Code from GitHub') {
            steps {
                git branch: 'jenkins-back',
                url: 'https://github.com/beyond-sw-camp/be08-4th-2team.git',
                credentialsId: 'be08-4th-2team-jenkins'
            }
        }   
        stage('Install Dependencies') {
            steps {
                dir('frontend') {
                    sh "npm install"
                }
            }
        }
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh "npm run build"
                }
            }
        }
        stage('Docker Image Build & Push') {
            steps {
                container('docker'){
                    script {
                        echo "DOCKER_IMAGE_TAG: $DOCKER_IMAGE_TAG"
                        sh 'docker logout'

                        withCredentials([usernamePassword(credentialsId: 'dockerhub_access', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD' )]) {
                            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        }

                        sh 'docker build --no-cache -t $DOCKER_HUB_REPO:$DOCKER_IMAGE_TAG ./'
                        sh 'docker image inspect $DOCKER_HUB_REPO:$DOCKER_IMAGE_TAG'
                        sh 'docker push $DOCKER_HUB_REPO:$DOCKER_IMAGE_TAG'

                        sh 'docker logout'
                    }
                }
            }
        }
    } 
    post {
        success {
            echo 'Frontend application successfully built and Docker image pushed!'
        }
        failure {
            echo 'Failed to build or push Docker image.'
        }
    }
}