pipeline {
    agent {
        kubernetes {
            yaml '''
            apiVersion: v1
            kind: Pod
            spec:
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
    }
}
