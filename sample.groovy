// Folders
folder( "${Application_Name}" ){

}

def sampleFolder = "${Application_Name}"
def applicationName =  "${Application_Name}"

// Jobs
def generateBuildPipelineView = sampleFolder + "/Build-Pipeline-View_" + applicationName
def generateJobA = sampleFolder + "/jobA_" + applicationName
def generateJobB = sampleFolder + "/jobB_" + applicationName
def generateJobC = sampleFolder + "/jobC_" + applicationName
def generateJobD = sampleFolder + "/jobD_" + applicationName

// ##### GENERATE BUILD PIPELINE VIEW #####
buildPipelineView(generateBuildPipelineView) {
	title('Build-Pipeline-View_' + applicationName)
    displayedBuilds(5)
    selectedJob("jobA_" + applicationName)
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(5)
}	
// ##### END OF BUILD PIPELINE VIEW #####

// ##### GENERATE JOB A ####
freeStyleJob(generateJobA) {
	wrappers {
	preBuildCleanup()
	timestamps()
    }
    
	steps {
		powerShell('Write-Output "Hello World!"')
    }
    
	publishers{
    	downstreamParameterized {
            trigger(generateJobB) {
                condition('SUCCESS')
                parameters {
					currentBuild()
                }
            }
        }
    }
}
// ##### END OF JOB A ####

// ##### GENERATE JOB B ####
freeStyleJob(generateJobB) {
	wrappers {
	timestamps()
    }
    
	steps {
		powerShell('Write-Output "Hello World!"')
    }
    
	publishers{
    	downstreamParameterized {
            trigger(generateJobC) {
                condition('SUCCESS')
                parameters {
					currentBuild()
                }
            }
        }
    }
}
// ##### END OF JOB B ####

// ##### GENERATE JOB C ####
freeStyleJob(generateJobC) {
	wrappers {
	timestamps()
    }
    
	steps {
		powerShell('Write-Output "Hello World!"')
    }
    
	publishers{
    	downstreamParameterized {
            trigger(generateJobD) {
                condition('SUCCESS')
                parameters {
					currentBuild()
                }
            }
        }
    }
}
// ##### END OF JOB C ####
// ##### GENERATE JOB D ####
freeStyleJob(generateJobD) {
	wrappers {
	timestamps()
    }
    
	steps {
		powerShell('Write-Output "Hello World!"')
    }
}
// ##### END OF JOB D ####