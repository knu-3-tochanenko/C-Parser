import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        System.out.println("Init");
    }

    private static void fillTokens(Vector<Token> tokens) {
        tokens.add(new Token("int", Token.lexemes.TYPE));
        tokens.add(new Token("main", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("("));
        tokens.add(new Token(")"));
        tokens.add(new Token("{"));


        tokens.add(new Token("int", Token.lexemes.TYPE));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("4", Token.lexemes.LITERAL));
        tokens.add(new Token(";"));


        tokens.add(new Token("if"));

        tokens.add(new Token("("));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("<"));
        tokens.add(new Token("0", Token.lexemes.LITERAL));
        tokens.add(new Token(")"));

        tokens.add(new Token("{"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("-"));
        tokens.add(new Token("10", Token.lexemes.LITERAL));
        tokens.add(new Token(";"));
        tokens.add(new Token("}"));

        tokens.add(new Token("else"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("-"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));

        tokens.add(new Token("for"));
        tokens.add(new Token("("));
        tokens.add(new Token("int",Token.lexemes.TYPE));
        tokens.add(new Token("i", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("0", Token.lexemes.LITERAL));
        tokens.add(new Token(";"));
        tokens.add(new Token("i", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("<="));
        tokens.add(new Token("5", Token.lexemes.LITERAL));
        tokens.add(new Token(";"));
        tokens.add(new Token("i", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("i", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("+"));
        tokens.add(new Token("1", Token.lexemes.LITERAL));
        tokens.add(new Token(")"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("i", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));


        tokens.add(new Token("while"));
        tokens.add(new Token("("));
        tokens.add(new Token("false", Token.lexemes.LITERAL));
        tokens.add(new Token(")"));
        tokens.add(new Token("{"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));
        tokens.add(new Token("}"));

        tokens.add(new Token("do"));
        tokens.add(new Token("{"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("*"));
        tokens.add(new Token("1", Token.lexemes.LITERAL));
        tokens.add(new Token(";"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));
        tokens.add(new Token("}"));
        tokens.add(new Token("while"));
        tokens.add(new Token("("));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("=="));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(")"));
        tokens.add(new Token(";"));


        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("+"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("*"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));


        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("="));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("*"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token("-"));
        tokens.add(new Token("a", Token.lexemes.IDENTIFIER));
        tokens.add(new Token(";"));



        tokens.add(new Token("return"));
        tokens.add(new Token("0", Token.lexemes.LITERAL));
        tokens.add(new Token(";"));


        tokens.add(new Token("}"));
    }
}
