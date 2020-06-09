import java.util.Vector;

public class Node {
    private String name;
    private Vector<Node> children;

    public Node(String name) {
        this.name = name;
        this.children = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public Vector<Node> getChildren() {
        return children;
    }
}
