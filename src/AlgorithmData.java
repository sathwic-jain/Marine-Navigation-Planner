
public class AlgorithmData {
    int nodesTraversed;
    double cost;
    long timeTakenInMS;

    public void setNodesTraversed(int n) {
        nodesTraversed = n;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setTimeTaken(long ms) {
        this.timeTakenInMS = ms;
    }

    public int getNodesTraversed() {
        return nodesTraversed;
    }

    public double getCost() {
        return cost;
    }

    public long getTimeTaken() {
        return timeTakenInMS;
    }
}