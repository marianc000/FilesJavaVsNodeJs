package folder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Single extends Runner {

    @Override
    void loadAllFiles(int run, List<Path> files) throws IOException {

        long start = System.currentTimeMillis();
        List<String> contents = new LinkedList<>();

        for (Path file : files) {
           contents.add(Files.readString(file, StandardCharsets.UTF_8));
           // contents.add(new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
        }

        long time = System.currentTimeMillis() - start;
        int totalChars = contents.stream().collect(Collectors.joining()).length();
        System.out.println(run + " loaded " + files.size() + " files, " + totalChars + " characters in " + time + " milliseconds");
    }

    public static void main(String[] args) throws Exception {
        new Single().run(args);
    }
}
