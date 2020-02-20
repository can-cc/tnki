pipeline {
    agent none
    triggers {
        pollSCM('*/1 * * * *')
    }
     environment {
        TNKI_MYSQL_HOST     = credentials('jenkins-tnki-mysql-host')
        TNKI_MYSQL_USERNAME = credentials('jenkins-tnki-mysql-username')
        TNKI_MYSQL_PASSWORD = credentials('jenkins-tnki-mysql-password')
        DOCKER_REGISTER = 'fwchen'
        docker_hub_username = credentials('docker_hub_username')
        docker_hub_password = credentials('docker_hub_password')
    }
    stages {
        stage('test') {
            agent {
                docker {
                    image 'gradle:jdk8'
                }
            }
            steps {
                sh './gradlew test'
            }
        }
        stage('containerize') {
            agent {
                docker {
                    image 'docker:19.03.5'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            stages {
                stage("build docker container") {
                    steps {
                        sh 'docker build . -t $DOCKER_REGISTER/tnki:v0.0.$BUILD_NUMBER'
                    }
                }
                stage('registry Login') {
                    steps {
                        sh "echo $docker_hub_password | docker login -u $docker_hub_username --password-stdin"
                    }
                }
                stage('publish image') {
                    steps {
                        sh 'docker push $DOCKER_REGISTER/tnki:v0.0.$BUILD_NUMBER'
                        sh 'echo "$DOCKER_REGISTER/tnki:v0.0.$BUILD_NUMBER" > .artifacts'
                        archiveArtifacts(artifacts: '.artifacts')
                    }
                }
                stage('remove image') {
                    steps {
                        sh "docker image rm $DOCKER_REGISTER/jellyfish:v0.0.$BUILD_NUMBER"
                    }
                }
            }
        }
    }
    post {
        always {
            rocketSend currentBuild.currentResult
        }
    }
}