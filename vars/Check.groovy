import utils.UtilsMobile
def call() {
def changesString = UtilsMobile.getChangeString(this)
  writeFile file: 'changes.txt', text: "${changesString}"
}

pipeline {
    agent any
    
    stages {
        stage('Git checkout') {
            steps {
                script{
                    Check()
                }
            }
        }
    }
}
