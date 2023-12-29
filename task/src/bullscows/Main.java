package bullscows;

import javax.annotation.processing.Generated;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String secretCode = "9305";
    //static String userCode = userInput();
    static int countBulls = 0;
    static int countCows = 0;

    public static void main(String[] args) {
        //checkCode(secretCode,userCode);
        //printAnswer(countBulls,countCows,secretCode);
        System.out.printf("The random secret number is %d.", generateSecret());
    }


    protected static int generateSecret (){
        Scanner scanner = new Scanner(System.in);
        int numDigit = 0;
        while (numDigit <=0 || numDigit > 10) {
            numDigit = scanner.nextInt();
            if (numDigit > 10) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", numDigit);
            }
        }

        int min = (int) Math.pow(10, numDigit -1); // 10ee digit -1
        int max = (int) Math.pow(10, numDigit) -1;
        int generated;
        do {
            long pseudoRandomNumber = System.nanoTime();
            Random random = new Random(pseudoRandomNumber);
            generated = random.nextInt(max - min + 1) + min;
        } while (!areDigitsUnique(generated, numDigit));
        return generated;
    }

    public static boolean areDigitsUnique(int number, int length) {
        boolean[] digits = new boolean[10]; // for digits 0 to 9, all initialized at false
        //loop from the end to the beginning of the number
        while (number > 0) {
            int digit = number % 10; //extraction of the last digit
            //check if the digit is already at true in the array
            if (digits[digit]){
                return false; // if already at true -> not unique -> return false
            }
            digits[digit] = true; // if not found switch the presence of that digit to true
            number /= 10; // cut the last digit by dividing by 10
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
    }

    protected static void printAnswer(int countBull, int countCow, String secretCode) {
        if (countCow > 0 && countBull == 0){
            System.out.printf("Grade: %d cow(s). The secret code is %s." ,countCow, secretCode);
        } else if (countCow == 0 && countBull > 0) {
            System.out.printf("Grade: %d bull(s). The secret code is %s.", countBull, secretCode);
        } else if (countCow > 0 && countBull > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s). The secret code is %s.", countBull,countCow,secretCode);
        } else {
            System.out.printf("Grade: None. The secret code is %s", secretCode);
        }
    }
    protected static String userInput(){
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while (!isValidInput(userInput)) {
            userInput = scanner.nextLine();
        }
        return userInput;
    }
    protected static boolean isValidInput(String input){
        return input.matches("\\d\\d\\d\\d");
    }


    static void addBulls() {
        countBulls++;
    }
    static void addCows() {
        countCows++;
    }
    public static boolean isLengthValid (String length){
        return length.matches("\\d{1,10}");
    }
}
