import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by ben.coleman on 20/04/2017.
 */
public class GetTasskClass extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project proj = e.getData(PlatformDataKeys.PROJECT);

        Path path = Paths.get(proj.getBasePath());
        FileChecker filewalker = new FileChecker(path, proj);
        try {
            Files.walkFileTree(path, filewalker);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static class FileChecker extends SimpleFileVisitor<Path> {
        Path startDir;
        Project proj;
        public FileChecker(Path path, Project proj) {
            this.startDir = path;
            this.proj = proj;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if(!dir.getFileName().toString().equals("src")){
                return FileVisitResult.SKIP_SUBTREE;
            }
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Messages.showMessageDialog(proj, "File is: " +  file.getFileName().toString(), "Information", Messages.getInformationIcon());
            return super.visitFile(file, attrs);
        }
    }
}
