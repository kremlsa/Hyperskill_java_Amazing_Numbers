package numbers;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static List<String> props = Arrays.asList("even", "odd", "buzz",
            "duck", "palindromic" , "gapful", "spy", "square", "sunny", "jumping");
    public static List<String> exl1 = new ArrayList<String> (Arrays.asList("even", "odd"));
    public static List<String> exl2 = Arrays.asList("square", "sunny");
    public static List<String> exl3 = Arrays.asList("spy", "duck");

    List<String> propertiesList;
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        List<String> propertiesList;

        printInstructions();
        while (true) {
            String input;
            int listSize = 1;
            String properties = "none";
            String propertiesTwo = "none";
            String mode = "none";
            System.out.println("Enter a request:");
            input = sc.nextLine();
            if (input.equals("")) {
                printInstructions();
                continue;
            }
            if (input.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }
            mode = getMode(input);
            if (mode.equals("none")) continue;

            String initialValue = removeZeroes(input.split(" ")[0]);

            switch (mode) {
                case "singleMode":
                    new Number(initialValue).printOne();
                    break;
                case "cycleMode":
                    listSize = Integer.parseInt(input.split(" ")[1]);
                    printCycle(input, initialValue, listSize);
                    break;
                case "typeMode":
                case "twoTypeMode":
                    listSize = Integer.parseInt(input.split(" ")[1]);
                    propertiesList = parseProperties(input);
                    printProps(initialValue, listSize, propertiesList);
                    break;

            }
        }
    }

    public static List<String> parseProperties(String input) {
        List<String> propertiesList = new ArrayList<>();
        for (int i = 2; i < input.split(" ").length; i++) {
            propertiesList.add(input.split(" ")[i].toLowerCase());
        }
        return propertiesList;
    }

    public static void printCycle(String input, String initialValue, int listSize) {
        List<Number> numbers = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            numbers.add(new Number(initialValue));
            initialValue = String.valueOf(Long.parseLong(initialValue) + 1L);
        }
        numbers.forEach(x -> System.out.println(x.toString()));
    }

    private static String getMode(String input) {
        Checker checker = new Checker();
        int size = input.split(" ").length;
        switch (size) {
            case 1:
                return checker.checkNumber(input).isOk() ? "singleMode" : "none";
            case 2:
                return checker.checkNumber(input)
                        .checkCycle(input).isOk() ? "cycleMode" : "none";
            case 3:
                return checker.checkNumber(input)
                        .checkCycle(input)
                        .checkMode(input).isOk() ? "typeMode" : "none";
            default:
                return checker.checkNumber(input)
                        .checkCycle(input)
                        .checkModeTwo(input)
                        .checkExclusive(input).isOk() ? "twoTypeMode" : "none";
        }
    }

    private static void printProps(String initialValue, int listSize, List<String> propertiesList) {
        int finds = 0;
        List<Number> numbers = new ArrayList<>();
        while (finds < listSize) {
            Number number = new Number(initialValue);
            if (number.toList().containsAll(propertiesList)) {
                numbers.add(number);
                finds++;
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
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit");
    }

    public static boolean checkIsDuck(String in) {
        return in.contains("0");
    }

    public static boolean checkIsEven(String in) {
        return Long.parseLong(in) % 2 == 0;
    }

    public static boolean checkIsBuzz(String in) {
        long cutNumber;
        boolean result = false;
        long lastDigit = Long.parseLong(in.substring(in.length() - 1));
        long num = Long.parseLong(in);
        if (num > 9) {
            cutNumber = Long.parseLong(in.substring(0, in.length() - 1));
        } else {
            cutNumber = num;
        }
        long buzzCheck = (cutNumber - lastDigit * 2) % 7;
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

    public static boolean checkIsJumping(String input) {
        if (input.length() == 1) return true;
        boolean isOk = true;
        int deltaNext = 0;
        int prev = Integer.parseInt(String.valueOf(input.charAt(0)));
        for (int i = 1; i < input.length(); i++) {
            int current = Integer.parseInt(String.valueOf(input.charAt(i)));
            int deltaPrev =  Math.abs(current - prev);
            if (i < input.length() - 1) {
                deltaNext = Math.abs(Integer.parseInt(String.valueOf(input.charAt(i + 1))) - current);
            } else deltaNext = 1;
            if (deltaPrev != 1 || deltaNext != 1) isOk = false;
            prev = current;
        }
        return isOk;
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

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    boolean isJumping = false;

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
        this.isJumping = Main.checkIsJumping(input);
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
                + (isSpy ? "spy, " : "")
                + (isJumping ? "jumping, " : "");
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public void printOne() {
        System.out.println("Properties of " + value + "\n" +
                "        buzz: " + isBuzz + "\n" +
                "        duck: " + isDuck + "\n" +
                "        palindromic: " + isPalindrome + "\n" +
                "        gapful: " + isGapful + "\n" +
                "        spy: " + isSpy + "\n" +
                "        square: " + isSquare + "\n" +
                "        sunny: " + isSunny + "\n" +
                "        even: " + isEven + "\n" +
                "         odd: " + !isEven + "\n" +
                "         jumping: " + isJumping);
    }

    public List<String> toList() {
        List<String> result = new ArrayList<>();
        if (isBuzz) result.add("buzz");
        if (isDuck) result.add("duck");
        if(isPalindrome) result.add("palindromic");
        if(isGapful) result.add("gapful");
        if(isSpy) result.add("spy");
        if(isSquare) result.add("square");
        if(isSunny) result.add("sunny");
        if(isEven) result.add("even");
        if(!isEven) result.add("odd");
        if(isJumping) result.add("jumping");
        return result;
    }
}

class Checker {
    public boolean isOk = true;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public Checker checkNumber(String input) {
        if (!input.split(" ")[0].matches("[0-9 -]+")) {
            System.out.println("The first parameter should be a natural number or zero.");
            isOk = false;
            return this;
        }
        if (Long.parseLong(input.split(" ")[0]) < 0L) {
            System.out.println("The first parameter should be a natural number or zero.");
            isOk = false;
        }
        return this;
    }

    public Checker checkCycle(String input) {
        if (Long.parseLong(input.split(" ")[1]) < 0L) {
            System.out.println("The second parameter should be a natural number or zero.");
            isOk = false;
        }
        return this;
    }

    public Checker checkMode(String input) {
        if (!Main.props.contains(input.split(" ")[2].toLowerCase())) {
            System.out.println("The property [" + input.split(" ")[2] + "] is wrong.\n" +
                    "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, EVEN, ODD, JUMPING]");
            isOk = false;
        }
        return this;
    }

    public Checker checkModeTwo(String input) {
        String wrongProps = "[";
        int wrongs = 0;
        for (int i = 2; i < input.split(" ").length; i++) {
            if (!Main.props.contains(input.split(" ")[i].toLowerCase())) {
                wrongProps += input.split(" ")[i] + ", ";
                wrongs++;
                isOk = false;
            }
        }
        if (wrongProps.length() > 3) {
            wrongProps = wrongProps.substring(0, wrongProps.length() - 2);
        }
        wrongProps += "]";
        if (!isOk) {
            if (wrongs > 1) {
                System.out.println("The properties " + wrongProps + " are wrong.\n" +
                        "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, EVEN, ODD, JUMPING]");
            } else {
                System.out.println("The property " + wrongProps + " is wrong.\n" +
                    "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, EVEN, ODD, JUMPING]");
            }
        }
        return this;
    }

    public Checker checkExclusive(String input) {
        List<String> propertiesList = Main.parseProperties(input);
        if (propertiesList.containsAll(Main.exl1)) {
            isOk = false;
            System.out.println("The request contains mutually exclusive properties: [" +
                    Main.exl1.get(0) + ", " + Main.exl1.get(1) + "]\n" +
                    "There are no numbers with these properties.");
            return this;
        }
        if (propertiesList.containsAll(Main.exl2)) {
            isOk = false;
            System.out.println("The request contains mutually exclusive properties: [" +
                    Main.exl2.get(0) + ", " + Main.exl2.get(1) + "]\n" +
                    "There are no numbers with these properties.");
            return this;
        }
        if (propertiesList.containsAll(Main.exl3)) {
            isOk = false;
            System.out.println("The request contains mutually exclusive properties: [" +
                    Main.exl3.get(0) + ", " + Main.exl3.get(1) + "]\n" +
                    "There are no numbers with these properties.");
            return this;
        }

        return this;
    }


}
