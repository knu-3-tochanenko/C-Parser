import java.util.Vector;

public class Parser {
    int position = 0;
    String noStatement = "<no statement>";

    private void assertToken(Token token, String s) throws Exception {
        if (!token.content().equals(s))
            throw new Exception(String.format("%s expected but token %d value is %s", s, position, token.content()));
    }

    Node parse(Vector<Token> tokens) throws Exception {
        return parseProgram(tokens);
    }

    private Node parseProgram(Vector<Token> tokens) throws Exception {
        // TODO
        return null;
    }

    private boolean isEqualityToken(Token token) {
        String content = token.content();
        return content.equals("==") || content.equals("!=") || content.equals("<=") || content.equals(">=") ||
                content.equals("<") || content.equals(">");
    }

    private boolean isAdditiveToken(Token token) {
        String content = token.content();
        return content.equals("+") || content.equals("-");
    }

    private boolean isMultiplicativeToken(Token token) {
        String content = token.content();
        return content.equals("*") || content.equals("/") || content.equals("%");
    }

    private boolean isUnaryToken(Token token) throws Exception {
        String content = token.content();
        return content.equals("+") || content.equals("-") || content.equals("~") || content.equals("!");
    }
}
