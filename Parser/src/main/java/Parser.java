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
        assertToken(tokens.elementAt(position++), "}");
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

    private Node parseDeclaration(Vector<Token> tokens) throws Exception {
        if (!tokens.elementAt(position).isType())
            throw new Exception("type expected");
        if (!tokens.elementAt(position + 1).isIdentifier())
            throw new Exception("identifier expected");

        Node declarationNode = new Node("declaration");
        declarationNode.addChild(new Node(String.format("variable name: %s", tokens.elementAt(position + 1).content())));
        if (tokens.elementAt(position + 2).content().equals("=")) {
            position += 3;
            declarationNode.addChild(parseExpression(tokens));
        } else {
            position += 2;
        }

        return declarationNode;
    }

    private Node parseExpression(Vector<Token> tokens) throws Exception {
        if (tokens.elementAt(position).isIdentifier() && tokens.elementAt(position + 1).content().equals("=")) {
            Node assignmentNode = new Node("=");
            assignmentNode.addChild(new Node(String.format("id: %s", tokens.elementAt(position).content())));
            position += 2;
            assignmentNode.addChild(parseExpression(tokens));
            return assignmentNode;
        } else {
            return parseCompareExpression(tokens);
        }
    }

    private boolean isEqualityToken(Token token) {
        String content = token.content();
        return content.equals("==") || content.equals("!=") || content.equals("<=") || content.equals(">=") ||
                content.equals("<") || content.equals(">");
    }

    private Node parseCompareExpression(Vector<Token> tokens) throws Exception {
        Node temp = parseAdditiveExpression(tokens);
        while (isEqualityToken(tokens.elementAt(position))) {
            Node equalityNode = new Node(tokens.elementAt(position).content());
            equalityNode.addChild(temp);
            position++;
            equalityNode.addChild(parseAdditiveExpression(tokens));
            return equalityNode;
        }
        return temp;
    }

    private boolean isAdditiveToken(Token token) {
        String content = token.content();
        return content.equals("+") || content.equals("-");
    }

    private Node parseAdditiveExpression(Vector<Token> tokens) throws Exception {
        Node temp = parseMultiplicativeExpression(tokens);
        while (isAdditiveToken(tokens.elementAt(position))) {
            Node equalityNode = new Node(tokens.elementAt(position).content());
            equalityNode.addChild(temp);
            position++;
            equalityNode.addChild(parseMultiplicativeExpression(tokens));
            return equalityNode;
        }
        return temp;
    }

    private boolean isMultiplicativeToken(Token token) {
        String content = token.content();
        return content.equals("*") || content.equals("/") || content.equals("%");
    }

    private Node parseMultiplicativeExpression(Vector<Token> tokens) throws Exception {
        Node temp = parseUnaryExpression(tokens);
        while (isMultiplicativeToken(tokens.elementAt(position))) {
            Node equalityNode = new Node(tokens.elementAt(position).content());
            equalityNode.addChild(temp);
            position++;
            equalityNode.addChild(parseUnaryExpression(tokens));
            return equalityNode;
        }
        return temp;
    }

    private boolean isUnaryToken(Token token) throws Exception {
        String content = token.content();
        return content.equals("+") || content.equals("-") || content.equals("~") || content.equals("!");
    }

    private Node parseUnaryExpression(Vector<Token> tokens) throws Exception {
        if (isUnaryToken(tokens.elementAt(position))) {
            Node unaryNode = new Node(tokens.elementAt(position).content());
            position++;
            unaryNode.addChild(parseCompareExpression(tokens));
            return unaryNode;
        }
        return parseFactor(tokens);
    }
}
