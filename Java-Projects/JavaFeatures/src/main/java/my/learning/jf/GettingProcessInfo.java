package my.learning.jf;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;

public class GettingProcessInfo {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("echo", "Hello World");
        String na = "<not available>";

        Process p = pb.start();
        ProcessHandle.Info info = p.info();

        System.out.printf("Process ID: %s%n", p.pid());
        System.out.printf("Command Name: %s%n", info.command().orElse(na));
        System.out.printf("Command Line: %s%n", info.commandLine().orElse(na));

        System.out.printf("Start time: %s%n",
            info.startInstant()
                .map((Instant startTime) -> startTime.atZone(ZoneId.systemDefault()).toLocalDateTime().toString())
                .orElse(na));
        System.out.printf("Arguments: %s%n",
            info.arguments()
                .map((String[] inArgs) -> String.join(" ", inArgs))
                .orElse(na));
        System.out.printf("User: %s%n", info.user().orElse(na));
    }
}
