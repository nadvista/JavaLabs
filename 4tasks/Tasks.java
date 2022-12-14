import java.util.ArrayList;

class Task {
    public static void main(String[] args) {
        // Bessi(10, 7, "hello my name is Bessie and this is my essay");
        // clasters("((())())(()(()()))");
        // toCamelCase("is_modal_open");
        // toSnakeCase("getColor");
        // overTime(new float[] { 13.25f, 15, 30, 1.5f });
        // BMI("154 pounds", "2 meters");
        // bugger(4);
        // toStarShorthand("77777geff");
        // System.out.println(doesRhyme("You are off to the races", "a splendid day."));
        // System.out.println(trouble(666789, 12345667));
        // System.out.println(countUniqueBooks("ZZABCDEF", 'Z'));
    }

    public static ArrayList<String> bessi(int n, int k, String source) {
        var words = source.split(" ");
        var lines = new ArrayList<String>();
        var currentLine = new StringBuilder();
        var currentLength = 0;
        var startNewLine = true;

        for (var word : words) {
            var length = word.length();
            if (currentLength + length > k) {
                lines.add(currentLine.toString());
                currentLine.setLength(0);
                currentLength = 0;
                startNewLine = true;
            }

            currentLength += length;

            if (!startNewLine)
                currentLine.append(" ");
            else
                startNewLine = false;

            currentLine.append(word);
        }
        lines.add(currentLine.toString());
        return lines;
    }

    public static ArrayList<String> clasters(String source) {
        var groups = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (var i = 0; i < source.length(); i++) {
            var symbol = source.charAt(i);
            if (symbol == '(')
                depth++;
            else
                depth--;
            builder.append(symbol);
            if (depth == 0) {
                groups.add(builder.toString());
                builder.setLength(0);
                depth = 0;
            }
        }
        return groups;
    }

    public static String toCamelCase(String source) {
        if (source.indexOf("_") == -1) {
            return source;
        }

        var builder = new StringBuilder();
        var previousSnake = false;
        for (var i = 0; i < source.length(); i++) {
            var symbol = source.charAt(i);
            if (symbol == '_') {
                previousSnake = true;
            } else if (previousSnake) {
                builder.append(Character.toUpperCase(symbol));
                previousSnake = false;
            } else {
                builder.append(symbol);
            }
        }
        return builder.toString();
    }

    public static String toSnakeCase(String source) {
        var builder = new StringBuilder();
        for (var i = 0; i < source.length(); i++) {
            var symbol = source.charAt(i);
            if (Character.isUpperCase(symbol)) {
                builder.append('_');
                builder.append(Character.toLowerCase(symbol));
            } else {
                builder.append(symbol);
            }
        }
        return builder.toString();
    }

    public static String overTime(float[] inputs) throws Exception {
        if (inputs.length != 4)
            throw new Exception("Неправильный ввод");
        var startTime = inputs[0];
        var endTime = inputs[1];
        var salary = inputs[2];
        var multiplyer = inputs[3];

        var answer = 0f;

        for (int i = (int) (startTime - startTime % 1f) + 1; i < (int) (endTime - endTime % 1f); i++) {
            if (i >= 9 && i < 17)
                answer += salary;
            else
                answer += salary * multiplyer;
        }
        if (startTime < 9 && startTime > 17)
            answer += (1 - startTime % 1f) * salary * multiplyer;
        else
            answer += (1 - startTime % 1f) * salary;

        if (endTime < 9 && endTime > 17)
            answer += (endTime % 1f) * salary * multiplyer;
        else
            answer += (endTime % 1f) * salary;

        return String.format("$%.2f", answer);
    }

    public static String BMI(String weight, String height) {
        final float POUNDS_TO_KILOS = 0.45f;
        final float INCHES_TO_METERS = 0.025f;
        var weightParams = weight.split(" ");
        var heightParams = height.split(" ");

        var weightKilos = weightParams[1].equals("pounds") ? Float.parseFloat(weightParams[0]) * POUNDS_TO_KILOS
                : Float.parseFloat(weightParams[0]);

        var heightMeters = heightParams[1].equals("inches") ? Float.parseFloat(heightParams[0]) * INCHES_TO_METERS
                : Float.parseFloat(heightParams[0]);

        var bmi = weightKilos / Math.pow(heightMeters, 2);
        var status = bmi < 18.5f ? "Underweight" : bmi < 25f ? "Normal weight" : "Overweight";

        var answer = String.format("%s %s", bmi, status);
        return answer;
    }

    public static int bugger(int num) {
        var iteration = 0;
        while (num / 10 > 0) {
            iteration++;
            var mult = 1;
            while (num != 0) {
                var digit = num % 10;
                num -= digit;
                num /= 10;
                mult *= digit;
            }
            num = mult;
        }
        return iteration;
    }

    public static String toStarShorthand(String input) {
        var lastSymbol = input.charAt(0);
        var length = 0;
        var builder = new StringBuilder();
        for (var symbol : input.toCharArray()) {
            if (symbol == lastSymbol)
                length++;
            else {
                if (length > 1)
                    builder.append(String.format("%s*%s", lastSymbol, length));
                else
                    builder.append(lastSymbol);
                length = 1;
                lastSymbol = symbol;
            }
        }
        if (length > 1)
            builder.append(String.format("%s*%s", lastSymbol, length));
        else
            builder.append(lastSymbol);

        return builder.toString();
    }

    public static boolean doesRhyme(String a, String b) {
        var aWords = a.split("");
        var aLastWord = aWords[aWords.length - 1].toLowerCase();

        var bWords = b.split("");
        var bLastWord = bWords[bWords.length - 1].toLowerCase();

        for (char symbol : bLastWord.toCharArray()) {
            if ("aeiou".indexOf(symbol) == -1)
                continue;
            if (aLastWord.indexOf(symbol) == -1)
                return false;
        }
        for (char symbol : aLastWord.toCharArray()) {
            if ("aeiou".indexOf(symbol) == -1)
                continue;
            if (bLastWord.indexOf(symbol) == -1)
                return false;
        }

        return true;
    }

    public static boolean trouble(long a, long b) {
        var aString = Long.toString(a);
        var bString = Long.toString(b);

        for (int i = 0; i < 10; i++) {
            var seqB = String.format("%s%s", i, i);
            var seqA = String.format("%s%s%s", i, i, i);
            if (aString.contains(seqA) && bString.contains(seqB))
                return true;
        }
        return false;
    }

    public static int countUniqueBooks(String input, char separator) {
        var uniqueBooks = new ArrayList<Character>();
        var started = false;
        for (var book : input.toCharArray()) {
            if (started) {
                if (book == separator)
                    started = false;
                else if (!uniqueBooks.contains(book))
                    uniqueBooks.add(book);
            } else if (book == separator)
                started = true;
        }
        return uniqueBooks.size();
    }
}