/*
 * Here should be licence
 */
package folder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Runner {

    abstract void loadAllFiles(int run,List<Path> files) throws Exception;

    void run(String[] args) throws IOException, Exception {
        System.out.println("Java "+System.getProperty("java.version"));
        System.out.println("CPUs " + Runtime.getRuntime().availableProcessors());
        String folderPath = args[0];
        int repeats = Integer.valueOf(args[1]);

        List<Path> files = Files.list(Paths.get(folderPath)).collect(Collectors.toList());
        for (int c = 0; c < repeats; c++) {
            loadAllFiles(c,files);
        }
    }

}
