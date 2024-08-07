pipeline {
    agent any
    environment {
        TOMCAT_PORT = '9090'
        DOCKER_REGISTRY = '192.168.33.10:5000' // Address of your local Docker registry
        IMAGE_NAME = 'gestiondesstages_v_0'
        IMAGE_TAG = 'karoui'
        KUBECONFIG = '/home/vagrant/.kube/config'
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
        stage('Deploy to Nexus') {
            steps {
                sh "mvn deploy -Dusername=admin -Dpassword=othmen199800"
            }
        }
        
        stage('Run JUnit and JaCoCo tests') {
            steps {
                sh 'mvn test'
                junit testResults: 'target/surefire-reports/*.xml'
                jacoco(execPattern: 'target/jacoco.exec')
            }
        }
         stage('OWASP SCAN') {
            steps {
                dependencyCheck additionalArguments: '', odcInstallation: 'DP-Check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
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
        stage('Tag Docker Image') {
            steps {
                  sh 'docker tag oth007/gestiondesstages_v_0:karoui localhost:5000/oth007/gestiondesstages_v_0:karoui'
               }
    
        }

        stage('Push Docker-registry Image') {
            steps {
                    sh 'docker push 192.168.33.10:5000/oth007/gestiondesstages_v_0:karoui'
                
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
 stage('Deploy Backend to Kubernetes') {
            steps {
                withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'kubernetes', contextName: 'kubernetes-admin@kubernetes', credentialsId: 'SECRET_TOKEN', namespace: 'default', serverUrl: 'https://10.0.0.10:6443']]) {
                    script {
                        try {
                            def result = sh(script: 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"', returnStatus: true)
                            if (result != 0) {
                                error "Download kubectl failed with exit code ${result}"
                            }
                            result = sh(script: 'chmod u+x ./kubectl', returnStatus: true)
                            if (result != 0) {
                                error "Make kubectl executable failed with exit code ${result}"
                            }

                            // Apply MySQL resources
                            result = sh(script: './kubectl apply -f mysql-storage.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-storage.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f mysql-pv.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-pv.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f mysql-pv-claim-new.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-pv-claim-new.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f mysql-configMap.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-configMap.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f mysql-secrets.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-secrets.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f mysql-deployment.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-deployment.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f mysql-service.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply mysql-service.yaml failed with exit code ${result}"
                            }

                            // Check MySQL deployment status
                            result = sh(script: './kubectl rollout status deployment/mysql', returnStatus: true)
                            if (result != 0) {
                                error "Check MySQL deployment status failed with exit code ${result}"
                            }

                            // Apply backend resources
                            result = sh(script: './kubectl apply -f backend-service.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply backend-service.yaml failed with exit code ${result}"
                            }
                            result = sh(script: './kubectl apply -f deployment-backend.yaml', returnStatus: true)
                            if (result != 0) {
                                error "Apply deployment-backend.yaml failed with exit code ${result}"
                            }
                        } catch (Exception e) {
                            echo "Error during Kubernetes deployment: ${e.message}"
                            currentBuild.result = 'FAILURE'
                            throw e
                        }
                    }
                }
            }
        }
    }
}
