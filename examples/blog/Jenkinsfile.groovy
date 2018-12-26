pipeline {
    agent any
    options { disableConcurrentBuilds() }
    environment {
        registry = "celavi/mojo-blog"
        registryCredential = 'dockerhub'
    }
    stages {
        stage("SCM Checkout") {
            steps{
                git poll: false, url: 'https://github.com/loncarales/mojo-pg.git'
            }
        }

        stage('Config') {
            steps {
                script {
                    dir('examples/blog') {
                        version = readFile('VERSION').trim()
                    }
                }
            }
        }

        stage("Build") {
            steps {
                script {
                    dir('examples/blog') {
                        dockerImage = docker.build registry + ":build"
                        docker.withRegistry('', registryCredential ) {
                            dockerImage.push()
                        }
                    }
                }
            }
        }

        stage("Test") {
            steps {
                script {
                    dir('examples/blog') {
                        dockerImage.pull()
                        sh 'docker run --rm -v $PWD/t:/opt/app/t $registry:build carton exec prove -lr -j4'
                    }
                }
            }
        }

        stage("Release") {
            steps {
                script {
                    dir('examples/blog') {
                        dockerImage.pull()
                        dockerImage.tag('v' + version)
                        dockerImage.tag('latest')
                        docker.withRegistry('', registryCredential ) {
                            dockerImage.push('v' + version)
                            dockerImage.push('latest')
                        }
                    }
                }
            }
        }

        stage("Deploy") {
            steps {
                script {
                    dir('examples/blog') {
                        echo "TBA"
                    }
                }
            }
        }
    }
}
