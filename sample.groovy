// Folders
folder( "${Application_Name}" ){

}

def sampleFolder = "${Application_Name}"
def applicationName =  "${Application_Name}"

// Jobs
def generateBuildPipelineView = sampleFolder + "/Pipeline_View_" + applicationName
def generateBuildJob = sampleFolder + "/mavenBuildJob_" + applicationName
def generateCodeAnalysisJob = sampleFolder + "/codeAnalysisJob_" + applicationName
def generateUploadToNexusJob = sampleFolder + "/uploadToNexusJob_" + applicationName
def generateDeploymentToTOmcatJob = sampleFolder + "/deploymentToTomcat_" + applicationName

// ##### GENERATE BUILD PIPELINE VIEW #####
buildPipelineView(generateBuildPipelineView) {
	title('Pipeline_View')
    displayedBuilds(5)
    selectedJob(generateBuildJob)
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(5)
}	
// ##### END OF BUILD PIPELINE VIEW #####

// ##### GENERATE MAVEN BUILD JOB #####
freeStyleJob(generateBuildJob) {
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
            trigger(generateCodeAnalysisJob) {
                condition('SUCCESS')
                parameters {
			currentBuild()
			predefinedProps([CUSTOM_WORKSPACE: '$WORKSPACE'])
                }
            }
        }
    }
}
// ##### END MAVEN BUILD JOB #####

// ##### GENERATE CODE ANALYSIS JOB #####
freeStyleJob(generateCodeAnalysisJob) {
    parameters {
        stringParam('CUSTOM_WORKSPACE', '', '')
    }
	
	customWorkspace('$CUSTOM_WORKSPACE')
    
	wrappers {
		timestamps()
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
// ##### END CODE ANALYSIS JOB #####