pipeline: pipeline {
agent any

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
        script {
            sh 'mvn deploy'
        }
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
   stage('Undeploy Existing Application') {
    steps {
        script {
            sh "curl -u admin:othmen199800 http://192.168.33.10:9090/manager/text/undeploy?path=/GestionDesStages"
        }
    }
}

stage('Deploy to Tomcat') {
    steps {
        script {
            // Download the JAR file from Nexus
            sh 'wget -O app.jar http://192.168.1.15:8081/repository/deploymentRepo/Robots/enicar/GestionDesStages/0.0.1/GestionDesStages-0.0.1.jar'
            
            // Deploy the downloaded JAR file to Tomcat (adjust the context path)
            sh "curl -u admin:othmen199800 -T app.jar http://192.168.33.10:9090/manager/text/deploy?path=/GestionDesStages"
        }
    }
}


}
}
