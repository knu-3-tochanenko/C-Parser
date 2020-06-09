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
        Node res = new Node("program");
        while (position < tokens.size()) {
            res.addChild(parseFunctionDeclaration(tokens));
        }
        return res;
    }

    private Node parseFunctionDeclaration(Vector<Token> tokens) throws Exception {
        if (!tokens.get(position).isType()) {
            throw new Exception("function type expected");
        }
        if (!tokens.get(position + 1).isIdentifier()) {
            throw new Exception("function name expected");
        }
        assertToken(tokens.get(position++), "int");
        assertToken(tokens.get(position++), "main");
        assertToken(tokens.get(position++), "(");
        assertToken(tokens.get(position++), ")");
        assertToken(tokens.get(position++), "{");
        Node res = new Node("main function");
        while (!tokens.elementAt(position).content().equals("}")) {
            res.addChild(parseBlockItem(tokens));
        }
        return res;
    }

    private Node parseBlockItem(Vector<Token> tokens) throws Exception {

        if (tokens.elementAt(position).isType()) {
            Node declarationNode = parseDeclaration(tokens);
            assertToken(tokens.elementAt(position++), ";");
            return declarationNode;
        } else {
            return parseStatement(tokens);
        }
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
