package parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {
    public static void main(String[] args) {
        System.out.println(parseString(null)); // expected -> Invalid data!
        System.out.println(parseString("")); // expected -> Invalid data!
        System.out.println(parseString("3[xyz]4[xy]z"));  // expected -> xyzxyzxyzxyxyxyxyz
        System.out.println(parseString("   --z]4[xy]z")); // expected -> Invalid data!
        System.out.println(parseString("+-+")); // expected -> Invalid data!
        System.out.println(parseString("asdada")); // expected -> Invalid data!
    }

    public static String parseString(String string) {
        String result;
        if (!checkString(string)) {
            result = "Invalid data!";
        } else {
            Pattern pattern = Pattern.compile("[(\\[{](.*?)[)\\]}]");
            Matcher matcher = pattern.matcher(string);
            List<String> list = new ArrayList<>();
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
            String[] nums = string.split("[^0-9]");
            List<Integer> numbers = new ArrayList<>();
            for (String s : nums) {
                if (isStringADigit(s)) {
                    numbers.add(Integer.parseInt(s));
                }
            }

            StringBuilder builder = new StringBuilder();

            String[] lastSplit = string.split("]");
            String lastValue = lastSplit[lastSplit.length - 1];

            for (int i = 0; i < numbers.size(); i++) {
                builder.append(String.valueOf(list.get(i)).repeat(Math.max(0, numbers.get(i))));
            }

            result = "Result is: " + builder.toString() + lastValue;
        }

        return result;
    }

    private static boolean checkString(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        } else {
            if (!string.contains("[") && !string.contains("]")) {
                return false;
            }
            char[] chars = string.toCharArray();
            for (Character c : chars) {
                if (!Character.isLetterOrDigit(c) && c != '[' && c != ']') {
                    return false;
                }
                int counter = 0;
                if (c == '[') {
                    counter++;
                }
                if (c == ']') {
                    counter--;
                }
                return counter == 0;
            }
        }
        return true;
    }

    private static boolean isStringADigit(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
