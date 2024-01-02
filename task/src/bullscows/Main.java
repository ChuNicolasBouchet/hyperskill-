package bullscows;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String secretCode = "";
    static int secretLength = 0;
    static int countBulls = 0;
    static int countCows = 0;

    public static void main(String[] args) {
        secretCode = generateSecret();
        System.out.println("Okay, let's start a game!");
        int turnCounter = 1;
        System.out.println("The secret is: " + secretCode);
        while (countBulls != secretLength) {
            resetBulls();
            resetCows();
            System.out.printf("Turn %d:%n", turnCounter);
            checkCode(secretCode, userInput());
            turnCounter++;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    protected static String generateSecret() {
        Scanner scanner = new Scanner(System.in);
        SecureRandom random = new SecureRandom();
        System.out.println("Input the length of the secret code:");
        while (secretLength <= 0 || secretLength > 36) {
            secretLength = scanner.nextInt();
            if (secretLength > 36) {
                System.out.printf("Error: can't generate a secret number with a length of %d because it's over the limit of 36.%n", secretLength);
            }
        }
        System.out.println("Input the number of possible symbols in the code:");
        int symbolRange = 0;
        while (symbolRange <= 0 || symbolRange < secretLength) {
            symbolRange = scanner.nextInt();
            if (symbolRange < secretLength) {
                System.out.printf("Error: can't generate a secret with %d characters, because there aren't enough unique characters with a length of %d symbols.%n", symbolRange, secretLength);
            } else if (symbolRange > 36) {
                System.out.println("Error: too much symbols range, the secret is limited to 36 symbols");
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("The secret is prepared: ");
        char star = '*';
        sb.append(String.valueOf(star).repeat(secretLength));

        int digitsLength;
        int alphasLength;
        String combined;
        if (symbolRange > 10) {
            alphasLength = random.nextInt(symbolRange - 10) + 1;
            digitsLength = secretLength - alphasLength;
            int correctedSymbolRange = symbolRange - 10;
            combined = digitGenerator(digitsLength) + alphaGenerator(alphasLength, correctedSymbolRange);
            sb.append(" (0-9, a-");
            char rangeEnd = (char) ((char) symbolRange + 86);
            sb.append(rangeEnd);
        } else {
            combined = digitGenerator(secretLength);
            sb.append(" (0-9");
        }
        sb.append(").");
        return shuffleStrings(combined);
    }

    public static String shuffleStrings(String combined) {
        char[] charsBefore = combined.toCharArray();
        char[] charsAfter = new char[combined.length()];
        boolean[] isWritten = new boolean[combined.length()];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < combined.length(); i++) {
            int randomIndex;
            do {
                randomIndex = random.nextInt(charsBefore.length);
            } while (isWritten[randomIndex]);
            charsAfter[randomIndex] = charsBefore[i];
            isWritten[randomIndex] = true;
        }
        return String.valueOf(charsAfter);
    }

    public static String alphaGenerator(int length, int range) {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            int index = random.nextInt(range);
            char c = alphabet.charAt(index);
            if (sb.indexOf(String.valueOf(c)) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String digitGenerator(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sbDigit = new StringBuilder();
        while (sbDigit.length() < length) {
            long digit = random.nextLong(10);
            if (sbDigit.indexOf(String.valueOf(digit)) == -1) {
                sbDigit.append(digit);
            }
        }
        System.out.println(sbDigit.toString());
        return sbDigit.toString();
    }

    protected static void checkCode(String secretCode, String userCode) {
        for (int i = 0; i < secretCode.length(); i++) {
            char charSecret = secretCode.charAt(i);
            char charGuess = userCode.charAt(i);
            if (charSecret == charGuess) {
                addBulls();
            } else if (secretCode.indexOf(charGuess) > 0) {
                addCows();
            }
        }
        printAnswer(countBulls, countCows);
    }

    protected static void printAnswer(int bull, int cow) {

        String cowS = "";
        String bullS = "";
        if (cow > 1) {
            cowS = "s";
        }
        if (bull > 1) {
            bullS = "s";
        }
        if (cow > 0 && bull == 0) {
            System.out.printf("Grade: %d cow%s.%n", cow, cowS);
        } else if (cow == 0 && bull > 0) {
            System.out.printf("Grade: %d bull%s.%n", bull, bullS);
        } else if (cow > 0 && bull > 0) {
            System.out.printf("Grade: %d bull%s and %d cow%s.%n", bull, bullS, cow, cowS);
        } else {
            System.out.printf("Grade: None.%n");
        }
    }

    protected static String userInput() {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while (!isValidInput(userInput, secretLength)) {
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    protected static boolean isValidInput(String userInput, int secretLength) {
        if (userInput == null || userInput.length() != secretLength) {
            return false;
        }
        return true;
    }

    static void addBulls() {
        countBulls++;
    }

    static void resetBulls() {
        countBulls = 0;
    }

    static void addCows() {
        countCows++;
    }

    static void resetCows() {
        countCows = 0;
    }

    public static boolean isLengthValid(String length) {
        return length.matches("\\d{1,10}");
    }
}
    //    public static String digitGenerator(int length){
//        int min = (int) Math.pow(10, length -1); // 10ee digit -1
//        int max = (int) Math.pow(10, length) -1;
//        System.out.println(min);
//        System.out.println(max);
//        int generated;
//        do {
//            int randomNumber = (int) (Math.random() * 9) + 1;
//            Random random = new Random(randomNumber);
//            generated = random.nextInt(max - min + 1) + min;
//            System.out.println(generated);
//        } while (!areDigitsUnique(generated));
//        return String.valueOf(generated);
//    }
//
//    public static boolean areDigitsUnique(int number) {
//        boolean[] digits = new boolean[10];
//        while (number > 0) {
//            int digit = number % 10;
//            if (digits[digit]){
//                return false;
//            }
//            digits[digit] = true;
//            number /= 10;
//        }
//        return true;
//    }
//}
