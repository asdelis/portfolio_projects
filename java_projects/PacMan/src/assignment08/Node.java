package assignment08;

import java.util.ArrayList;

public class Node {

    public char value_;
    public ArrayList<Node> neighbors_ = new ArrayList<>();
    public boolean visited_;
    public Node cameFrom_;

    /**
     * The constructor for the nodes that make up our maze_ graph
     * in the PathFinder class
     *
     * @param value : the char that is saved in the node
     *              : can be either a space, "X", "S", or "G"
     */
    Node ( char value ) {
        value_ = value;
        visited_ = false;
    }

}
