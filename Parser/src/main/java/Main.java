import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {

        Lexer lexer = new Lexer("text.txt");
        List<Token> tokens = lexer.getTokens();

        System.out.println(tokens);

        Parser parser = new Parser();
        Node root;
        try {
            root = parser.parse(tokens);
            TreeVisualisation treeVisualisation = new TreeVisualisation(root);
            treeVisualisation.build();
        } catch (Exception e) {
            System.out.println("\n----------------------------------------------");
            System.out.println("c.lang.ParserException: " + e.getMessage());
            System.out.println("----------------------------------------------");
        }
    }

}
