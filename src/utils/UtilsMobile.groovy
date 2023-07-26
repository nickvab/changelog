package utils

class UtilsMobile {

  static def getCommitSha(Object ctx) {
    return ctx.sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
  }

  static String getChangeString(Object ctx) {
    def MAX_MSG_LEN = 100
    def passedBuilds = []

    ctx.println"Gathering SCM changes"

    for (def curBuild = ctx.currentBuild; ((curBuild != null) && (curBuild.result != 'SUCCESS')); curBuild = curBuild.previousBuild) {
      passedBuilds.add(curBuild)
    }

    def changeString = ''
    for (int x = 0; x < passedBuilds.size(); x++) {
      def currentBuild = passedBuilds[x]
      def changeLogSets = currentBuild.changeSets
      for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
          def entry = entries[j]
          def truncated_msg = entry.msg.take(MAX_MSG_LEN)
          changeString += "${truncated_msg} [${entry.author}]\n"
        }
      }
    }

    if (!changeString) {
      changeString = 'No new changes'
    }
    return changeString
  }

}

