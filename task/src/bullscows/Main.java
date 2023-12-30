package bullscows;

import javax.annotation.processing.Generated;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String secretCode = "";
    static int secretDigits = 0;
    static int countBulls = 0;
    static int countCows = 0;

    public static void main(String[] args) {
        secretCode = generateSecret();
        System.out.println("Okay, let's start a game!");
        int turnCounter = 1;
//        System.out.println("The secret is: " + secretCode);
        while (countBulls != secretDigits) {
            resetBulls();
            resetCows();
            System.out.printf("Turn %d:%n", turnCounter);
            checkCode(secretCode,userInput());
            turnCounter++;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    protected static String generateSecret (){
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);

        while (secretDigits <=0 || secretDigits > 10) {
            secretDigits = scanner.nextInt();
            if (secretDigits > 10) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", secretDigits);
            }
        }
        int min = (int) Math.pow(10, secretDigits -1); // 10ee digit -1
        int max = (int) Math.pow(10, secretDigits) -1;
        int generated;
        do {
            int randomNumber = (int) (Math.random() * 9) + 1;
            Random random = new Random(randomNumber);
            generated = random.nextInt(max - min + 1) + min;
        } while (!areDigitsUnique(generated));
        return String.valueOf(generated);
    }
//    protected static String generateSecret (){
//        System.out.println("Please, enter the secret code's length:");
//        Scanner scanner = new Scanner(System.in);
//
//        while (secretDigits <=0 || secretDigits > 10) {
//            secretDigits = scanner.nextInt();
//            if (secretDigits > 10) {
//                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", secretDigits);
//            }
//        }
//        int min = (int) Math.pow(10, secretDigits -1); // 10ee digit -1
//        int max = (int) Math.pow(10, secretDigits) -1;
//        int generated;
//        do {
//            long pseudoRandomNumber = System.nanoTime();
//            Random random = new Random(pseudoRandomNumber);
//            generated = random.nextInt(max - min + 1) + min;
//        } while (!areDigitsUnique(generated));
//        return String.valueOf(generated);
//    }

    public static boolean areDigitsUnique(int number) {
        boolean[] digits = new boolean[10];
        while (number > 0) {
            int digit = number % 10;
            if (digits[digit]){
                return false;
            }
            digits[digit] = true;
            number /= 10;
        }
        return true;
    }

    protected static void checkCode (String secretCode, String userCode) {
        for (int i = 0; i < secretCode.length(); i++){
            char charSecret = secretCode.charAt(i);
            char charGuess = userCode.charAt(i);
            if (charSecret == charGuess){
                addBulls();
            } else if (secretCode.indexOf(charGuess) > 0) {
                addCows();
            }
        }
        printAnswer(countBulls,countCows);
    }

    protected static void printAnswer(int bull, int cow) {

        String cowS = "";
        String bullS = "";
        if (cow > 1){
            cowS = "s";
        }
        if (bull > 1){
            bullS = "s";
        }
        if (cow > 0 && bull == 0){
            System.out.printf("Grade: %d cow%s.%n" ,cow, cowS);
        } else if (cow == 0 && bull > 0) {
            System.out.printf("Grade: %d bull%s.%n", bull, bullS);
        } else if (cow > 0 && bull > 0) {
            System.out.printf("Grade: %d bull%s and %d cow%S.%n", bull, bullS, cow, cowS);
        } else {
            System.out.printf("Grade: None.%n");
        }
    }
    protected static String userInput(){
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while (!isValidInput(userInput, secretDigits)) {
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    protected static boolean isValidInput(String userInput, int secretDigits){
        if (userInput == null || userInput.length() != secretDigits ){
            return false;
        }
        for (int i = 0; i < secretDigits; i++){
            if (!Character.isDigit(userInput.charAt(i))){
                return false;
            }
        }
        return true;
    }

    static void addBulls() {
        countBulls++;
    }
    static void resetBulls(){
        countBulls = 0;
    }
    static void addCows() {
        countCows++;
    }
    static void resetCows(){
        countCows = 0;
    }
    public static boolean isLengthValid (String length){
        return length.matches("\\d{1,10}");
    }
}
