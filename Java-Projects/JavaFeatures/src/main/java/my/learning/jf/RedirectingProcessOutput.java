package my.learning.jf;

import java.io.File;
import java.io.IOException;

public class RedirectingProcessOutput {
    public static void main(String[] args) throws IOException, InterruptedException {
        var outFile = new File("out.tmp");
        Process lsProc = new ProcessBuilder("ls", "-la")
            .redirectOutput(outFile)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start();
        int status = lsProc.waitFor();

        if (status == 0) {
            Process catProc = new ProcessBuilder("cat", outFile.toString())
                .inheritIO()
                .start();
            catProc.waitFor();
        }
    }
}
