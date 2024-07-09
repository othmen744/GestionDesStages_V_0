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
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
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
        
        stage('Update Docker Image') {
    steps {
        script {
            // Remove old Docker container if it exists
            sh 'docker rm -f gestiondesstages_container || true' // '|| true' prevents pipeline failure if container doesn't exist

            // Pull the latest Docker image
            sh 'docker pull oth007/gestiondesstages_v_0:karoui'

            // Run the updated Docker container
        }
    }
}
stage('Ensure kubectl is available') {
            steps {
                script {
                    sh '''
                    if [ ! -f ./kubectl ]; then
                        echo "kubectl not found, downloading..."
                        curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"
                        chmod u+x ./kubectl
                    else
                        echo "kubectl is already downloaded"
                    fi
                    '''
                }
            }
        }
stage('Deploy Backend to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'SECRET_TOKEN', serverUrl: 'https://10.0.0.10:6443']) {

                    // Apply MySQL resources
                    sh './kubectl apply -f mysql-storage.yaml'
                    sh './kubectl apply -f mysql-pv.yaml'
                    sh './kubectl apply -f mysql-pv-claim-new.yaml'
                    sh './kubectl apply -f mysql-configMap.yaml'
                    sh './kubectl apply -f mysql-secrets.yaml'
                    sh './kubectl apply -f mysql-deployment.yaml'
                    sh './kubectl apply -f mysql-service.yaml'

                    // Check MySQL deployment status
                    sh './kubectl rollout status deployment/mysql'

                    // Apply backend resources
                    sh './kubectl apply -f backend-service.yaml'
                    sh './kubectl apply -f deployment-backend.yaml'
                }
            }
        }

        
    }
}
