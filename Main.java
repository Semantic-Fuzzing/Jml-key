import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    private static final Pattern METHOD_PATTERN = Pattern.compile(
            "public\\s+static\\s+(\\w+)\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*\\{([^}]*)\\}");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <java_file>");
            return;
        }

        try {
            // Read input file
            String inputFile = args[0];
            String fileContent = new String(Files.readAllBytes(Paths.get(inputFile)));

            // Parse and translate annotations
            List<JMLAnnotation> annotations = JMLParser.parseAnnotations(fileContent);
            String keySpec = KeyTranslator.translateJMLToKey(annotations);

            // Extract method information
            Matcher matcher = METHOD_PATTERN.matcher(fileContent);
            String methodInfo = "";
            String methodCode = "";
            if (matcher.find()) {
                String returnType = matcher.group(1);
                String methodName = matcher.group(2);
                String params = matcher.group(3);
                methodCode = matcher.group(4).trim();

                methodInfo = String.format("method %s %s(%s) {", returnType, methodName, params);
            }

            // Create output content
            String outputContent = keySpec + "\n" + methodInfo + "\n" + methodCode + "\n}";

            // Write to new file
            String outputFile = inputFile.replace(".java", "_output.key");
            Files.write(
                    Paths.get(outputFile),
                    outputContent.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Translation saved to " + outputFile);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}