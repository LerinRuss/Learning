package my.learning.jf;

import java.io.IOException;

public class ProcessApi {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "echo $HORSE $DOG $HOME").inheritIO();

        pb.environment().put("HORSE", "MUSTANG");
        pb.environment().put("DOG", "Corgi");

        int exitStatus = pb.start().waitFor();
        System.out.printf("Exit status is %s.%n", exitStatus);
    }
}
