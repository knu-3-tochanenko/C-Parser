import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizJdkEngine;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class TreeVisualisation {
    private final MutableGraph graph;
    private final Node root;

    TreeVisualisation(Node node) {
        Graphviz.useEngine(new GraphvizJdkEngine());
        this.root = node;
        graph = mutGraph("Parser tree").setDirected(true);
    }

    void build(String fileName) throws IOException {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();

        queue.add(root);
        queue.add(null);

        Node current;

        while (!queue.isEmpty()) {
            current = queue.remove();

            if (current == null && queue.isEmpty()) {
                queue.add(null);
            } else if (current != null) {
                for (Node node : current.getChildren()) {
                    if (node != null) {
                        graph.add(
                                mutNode(current.getName() + " hash: " + current.hashCode()).addLink(mutNode(node.getName() + " hash: " + node.hashCode())));
                        queue.add(node);
                    }
                }
            }
        }

        Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File("src/main/resources/" + fileName + ".png"));
    }
}
