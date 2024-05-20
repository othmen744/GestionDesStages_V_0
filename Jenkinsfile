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
    }
}
