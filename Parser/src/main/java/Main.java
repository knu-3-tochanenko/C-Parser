import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "small";
        Lexer lexer = new Lexer(fileName);
        List<Token> tokens = lexer.getTokens();

        System.out.println(tokens);

        Parser parser = new Parser();
        Node root;
        try {
            root = parser.parse(tokens);
            TreeVisualisation treeVisualisation = new TreeVisualisation(root);
            treeVisualisation.build(fileName);
        } catch (Exception e) {
            System.out.println("\n----------------------------------------------");
            System.out.println("c.lang.ParserException: " + e.getMessage());
            System.out.println("----------------------------------------------");
        }
    }

}
