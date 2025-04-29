import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class KeyTranslator {
    private static final Pattern RESULT_PATTERN = Pattern.compile("\\\\result");

    public static String translateJMLToKey(List<JMLAnnotation> annotations) {
        StringBuilder keySpec = new StringBuilder();

        for (JMLAnnotation annotation : annotations) {
            if ("ensures".equals(annotation.getType())) {
                String content = annotation.getContent();
                // Replace \result with return value
                content = RESULT_PATTERN.matcher(content).replaceAll("return");

                // Translate logical operators
                content = content.replace("&&", "&");
                content = content.replace("||", "|");

                keySpec.append("ensures ").append(content).append(";\n");
            }
        }

        return keySpec.toString();
    }
}