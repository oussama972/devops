

properties([pipelineTriggers([githubPush()])])
pipeline {
    agent any 
    tools {
            maven "M2_HOME"
            jdk "JAVA_HOME"
        }

     environment {
      registry = "oussamakhabouchi/tpachat"

        registryCredential = 'dockerhub'

        dockerImage = ''

        DOCKERHUB_CREDENTIALS="dckr_pat_d7KO-mlso2flhqMY7Uh77THlqUk"
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.6.117:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo1"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    }



    stages {
    stage('git clone') {
            steps {
               git branch: 'master', url: 'https://github.com/oussama972/devops'

            }
        }

    stage('clean package') {
            steps {
             sh 'mvn clean install -DskipTests=true'


            }
        }

    stage('mvn test') {
            steps {
             sh 'mvn test'


            }
        }

    stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
            }
        }

    stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }

    stage('push docker hub') {
            steps{
               script {

                dockerImage = docker.build registry + ":$BUILD_NUMBER"

                docker.withRegistry( '', registryCredential ) {

                    dockerImage.push()

                }

            }
        }
        }
    stage(' docker-compose') {
            steps {
                sh 'docker-compose -f docker-compose-app.yml up -d'

            }
        }



     }


    post{
        always{
        
        emailext body: 'jenkins', subject: 'jenkins', to: 'oussama.khabouchi@esprit.tn'
        }
        
    }    
        

}
