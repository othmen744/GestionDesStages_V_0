pipeline {
    agent any
    environment {
        TOMCAT_PORT = '9090'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/othmen744/GestionDesStages_V_0.git'
            }
        }
        
        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube-10.4.1') {
                    sh 'mvn sonar:sonar -Dsonar.login=sqa_b6840cd936c17ce7127f90e7aa528a023b8c037e'
                }
            }
        }
        
        stage('Run JUnit and JaCoCo tests') {
            steps {
                sh 'mvn test'
                junit testResults: 'target/surefire-reports/*.xml'
                jacoco(execPattern: 'target/jacoco.exec')
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t oth007/gestiondesstages_v_0:karoui .'
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                sh 'docker login -u oth007 -p othmen199800'
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push oth007/gestiondesstages_v_0:karoui'
            }
        }

        stage('Setup MySQL') {
            steps {
                script {
                    def networkExists = sh(script: 'docker network ls | grep my-network', returnStatus: true) == 0
                    if (!networkExists) {
                        sh 'docker network create my-network'
                    }
                    sh 'docker run --name mysql-container --network my-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=pfe -d mysql:8.0.32'
                }
            }
        }

        stage('Undeploy Existing Application') {
            steps {
                script {
                    sh "curl -u admin:othmen199800 http://192.168.33.10:9090/manager/text/undeploy?path=/GestionDesStages"
                }
            }
        }

        stage('Deploy Docker Image to Tomcat') {
            steps {
                script {
                    sh 'docker pull oth007/gestiondesstages_v_0:karoui'
                    sh 'docker tag oth007/gestiondesstages_v_0:karoui gestiondesstages_v_0_karoui'
                    sh "curl -u admin:othmen199800 -T app.jar http://localhost:${env.TOMCAT_PORT}/manager/text/deploy?path=/GestionDesStages"
                }
            }
        }
    }
}
