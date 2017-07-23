freeStyleJob('example') {
    scm {
        git {
            remote {
                url("https://github.com/jareddeuna/SampleWebApp.git")
            }
        }
    }
    wrappers {
	preBuildCleanup()
	timestamps()
    }
    steps {
	maven {
		mavenInstallation('maven-3.5.0')
		goals('clean')
		goals('package')
	}
    }
    publishers{
    	downstreamParameterized {
            trigger('sonarJob') {
                condition('SUCCESS')
                parameters {
			currentBuild()
			predefinedProps([CUSTOM_WORKSPACE: '$WORKSPACE'])
                }
            }
        }
    }
}