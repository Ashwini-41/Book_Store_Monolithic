pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                git 'https://github.com/pushpakdeore/Book_Store.git'
            }
        }
        stage('Build Stage') {
            steps {
                sh '/usr/bin/mvn clean install'
            }
        }
        stage('Stop Old Service') {
            steps {
                script {
                    sh '''
                    ssh ubuntu@172.31.30.199 '
                        sudo systemctl stop bookstore.service 
                    '
                    '''
                }
            }
        }
        stage('Delete Old Artifact') {
            steps {
                script {
                    sh '''
                    ssh ubuntu@172.31.30.199 '
                        sudo rm -rf /home/ubuntu/Book_Store/target/BookStore-0.0.1-SNAPSHOT.jar
                    '
                    '''
                }
            }
        }
        stage('Delete Old Artifact2') {
            steps {
                script {
                    sh '''
                    ssh ubuntu@172.31.30.199 '
                        sudo rm -rf /home/ubuntu/Book_Store/target/BookStore-0.0.1-SNAPSHOT.jar.original
                    '
                    '''
                }
            }
        }
        stage('Copy New Artifact') {
            steps {
                script {
                    sh '''
                    scp -o StrictHostKeyChecking=no $WORKSPACE/target/BookStore-0.0.1-SNAPSHOT.jar ubuntu@172.31.30.199:/home/ubuntu/Book_Store/target
                    '''
                }
            }
        }
        stage('refresh daemon') {
            steps {
                script {
                    sh '''
                    ssh ubuntu@172.31.30.199 '
                        sudo systemctl daemon-reload
                    '
                    '''
                }
            }
        }
        stage('Start New Service') {
            steps {
                script {
                    sh '''
                    ssh ubuntu@172.31.30.199 '
                        sudo systemctl start bookstore.service
                    '
                    '''
                }
            }
        }
    }
}



