# Marine-Navigation-Planner

## Description
This project aims to create a simplified marine navigation route planner for autonomous ferries. The system computes the best route for a vessel to navigate the sea surrounding the Republic of Izaland, connecting its 400 islands.

### Features
- **2D Triangular Mesh Map:** Utilizes a 2D triangular grid representing Izaland's sea and land.
- **Navigation Agent:** Determines the best route from the departure port to the destination port.
- **Search Algorithms:** Implements various AI search algorithms to find efficient routes for the ferry agent.

## Practical Requirements
The practical details can be found in the CS5011-A1.pdf file.

## Usage
### Basic Ferry Agent
#### Depth-First Search (DFS) and Breadth-First Search (BFS)
- Operates using uninformed search to identify the route.
- Avoids loops and redundant paths in the search algorithm.
- Provides coordinates, path sequence, path cost, and number of nodes visited.

### Intermediate Ferry Agent
#### Best-First Search (BestF) and A* Search (AStar)
- Implements informed search for efficient route planning.
- Uses the Manhattan distance heuristic on a triangular grid.
- Prints the coordinates and the cost in the frontier.

### Advanced Ferry Agent
#### Additional Functionalities
- Extension of search algorithms (e.g., bidirectional search).
- Implementing new types of actions (e.g., tide planning) and evaluating algorithms under those conditions.
- Adapting algorithms for a team of agents moving simultaneously with specific constraints.

## Report Overview
### Summary
Summarize the report and the functionalities implemented in your code.

### Design and Implementation
Outline the project structure, classes used, and implementation overview.

### Testing
Describe the methods used to test the program's functionalities.

---

This template provides a high-level structure for the README. You should fill in the specific details of your project, including usage instructions, examples, and other pertinent information. Also, link your code files or implementations wherever necessary to provide a better understanding for potential users.
