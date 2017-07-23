// Folders
folder( "${Application_Name}" ){

}

def sampleFolder = "${Application_Name}"
def applicationName =  "${Application_Name}"

// Jobs
def generateBuildPipelineView = TitaniumBuildPipelineFolderName + "/Pipeline_View_" + applicationName
def generateBuildJob = TitaniumBuildPipelineFolderName + "/mavenBuildJob_" + applicationName
def generateCodeAnalysisJob = TitaniumBuildPipelineFolderName + "/codeAnalysisJob_" + applicationName
def generateUploadToNexusJob = TitaniumBuildPipelineFolderName + "/uploadToNexusJob_" + applicationName
def generateDeploymentToTOmcatJob = TitaniumBuildPipelineFolderName + "/deploymentToTomcat_" + applicationName

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
// ##### END MAVEN BUILD JOB #####