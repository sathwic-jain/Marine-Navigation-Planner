import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/********************
 * Starter Code
 * 
 * This class contains some examples on how to handle the required inputs and
 * outputs
 * and other debugging options
 * 
 * @author at258
 * 
 *         run with
 *         java A1main <Algo> <ConfID>
 * 
 */

public class A1main {

	public static void main(String[] args) {

		/*
		 * 
		 * Retrieve input and configuration
		 * and run search algorithm
		 *
		 */

		 /**
		  * To run all the algorithms through all the configurations -
		  * uncomment the following commands with caution.
		  * This is a data gathering and checking section.
		  * The code is written for the author to retrieve all the data
		  * required for analysis(only).
		  */
	

			Conf conf = Conf.valueOf(args[1]);


			// run your search algorithm

			runSearch(args[0],conf.getMap(),conf.getS(),conf.getG());


		}

		private static void runSearch(String algo, Map map, Coord start, Coord goal) {
		switch (algo) {
			case "BFS": // run BFS
				BFS(map, start, goal);
				break;
			case "DFS": // run DFS
				DFS(map, start, goal);
				break;
			case "BestF": // run BestF
				BestF(map, start, goal);				
				break;
			case "AStar": // run AStar
				AStar(map, start, goal);
				break;
			case "BidirectionalAStar":
				BidirectionalAStar(map, start, goal);
      			break;
			case "tripleAgentBestFS":
				tripleAgentBestFS(map, start, goal);
				break;
		}
	}

	private static void printMap(Map m, Coord init, Coord goal) {
		int[][] map = m.getMap();

		System.out.println();
		int rows = map.length;
		int columns = map[0].length;

		// top row
		System.out.print("  ");
		for (int c = 0; c < columns; c++) {
			System.out.print(" " + c);
		}
		System.out.println();
		System.out.print("  ");
		for (int c = 0; c < columns; c++) {
			System.out.print(" -");
		}
		System.out.println();

		// print rows
		for (int r = 0; r < rows; r++) {
			boolean right;
			System.out.print(r + "|");
			if (r % 2 == 0) { // even row, starts right [=starts left & flip right]
				right = false;
			} else { // odd row, starts left [=starts right & flip left]
				right = true;
			}
			for (int c = 0; c < columns; c++) {
				System.out.print(flip(right));
				if (isCoord(init, r, c)) {
					System.out.print("S");
				} else {
					if (isCoord(goal, r, c)) {
						System.out.print("G");
					} else {
						if (map[r][c] == 0) {
							System.out.print(".");
						} else {
							System.out.print(map[r][c]);
						}
					}
				}
				right = !right;
			}
			System.out.println(flip(right));
		}
		System.out.println();

	}

	private static boolean isCoord(Coord coord, int r, int c) {
		// check if coordinates are the same as current (r,c)
		if (coord.getR() == r && coord.getC() == c) {
			return true;
		}
		return false;
	}

	public static String flip(boolean right) {
		// prints triangle edges
		if (right) {
			return "\\"; // right return left
		} else {
			return "/"; // left return right
		}
	}

