package bullscows;

import java.util.Scanner;

public class Main {
    static String secretCode = "9305";
    static String userCode = userInput();
    static int countBulls = 0;
    static int countCows = 0;

    public static void main(String[] args) {
        checkCode(secretCode,userCode);
        printAnswer(countBulls,countCows,secretCode);
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
}
