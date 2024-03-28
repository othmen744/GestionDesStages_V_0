pipeline {
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
                    def version = '1.0.0-SNAPSHOT'
                    def jarFile = findFiles(glob: 'target/*.jar').first() // Recherche du fichier JAR dans le répertoire target
                    if (jarFile != null) {
                        nexusArtifactUploader(
                            nexusVersion: 'nexus3',
                            protocol: 'http',
                            nexusUrl: 'http://192.168.33.10:8081',
                            groupId: 'Robots.enicar',
                            version: version,
                            repository: 'deploymentRepo',
                            credentialsId: 'nexus-auth',
                            artifacts: [
                                [artifactId: 'GestionDesStages',
                                 classifier: '',
                                 file: jarFile.path,
                                 type: 'jar']
                            ]
                        )
                    } else {
                        error 'Fichier JAR non trouvé dans le répertoire target.'
                    }
                }
            }
        }
    }
}
