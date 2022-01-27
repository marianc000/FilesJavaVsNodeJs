/*
 * Here should be licence
 */
package folder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Concurrent extends Runner {

    CompletableFuture<String> load(Path filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Files.readString(filePath, StandardCharsets.UTF_8);
           //     return new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    void loadAllFiles(int run, List<Path> files) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        CompletableFuture<String>[] requests = files
                .stream().map(url -> load(url)).toArray(i -> new CompletableFuture[i]);

        CompletableFuture.allOf(requests)
                .thenAccept(v -> {
                    long time = System.currentTimeMillis() - start;
                    int totalChars = Stream.of(requests)
                            .map(future -> future.join())
                            .collect(Collectors.joining()).length();
                    System.out.println(run + " loaded " + files.size() + " files, " + totalChars + " characters in " + time + " milliseconds");
                }).get();
    }

    public static void main(String[] args) throws Exception {
        new Concurrent().run(args);
    }
}
