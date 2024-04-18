@Library('metaborg.jenkins.pipeline') _

gradlePipeline(

  gradleBuildTasks: "build",
  gradlePublishTasks: "publish",

  mainBranch: "main",
  developBranch: "develop",
  releaseTagPattern: "*.*.*",

  publishUsernameProperty: "metaborg-artifacts.username",
  publishPasswordProperty: "metaborg-artifacts.password",

  // Enable Slack notifications
  slack: true,
  slackChannel: "#spoofax3-dev"
 )
