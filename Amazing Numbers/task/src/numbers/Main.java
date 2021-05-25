package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        List<String> props = Arrays.asList("even", "odd", "buzz",
                "duck", "palindromic" , "gapful", "spy");
        printInstructions();
        while (true) {
            String input;
            int listSize = 1;
            String properties = "none";
            List<Number> numbers = new ArrayList<>();
            System.out.println("Enter a request:");
            input = sc.nextLine();
            if (input.equals("")) {
                printInstructions();
                continue;
            }
            if (!input.split(" ")[0].matches("[0-9 -]+")) {
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

                if (input.split(" ").length > 2) {
                    if (!props.contains(input.split(" ")[2].toLowerCase())) {
                        System.out.println("The property [" + input.split(" ")[2] + "] is wrong.\n" +
                                "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY]");
                        continue;
                    }
                    properties = input.split(" ")[2].toLowerCase();
                }
            }

            String initialValue = removeZeroes(input.split(" ")[0]);

            switch (properties) {
                case "none":
                    for (int i = 0; i < listSize; i++) {
                        numbers.add(new Number(initialValue));
                        initialValue = String.valueOf(Long.parseLong(initialValue) + 1L);
                    }
                    if (input.split(" ").length > 1) {
                        numbers.forEach(x -> System.out.println(x.toString()));
                    } else {
                        numbers.forEach(Number::printOne);
                    }
                    break;
                    default:
                        printProps(initialValue, listSize, properties);
                        break;
            }
        }
    }

    private static void printProps(String initialValue, int listSize, String properties) {
        int finds = 0;
        List<Number> numbers = new ArrayList<>();
        while (finds < listSize) {
            switch (properties) {
                case "even":
                    if (checkIsEven(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));}
                    break;
                case "odd":
                    if (!checkIsEven(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));
                    }
                    break;
                case "duck":
                    if (checkIsDuck(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));
                    }
                    break;
                case "buzz":
                    if (checkIsBuzz(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));
                    }
                    break;
                case "palindromic":
                    if (checkIsPalindrome(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));
                    }
                    break;
                case "gapful":
                    if (checkIsGapful(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));
                    }
                    break;
                case "spy":
                    if (checkIsSpy(initialValue)) {
                        finds++;
                        numbers.add(new Number(initialValue));
                    }
                    break;
            }
            initialValue = String.valueOf(Long.parseLong(initialValue) + 1L);
        }
        numbers.forEach(x -> System.out.println(x.toString()));
    }

    public static void printInstructions() {
        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and a property to search for;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
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


    public static boolean checkIsSpy(String input) {
        long product = 1;
        for (String s : input.split("")) {
            product *= Long.parseLong(s);
        }
        long sum = 0;
        for (String s : input.split("")) {
            sum += Long.parseLong(s);
        }
        return sum == product;
    }

    public static boolean checkIsSquare(String input) {
        long sr = (long) Math.sqrt(Long.parseLong(input));
        return ((sr * sr) == Long.parseLong(input));
    }

    public static boolean checkIsSunny(String input) {
        input = String.valueOf(Long.parseLong(input) + 1L);
        long sr = (long) Math.sqrt(Long.parseLong(input));
        return ((sr * sr) == Long.parseLong(input));
    }
}

class Number {
    long value;
    boolean isEven = false;
    boolean isBuzz = false;
    boolean isDuck = false;
    boolean isPalindrome = false;
    boolean isGapful = false;
    boolean isSpy = false;
    boolean isSquare = false;
    boolean isSunny = false;

    public boolean isSpy() {
        return isSpy;
    }

    public void setSpy(boolean spy) {
        isSpy = spy;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public void setSquare(boolean square) {
        isSquare = square;
    }

    public boolean isSunny() {
        return isSunny;
    }

    public void setSunny(boolean sunny) {
        isSunny = sunny;
    }

    public Number(String input) {
        this.value = Long.parseLong(input);
        this.isEven = Main.checkIsEven(input);
        this.isBuzz = Main.checkIsBuzz(input);
        this.isDuck = Main.checkIsDuck(input);
        this.isPalindrome = Main.checkIsPalindrome(input);
        this.isGapful = Main.checkIsGapful(input);
        this.isSpy = Main.checkIsSpy(input);
        this.isSquare = Main.checkIsSquare(input);
        this.isSunny = Main.checkIsSunny(input);
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
                + (isGapful ? "gapful, " : "")
                + (isSquare ? "square, " : "")
                + (isSunny ? "sunny, " : "")
                + (isSpy ? "spy, " : "");
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public void printOne() {
        System.out.println("Properties of " + value + "\n" +
                "        buzz: " + isBuzz + "\n" +
                "        duck: " + isDuck + "\n" +
                "        palindromic: " + isPalindrome + "\n" +
                "        gapful: " + isGapful + "\n" +
                "        spy: " + isSpy + "\n" +
                "        square: " + isSquare + "\n" +
                "        sunny: " + isSunny + "\n" +
                "        even: " + isEven + "\n" +
                "         odd: " + !isEven);
    }
}
