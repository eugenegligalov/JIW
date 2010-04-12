import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CopyToLib extends DefaultTask {

    CopyToLib() {
//        dependsOn("jar")
    }

    @TaskAction
    def run() {
        libDir = project.file('lib')
        libDir.mkdirs()
        project.configurations.compile.each { file ->
            ant.copy(file: file.path, todir: libDir)
        }
        project.configurations.testCompile.each { file ->
            ant.copy(file: file.path, todir: libDir)
        }

    }

}