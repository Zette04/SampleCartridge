
node 
{
deleteDir()

stage ("Clone the SimpleCartridge Repository")
	{
	checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Zette', url: 'https://github.com/Zette04/SampleCartridge.git']]])
	echo: "Clone Successful"
	echo: "Starting another stage."
	}
	
stage ("Process Groovy files") 
	{
	jobDsl targets: '*.groovy'
	}
}