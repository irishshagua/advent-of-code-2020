package io.github.irishshagua.app.io;

import io.github.irishshagua.app.TaskRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IO {

    public static String read(Path path) {
        try {
            var uri = TaskRunner.class.getClassLoader().getResource(path.toString()).toURI();
            return Files.readString(Paths.get(uri));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("File Reading Failed for ["+path.toString()+"]", e);
        }

    }

    public static <T> List<T> readLinesAs(Path path, Converter<String, T> converter) {
        try {
            var uri = TaskRunner.class.getClassLoader().getResource(path.toString()).toURI();
            return Files
                    .readAllLines(Paths.get(uri))
                    .stream()
                    .map(converter::convert)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("File Read/Parsing Failed for ["+path.toString()+"]", e);
        }
    }
}
