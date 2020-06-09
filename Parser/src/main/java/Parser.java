public class Parser {
    int position = 0;
    String noStatement = "<no statement>";

    private void assertToken(Token token, String s) throws Exception {
        if (!token.content().equals(s))
            throw new Exception(String.format("%s expected but token %d value is %s", s, position, token.content()));
    }
}