	/**
	 * Method that implements the Breadth first search algorithm.
	 * 
	 * @param map
	 * @param start
	 * @param goal
	 */
	public static void BFS(Map map, Coord start, Coord goal) {

		Search breadthFS = new Search();
		// arraylist for storing explored nodes.
		ArrayList<Coord> exploredCoord = new ArrayList<Coord>();
		// initializing the start node.
		Node startNode;
		// setting the value for triangle direction.
		if (breadthFS.setTriangle(start)) {
			startNode = new Node(null, start.getR(), start.getC(), -1, -1, null, 0);
		} else {
			startNode = new Node(null, start.getR(), start.getC(), -1, -1, null, 1);
		}
		Queue<Node> frontier = new LinkedList<Node>();
		// adding startNode to the frontier.
		frontier.add(startNode);
		// adding the coordinates of start node to explored Coord.
		exploredCoord.add(startNode.getCoord());
		// printing the startNode coordinates.
		breadthFS.printFrontier(frontier);
		// while frontier is not empty
		while (!frontier.isEmpty()) {
			// popping and checking if the goal is reached.
			Node currentNode = frontier.remove();
			// adding one to the number of nodes traversed when it is popped.
			breadthFS.addNodeNum();
			// goalcheck initiated
			if (breadthFS.goalCheck(currentNode, goal)) {
				try {
					Node dummyNode = (Node) currentNode.clone();
					breadthFS.printPath(dummyNode);
				} catch (Exception e) {
					// printing clone error exception
					System.out.println(e);
				}
				// to print the cost and depth from source to the goal
				System.out.println(currentNode.getCost());
				breadthFS.setFinalCost(currentNode.getCost());
				// breaks of the loop if the goal is reached.
				break;
			}
			// creating nodes to push into the stack according to the order prefered.
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes = breadthFS.nodeCreator(currentNode, map);
			// to check whether the node has been visited or not
			// if is only applied if there are nodes to be checked.
			if (!nodes.isEmpty()) {
				ArrayList<Node> sameCoord = new ArrayList<Node>();
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < exploredCoord.size(); j++) {
						if (nodes.get(i).getCoord().equals(exploredCoord.get(j))) {
							// saving the indexes to remove later.
							// we do not remove immediately as it will change the size of the arraylists
							// used for the loop.
							if (!sameCoord.contains(nodes.get(i))) {
								sameCoord.add(nodes.get(i));
							}
						}
					}
				}
				// removing the same Coordinates from the nodes.
				for (int i = 0; i < sameCoord.size(); i++) {
					nodes.remove((sameCoord.get(i)));
				}
			}
			// adding the nodes to the frontier and the coordinates to the explored Coord.
			for (int i = 0; i < nodes.size(); i++) {
				frontier.add(nodes.get(i));
				exploredCoord.add(nodes.get(i).getCoord());
			}
			// printing the frontier.
			breadthFS.printFrontier(frontier);
		}
		if (!breadthFS.hasGoalReached()) {
			System.out.println();
			System.out.println("fail");

		}
		System.out.println(breadthFS.getNodesTraversed());
	}

	/**
	 * Method implementing Depth First Search
	 * 
	 * @param map   The map for the configuration.
	 * @param start The start coordinates.
	 * @param goal  The goal coordinates.
	 */
	public static void DFS(Map map, Coord start, Coord goal) {

		Search depthFS = new Search();
		// arraylist for storing explored nodes.
		ArrayList<Coord> exploredCoord = new ArrayList<Coord>();
		// initializing the start node.
		Node startNode;
		// setting the value for triangle direction.
		if (depthFS.setTriangle(start)) {
			startNode = new Node(null, start.getR(), start.getC(), -1, -1, null, 0);
		} else {
			startNode = new Node(null, start.getR(), start.getC(), -1, -1, null, 1);
		}
		Stack<Node> frontier = new Stack<Node>();
		// adding startNode to the frontier.
		frontier.push(startNode);
		// adding the coordinates of start node to explored Coord.
		exploredCoord.add(startNode.getCoord());
		// printing the startNode coordinates.
		depthFS.printFrontier(frontier);
		// while frontier is not empty
		while (!frontier.isEmpty()) {
			// popping and checking if the goal is reached.
			Node currentNode = frontier.pop();
			// adding one to the number of nodes traversed when it is popped.
			depthFS.addNodeNum();
			// goalcheck initiated
			if (depthFS.goalCheck(currentNode, goal)) {
				try {
					Node dummyNode = (Node) currentNode.clone();
					depthFS.printPath(dummyNode);
				} catch (Exception e) {
					// printing clone error exception
					System.out.println(e);
				}
				// to print the cost and depth from source to the goal
				System.out.println(currentNode.getCost());
				// setting the final cost.
				depthFS.setFinalCost(currentNode.getCost());
				// breaks of the loop if the goal is reached.
				break;
			}
			// creating nodes to push into the stack according to the order prefered.
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes = depthFS.nodeCreator(currentNode, map);

			// to check whether the node has been visited or not.
			// if is only applied if there are nodes to be checked.
			if (!nodes.isEmpty()) {
				ArrayList<Node> sameCoord = new ArrayList<Node>();
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < exploredCoord.size(); j++) {
						if (nodes.get(i).getCoord().equals(exploredCoord.get(j))) {
							// saving the indexes to remove later.
							// we do not remove immediately as it will change the size of the arraylists
							// used for the loop.
							if (!sameCoord.contains(nodes.get(i))) {
								sameCoord.add(nodes.get(i));
							}
						}
					}
				}
				// removing visited nodes from the node array.
				for (int i = 0; i < sameCoord.size(); i++) {
					nodes.remove((sameCoord.get(i)));
				}
			}
			// adding the nodes to the frontier and the coordinates to the explored Coord.
			for (int i = nodes.size() - 1; i >= 0; i--) {
				frontier.push(nodes.get(i));
				exploredCoord.add(nodes.get(i).getCoord());
			}
			depthFS.printFrontier(frontier);
		}
		if (!depthFS.hasGoalReached()) {
			System.out.println();
			System.out.println("fail");
		}
		System.out.println(depthFS.getNodesTraversed());
	}

	/**
	 * Method that implements BestFirst(Greedy search).
	 * 
	 * @param map   The map for the configuration.
	 * @param start The start coordinates.
	 * @param goal  The goal coordinates.
	 */
	public static void BestF(Map map, Coord start, Coord goal) {

		Search bestFirst = new Search();
		// arraylist for storing explored nodes.
		ArrayList<Coord> exploredCoord = new ArrayList<Coord>();
		// initializing the start node.
		NodeInformed startNode;
		// arraylist that stores the 3 coordinates of the goal coordinate.
		ArrayList<Double> goalTriangleCoord = bestFirst.calcGoalCoord(goal);
		if (bestFirst.setTriangle(start)) {
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 0);
		} else {
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 1);
		}
		ArrayList<NodeInformed> frontier = new ArrayList<NodeInformed>();
		// adding startNode to the frontier.
		frontier.add(startNode);
		// adding the coordinates of start node to explored Coord.
		exploredCoord.add(startNode.getCoord());
		// finding h_distance for source coord
		startNode.calcH_distance(goalTriangleCoord);
		// Printing the startNode
		bestFirst.printFrontierBestF(frontier);
		// while frontier is not empty
		while (!frontier.isEmpty()) {
			// popping and checking if the goal is reached.
			NodeInformed currentNode = frontier.remove(0);
			// adding one to the number of nodes traversed when it is popped.
			bestFirst.addNodeNum();
			if (bestFirst.goalCheck(currentNode, goal)) {
				try {
					// creating a dummyNode to send for printing the path.
					NodeInformed dummyNode = (NodeInformed) currentNode.clone();
					bestFirst.printPath(dummyNode);
				} catch (Exception e) {
					// printing clone error exception
					System.out.println(e);
				}
				// to print the cost and depth from source to the goal
				System.out.println(currentNode.getCost());
				// setting the final Cost.
				bestFirst.setFinalCost(currentNode.getCost());
				// breaks of the loop if the goal is reached.
				break;
			}
			// creating nodes to push into the stack according to the order prefered.
			ArrayList<NodeInformed> nodes = new ArrayList<NodeInformed>();
			nodes = bestFirst.nodeCreator(currentNode, map);
			// setting h_distance for all the nodes created.
			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).calcH_distance(goalTriangleCoord);
			}
			if (!nodes.isEmpty()) {
				ArrayList<NodeInformed> sameCoord = new ArrayList<NodeInformed>();
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < exploredCoord.size(); j++) {
						// System.out.println(i + "hi" + j);
						if (nodes.get(i).getCoord().equals(exploredCoord.get(j))) {
							// saving the indexes to remove later.
							// we do not remove immediately as it will change the size of the arraylists
							// used for the loop.
							// if condition to eliminate duplicity.
							if (!sameCoord.contains(nodes.get(i))) {
								sameCoord.add(nodes.get(i));
							}
						}
					}
				}
				// already visited coordinates are removed from the nodes.
				for (int i = 0; i < sameCoord.size(); i++) {
					nodes.remove((sameCoord.get(i)));
				}
			}
			// adding the nodes to the frontier and the coordinates to the explored Coord.
			for (int i = nodes.size() - 1; i >= 0; i--) {
				frontier.add(nodes.get(i));
				exploredCoord.add(nodes.get(i).getCoord());
			}
			// need to rearrange the frontier according to h_distance
			NodeInformed tempNode;
			for (int i = 0; i < frontier.size(); i++) {
				for (int j = 0; j < frontier.size() - 1; j++) {
					// comparing h_distance
					if (frontier.get(j).getH_distance() > frontier.get(j + 1).getH_distance()) {
						tempNode = frontier.get(j);
						frontier.set(j, frontier.get(j + 1));
						frontier.set(j + 1, tempNode);
						// if both the nodes have the same h_distance,comparing the depth
					} else if (Double.compare(frontier.get(j).getH_distance(),
							frontier.get(j + 1).getH_distance()) == 0) {
						if (!(frontier.get(j + 1).getDirectionFromParentNode()
								.equals(frontier.get(j).getDirectionFromParentNode()))) {
							if (frontier.get(j + 1).getDirectionFromParentNode().equals("Right")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							} else if (frontier.get(j + 1).getDirectionFromParentNode().equals("Down")
									&& !frontier.get(j).getDirectionFromParentNode().equals("Right")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							} else if (frontier.get(j).getDirectionFromParentNode().equals("Up")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							}
						} else {
							if (!(frontier.get(j).getDepth() == frontier.get(j + 1).getDepth())) {
								if (frontier.get(j).getDepth() > frontier.get(j + 1).getDepth()) {
									tempNode = frontier.get(j);
									frontier.set(j, frontier.get(j + 1));
									frontier.set(j + 1, tempNode);
								}
								// if they have the same depth, they are left as it is.
							}
						}
					}
				}
			}
			// printing the frontier.
			bestFirst.printFrontierBestF(frontier);
		}
		if (!bestFirst.hasGoalReached()) {
			System.out.println();
			System.out.println("fail");
		}
		System.out.println(bestFirst.getNodesTraversed());
	}

	/**
	 * Method that implements AStar search.
	 * 
	 * @param map   The map for the configuration.
	 * @param start The start coordinates.
	 * @param goal  The goal coordinates.
	 */
	public static void AStar(Map map, Coord start, Coord goal) {

		Search aStarSearch = new Search();
		// arraylist for storing explored nodes.
		ArrayList<Coord> exploredCoord = new ArrayList<Coord>();
		// declaring the start node.
		NodeInformed startNode;
		// arraylist that stores the 3 coordinates of the goal coordinate.
		ArrayList<Double> goalTriangleCoord = aStarSearch.calcGoalCoord(goal);
		// creating a variable to keep track of the triangle direction.
		if (aStarSearch.setTriangle(start)) {
			// initializing the start node.
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 0);
		} else {
			// initializing the start node.
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 1);
		}
		ArrayList<NodeInformed> frontier = new ArrayList<NodeInformed>();
		// adding startNode to the frontier.
		frontier.add(startNode);
		// adding the coordinates of start node to explored Coord.
		// finding h_distance for source coord
		startNode.calcH_distance(goalTriangleCoord);
		// calculating the f_cost function value for the startNode.
		startNode.calcF_cost();
		// printing startNode
		aStarSearch.printFrontierAStar(frontier);
		while (!frontier.isEmpty()) {
			// popping and checking if the goal is reached.
			NodeInformed currentNode = frontier.remove(0);
			// adding one to the number of nodes traversed when it is popped.
			aStarSearch.addNodeNum();
			// adding the popped node coordinate to the explored coord as we do not want to
			// visit the node again.
			exploredCoord.add(currentNode.getCoord());
			if (aStarSearch.goalCheck(currentNode, goal)) {
				try {
					// creating a dummyNode as the same as currentNode to be send for printing the
					// path.
					NodeInformed dummyNode = (NodeInformed) currentNode.clone();
					aStarSearch.printPath(dummyNode);
				} catch (Exception e) {
					// printing clone error exception
					System.out.println(e);
				}
				// to print the cost from source to the goal
				System.out.println(currentNode.getCost());
				aStarSearch.setFinalCost(currentNode.getCost());
				// breaks of the loop if the goal is reached.
				break;
			}
			// creating nodes to push into the stack according to the order prefered.
			ArrayList<NodeInformed> nodes = new ArrayList<NodeInformed>();
			nodes = aStarSearch.nodeCreator(currentNode, map);
			// calculating and setting the h_distance and f_cost function values
			// for all the nodes created.
			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).calcH_distance(goalTriangleCoord);
				nodes.get(i).calcF_cost();
			}
			// comparing the nodes and the frontier checking for same coordinate nodes.
			// When any are found they are compared according to their f_cost function value
			// adn is replaced
			// if the newly created node has lowest value among both.
			if (!nodes.isEmpty()) {
				ArrayList<NodeInformed> sameCoord = new ArrayList<NodeInformed>();
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < frontier.size(); j++) {
						if (nodes.get(i).getCoord().equals(frontier.get(j).getCoord())) {
							// checking the f_cost of the equal coordinate nodes and changing the frontier
							// node if the condition is met.
							if (frontier.get(j).getF_costForAStar() > nodes.get(i).getF_costForAStar()) {
								frontier.set(j, nodes.get(i));
							}
							// if both have the same h_distance,comparing the depth
							else if (Double.compare(frontier.get(j).getF_costForAStar(),
									nodes.get(i).getF_costForAStar()) == 0) {
								if (!(frontier.get(j).getDepth() == nodes.get(i).getDepth())) {
									if (frontier.get(j).getDepth() > nodes.get(i).getDepth()) {
										frontier.set(j, nodes.get(i));
									}
								}
								// adding the coordinates of the node to the sameCoord, to be remopved later.
							}
							if (!sameCoord.contains(nodes.get(i))) {
								sameCoord.add(nodes.get(i));
							}
						}

					}
				}
				// adding explored Coord to sameCoord.
				// These are the coordinates already visited and popped out/expanded.
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < exploredCoord.size(); j++) {
						if (nodes.get(i).getCoord().equals(exploredCoord.get(j))) {
							sameCoord.add(nodes.get(i));
						}
					}
				}
				// remove from the sameCoord after everything.
				for (int i = 0; i < sameCoord.size(); i++) {
					nodes.remove((sameCoord.get(i)));
				}
			}
			// adding the popped nodes to the frontier.
			for (int i = nodes.size() - 1; i >= 0; i--) {
				frontier.add(nodes.get(i));
			}
			// need to rearrange the frontier according to f_cost function value of the
			// nodes.
			NodeInformed tempNode;
			for (int i = 0; i < frontier.size(); i++) {
				for (int j = 0; j < frontier.size() - 1; j++) {
					// comparing h_distance
					if (frontier.get(j).getF_costForAStar() > frontier.get(j + 1).getF_costForAStar()) {
						tempNode = frontier.get(j);
						frontier.set(j, frontier.get(j + 1));
						frontier.set(j + 1, tempNode);
						// if both have the same h_distance,tiebreaker - direction from the parentNode
						// is applied irrespective of the parentNode.
					} else if (Double.compare(frontier.get(j).getF_costForAStar(),
							frontier.get(j + 1).getF_costForAStar()) == 0) {
						if (!(frontier.get(j + 1).getDirectionFromParentNode()
								.equals(frontier.get(j).getDirectionFromParentNode()))) {
							if (frontier.get(j + 1).getDirectionFromParentNode().equals("Right")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							} else if (frontier.get(j + 1).getDirectionFromParentNode().equals("Down")
									&& !frontier.get(j).getDirectionFromParentNode().equals("Right")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							} else if (frontier.get(j).getDirectionFromParentNode().equals("Up")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							}
						} else {
							// if both the f_cost function and the the direction from the parent Node is the
							// same, depth is compared.
							if (!(frontier.get(j).getDepth() == frontier.get(j + 1).getDepth())) {
								if (frontier.get(j).getDepth() > frontier.get(j + 1).getDepth()) {
									tempNode = frontier.get(j);
									frontier.set(j, frontier.get(j + 1));
									frontier.set(j + 1, tempNode);
								}
								// if they have the same depth,comparing the direction from parentNode, they are
								// left as they are.
							}
						}
					}
				}
			}
			aStarSearch.printFrontierAStar(frontier);
		}
		if (!aStarSearch.hasGoalReached()) {
			System.out.println();
			System.out.println("fail");
		}
		System.out.println(aStarSearch.getNodesTraversed());
	}

	/**
	 * Method that implements Bidirectional search with heuristics.
	 * The same heuristics of the AStar algorithm is applied.
	 * 
	 * @param map   respective map acccording to the configuration.
	 * @param start Coordinates of the start node.
	 * @param goal  Coordiates of the goal node.
	 */
	public static void BidirectionalAStar(Map map, Coord start, Coord goal) {

		Search biAStar = new Search();
		// arraylists for storing explored nodes in forward and backward directions.
		ArrayList<NodeInformed> exploredNode = new ArrayList<NodeInformed>();
		ArrayList<NodeInformed> exploredNodeReverse = new ArrayList<NodeInformed>();
		// declaring the start and goal node.
		NodeInformed startNode;
		NodeInformed goalNode;
		// calculating the triangle coordinates for both the start and goal node.
		ArrayList<Double> goalTriangleCoord = biAStar.calcGoalCoord(goal);
		ArrayList<Double> startTriangleCoord = biAStar.calcGoalCoord(start);
		// initializing the start node.
		if (biAStar.setTriangle(start)) {
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 0);
		} else {
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 1);
		}
		// initializing the goal node.
		if (biAStar.setTriangle(goal)) {
			goalNode = new NodeInformed(null, goal.getR(), goal.getC(), -1, -1, null, 0);
		} else {
			goalNode = new NodeInformed(null, goal.getR(), goal.getC(), -1, -1, null, 1);
		}
		// declaring frontier.
		ArrayList<NodeInformed> frontier = new ArrayList<NodeInformed>();
		// adding startNode to the frontier.
		frontier.add(startNode);
		// creating a backfrontier and initializing it.
		ArrayList<NodeInformed> reverseFrontier = new ArrayList<NodeInformed>();
		reverseFrontier.add(goalNode);
		// finding h_distance for source coord
		startNode.calcH_distance(goalTriangleCoord);
		startNode.calcF_cost();
		// printing startNode
		System.out.print("Frontier start: ");
		biAStar.printFrontierAStar(frontier);
		// doing the same for goal Node
		goalNode.calcH_distance(startTriangleCoord);
		goalNode.calcF_cost();
		System.out.print("Reverse frontier start: ");
		biAStar.printFrontierAStar(reverseFrontier);
		if (map.getMap()[goal.getR()][goal.getC()] != 0) {
			frontier.clear();
			reverseFrontier.clear();
		}
		outer:while (!frontier.isEmpty() && !reverseFrontier.isEmpty()) {
			// popping and checking if the goal is reached.
			NodeInformed currentNode = frontier.remove(0);
			NodeInformed currentReverseNode = reverseFrontier.remove(0);
			// adding the nodes traversed.
			// since its bidirectional at a single loop two nodes are navigated/explored.
			biAStar.addNodeNum();
			biAStar.addNodeNum();
			// adding the popped node coordinates to the explored coord as we do not want to
			// visit the node again.
			exploredNode.add(currentNode);
			exploredNodeReverse.add(currentReverseNode);
			/**
			 * first checking if the currentNode exists in the reverseexploredCoords
			 * and if NodeurrentReverseNode exists in the exploredCoords.
			 * If either of them are true, then the goal is already met.
			 * IF the goal is met this way, the nodes traversde still is not changed
			 * as they are already popped.
			*/
			for(int i = 0; i < exploredNodeReverse.size(); i++){
				if(biAStar.goalCheck(currentNode, exploredNodeReverse.get(i).getCoord())) {
					try{
						NodeInformed dummyNode = (NodeInformed) currentNode.clone();
						System.out.println("from start :");
						biAStar.printPath(dummyNode);
						NodeInformed dummyReverseNode = (NodeInformed) exploredNodeReverse.get(i).clone();
						System.out.println("from goal :");
						biAStar.printPath(dummyReverseNode);
					} catch (Exception e) {
						// printing clone error exception
						System.out.println(e);
					}
					// to print the cost from source to the goal
					System.out.println(currentNode.getCost() + exploredNodeReverse.get(i).getCost());
					// setting the final cost
					biAStar.setFinalCost(currentNode.getCost() + exploredNodeReverse.get(i).getCost());
					// breaks of the loop if the goal is reached.
					break outer;
				}
			}
			//doing the vice-versa check
			for(int i = 0; i < exploredNode.size(); i++) {
				if(biAStar.goalCheck(currentReverseNode, exploredNode.get(i).getCoord())) {
					try{
						NodeInformed dummyNode = (NodeInformed) exploredNode.get(i).clone();
						System.out.print("From Source: ");
						biAStar.printPath(dummyNode);
						NodeInformed dummyReverseNode = (NodeInformed) currentReverseNode.clone();
						System.out.print("From goal: ");
						biAStar.printPath(dummyReverseNode);
					} catch (Exception e) {
						// printing clone error exception
						System.out.println(e);
					}
					// to print the total cost
					System.out.println(exploredNode.get(i).getCost() + currentReverseNode.getCost());
					// setting the final cost
					biAStar.setFinalCost(exploredNode.get(i).getCost() + currentReverseNode.getCost());
					// breaks of the loop if the goal is reached.
					break outer;
				}
			}

			if (biAStar.goalCheck(currentNode, currentReverseNode.getCoord())) {
				try {
					NodeInformed dummyNode = (NodeInformed) currentNode.clone();
					NodeInformed dummyNodeReversed = (NodeInformed) currentReverseNode.clone();
					System.out.print("From Source: ");
					biAStar.printPath(dummyNode);
					System.out.print("From goal: ");
					biAStar.printPath(dummyNodeReversed);
				} catch (Exception e) {
					// printing clone error exception
					System.out.println(e);
				}
				// to print the cost and depth from source to the goal
				System.out.println(currentNode.getCost() + currentReverseNode.getCost());
				// setting the final cost
				biAStar.setFinalCost(currentNode.getCost() + currentReverseNode.getCost());
				// breaks of the loop if the goal is reached.
				break;
			}
			// creating nodes to push into the stack according to the order mentioned.
			ArrayList<NodeInformed> nodes = new ArrayList<NodeInformed>();
			ArrayList<NodeInformed> nodesFromGoal = new ArrayList<NodeInformed>();
			nodes = biAStar.nodeCreator(currentNode, map);
			nodesFromGoal = biAStar.nodeCreator(currentReverseNode, map);
			// setting h_distance and f_cost function values for all the nodes created.
			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).calcH_distance(goalTriangleCoord);
				nodes.get(i).calcF_cost();
			}
			for (int i = 0; i < nodesFromGoal.size(); i++) {
				nodesFromGoal.get(i).calcH_distance(startTriangleCoord);
				nodesFromGoal.get(i).calcF_cost();
			}
			// to check if the coordinate of the nodes created coincide with the ones
			// already in the frontier.
			// And to compare and replace if they are already in the respective forward and
			// reverse frontiers.
			if (!nodes.isEmpty()) {
				ArrayList<NodeInformed> sameCoord = new ArrayList<NodeInformed>();
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < frontier.size(); j++) {
						if (nodes.get(i).getCoord().equals(frontier.get(j).getCoord())) {
							// checking the f_cost of the equal coordinate nodes and changing the frontier
							// node if the condition is met.
							if (frontier.get(j).getF_costForAStar() > nodes.get(i).getF_costForAStar()) {
								frontier.set(j, nodes.get(i));
								// if both have the same h_distance,comparing the depth
							} else if (Double.compare(frontier.get(j).getF_costForAStar(),
									nodes.get(i).getF_costForAStar()) == 0) {
								if (!(frontier.get(j).getDepth() == nodes.get(i).getDepth())) {
									if (frontier.get(j).getDepth() > nodes.get(i).getDepth()) {
										frontier.set(j, nodes.get(i));
									}
								}
							}
							// adding the coordinate to sameCoord array to be removed from the node later
							// on.
							if (!sameCoord.contains(nodes.get(i))) {
								sameCoord.add(nodes.get(i));
							}
						}

					}
				}
				// adding explored Coord to sameCoord.
				// These are the coordinates already visited and popped out/expanded.
				for (int i = 0; i < nodes.size(); i++) {
					for (int j = 0; j < exploredNode.size(); j++) {
						if (nodes.get(i).getCoord().equals(exploredNode.get(j).getCoord())) {
							sameCoord.add(nodes.get(i));
						}
					}
				}
				// remove from the sameCoord after everything.
				for (int i = 0; i < sameCoord.size(); i++) {
					nodes.remove((sameCoord.get(i)));
				}
				// finally adding the nodes to frontier.
				for (int i = nodes.size() - 1; i >= 0; i--) {
					frontier.add(nodes.get(i));
				}
			}
			// doing the same steps for the reverseNode expanded.
			if (!nodesFromGoal.isEmpty()) {
				ArrayList<NodeInformed> sameCoord = new ArrayList<NodeInformed>();
				for (int i = 0; i < nodesFromGoal.size(); i++) {
					for (int j = 0; j < reverseFrontier.size(); j++) {
						if (nodesFromGoal.get(i).getCoord().equals(reverseFrontier.get(j).getCoord())) {
							// checking the f_cost of the equal coordinate nodes and changing the frontier
							// node if the condition is met.
							if (reverseFrontier.get(j).getF_costForAStar() > nodesFromGoal.get(i).getF_costForAStar()) {
								reverseFrontier.set(j, nodesFromGoal.get(i));
							} else if (Double.compare(reverseFrontier.get(j).getF_costForAStar(),
									nodesFromGoal.get(i).getF_costForAStar()) == 0) {
								if (!(reverseFrontier.get(j).getDepth() == nodesFromGoal.get(i).getDepth())) {
									if (reverseFrontier.get(j).getDepth() > nodesFromGoal.get(i).getDepth()) {
										reverseFrontier.set(j, nodesFromGoal.get(i));
									}
								}
							}
							if (!sameCoord.contains(nodesFromGoal.get(i))) {
								sameCoord.add(nodesFromGoal.get(i));
							}
						}
					}
				}
				// adding explored Coord to sameCoord.
				// These are the coordinates already visited and popped out/expanded.
				for (int i = 0; i < nodesFromGoal.size(); i++) {
					for (int j = 0; j < exploredNodeReverse.size(); j++) {
						if (nodesFromGoal.get(i).getCoord().equals(exploredNodeReverse.get(j).getCoord())) {
							sameCoord.add(nodesFromGoal.get(i));
						}
					}
				}
				// remove from the sameCoord after everything.
				for (int i = 0; i < sameCoord.size(); i++) {
					nodesFromGoal.remove((sameCoord.get(i)));
				}
			}
			// adding the popped nodes to the frontier.
			for (int i = nodesFromGoal.size() - 1; i >= 0; i--) {
				reverseFrontier.add(nodesFromGoal.get(i));
			}

			// To re-arrange both the frontiers according to the f_cost function value
			// obtained.
			NodeInformed tempNode;
			for (int i = 0; i < frontier.size(); i++) {
				for (int j = 0; j < frontier.size() - 1; j++) {
					// comparing h_distance
					if (frontier.get(j).getF_costForAStar() > frontier.get(j + 1).getF_costForAStar()) {
						tempNode = frontier.get(j);
						frontier.set(j, frontier.get(j + 1));
						frontier.set(j + 1, tempNode);
						// if both have the same h_distance,comparing the direction from the parentnode
						// irrespective of the parentNode.
					} else if (Double.compare(frontier.get(j).getF_costForAStar(),
							frontier.get(j + 1).getF_costForAStar()) == 0) {
						if (!(frontier.get(j + 1).getDirectionFromParentNode()
								.equals(frontier.get(j).getDirectionFromParentNode()))) {
							if (frontier.get(j + 1).getDirectionFromParentNode().equals("Right")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							} else if (frontier.get(j + 1).getDirectionFromParentNode().equals("Down")
									&& !frontier.get(j).getDirectionFromParentNode().equals("Right")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							} else if (frontier.get(j).getDirectionFromParentNode().equals("Up")) {
								tempNode = frontier.get(j);
								frontier.set(j, frontier.get(j + 1));
								frontier.set(j + 1, tempNode);
							}
						} else {
							// if both f_cost function value and the direction are the same, compare the
							// depths of said nodes.
							if (!(frontier.get(j).getDepth() == frontier.get(j + 1).getDepth())) {
								if (frontier.get(j).getDepth() > frontier.get(j + 1).getDepth()) {
									tempNode = frontier.get(j);
									frontier.set(j, frontier.get(j + 1));
									frontier.set(j + 1, tempNode);
								}
							}
						}
					}
				}
			}
			// doing the same for the frontier keeping track from the goalnode.
			NodeInformed tempNodeForReverse;
			for (int i = 0; i < reverseFrontier.size(); i++) {
				for (int j = 0; j < reverseFrontier.size() - 1; j++) {
					// comparing h_distance
					if (reverseFrontier.get(j).getF_costForAStar() > reverseFrontier.get(j + 1).getF_costForAStar()) {
						tempNodeForReverse = reverseFrontier.get(j);
						reverseFrontier.set(j, reverseFrontier.get(j + 1));
						reverseFrontier.set(j + 1, tempNodeForReverse);
						// if both have the same h_distance,comparing the direction from the parentnode
						// irrespective of the parentNode.
					} else if (Double.compare(reverseFrontier.get(j).getF_costForAStar(),
							reverseFrontier.get(j + 1).getF_costForAStar()) == 0) {
						if (!(reverseFrontier.get(j + 1).getDirectionFromParentNode()
								.equals(reverseFrontier.get(j).getDirectionFromParentNode()))) {
							if (reverseFrontier.get(j + 1).getDirectionFromParentNode().equals("Right")) {
								tempNodeForReverse = reverseFrontier.get(j);
								reverseFrontier.set(j, reverseFrontier.get(j + 1));
								reverseFrontier.set(j + 1, tempNodeForReverse);
							} else if (reverseFrontier.get(j + 1).getDirectionFromParentNode().equals("Down")
									&& !reverseFrontier.get(j).getDirectionFromParentNode().equals("Right")) {
								tempNodeForReverse = reverseFrontier.get(j);
								reverseFrontier.set(j, reverseFrontier.get(j + 1));
								reverseFrontier.set(j + 1, tempNodeForReverse);
							} else if (reverseFrontier.get(j).getDirectionFromParentNode().equals("Up")) {
								tempNodeForReverse = reverseFrontier.get(j);
								reverseFrontier.set(j, reverseFrontier.get(j + 1));
								reverseFrontier.set(j + 1, tempNodeForReverse);
							}
						} else {
							// if both f_cost function value and the direction are the same, compare the
							// depths of said nodes.
							if (!(reverseFrontier.get(j).getDepth() == reverseFrontier.get(j + 1).getDepth())) {
								if (reverseFrontier.get(j).getDepth() > reverseFrontier.get(j + 1).getDepth()) {
									tempNodeForReverse = reverseFrontier.get(j);
									reverseFrontier.set(j, reverseFrontier.get(j + 1));
									reverseFrontier.set(j + 1, tempNodeForReverse);
								}
							}
						}
					}
				}
			}
			System.out.print("from Start: ");
			biAStar.printFrontierAStar(frontier);
			System.out.print("from goal: ");
			biAStar.printFrontierAStar(reverseFrontier);
		}
		if (!biAStar.hasGoalReached()) {
			System.out.println();
			System.out.println("fail");
		}
		System.out.println(biAStar.getNodesTraversed());
	}

	/**
	 * Method that implements tripleAgent with heuristics search.
	 * A search method that implements BestFirst search heuristics with three
	 * agents looking for the goal state. There is a main agent and the other 2
	 * are always adjacent to the main agent. The method is implemented to be
	 * adaptive to the
	 * number of nodes available at an instance(according to the triangular
	 * constraints) and has
	 * the ability to shrink irself to a two agent search and then back to three
	 * accordingly.
	 * Here the main agent has full flexibility and is able to move through and take
	 * over
	 * the adjacent agents according to the nodes availablity. The 3 agents perform
	 * as a
	 * single body/entity with two arms.
	 * 
	 * @param map   The map for the configuration.
	 * @param start The start coordinates.
	 * @param goal  The goal coordinates.
	 */
	public static void tripleAgentBestFS(Map map, Coord start, Coord goal) {

		Search bestFirstTriple = new Search();
		// arraylist for storing explored nodes.
		ArrayList<Coord> exploredCoord = new ArrayList<Coord>();
		// declaring the startNode and the 2 adjacentAgents.
		NodeInformed startNode;
		NodeInformed adjacentAgentOne;
		NodeInformed adjacentAgentTwo;
		ArrayList<Double> goalTriangleCoord = bestFirstTriple.calcGoalCoord(goal);
		// initializing the start Node.
		if (bestFirstTriple.setTriangle(start)) {
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 0);
		} else {
			startNode = new NodeInformed(null, start.getR(), start.getC(), -1, -1, null, 1);
		}
		ArrayList<NodeInformed> frontier = new ArrayList<NodeInformed>();
		/**
		 * Here the explored Coord takes all the coords that has been visited by both
		 * the
		 * adjacent agents and the main agent.Whereas the frontiertrackerCoord contains
		 * the nodes that only the main agent has been through.
		 */
		ArrayList<Coord> frontiertrackerCoord = new ArrayList<Coord>();
		// adding startNode to the frontier.
		//frontier.add(startNode);
		// adding the coordinates of start node to explored Coord adn
		// frontiertrackerCoord.
		exploredCoord.add(startNode.getCoord());
		frontiertrackerCoord.add(startNode.getCoord());
		// finding h_distance for source coord
		startNode.calcH_distance(goalTriangleCoord);
		// Printing the startNode
		System.out.println("StartNode" + startNode.getCoord());
		//bestFirstTriple.printFrontierBestF(frontier);
		// Steps to initializing the adjacent node to the adjacent agent.
		ArrayList<NodeInformed> initialNodes = bestFirstTriple.nodeCreator(startNode, map);
		if (initialNodes.size() == 1) {
			// the program is made to adapt to become 2 agent in the case of nodes.size() ==
			// 1.
			// The adjacent agents occupy the startNode and by the end of the first loop,
			// the
			// frontier occupies the expanded node.
			adjacentAgentOne = startNode;
			adjacentAgentTwo = startNode;
			// since there is only one node when expanding the startNode, and the
			// adjacent Agents are initialized by the start node, the frontier is added with
			// the
			// expanded node, moving one step ahead the loop.
			//frontier.remove(startNode);
			frontier.add(initialNodes.get(0));
			// Now in theory there is only one agent adjacent to the main agent.
			// This is because of the limited availability of the nodes.
			System.out.println("Agent1 :" + adjacentAgentOne.getCoord());
			System.out.println("Agent2 :" + adjacentAgentTwo.getCoord());
		} else if (initialNodes.size() == 0) {
			// if there are no expanded nodes then the search has failed.
			adjacentAgentOne = null;
			adjacentAgentTwo = null;
			startNode = null;
			System.out.println("fail");
		} else {
			// When there is more than 1 node, the adjacent agents randomly allocate 2 of
			// the popped occupiable nodes according to the constraints of the grid.
			int[] indexesToBeAllocated = bestFirstTriple.settingAdjacentNode(initialNodes.size());

			adjacentAgentOne = initialNodes.get(indexesToBeAllocated[0]);
			adjacentAgentTwo = initialNodes.get(indexesToBeAllocated[1]);

			initialNodes.remove(adjacentAgentOne);
			initialNodes.remove(adjacentAgentTwo);

			for(int i = 0; i < initialNodes.size(); i++) {
				frontier.add(initialNodes.get(i));
			}
			System.out.println("Agent1 :" + adjacentAgentOne.getCoord());
			System.out.println("Agent2 :" + adjacentAgentTwo.getCoord());

		}
		if (!(startNode == null) && !(adjacentAgentOne == null)) {
			// ArrayList<NodeInformed> PathNodeOne = new ArrayList<NodeInformed>();
			exploredCoord.add(adjacentAgentOne.getCoord());
			// ArrayList<NodeInformed> PathNodeTwo = new ArrayList<NodeInformed>();
			exploredCoord.add(adjacentAgentTwo.getCoord());
			// while frontier is not empty
			while (!frontier.isEmpty()) {
				// popping and checking if the goal is reached.
				NodeInformed currentNode = frontier.remove(0);

				// adding the nodes explored.
				// Here we only check the number oof nodes explored by the main agent.
				// As the other two agents always occupy adjacent nodes randomly.
				bestFirstTriple.addNodeNum();
				// goalCheck for all the three agents.
				if (bestFirstTriple.goalCheck(currentNode, goal)) {
					try {
						NodeInformed dummyNode = (NodeInformed) currentNode.clone();
						bestFirstTriple.printPath(dummyNode);
					} catch (Exception e) {
						// printing clone error exception
						System.out.println(e);
					}
					// to print the cost and depth from source to the goal
					System.out.println(currentNode.getCost());
					// feeding the final cost.
					bestFirstTriple.setFinalCost(currentNode.getCost());
					// breaks of the loop if the goal is reached.
					break;
				} else if (bestFirstTriple.goalCheck(adjacentAgentOne, goal)) {
					try {
						NodeInformed dummyNode = (NodeInformed) adjacentAgentOne.clone();
						bestFirstTriple.printPath(dummyNode);
					} catch (Exception e) {
						// printing clone error exception
						System.out.println(e);
					}
					// to print the cost and depth from source to the goal
					System.out.println(adjacentAgentOne.getCost());
					// feeding the final cost.
					bestFirstTriple.setFinalCost(adjacentAgentOne.getCost());
					// breaks of the loop if the goal is reached.
					break;
				} else if (bestFirstTriple.goalCheck(adjacentAgentTwo, goal)) {
					try {
						NodeInformed dummyNode = (NodeInformed) adjacentAgentTwo.clone();
						bestFirstTriple.printPath(dummyNode);
					} catch (Exception e) {
						// printing clone error exception
						System.out.println(e);
					}
					// to print the cost and depth from source to the goal
					System.out.println(adjacentAgentTwo.getCost());
					// feeding the final cost.
					bestFirstTriple.setFinalCost(adjacentAgentTwo.getCost());
					// breaks of the loop if the goal is reached.
					break;
				}

				// creating nodes to push into the stack according to the order prefered.
				ArrayList<NodeInformed> nodes = new ArrayList<NodeInformed>();
				nodes = bestFirstTriple.nodeCreator(currentNode, map);
				// setting h_distance for all the nodes cr eated.
				for (int i = 0; i < nodes.size(); i++) {
					nodes.get(i).calcH_distance(goalTriangleCoord);
				}
				if (!nodes.isEmpty()) {
					ArrayList<NodeInformed> sameCoord = new ArrayList<NodeInformed>();
					for (int i = 0; i < nodes.size(); i++) {
						for (int j = 0; j < exploredCoord.size(); j++) {
							if (nodes.get(i).getCoord().equals(exploredCoord.get(j))) {
								// adding explored nodes among the new nodes to sameCoord to be removed later.
								if (!sameCoord.contains(nodes.get(i))) {
									sameCoord.add(nodes.get(i));
								}
							}
						}
					}
					// removing the same nodes from the new node array.
					for (int i = 0; i < sameCoord.size(); i++) {
						nodes.remove((sameCoord.get(i)));
					}
				}
				// node allocation for the adjacent Nodes.
				if (nodes.size() >= 2) {
					int[] indexesToBeAllocated = bestFirstTriple.settingAdjacentNode(nodes.size());
					adjacentAgentOne = nodes.get(indexesToBeAllocated[0]);
					adjacentAgentTwo = nodes.get(indexesToBeAllocated[1]);
					//removing the nodes from the node array

					nodes.remove(adjacentAgentTwo);
					 nodes.remove(adjacentAgentOne);

					exploredCoord.add(adjacentAgentOne.getCoord());
					exploredCoord.add(adjacentAgentTwo.getCoord());
					// if(nodes.size() == 0) {
					// 	frontier.clear();
					// }
				} else if (nodes.size() == 1) {
					// making the adjacentNodes the currentNode
					// the main agent will be the frontier that has the new node
					adjacentAgentOne = currentNode;
					adjacentAgentTwo = currentNode;
				} else if (initialNodes.size() == 0) {
					// if there are no expanded nodes then clear the frontier
					//when the frontier is empty the main agent moves to one of the adjacentNode
					//and then move forward from there.
					if(!frontier.isEmpty()) {
						//since we are emptying the frontier so that it moves to the adjacent Node, we need to remove the
						//nodes in the frontier from exploredCoord.
						for(int i = 0; i < frontier.size(); i++) {
							for(int j = 0; j < exploredCoord.size(); j++){
							if(exploredCoord.get(j).equals(frontier.get(i).getCoord())) {
								exploredCoord.remove(j);
							}
						}
						}
					}
					frontier.clear();
				}
				// adding the nodes to the frontier and the coordinates to the explored Coord.
				for (int i = nodes.size() - 1; i >= 0; i--) {
					frontier.add(nodes.get(i));
					exploredCoord.add(nodes.get(i).getCoord());
				}
				// need to rearrange according to h_distance
				NodeInformed tempNode;
				for (int i = 0; i < frontier.size(); i++) {
					for (int j = 0; j < frontier.size() - 1; j++) {
						// comparing h_distance
						if (frontier.get(j).getH_distance() > frontier.get(j + 1).getH_distance()) {
							tempNode = frontier.get(j);
							frontier.set(j, frontier.get(j + 1));
							frontier.set(j + 1, tempNode);
							// if both have the same h_distance, arranging it according to the
							// directionFromParentNode.
						} else if (Double.compare(frontier.get(j).getH_distance(),
								frontier.get(j + 1).getH_distance()) == 0) {
							if (!(frontier.get(j + 1).getDirectionFromParentNode()
									.equals(frontier.get(j).getDirectionFromParentNode()))) {
								if (frontier.get(j + 1).getDirectionFromParentNode().equals("Right")) {
									tempNode = frontier.get(j);
									frontier.set(j, frontier.get(j + 1));
									frontier.set(j + 1, tempNode);
								} else if (frontier.get(j + 1).getDirectionFromParentNode().equals("Down")
										&& !frontier.get(j).getDirectionFromParentNode().equals("Right")) {
									tempNode = frontier.get(j);
									frontier.set(j, frontier.get(j + 1));
									frontier.set(j + 1, tempNode);
								} else if (frontier.get(j).getDirectionFromParentNode().equals("Up")) {
									tempNode = frontier.get(j);
									frontier.set(j, frontier.get(j + 1));
									frontier.set(j + 1, tempNode);
								}
							} else {
								if (!(frontier.get(j).getDepth() == frontier.get(j + 1).getDepth())) {
									if (frontier.get(j).getDepth() > frontier.get(j + 1).getDepth()) {
										tempNode = frontier.get(j);
										frontier.set(j, frontier.get(j + 1));
										frontier.set(j + 1, tempNode);
									}
								}
							}
						}
					}
				}
				bestFirstTriple.printFrontierBestF(frontier);
				System.out.println("Agent1 :" + adjacentAgentOne.getCoord());
				System.out.println("Agent2 :" + adjacentAgentTwo.getCoord());
				System.out.println("currentNode  :" + currentNode.getCoord());
				/**
				 * when the frontier is empty it would mean that the main agent was at a
				 * coordinate(before expanding) which
				 * has no new nodes, so we check if the frontier has been to either of the two
				 * agents.
				 * Then we shift the frontier (here the main head) to one of the adjacent Agents
				 * and continue from there.
				 * 
				 */
				if (frontier.isEmpty()) {
					if (!frontiertrackerCoord.contains(adjacentAgentOne.getCoord())
							&& !frontiertrackerCoord.contains(adjacentAgentTwo.getCoord())) {
						// the adjacentNode to be added to the frontier is decided by comparing the
						// distance.
						if (adjacentAgentOne.getH_distance() < adjacentAgentTwo.getH_distance()) {
							frontier.add(adjacentAgentOne);
							frontiertrackerCoord.add(adjacentAgentOne.getCoord());
						} else if (adjacentAgentOne.getH_distance() > adjacentAgentTwo.getH_distance()) {
							frontier.add(adjacentAgentTwo);
							frontiertrackerCoord.add(adjacentAgentTwo.getCoord());
							// if the h_distance function values are equal, the depth is taken as the next
							// parameter for adding.
						} else if (Double.compare(adjacentAgentOne.getH_distance(),
								adjacentAgentTwo.getH_distance()) == 0) {
							if (adjacentAgentOne.getDepth() < adjacentAgentTwo.getDepth()) {
								frontier.add(adjacentAgentOne);
								frontiertrackerCoord.add(adjacentAgentOne.getCoord());
							} else if (adjacentAgentOne.getDepth() > adjacentAgentTwo.getDepth()) {
								frontier.add(adjacentAgentTwo);
								frontiertrackerCoord.add(adjacentAgentTwo.getCoord());
								// if both the depth are equal, first the adjacentNodeOne is added.
								// if the first adjacent node is added the second takes the place in the
								// frontier.
							} else if (Double.compare(adjacentAgentOne.getDepth(), adjacentAgentTwo.getDepth()) == 0) {
								if (!currentNode.getCoord().equals(adjacentAgentOne.getCoord())) {
									frontier.add(adjacentAgentOne);
									frontiertrackerCoord.add(adjacentAgentOne.getCoord());
								} else {
									frontier.add(adjacentAgentTwo);
									frontiertrackerCoord.add(adjacentAgentTwo.getCoord());
								}
							}
						}
						// if either one of the adjacentNodes was occupied by the main agent the main
						// agent moves to the other node.
					} else if (!frontiertrackerCoord.contains(adjacentAgentOne.getCoord())) {
						frontier.add(adjacentAgentOne);
						frontiertrackerCoord.add(adjacentAgentOne.getCoord());
					} else if (!frontiertrackerCoord.contains(adjacentAgentTwo.getCoord())) {
						frontier.add(adjacentAgentTwo);
						frontiertrackerCoord.add(adjacentAgentTwo.getCoord());
					} else {
						break;
					}

				}
			}
		}
		if (!bestFirstTriple.hasGoalReached()) {
			System.out.println();
			System.out.println("fail");
		}
		System.out.println(bestFirstTriple.getNodesTraversed());

	}

}
