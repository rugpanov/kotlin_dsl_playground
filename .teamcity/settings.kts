import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.investigationsAutoAssigner
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

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

version = "2019.1"

project {
    vcsRoot(HttpsGithubComJetBrainsTeamcityInvestigations)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComJetBrainsTeamcityInvestigations)
    }

    steps {
        gradle {
            name = "gradle - build"
            tasks = "clean build"
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_DEFAULT
            branchFilter = ""
        }
    }

    features {
        investigationsAutoAssigner {
            enableDelayAssignments = true
        }
    }
})

object HttpsGithubComJetBrainsTeamcityInvestigations : GitVcsRoot({
    name = "https://github.com/rugpanov/teamcity-investigations-auto-assigner.git#refs/heads/master"
    pollInterval = 20
    url = "https://github.com/rugpanov/teamcity-investigations-auto-assigner.git"
})