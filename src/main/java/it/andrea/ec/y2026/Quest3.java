package it.andrea.ec.y2026;

import it.andrea.ec.Input;
import it.andrea.ec.Timing;

import java.util.List;

public class Quest3 {

    private static final String ISO_STRING = "ISO-";
    private static final String ISO_TWO_STRING = "ISO2-";

    private record Vote (String standardISO, String voteValue){
        static Vote parse(String line, int questPhase) {
            return switch (questPhase) {
                case 1 -> parseVote(line);
                case 2, 3 -> parseIncompleteStandard(line);
                default -> throw new IllegalStateException("Unexpected value: " + questPhase);
            };
        }

        private static Vote parseVote(String line){
            String[] parts =  getParts(line);
            return new Vote(parts[0],parts[1]);
        }

        private static Vote parseIncompleteStandard(String line){
            String[] parts = getParts(line);
            int partsZeroSize = parts[0].length();
            int partsOneSize = parts[1].length();
            if(partsZeroSize== partsOneSize) return new Vote(parts[0],parts[1]);
            int diff = partsOneSize - partsZeroSize;
            return new Vote(fullFillISO(parts[0],diff),parts[1]);
        }

        private static String fullFillISO(String standardISO , int diffCount){
            StringBuilder newStandardISO = new StringBuilder();
            int standardIsoLength = standardISO.length()-1;
            for(int i =0; i < diffCount; i++){
                int index = standardIsoLength - (i % standardISO.length());
                newStandardISO.append(standardISO.charAt(index));
            }
            newStandardISO.reverse();
            newStandardISO.append(standardISO);
            return newStandardISO.toString();
        }

        private static String[] getParts(String line){
            return line.replace(ISO_STRING,"").replace(ISO_TWO_STRING,"").split(" ");
        }
    }

    static void main(String[] args) {

        Timing.run("Parte 1", Input.lines(2026, 3, 1), Quest3::partOne);
        Timing.run("Parte 2", Input.lines(2026, 3, 2), Quest3::partTwo);
        Timing.run("Parte 3", Input.lines(2026, 3, 3), Quest3::partThree);
    }


    private static long partOne(List<String> lines) {
        long total = 0;
        for (String line : lines) {
            total += calculateVote(line,1);
        }
        return total;
    }

    private static long partTwo(List<String> lines) {
        long total = 0;
        for (String line : lines) {
            total += calculateVote(line,2);
        }
        return total;
    }

    private static long partThree(List<String> lines) {
        long total = 0;
        for (String line : lines) {
            total += calculateVote(line,3);
        }
        return total;
    }

    private static long calculateVote(String line, int questPhase){
        Vote vote = Vote.parse(line, questPhase);
        long target = valueOf(vote.standardISO(), vote.voteValue());
        if (questPhase == 3 && !validateVote(vote.standardISO(), vote.voteValue(), target)) return 0;
        return target;
    }

    private static long valueOf(String standardISO, String number){
        int size = number.length();
        int offset = standardISO.length() - size;
        long total = 0;
        for (int i = 0; i < size; i++) {
            int exp = size - 1 - i;
            total += powerVote(standardISO.charAt(i + offset), number.charAt(i), exp);
        }
        return total;
    }

    private static boolean validateVote(String standardISO, String voteValue, long target){
        long voteNumber = Long.parseLong(voteValue);
        for (long n = 0; n < voteNumber; n++) {
            if (valueOf(standardISO, String.valueOf(n)) == target) return false;
        }
        return true;
    }

    private static long powerVote(char standardChar, char vote, int exp){
        return (long) (Math.pow(standardChar-'0',exp)) * (vote -'0');
    }




}
