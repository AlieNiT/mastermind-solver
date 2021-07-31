package code;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int digitCount = 2;
    public static void main(String[] args) {
        ArrayList<Integer> options = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < Math.pow(10, digitCount); i++)
            options.add(i);

        while (true) {
            int guess = guesser(options);
            System.out.println(guess);
            int outcome = scanner.nextInt();
            options = removeOptions(options, guess, outcome);
//            for (int option : options) {
//                System.out.println(option);
//            }
        }
    }

    public static int guesser(ArrayList<Integer> options) {
        if (digitCount == 4 && options.size() == Math.pow(10 , digitCount))
            return 1122;
        System.out.println("////");
        for (int option : options) {
            System.out.println(option);
        }
        System.out.println("////");
        int choice = 0;
        int tmp = 0;
        int bestWorstOutcomeRemoval = 0;
        for (int option : options) {
            int worstCaseRemoval = 1000;
            for (int i = 0; i < digitCount + 1; i++)
                for (int j = 0; j < digitCount + 1 - i; j++)
                    if (numOfRemoval(options, option, i * 10 + j) < worstCaseRemoval)
                        worstCaseRemoval = numOfRemoval(options, option, i * 10 + j);
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
            if (outComeOf(option, guess) == outCome)
                staying++;

        return options.size() - staying;
    }

    public static ArrayList<Integer> removeOptions(ArrayList<Integer> options, int guess, int outcome) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int option : options)
            if (outComeOf(option, guess) == outcome)
                res.add(option);
        return res;
    }

    public static int outComeOf(int goal, int choice) {
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
        int res = 0;
        while (n != 0) {
            n /= 10;
            res++;
        }
        return res;
    }
}
