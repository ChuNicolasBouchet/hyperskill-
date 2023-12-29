package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String secretCode = "9305";
        String guessCode = guessInput();
        int countBull = 0;
        int countCow = 0;
        int index = 0;
        for (int i = 0; i < secretCode.length(); i++){
            char charSecret = secretCode.charAt(i);
            char charGuess = guessCode.charAt(i);
            if (charSecret == charGuess){
                countBull++;
            } else if (secretCode.indexOf(charGuess) > 0) {
                countCow++;
            }
        }

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

    protected static String guessInput(){
        Scanner scanner = new Scanner(
                System.in);
        String guessInput = "";
        while (!isValidInput(guessInput)) {
            guessInput = scanner.nextLine();
        }
        return guessInput;
    }
    protected static boolean isValidInput(String input){
        return input.matches("\\d\\d\\d\\d");
    }

}
