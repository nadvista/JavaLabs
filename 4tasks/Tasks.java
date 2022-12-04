import java.text.DecimalFormat;
import java.util.ArrayList;

class Task {
    public static void main(String[] args) {
        // Bessi(10, 7, "hello my name is Bessie and this is my essay");
        // clasters("((())())(()(()()))");
        // toCamelCase("is_modal_open");
        // toSnakeCase("getColor");
        overTime(new float[] { 16, 18, 30, 1.8f });
    }

    public static void bessi(int n, int k, String source) {
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
        for (var line : lines)
            System.out.println(line);
    }

    public static void clasters(String source) {
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
        for (var group : groups) {
            System.out.println(group);
        }
    }

    public static void toCamelCase(String source) {
        if (source.indexOf("_") == -1) {
            System.out.println(source);
            return;
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
        System.out.println(builder.toString());
    }

    public static void toSnakeCase(String source) {
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
        System.out.println(builder.toString());
    }

    public static void overTime(float[] inputs) {
        if (inputs.length != 4)
            return;
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

        System.out.println(String.format("$%.2f", answer));
    }
}