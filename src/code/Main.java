package code;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int digitCount = 4;

    public static void main(String[] args) {
        ArrayList<Integer> options = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 1111; i < 6667; i++) {
            if (
                Integer.toString(i).contains("7") ||
                Integer.toString(i).contains("8") ||
                Integer.toString(i).contains("9") ||
                Integer.toString(i).contains("0")
            ) continue;
            options.add(i);
        }
        for (int i = 0; ; i++) {
            if (options.size() == 1) {
                System.out.println(options.get(0));
                break;
            }
            int guess = guesser(options);
            System.out.println("guess number" + (i + 1) + ": " + "0".repeat(Math.max(0, digitCount - numOfDigits(guess))) + guess);
            int outcome = scanner.nextInt();
            options = removeOptions(options, guess, outcome);
        }
    }

    public static int guesser(ArrayList<Integer> options) {
        if (digitCount == 4 && options.size() == 1296)
            return 1122;
        int choice = 0;
        int bestWorstOutcomeRemoval = -1;
        for (int option = 1111; option < 6667; option++) {
            if (
                    Integer.toString(option).contains("7") ||
                            Integer.toString(option).contains("8") ||
                            Integer.toString(option).contains("9") ||
                            Integer.toString(option).contains("0")
            ) continue;
            int worstCaseRemoval = (int) Math.pow(10, digitCount);
            for (int i = 0; i < digitCount + 1; i++)
                for (int j = 0; j < digitCount + 1 - i; j++)
                    if (numOfRemoval(options, option, i * 10 + j) < worstCaseRemoval) {
                        worstCaseRemoval = numOfRemoval(options, option, i * 10 + j);
                    }
            if (worstCaseRemoval > bestWorstOutcomeRemoval) {
                choice = option;
                bestWorstOutcomeRemoval = worstCaseRemoval;
            }
        }
        return choice;
    }

    public static int numOfRemoval(ArrayList<Integer> options, int guess, int outCome) {
        int staying = 0;
        for (int option : options)
            if (outcomeOf(option, guess) == outCome)
                staying++;

        return options.size() - staying;
    }

    public static ArrayList<Integer> removeOptions(ArrayList<Integer> options, int guess, int outcome) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int option : options)
            if (outcomeOf(option, guess) == outcome)
                res.add(option);
        return res;
    }

    public static int outcomeOf(int goal, int choice) {
        int res = 0;
        String sChoice, sGoal;
        StringBuilder sbChoice = new StringBuilder();
        StringBuilder sbGoal = new StringBuilder();
        sbGoal.append("0".repeat(Math.max(0, digitCount - numOfDigits(goal))));
        sbChoice.append("0".repeat(Math.max(0, digitCount - numOfDigits(choice))));
        sGoal = sbGoal.append(goal).toString();
        sChoice = sbChoice.append(choice).toString();
        for (int i = 0; i < digitCount; i++)
            if (sGoal.charAt(i) == sChoice.charAt(i)) {
                sChoice = sChoice.substring(0, i) + 'n' + sChoice.substring(i + 1);
                sGoal = sGoal.substring(0, i) + 'n' + sGoal.substring(i + 1);
                res += 10;
            }
        for (int i = 0; i < sGoal.length(); i++) {
            for (int j = 0; j < sChoice.length(); j++) {
                if (sGoal.charAt(i) == sChoice.charAt(j) && i != j && sGoal.charAt(i) != 'n') {
                    res++;
                    sChoice = sChoice.substring(0, j) + 'n' + sChoice.substring(j + 1);
                    break;
                }
            }
        }
        return res;
    }

    public static int numOfDigits(int n) {
        if (n == 0)
            return 1;
        int res = 0;
        while (n != 0) {
            n /= 10;
            res++;
        }
        return res;
    }
}
