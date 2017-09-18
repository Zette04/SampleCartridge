def TEST_VALUE=1



node 
{
deleteDir()

stage ("Clone the SimpleCartridge Repository")
	{
	checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Zette', url: 'https://github.com/Zette04/SampleCartridge.git']]])
	echo "Clone Successful"
	echo "Starting another stage."
	}
	
stage ("Process Groovy files") 
	{
	jobDsl targets: '*.groovy'
	
	echo: TEST_VALUE
	if ( TEST_VALUE != 0 ) {
		echo "TEST_VALUE is not equal to zero"
		sh "exit 0"
	}
	}

stage ("Double Check") {
	echo "This should not be visible"
}
}

