pipeline {
    agent any

    parameters {
        booleanParam(defaultValue: false, description: 'Skip tests', name: 'SkipTests')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                mvn 'clean install -DskipTests'
            }
        }
        stage('Tests') {
        	when { expression { !params.SkipTests } }
            steps {
                parallel (
                    "unit" : {mvn 'surefire:test -DtestFailureIgnore=true'},
                    "integration" : {mvn 'failsafe:integration-test -DskipAfterFailureCount=999'}
                    )
            }
        }
        stage('SonarQube analysis') {
        	when { branch 'master' }
            steps {
            	withSonarQubeEnv('SonarDeroffal') {
            		mvn 'clean org.jacoco:jacoco-maven-plugin:prepare-agent package failsafe:integration-test sonar:sonar'
            	}
            }
		}
    }
}

def mvn(def cmd){
     def mvnHome = tool 'MAVEN_3.6.0'
     def javaHome = tool 'JDK9'

     withEnv(["PATH+MAVEN=${mvnHome}/bin", "JAVA_HOME=${javaHome}", "PATH+JDK=${javaHome}/bin"]) {
        sh "mvn -B $cmd"
     }

}