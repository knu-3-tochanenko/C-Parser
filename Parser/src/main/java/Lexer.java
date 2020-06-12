import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Very simple C Language lexer.
 * It DOES NOT identify DOUBLE, FLOAT, BOOLEAN, STRING and CHAR literals.
 * It identifies ONLY INT type and INT and LONG literals.
 * It parses ONLY main function.
 */

public class Lexer {
    private final List<Character> separators = List.of('{', '}', '(', ')', ':', ';');
    private final List<Character> operators = List.of('=', '!', '<', '>', '*', '-', '%', '/', '+');
    private final List<String> types = List.of("int", "long");
    private String fileName;
    private String string;
    private int i = 0;
    private List<Token> tokens = new ArrayList<>();

    public Lexer(String fileName) {
        this.fileName = fileName;
        try {
            readTokens();
            tokenize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void readTokens() throws IOException {
        string = Files.readString(Path.of("src/main/resources/" + fileName + ".c"));
        System.out.println(string);
    }

    private void tokenize() {
        StringBuilder lexeme = new StringBuilder();
        while (i < string.length()) {

            // Operator
            if (operators.contains(string.charAt(i))) {
                while (i < string.length() && operators.contains(string.charAt(i))) {
                    lexeme.append(string.charAt(i));
                    i++;
                }
                tokens.add(new Token(lexeme.toString()));
                lexeme = new StringBuilder();
            }

            // Separator
            else if (separators.contains(string.charAt(i))) {
                lexeme.append(string.charAt(i));
                tokens.add(new Token(lexeme.toString()));
                lexeme = new StringBuilder();
                i++;
            }

            // Literal or Type or Identifier
            else if (Character.isJavaIdentifierPart(string.charAt(i))) {
                while (i < string.length() && Character.isJavaIdentifierPart(string.charAt(i))) {
                    lexeme.append(string.charAt(i));
                    i++;
                }
                if (types.contains(lexeme.toString())) {
                    tokens.add(new Token(lexeme.toString(), Token.lexemes.TYPE));
                } else if (Pattern.matches("\\d+", lexeme.toString())) {
                    tokens.add(new Token(lexeme.toString(), Token.lexemes.LITERAL));
                } else {
                    tokens.add(new Token(lexeme.toString(), Token.lexemes.IDENTIFIER));
                }
                lexeme = new StringBuilder();
            } else
                i++;
        }
    }

}
