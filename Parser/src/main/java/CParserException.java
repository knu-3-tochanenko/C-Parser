import jdk.jfr.Name;

@Name("C Parser Exception")
public class CParserException extends Exception {
    CParserException(String s) {
        super(s);
    }
}
