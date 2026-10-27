package it.andrea.ec;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public final class Input {

    private static final String PATH_STRING = "src/main/resources/y%d/example_quest%02d_p%d.txt";

    private Input() {}

    public static List<String> lines(int year, int quest, int part) {
        Path path = Path.of(PATH_STRING.formatted(year, quest, part));
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Input non trovato: " + path, e);
        }
    }

    public static int[] ints(String line) {
        return Arrays.stream(line.split("[^-\\d]+"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static int[] intPerLine(List<String> lines) {
        return lines.stream()
                .filter(l -> !l.isBlank())
                .mapToInt(l -> Integer.parseInt(l.trim()))
                .toArray();
    }

    public static char[][] grid(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }
}
