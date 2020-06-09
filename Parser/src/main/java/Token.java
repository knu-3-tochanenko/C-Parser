public class Token {

    enum lexemes {TYPE, IDENTIFIER, LITERAL}

    lexemes lexeme;

    private final String stringContent;

    public Token(String content) {
        this.stringContent = content;
    }

    public Token(String content, lexemes lexeme) {
        this.stringContent = content;
        this.lexeme = lexeme;
    }

    String content() {
        return stringContent;
    }

    public boolean isType() {
        return lexeme == lexemes.TYPE;
    }

    public boolean isIdentifier() {
        return lexeme == lexemes.IDENTIFIER;
    }

    public boolean isLiteral() {
        return lexeme == lexemes.LITERAL;
    }
}
