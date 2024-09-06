pipeline {
    agent any

    stages {
        stage('Backend Build') {
            steps {
                dir('Backend') {
                    sh './gradlew build'
                }
            }
        }
        stage('Frontend Build') {
            steps {
                dir('Frontend/HangUpT') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }
    }
}
