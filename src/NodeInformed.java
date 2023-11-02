import java.lang.Math;
import java.util.ArrayList;
/*
 * Separate class extending from the basic node.
 * For implementing Informed search.
 */
public class NodeInformed extends Node {
    private NodeInformed parentNodeInformed;
    private double h_distance;
    private double f_cost;
    /**
     * Constructor for NodeInformed class.
     * @param parentNode
     * @param r
     * @param c
     * @param cost
     * @param depth
     * @param directionFromParentNode
     * @param triangleDirectionValue
     */
    public NodeInformed (NodeInformed parentNode, int r, int c, double cost, int depth, String directionFromParentNode, int triangleDirectionValue) {
        super(null, r, c, cost, depth, directionFromParentNode, triangleDirectionValue);
        this.parentNodeInformed = parentNode;
    }
    /**
     * Method that returns the parentNode for the given Node.
     * @return NodeInformed parentNode.
     */
    public NodeInformed getParentNode() {
        return(this.parentNodeInformed);
    }
    /**
     * Method to check if the triangle is pointing up.
     * @return Boolean according to the direction of the triangle.
     */
    public boolean isTriangleUp() {
        if (getTriangleDirection() == 0) {
            return true;
        } else {
            return false;
        }
    }
    //function to calculate manhattan distance.
    public void calcH_distance(ArrayList<Double> goalCoord) {
        int rowNode = getCoord().getR();
        int colNode = getCoord().getC();
        double aNode = (double)  -rowNode;
        double bNode = (rowNode + colNode - getTriangleDirection())/2;
        double cNode = bNode - rowNode + getTriangleDirection();
        this.h_distance = Math.abs(goalCoord.get(0) - (aNode)) + Math.abs(goalCoord.get(1) - bNode) + Math.abs(goalCoord.get(2) - cNode);
    }

    /**
     * Method to get manhattan distance.
     * @return h_distance
     */
    public double getH_distance() {
        return h_distance;
    }

    //calculating total f_cost
    /**
     * Method to calculate f_Cost of a node.
     */
    public void calcF_cost() {
        this.f_cost = (double) getDepth() + h_distance;
    }

    /**
     * Method that returns f_cost for Astar.
     * @return f_cost = depth + h_distance
     */
    public double getF_costForAStar() {
        return f_cost;
    }
}
    