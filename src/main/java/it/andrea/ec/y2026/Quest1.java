package it.andrea.ec.y2026;

import it.andrea.ec.Input;
import it.andrea.ec.Timing;

import java.util.List;

public class Quest1 {

    static void main(String[] args) {

        Timing.run("Parte 1", Input.lines(2026, 1, 1), Quest1::partOne);
        Timing.run("Parte 2", Input.lines(2026, 1, 2), Quest1::partTwo);
        Timing.run("Parte 3", Input.lines(2026, 1, 3), Quest1::partThree);
    }


    private static long partOne(List<String> lines) {
        int[] notes = Input.ints(String.join(".", lines));
        long total = 0;
        for (int nota : notes) {
            double radiceQuadrata = Math.sqrt(nota);
            if (radiceQuadrata % 1 == 0) continue;
            int quadrato = (int) Math.pow(Math.ceil(radiceQuadrata), 2);
            total += quadrato - nota;
        }
        return total;
    }

    private static long partTwo(List<String> lines) {
        int[] notes = Input.ints(String.join(".", lines));
        long total = 0;
        for (int nota : notes) {
            if (nota < 8) {
                total += 8 - nota;
                continue;
            }
            int resto = nota % 4;
            if (resto == 0) continue;
            total += 4 - resto;
        }
        return total;
    }

    private static long partThree(List<String> lines) {
        int[] notes = Input.ints(String.join(".", lines));
        long total = 0;
        for (int nota : notes) {
            int latoQuadro = (int) Math.max(Math.ceil(Math.sqrt(nota+1)),3);
            if( latoQuadro % 2 == 0) latoQuadro++;
            int pigneExtra = (int) Math.pow(latoQuadro,2)-1;
            total += pigneExtra - nota;
        }
        return total;
    }
}
