import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Search {
	//ArrayList for storing the directions taken from source to goal.
    private ArrayList<String> directions = new ArrayList<String>();
    //Arraylist to store the (coordinate) path taken by the search.
    private ArrayList<Coord> pathTaken = new ArrayList<Coord>();
	//variable to keep track of ndoes traversed.
	private int nodesTraversed = 0;
	//boolean to check whether the goal state is reached or not.
	boolean goalReached = false;
	//Final cost when reaching the goalNode(Only set when the goal is reached).
	private double finalCost = 0.0;
    
	/**
	 * Method to add the nodes Traversed by one.
	 */
	public void addNodeNum() {
        nodesTraversed++;
	}

	/**
	 * Method that returns the number of nodes traversed.
	 * @return count of nodes traversed
	 */
	public int getNodesTraversed() {
		return nodesTraversed;
	}

	//method that returns goalReached
	public boolean hasGoalReached() {
		return goalReached;
	}

	/**
	 * Method that sets the final cost when the goal is reached.
	 * @param cost
	 */
	public void setFinalCost(double cost) {
		this.finalCost = cost;
	}

	/**
	 * Method to return the final cost.
	 * @return double - cost after reaching hte goal node.
	 */
	public double getFinalCost() {
		return finalCost;
	}
	/**
     * Method to check if the current node is the goal node.
     * @param currentNode The currentNode.
     * @param goal The coordinates of the goal.
     * @return Boolean according to whether the goal is reached.
     */
    public boolean goalCheck(Node currentNode, Coord goal) {
        //getting the current coordinate of the node.
		Coord currentState = currentNode.getCoord();
		//returns true if arrived at the goal.
        if (goal.equals(currentState)) {
			goalReached = true;
			return true;
		} else {
			goalReached = false;
			return false;
		}
    };

    //Overloading goalcheck for informed search.
    /**
     * Method to check if the goal is reached for the informed search.
     * @param currentNode The current node.
     * @param goal Coordinates of the goal node.
     * @return Boolean according to whether the goal is reached.
     */
    public boolean goalCheck(NodeInformed currentNode, Coord goal) {
        //getting the current coordinate of the node.
		Coord currentState = currentNode.getCoord();
		//returns true if arrived at the goal.
        if (goal.equals(currentState)) {
			goalReached = true;
			return true;
		} else {
			goalReached = false;
			return false;
		}  
    }
    
    //nodeCreator  for the uninformed search
    /**
     * Method that expands a node and creates the nodes adjacent to it.
     * @param currentNode The current Node.
     * @param map The map provided.
     * @param top Boolean according to the direction of the triangle.
     * @return ArrayList of nodes created from expanding the currentNode.
     */
    public ArrayList<Node> nodeCreator(Node currentNode, Map map) {
        ArrayList<Node> nodes = new ArrayList<Node>();
		if (currentNode.getTriangleDirection() == 0) {
			int childTriangleDirection = 1;
			//1.to the right
			//checking whether the right node is valid or not.
			if (currentNode.getCoord().getC() + 1 >= 0 && currentNode.getCoord().getC() + 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC()+1] != 1) {
				Node nodeRight = new Node(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() + 1, currentNode.getCost(), currentNode.getDepth(), "Right", childTriangleDirection);
			    nodes.add(nodeRight);
			}
			//2.to the down node.
			if (currentNode.getCoord().getR() + 1 >= 0 && currentNode.getCoord().getR() + 1 < map.rowSize() && map.getMap()[currentNode.getCoord().getR() + 1][currentNode.getCoord().getC()] != 1) {
				Node nodeDown = new Node(currentNode, currentNode.getCoord().getR() + 1, currentNode.getCoord().getC(), currentNode.getCost(), currentNode.getDepth(), "Down", childTriangleDirection);
			    nodes.add(nodeDown);
			}
			//3.to the left node.
			if (currentNode.getCoord().getC() - 1 >= 0 && currentNode.getCoord().getC() - 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC() - 1] != 1) {
				Node nodeLeft = new Node(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() - 1, currentNode.getCost(), currentNode.getDepth(), "Left", childTriangleDirection);
			    nodes.add(nodeLeft);
			}
		} else {
			int childTriangleDirection = 0;
			//1.to the right
			//checking whether the right node is valid or not.
			if (currentNode.getCoord().getC() + 1 >= 0 && currentNode.getCoord().getC() + 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC()+1] != 1) {
				Node nodeRight = new Node(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() + 1, currentNode.getCost(), currentNode.getDepth(), "Right", childTriangleDirection);
			    nodes.add(nodeRight);
			}
			//3.to the left node.
			if (currentNode.getCoord().getC() - 1 >= 0 && currentNode.getCoord().getC() - 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC() - 1] != 1) {
				Node nodeLeft = new Node(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() - 1, currentNode.getCost(), currentNode.getDepth(), "Left", childTriangleDirection);
				nodes.add(nodeLeft);
			}
			//4.to the up node.
		    if (currentNode.getCoord().getR() - 1 >= 0 && currentNode.getCoord().getR() - 1 < map.rowSize() && map.getMap()[currentNode.getCoord().getR() - 1][currentNode.getCoord().getC()] != 1) {
				Node nodeUp = new Node(currentNode, currentNode.getCoord().getR() - 1, currentNode.getCoord().getC(), currentNode.getCost(), currentNode.getDepth(), "Up", childTriangleDirection);
		        nodes.add(nodeUp);
			}
		}
		return nodes;
    };

    //Overloading nodeCreator for informed search
    /**
     * Method that expands a node and creates the nodes adjacent to it.
     * @param currentNode The current Node.
     * @param map The map provided.
     * @param top Boolean according to the direction of the triangle.
     * @return ArrayList of nodes created from expanding the currentNode.
     */
    public ArrayList<NodeInformed> nodeCreator(NodeInformed currentNode, Map map) {
        ArrayList<NodeInformed> nodes = new ArrayList<NodeInformed>();
		if (currentNode.isTriangleUp()) {
			int directionOfTriangle = 1;
			//1.to the right
			//checking whether the right node is valid or not.
			if (currentNode.getCoord().getC() + 1 >= 0 && currentNode.getCoord().getC() + 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC()+1] != 1) {
				// System.out.println(currentNode.getCoord());
				// System.out.println("COORD1");
				NodeInformed nodeRight = new NodeInformed(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() + 1, currentNode.getCost(), currentNode.getDepth(), "Right", directionOfTriangle);
			    nodes.add(nodeRight);
			}
			//2.to the down node.
			if (currentNode.getCoord().getR() + 1 >= 0 && currentNode.getCoord().getR() + 1 < map.rowSize() && map.getMap()[currentNode.getCoord().getR() + 1][currentNode.getCoord().getC()] != 1) {
				// System.out.println(currentNode.getCoord());
				// System.out.println("COORD2");
				NodeInformed nodeDown = new NodeInformed(currentNode, currentNode.getCoord().getR() + 1, currentNode.getCoord().getC(), currentNode.getCost(), currentNode.getDepth(), "Down", directionOfTriangle);
			    nodes.add(nodeDown);
			}
			//3.to the left node.
			if (currentNode.getCoord().getC() - 1 >= 0 && currentNode.getCoord().getC() - 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC() - 1] != 1) {
				// System.out.println(currentNode.getCoord());
				// System.out.println("COORD3");
				NodeInformed nodeLeft = new NodeInformed(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() - 1, currentNode.getCost(), currentNode.getDepth(), "Left", directionOfTriangle);
			    nodes.add(nodeLeft);
			}
		} else {
			int directionOfTriangle = 0;
			//1.to the right
			//checking whether the right node is valid or not.
			if (currentNode.getCoord().getC() + 1 >= 0 && currentNode.getCoord().getC() + 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC()+1] != 1) {
				// System.out.println(currentNode.getCoord());
				// System.out.println("COORD4");
				NodeInformed nodeRight = new NodeInformed(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() + 1, currentNode.getCost(), currentNode.getDepth(), "Right", directionOfTriangle);
			    nodes.add(nodeRight);
			}
			//3.to the left node.
			if (currentNode.getCoord().getC() - 1 >= 0 && currentNode.getCoord().getC() - 1 < map.colSize() && map.getMap()[currentNode.getCoord().getR()][currentNode.getCoord().getC() - 1] != 1) {
				// System.out.println(currentNode.getCoord());
				// System.out.println("COORD5");
				NodeInformed nodeLeft = new NodeInformed(currentNode, currentNode.getCoord().getR(), currentNode.getCoord().getC() - 1, currentNode.getCost(), currentNode.getDepth(), "Left", directionOfTriangle);
			    // System.out.println();
				nodes.add(nodeLeft);
			}
			//4.to the up node.
		    if (currentNode.getCoord().getR() - 1 >= 0 && currentNode.getCoord().getR() - 1 < map.rowSize() && map.getMap()[currentNode.getCoord().getR() - 1][currentNode.getCoord().getC()] != 1) {
				// System.out.println(currentNode.getCoord());
				// System.out.println("COORD6");
				NodeInformed nodeUp = new NodeInformed(currentNode, currentNode.getCoord().getR() - 1, currentNode.getCoord().getC(), currentNode.getCost(), currentNode.getDepth(), "Up", directionOfTriangle);
		        nodes.add(nodeUp);
			}
		}
		return nodes;
    }

    //function for setting the triangle direction
	/**
	 * Method that returns a boolean according to the triangle direction.
	 * @param start
	 * @return
	 */
    public boolean setTriangle(Coord start) {
        if((start.getR() + start.getC()) % 2 == 0) {
           return true;
        } else {
           return false;
        }
   }

    //to calculate a.b.c of goal
	/**
	 * 
	 * @param goal
	 * @return
	 */
    public ArrayList<Double> calcGoalCoord(Coord goal) {
        int rowGoal = goal.getR();
        int colGoal = goal.getC();
		int dir;
		if(setTriangle(goal)) {
            dir = 0;
		} else {
			dir = 1;
		}		
        ArrayList<Double> goalTriangleCoord = new ArrayList<Double>();
        double aGoal = (double) 0 - rowGoal;
        goalTriangleCoord.add(aGoal);
        double bGoal = (rowGoal + colGoal - dir)/2;
        goalTriangleCoord.add(bGoal);
        double cGoal = bGoal - rowGoal + dir;
        goalTriangleCoord.add(cGoal);
        return goalTriangleCoord;
    }

	//to store and print the pathTaken and direction followed for uninformed search.
    /**
    * 
    * @param dummyNode
    */
    public void printPath(Node dummyNode) {
		directions.clear();
		pathTaken.clear();
        do {
            pathTaken.add(dummyNode.getCoord());
            directions.add(dummyNode.getDirectionFromParentNode());
            dummyNode = dummyNode.getParentNode();
        } while(dummyNode != null);
        //to print the arrayList of pathTaken in reverse.
		Coord temp;
        for (int i = 0; i < pathTaken.size()/2 ; i++) {
			temp = pathTaken.get(i);
			pathTaken.set(i, pathTaken.get(pathTaken.size() - i - 1));
			pathTaken.set(pathTaken.size() - i - 1, temp);

        }
		// System.out.println(pathTaken); //reversed
		for(int i = 0; i < pathTaken.size(); i++) {
			System.out.print(pathTaken.get(i));
		}
		System.out.println();
        //to print the directions followed from source in reverse, but only till the source, hence total size - 2.
        for(int i = directions.size() - 2; i >= 0; i--) {
            System.out.print(directions.get(i) + " ");
        }
        System.out.println();
   }

   /**
	* Method for printing frontier.
	* @param frontier
    */
   	public void printFrontier(Queue<Node> frontier) {
		if(frontier.size() != 0) {
			ArrayList<Coord> frontierCoord = new ArrayList<Coord>();
			Object[] eachFrontier = frontier.toArray();
			for (int i = 0; i < eachFrontier.length; i++) {
				frontierCoord.add(((Node) eachFrontier[i]).getCoord());
			}
			System.out.print("[");
			for (int i = 0; i < frontierCoord.size() - 1; i++) {
				System.out.print(frontierCoord.get(i) + ",");
			}
			System.out.print(frontierCoord.get(frontierCoord.size() - 1));
			System.out.print("]");
			System.out.println();
		}
    }

	/**
	 * Overloading the print method for DepthFS
	 * @param frontier
	 */
	public void printFrontier(Stack<Node> frontier) {
		if(frontier.size() != 0) {
			ArrayList<Coord> frontierCoord = new ArrayList<Coord>();
			Object[] eachFrontier = frontier.toArray();
			for (int i = 0; i < eachFrontier.length; i++) {
				frontierCoord.add(((Node) eachFrontier[i]).getCoord());
			}
			System.out.print("[");
			for (int i = 0; i < frontierCoord.size() - 1; i++) {
				System.out.print(frontierCoord.get(i) + ",");
			}
			System.out.print(frontierCoord.get(frontierCoord.size() - 1));
			System.out.print("]");
			System.out.println();
		}
    }
	/**
	 * Print frontier for BestF
	 * @param frontier
	 */
	public void printFrontierBestF(ArrayList<NodeInformed> frontier) {
		if(frontier.size() != 0) {
			ArrayList<String> frontierCoord = new ArrayList<String>();
			Object[] eachFrontier = frontier.toArray();
			for (int i = 0; i < eachFrontier.length; i++) {
				frontierCoord.add(((NodeInformed) eachFrontier[i]).getCoord() + ":" + ((NodeInformed) eachFrontier[i]).getH_distance());
			}
			System.out.print("[");
			for (int i = 0; i < frontierCoord.size() - 1; i++) {
				System.out.print(frontierCoord.get(i) + ",");
			}
			System.out.print(frontierCoord.get(frontierCoord.size() - 1));
			System.out.print("]");
			System.out.println();
		}	
	}
	/**
	 * Print frontier for AStar
	 * @param frontier
	 */
	public void printFrontierAStar(ArrayList<NodeInformed> frontier) {
		if(frontier.size() != 0) {
			ArrayList<String> frontierCoord = new ArrayList<String>();
			Object[] eachFrontier = frontier.toArray();
			for (int i = 0; i < eachFrontier.length; i++) {
				frontierCoord.add(((NodeInformed) eachFrontier[i]).getCoord() + ":" + ((NodeInformed) eachFrontier[i]).getF_costForAStar());
				//+ ":" +((NodeInformed) eachFrontier[i]).getDirectionFromParentNode() + ((NodeInformed) eachFrontier[i]).getDepth()
				//+ ":distance" + ((NodeInformed) eachFrontier[i]).getH_distance() + ":depth" + ((NodeInformed) eachFrontier[i]).getDepth()
			}
			System.out.print("[");
			for (int i = 0; i < frontierCoord.size() - 1; i++) {
				System.out.print(frontierCoord.get(i) + ",");
			}
			System.out.print(frontierCoord.get(frontierCoord.size() - 1));
			System.out.print("]");
			System.out.println();
		}
	}
	/**
	 * Function that sets adjacent node in a random manner for triple agent search
	 * @param size
	 * @return
	 */
	public int[] settingAdjacentNode(int size) {
		Random random = new Random();
		int[] indexes = new int[2];
		int randomIndexFromNodes = random.nextInt(size);
		int secondIndex;
		while(true) {
			secondIndex = random.nextInt(size);
			if(secondIndex != randomIndexFromNodes) {
				break;
			}
		}
		indexes[0] = randomIndexFromNodes;
		indexes[1] = secondIndex;
		return indexes;
	}

}
