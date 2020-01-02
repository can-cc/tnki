pipeline {
    agent {
        docker {
            image 'gradle:jdk8'
        }
    }
    triggers {
        pollSCM('*/1 * * * *')
    }
     environment {
        TNKI_MYSQL_HOST     = credentials('jenkins-tnki-mysql-host')
        TNKI_MYSQL_USERNAME = credentials('jenkins-tnki-mysql-username')
        TNKI_MYSQL_PASSWORD = credentials('jenkins-tnki-mysql-password')
    }
    stages {
        stage('test') {
            steps {
                sh './gradlew test'
            }
        }
    }
    post {
        always {
            rocketSend currentBuild.currentResult
        }
    }
}