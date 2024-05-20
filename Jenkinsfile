pipeline: pipeline {
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
                sh 'mvn clean compile' // Nettoyer et compiler le code avec Maven
            }
        }

        stage('Run SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube-10.4.1') {
                    sh 'mvn sonar:sonar -Dsonar.login=sqa_b6840cd936c17ce7127f90e7aa528a023b8c037e' // Analyse avec SonarQube
                }
            }
        }
         stage('Run JUnit and JaCoCo tests') {
    steps {
        sh 'mvn test' // Exécuter les tests JUnit
        junit testResults: 'target/surefire-reports/*.xml' // Récupérer les rapports XML de JUnit
        jacoco(execPattern: 'target/jacoco.exec') // Récupérer le rapport de couverture JaCoCo
    }
}

      stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy' // Deploy to Nexus Repository Manager
            }
        }
      
         stage('Build Docker Image') {
                    steps {
                        script {
                            sh 'docker build -t oth007/gestiondesstages_v_0:karoui .'
                        }
                    }
                }
        stage('login dockerhub') {
                                        steps {
				sh 'docker login -u oth007 --password othmen199800'
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
                    // Check if the network exists, if not create it
                    def networkExists = sh(script: 'docker network ls | grep my-network', returnStatus: true) == 0
                    if (!networkExists) {
                        sh 'docker network create my-network'
                    }
                    // Run the MySQL container
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
                    // Pull the Docker image from Docker Hub
                    sh 'docker pull oth007/gestiondesstages_v_0:karoui'
                    
                    // Tag the Docker image for deployment
                    sh 'docker tag oth007/gestiondesstages_v_0:karoui gestiondesstages_v_0_karoui'
                    
                    // Deploy the Docker image to Tomcat using the manager interface
                    sh "curl -u admin:othmen199800 -T app.jar http://localhost:${env.TOMCAT_PORT}/manager/text/deploy?path=/GestionDesStages"
                }
            }
        }


}
}
