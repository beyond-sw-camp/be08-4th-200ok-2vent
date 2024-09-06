pipeline {
    agent {
        kubernetes {
            yaml '''
            apiVersion: v1
            kind: Pod
            metadata:
              namespace: jenkins  # 네임스페이스 설정
            spec:
              serviceAccountName: jenkins  # 서비스 계정 설정
              containers:
              - name: gradle
                image: gradle:7.0-jdk11
                command:
                - cat
                tty: true
              - name: node
                image: node:14-alpine
                command:
                - cat
                tty: true
              - name: docker
                image: docker:20.10.7-dind  # Docker-in-Docker 이미지 사용
                securityContext:
                  privileged: true  # DinD는 권한이 필요
                env:
                - name: DOCKER_TLS_CERTDIR
                  value: ""  # Docker TLS 비활성화
                volumeMounts:
                - name: docker-graph-storage
                  mountPath: /var/lib/docker
              volumes:
              - name: docker-graph-storage
                emptyDir: {}
            '''
        }
    }

    stages {
        stage('Backend Build') {
            steps {
                container('gradle') {
                    dir('Backend') {
                        sh './gradlew build'
                    }
                }
            }
        }
        stage('Frontend Build') {
            steps {
                container('node') {
                    dir('Frontend/HangUpT') {
                        sh 'npm install'
                        sh 'npm run build'
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                container('docker') {  // Docker 컨테이너 사용
                    script {
                        withCredentials([string(credentialsId: 'kubernetes-token2', variable: 'DOCKER_TOKEN')]) {
                            sh '''
                            echo "$DOCKER_TOKEN" | docker login -u <username> --password-stdin
                            docker build -t my-docker-image:${env.BUILD_NUMBER} .
                            '''
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Application successfully built and Docker image created!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
