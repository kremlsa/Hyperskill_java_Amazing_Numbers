package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        printInstructions();
        while (true) {
            String input;
            int listSize = 1;
            List<Number> numbers = new ArrayList<>();
            System.out.println("Enter a request:");
            input = sc.nextLine();
            if (input.equals("")) {
                printInstructions();
                continue;
            }
            if (!input.matches("[0-9 -]+")) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }
            if (input.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }

            if (Long.parseLong(input.split(" ")[0]) < 0L) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }

            if(input.split(" ").length > 1) {
                if (Long.parseLong(input.split(" ")[1]) < 0L) {
                    System.out.println("The second parameter should be a natural number or zero.");
                    continue;
                }
                listSize = Integer.parseInt(input.split(" ")[1]);
            }
            String initialValue = removeZeroes(input.split(" ")[0]);
            for (int i = 0; i < listSize; i++) {
                numbers.add(new Number(initialValue));
                initialValue = String.valueOf(Long.parseLong(initialValue) + 1L);
            }
            if (input.split(" ").length > 1) {
                numbers.forEach(x -> System.out.println(x.toString()));
            } else {
                numbers.forEach(Number::printOne);
            }
        }
    }

    public static void printInstructions() {
        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameters show how many consecutive numbers are to be processed;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
    }

    public static boolean checkIsDuck(String in) {
        return in.contains("0");
    }

    public static boolean checkIsEven(String in) {
        return Long.valueOf(in) % 2 == 0 ? true : false;
    }

    public static boolean checkIsBuzz(String in) {
        long cutNumber;
        boolean result = false;
        long lastDigit = Long.valueOf(in.substring(in.length() - 1));
        Long num = Long.valueOf(in);
        if (num > 9) {
            cutNumber = Long.valueOf(in.substring(0, in.length() - 1));
        } else {
            cutNumber = num;
        }
        Long buzzCheck = (cutNumber - lastDigit * 2) % 7;
        if (buzzCheck == 0L) {
            result = true;
        } else {
            if (in.endsWith("7")) {
                result = true;
            }
        }
        return result;
    }

    public static boolean checkIsGapful(String in) {
        if (in.length() < 3) return false;
        String lastDigit = in.substring(in.length() - 1);
        String firstDigit = in.substring(0, 1);
        long divider = Long.parseLong(firstDigit + lastDigit);
        long num = Long.parseLong(in);
        return num % divider == 0;
    }

    public static String removeZeroes(String in) {
        while(true) {
            if (in.startsWith("0")) {
                in = in.substring(1);
            } else {
                break;
            }
        }
        return in;
    }

    public static boolean checkIsPalindrome(String in) {
        int i = 0, j = in.length() - 1;
        while (i < j) {
            if (in.charAt(i) != in.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }
}

class Number {
    long value;
    boolean isEven = false;
    boolean isBuzz = false;
    boolean isDuck = false;
    boolean isPalindrome = false;
    boolean isGapful = false;

    public Number(String input) {
        this.value = Long.parseLong(input);
        this.isEven = Main.checkIsEven(input);
        this.isBuzz = Main.checkIsBuzz(input);
        this.isDuck = Main.checkIsDuck(input);
        this.isPalindrome = Main.checkIsPalindrome(input);
        this.isGapful = Main.checkIsGapful(input);
    }

    public boolean isGapful() {
        return isGapful;
    }

    public void setGapful(boolean gapful) {
        isGapful = gapful;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isEven() {
        return isEven;
    }

    public void setEven(boolean even) {
        isEven = even;
    }

    public boolean isBuzz() {
        return isBuzz;
    }

    public void setBuzz(boolean buzz) {
        isBuzz = buzz;
    }

    public boolean isDuck() {
        return isDuck;
    }

    public void setDuck(boolean duck) {
        isDuck = duck;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(boolean palindrome) {
        isPalindrome = palindrome;
    }

    @Override
    public String toString() {
        String result = value + " is "
                + (isEven ? "even, " : "odd, ")
                + (isBuzz ? "buzz, " : "")
                + (isDuck ? "duck, " : "")
                + (isPalindrome ? "palindromic, " : "")
                + (isGapful ? "gapful, " : "");
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public void printOne() {
        System.out.println("Properties of " + value + "\n" +
                "        buzz: " + isBuzz + "\n" +
                "        duck: " + isDuck + "\n" +
                "        palindromic: " + isPalindrome + "\n" +
                "        gapful: " + isGapful + "\n" +
                "        even: " + isEven + "\n" +
                "         odd: " + !isEven);
    }
}
