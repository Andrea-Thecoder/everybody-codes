package it.andrea.ec.y2026;

import it.andrea.ec.Input;
import it.andrea.ec.Timing;

import java.util.*;
import java.util.stream.Collectors;

public class Quest2 {

    static void main(String[] args) {

        Timing.run("Parte 1", Input.lines(2026, 2, 1), Quest2::partOne);
        Timing.run("Parte 2", Input.lines(2026, 2, 2), Quest2::partTwo);
        Timing.run("Parte 3", Input.lines(2026, 2, 3), Quest2::partThree);
    }



    private static long partOne(List<String> lines) {
        long result = 0;

        for (String[] groups : createGroups(lines)) {
            result += checksumGroup(groups);
        }
        return result;
    }

    private static long partTwo(List<String> lines) {
        long result = 0;

        for (String[] groups : createGroups(lines)) {
            result += completeChecksumGroup(groups);
        }

        return result;
    }

    private static long partThree(List<String> lines) {
        long result = 0;

        for (String[] groups : createGroups(lines)) {
            result += incompleteChecksumGroup(groups);
        }

        return result;
    }

    private static List<String[]> createGroups(List<String> lines) {
        return lines.stream()
                .map(line -> line.trim().split("\\s+"))
                .toList();
    }

    private static long checksumGroup(String[] group) {
        Map<Character,Integer> checksumMap = new HashMap<>();
        for (String code : group) {
            boolean leftHarder = code.charAt(1) =='>';
            char harder = leftHarder ? code.charAt(0) : code.charAt(2);
            char softer = leftHarder ? code.charAt(2) : code.charAt(0);
            checksumMap.merge(harder, 1, Integer::sum);
            checksumMap.putIfAbsent(softer, 0);
        }
        return checksumMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> String.valueOf(e.getKey()))
                .collect(Collectors.collectingAndThen(Collectors.joining(), Long::parseLong));

    }

    private static long completeChecksumGroup(String[] group) {
        Map<Character,Integer> checksumMap = new HashMap<>();
        Map<Character,Integer> alloyAppearances = new HashMap<>();

        for (String code : group) {
            boolean leftHarder = code.charAt(1) =='>';
            char harder = leftHarder ? code.charAt(0) : code.charAt(2);
            char softer = leftHarder ? code.charAt(2) : code.charAt(0);
            checksumMap.merge(harder, 1, Integer::sum);
            checksumMap.putIfAbsent(softer, 0);
            alloyAppearances.merge(harder, 1, Integer::sum);
            alloyAppearances.merge(softer, 1, Integer::sum);
        }

        int alloyAppearancesTimes = alloyAppearances.size()-1;
        List<Character> missingAlloys = alloyAppearances.entrySet()
                .stream()
                .filter(alloy -> alloy.getValue() < alloyAppearancesTimes)
                .map(Map.Entry::getKey)
                .toList();

        char alloyA = missingAlloys.get(0);
        char alloyB = missingAlloys.get(1);
        char harder = checksumMap.get(alloyA) > checksumMap.get(alloyB) ? alloyA : alloyB;
        checksumMap.merge(harder, 1, Integer::sum);

        return checksumMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> String.valueOf(e.getKey()))
                .collect(Collectors.collectingAndThen(Collectors.joining(), Long::parseLong));

    }

    private static long incompleteChecksumGroup(String[] group) {
        Map<Character, List<Character>> beats = new HashMap<>();
        Map<Character, Integer> lostTo = new HashMap<>();

        for (String code : group) {
            boolean leftHarder = code.charAt(1) == '>';
            char harder = leftHarder ? code.charAt(0) : code.charAt(2);
            char softer = leftHarder ? code.charAt(2) : code.charAt(0);

            beats.computeIfAbsent(harder, k -> new ArrayList<>()).add(softer);
            lostTo.merge(softer, 1, Integer::sum);
            lostTo.putIfAbsent(harder, 0);
        }

        StringBuilder order = new StringBuilder();
        while (!lostTo.isEmpty()) {
            char next = lostTo.entrySet().stream()
                    .filter(e -> e.getValue() == 0)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow();

            order.append(next);
            lostTo.remove(next);

            for (char softer : beats.getOrDefault(next, List.of())) {
                lostTo.merge(softer, -1, Integer::sum);
            }
        }
        return Long.parseLong(order.toString());
    }

}
