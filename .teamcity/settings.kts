import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubAppConnection
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2026.2"

project {

    buildType(Build)

    features {
        githubAppConnection {
            id = "PROJECT_EXT_5"
            displayName = "TeamCityMrk1"
            appId = "4875925"
            clientId = "Iv23lihtX1Uf9DtFw8CK"
            clientSecret = "credentialsJSON:2e65f54f-b0e3-40a6-b056-39e5faa6c4d0"
            privateKey = "credentialsJSON:a329d5e8-4b38-48bd-8a0b-7b743bf0e94f"
            webhookSecret = "credentialsJSON:49061f01-888f-428e-8204-679de7e1d8c0"
            ownerUrl = "https://github.com/aliene92"
            useUniqueCallback = true
        }
    }
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            name = "Maven clean test"
            id = "Maven_clean_test"

            conditions {
                equals("teamcity.build.branch.is_default", "false")
            }
            goals = "clean test"
            userSettingsSelection = "settings.xml"
        }
        maven {
            name = "Maven clean deploy"
            id = "Maven_clean_deploy"

            conditions {
                equals("teamcity.build.branch.is_default", "true")
            }
            goals = "clean deploy"
            userSettingsSelection = "settings.xml"
        }
    }

    triggers {
        vcs {
        }
    }
})
