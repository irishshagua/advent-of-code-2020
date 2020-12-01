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

    public static String read(Path path) throws URISyntaxException, IOException {
        var uri = TaskRunner.class.getClassLoader().getResource(path.toString()).toURI();
        return Files.readString(Paths.get(uri));
    }

    public static <T> List<T> readLinesAs(Path path, Converter<String, T> converter) throws URISyntaxException, IOException {
        var uri = TaskRunner.class.getClassLoader().getResource(path.toString()).toURI();
        return Files
                .readAllLines(Paths.get(uri))
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
