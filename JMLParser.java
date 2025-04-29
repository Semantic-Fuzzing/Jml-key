import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public class JMLParser {
    private static final Pattern JML_PATTERN = Pattern.compile("\\s*//\\s*@\\s*(\\w+)\\s*(.*)");

    public static List<JMLAnnotation> parseAnnotations(String fileContent) {
        List<JMLAnnotation> annotations = new ArrayList<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            line = line.trim();
            Matcher matcher = JML_PATTERN.matcher(line);
            if (matcher.matches()) {
                String type = matcher.group(1);
                String content = matcher.group(2).trim();
                annotations.add(new JMLAnnotation(type, content));
            }
        }

        return annotations;
    }
}

class JMLAnnotation {
    private final String type;
    private final String content;

    public JMLAnnotation(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}