# Marine Navigation Route Planning for Autonomous Ferries

## Introduction
This repository addresses the task of designing a marine navigation route planner for autonomous ferries to navigate the sea surrounding the Republic of Izaland. The goal is to connect its 400 islands using a simplified 2D triangular mesh-based map. 

## Practical Requirements
The project revolves around developing ferry agents to find the best route from the departure port (S) to the destination port (G). The agents navigate through a grid of equilateral triangles, moving from the center of one triangle to another while avoiding land portions.

### Requirements Overview
- **Basic Ferry Agent:** Implement depth-first search (DFS) and breadth-first search (BFS) for uninformed search.
- **Intermediate Ferry Agent:** Extend the basic agent by implementing best-first search (BestF) and A* (AStar) search using the Manhattan distance heuristic.
- **Advanced Ferry Agent:** Extend the intermediate agent with additional functionalities, such as implementing bidirectional search or integrating new actions like planning for tides into the algorithms.

## Algorithms Implemented
- **Basic Agents:** Implemented DFS and BFS, focusing on efficient route identification and loop avoidance.
- **Intermediate Agents:** Introduced BestF and AStar search with heuristics for informed search, prioritizing frontier nodes by cost and tie-breaking values.
- **Advanced Agents:** Addressed additional functionalities such as bidirectional search, planning for tides, or considering multiple agents' movements.

## Implementation Details
The system is designed to compute the best route for autonomous ferries, ensuring efficient passenger transportation across the islands. The algorithms are tested and evaluated based on path cost, efficiency in visiting search states, and the overall quality of the solution.

### Evaluation Criteria
- **Quality of Solution:** Path cost and effectiveness of the identified route.
- **Efficiency:** Number of search states visited and algorithm performance.

## Repository Structure
- `/src`: Contains source code for implementing the ferry agents using various search algorithms.
- `/data`: Includes sample maps and data for testing the algorithms.
- `/results`: Holds outputs generated by the implemented algorithms, showcasing the identified routes and search statistics.

## Usage
- **Basic and Intermediate Agents:** Run the implemented algorithms using provided sample maps and evaluate their performance in finding routes from the departure port to the destination port.
- **Advanced Agents:** Explore additional functionalities and evaluate their impact on route planning, comparing their efficiency and solution quality with the basic and intermediate agents.

## Conclusion
The project offers a comprehensive approach to marine navigation route planning for autonomous ferries, addressing the challenges of efficient, safe, and informed route identification across a complex triangular grid.
