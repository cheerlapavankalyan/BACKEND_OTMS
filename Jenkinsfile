pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64' // Set this to your JDK path
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', 
                          branches: [[name: '*/master']], 
                          doGenerateSubmoduleConfigurations: false, 
                          extensions: [], 
                          submoduleCfg: [], 
                          userRemoteConfigs: [[url: 'git@github.com:cheerlapavankalyan/BACKEND_OTMS.git', credentialsId: 'github']]])
            }
        }
        stage('Build') {
            parallel {
                stage('Build Service Registry') {
                    steps {
                        dir('ServiceRegistry') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn clean install'
                        }
                    }
                }
                /*stage('Build Student Service') {
                    steps {
                        dir('StudentService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn clean install'
                        }
                    }
                } */
                
                stage('Build Quiz Service') {
                    steps {
                        dir('QuizService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn clean install'
                        }
                    }
                }
                stage('Build Question Service') {
                    steps {
                        dir('QuestionService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn clean install'
                        }
                    }
                }
                stage('Build Feedback') {
                    steps {
                        dir('FeedbackService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn clean install'
                        }
                    }
                }
                /*stage('Build API Gateway') {
                    steps {
                        dir('API_Gateway') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn clean install'
                        }
                    }
                }*/
            }
        }

        stage('Test') {
            parallel {
                stage('Test Service Registry') {
                    steps {
                        dir('ServiceRegistry') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn test'
                        }
                    }
                }
                /*stage('Test Student Service') {
                    steps {
                        dir('StudentService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn test'
                        }
                    }
                }*/
                stage('Test Quiz Service') {
                    steps {
                        dir('QuizService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Question Service') {
                    steps {
                        dir('QuestionService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Feedback service') {
                    steps {
                        dir('FeedbackService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn test'
                        }
                    }
                }
            }
        } 

        /*stage('Package') {
            parallel {
                stage('Package Student Service') {
                    steps {
                        dir('StudentService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn package'
                            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                        }
                    }
                }
                stage('Package Service Registry') {
                    steps {
                        dir('ServiceRegistry') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn package'
                            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                        }
                    }
                }
                stage('Package Quiz Service') {
                    steps {
                        dir('QuizService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn package'
                            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                        }
                    }
                }
                stage('Package Question Service') {
                    steps {
                        dir('QuestionService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn package'
                            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                        }
                    }
                }
                stage('Package Feedback Service') {
                    steps {
                        dir('FeedbackService') {
                            sh 'chmod +x mvnw' // Ensure mvnw is executable
                            sh 'mvn package'
                            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                        }
                    }
                } 
            }
        }*/
    }

    post {
        success {
            echo 'Build, Test, and Package steps completed successfully!'
        }
        failure {
            echo 'Build, Test, or Package step failed.'
        }
    }
}
