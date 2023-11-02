public class Node implements Cloneable{
    private Node parentNode;
    private Coord coordOfNode;
    private int depth;
    private double cost;
    private int dir;
    private String directionFromParentNode;
    /**
     * Constructor for Node class
     * @param parentNode
     * @param r
     * @param c
     * @param cost
     * @param depth
     * @param directionFromParentNode
     * @param triangleDirectionValue
     */
    public Node(Node parentNode, int r, int c, double cost, int depth, String directionFromParentNode, int triangleDirectionValue) {
        this.parentNode = parentNode;
        coordOfNode = new Coord(r, c);
        this.depth = depth + 1;
        this.cost = cost + 1;
        this.directionFromParentNode = directionFromParentNode;
        this.dir = triangleDirectionValue;
    }
    /**
     * Getter method for triangle direction.
     * @return the direction value assigned.
     */
    public int getTriangleDirection() {
        return dir;
    }
    /**
     * Getter function for returning the coordinate of the node.
     * @return Coord of node
     */
    public Coord getCoord() {
        return(coordOfNode);
    }
    /**
     * Getter method for the depth of the node.
     * @return depth
     */
    public int getDepth() {
        return(depth);
    }
    /**
     * Getter method for the cost.
     * @return double cost
     */
    public double getCost() {
        return (cost);
    }
    /**
     * Getter method for the parentNode.
     * @return the parentNode.s
     */
    public Node getParentNode() {
        return (parentNode);
    }
    /**
     * Getter method for the direction taken from the parent Node.
     * @return direction from the parentNode.
     */
    public String getDirectionFromParentNode() {
        return (directionFromParentNode);
    }
    /**
     * Method that creates and returns a clone of the same object
     */
    public Object clone() throws CloneNotSupportedException{  
        return super.clone();  
    } 
}
