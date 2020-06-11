import java.util.Vector;

public class Parser {
    int position = 0;
    String noStatement = "<no statement>";

    private void assertToken(Token token, String s) throws Exception {
        if (!token.content().equals(s))
            throw new CParserException(String.format("%s expected but token %d value is %s", s, position, token.content()));
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
            throw new CParserException("function type expected");
        }
        if (!tokens.get(position + 1).isIdentifier()) {
            throw new CParserException("function name expected");
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
            throw new CParserException("type expected");
        if (!tokens.elementAt(position + 1).isIdentifier())
            throw new CParserException("identifier expected");

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

    private Node parseStatement(Vector<Token> tokens) throws Exception {
        switch (tokens.elementAt(position).content()) {
            case "return":
                Node returnNode = new Node("return");
                position++;
                returnNode.addChild(parseExpression(tokens));
                assertToken(tokens.elementAt(position), ";");
                position++;
                return returnNode;
            case "if":
                assertToken(tokens.elementAt(position), "if");
                assertToken(tokens.elementAt(position + 1), "(");
                position += 2;
                Node ifNode = new Node("if");
                ifNode.addChild(parseExpression(tokens));
                assertToken(tokens.elementAt(position), ")");
                position++;
                ifNode.addChild(parseStatement(tokens));
                if (tokens.elementAt(position).content().equals("else")) {
                    position++;
                    ifNode.addChild(parseStatement(tokens));
                }

                return ifNode;
            case "for":
                Node forNode = new Node("for");
                assertToken(tokens.elementAt(position + 1), "(");
                position += 2;

                //1
                if (!tokens.elementAt(position).content().equals(";")) {
                    forNode.addChild(parseDeclaration(tokens));
                    assertToken(tokens.elementAt(position), ";");
                    position++;
                } else {
                    position++;
                    forNode.addChild(new Node(noStatement));
                }

                //2
                if (!tokens.elementAt(position).content().equals(";")) {
                    forNode.addChild(parseExpression(tokens));
                    assertToken(tokens.elementAt(position), ";");
                    position++;
                } else {
                    position++;
                    forNode.addChild(new Node(noStatement));
                }

                //3
                if (!tokens.elementAt(position).content().equals(")")) {
                    forNode.addChild(parseExpression(tokens));
                    assertToken(tokens.elementAt(position), ")");
                    position++;
                } else {
                    position++;
                    forNode.addChild(new Node(noStatement));
                }

                //4
                forNode.addChild(parseBlockItem(tokens));

                return forNode;

            case "do":
                position++;
                Node doWhileNode = new Node("do while");
                doWhileNode.addChild(parseBlockItem(tokens));

                assertToken(tokens.elementAt(position), "while");
                assertToken(tokens.elementAt(position + 1), "(");
                position += 2;
                doWhileNode.addChild(parseExpression(tokens));
                assertToken(tokens.elementAt(position), ")");
                assertToken(tokens.elementAt(position + 1), ";");
                position += 2;

                return doWhileNode;

            case "while":
                Node whileNode = new Node("while");

                assertToken(tokens.elementAt(position), "while");
                assertToken(tokens.elementAt(position + 1), "(");
                position += 2;
                whileNode.addChild(parseExpression(tokens));
                assertToken(tokens.elementAt(position), ")");
                position++;
                whileNode.addChild(parseBlockItem(tokens));

                return whileNode;

            case "{":
                Node blockNode = new Node("block");
                position++;
                while (!tokens.elementAt(position).content().equals("}")) {
                    blockNode.addChild(parseBlockItem(tokens));
                }
                assertToken(tokens.elementAt(position), "}");
                position++;
                return blockNode;

            case ";":
                return new Node(noStatement);

            default:
                Node temp = parseExpression(tokens);
                assertToken(tokens.elementAt(position), ";");
                position++;
                return temp;
        }
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

    private Node parseFactor(Vector<Token> tokens) throws Exception {
        if (tokens.elementAt(position).content().equals("(")) {
            position++;
            Node bracketsNode = new Node("brackets");
            bracketsNode.addChild(parseExpression(tokens));
            assertToken(tokens.elementAt(position), ")");
            position++;
            return bracketsNode;
        } else if (tokens.elementAt(position).isLiteral()) {
            return new Node(String.format("literal: %s", tokens.elementAt(position++).content()));
        } else if (tokens.elementAt(position).isIdentifier()) {
            return new Node(String.format("id: %s", tokens.elementAt(position++).content()));
        }
        throw new CParserException(String.format("expression expected but got token %d with value %s", position, tokens.elementAt(position).content()));
    }
}
