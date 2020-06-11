import java.util.List;
import java.util.Vector;

public class Node {
    private final String name;
    private final List<Node> children;

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

    public List<Node> getChildren() {
        return children;
    }
}
