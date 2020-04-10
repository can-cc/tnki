pipeline {
    agent none
    triggers {
        pollSCM('*/1 * * * *')
    }
     environment {
        HOME = '.'
        DOCKER_REGISTER = 'fwchen'
        SPRING_DATASOURCE_URL = credentials('tnki-datasource')
        docker_hub_username = credentials('docker_hub_username')
        docker_hub_password = credentials('docker_hub_password')
    }
    stages {
        stage('test') {
            agent {
                docker {
                    image 'gradle:jdk11'
                }
            }
            steps {
                sh './gradlew clean'
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
                        sh 'docker build . -t $DOCKER_REGISTER/tnki:latest'
                        sh 'docker build . -t $DOCKER_REGISTER/tnki-apm:latest -f Dockerfile-apm'
                    }
                }
                stage('registry Login') {
                    steps {
                        sh "echo $docker_hub_password | docker login -u $docker_hub_username --password-stdin"
                    }
                }
                stage('publish image') {
                    steps {
                        sh 'docker push $DOCKER_REGISTER/tnki:latest'
                        sh 'docker push $DOCKER_REGISTER/tnki-apm:latest'
                        sh 'echo "$DOCKER_REGISTER/tnki:latest" > .artifacts'
                        sh 'echo "$DOCKER_REGISTER/tnki-apm:latest" >> .artifacts'
                        archiveArtifacts(artifacts: '.artifacts')
                    }
                }
                stage('remove image') {
                    steps {
                        sh "docker image rm $DOCKER_REGISTER/tnki:latest"
                        sh "docker image rm $DOCKER_REGISTER/tnki-apm:latest"
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