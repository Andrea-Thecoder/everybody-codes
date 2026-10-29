package it.andrea.ec;

import java.util.List;
import java.util.function.Function;

public final class Timing {

    private Timing() {}

    // L'input viene letto prima di avviare il cronometro: si misura solo la risoluzione.
    public static void run(String label, List<String> input, Function<List<String>, ?> solver) {
        long start = System.nanoTime();
        Object result = solver.apply(input);
        double elapsedMs = (System.nanoTime() - start) / 1_000_000.0;
        System.out.printf("%s: %s (%.3f ms)%n", label, result, elapsedMs);
    }
}
