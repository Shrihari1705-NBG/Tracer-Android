package com.shrihari.smartcampusnavigator.data.navigation.algorithm

import com.shrihari.smartcampusnavigator.data.navigation.graph.GraphNode
import kotlin.math.sqrt

object AStarAlgorithm {

    /**
     * Calculates the straight-line distance
     * between two graph nodes.
     */
    fun distance(
        from: GraphNode,
        to: GraphNode
    ): Float {

        val dx = to.position.x - from.position.x
        val dy = to.position.y - from.position.y

        return sqrt(dx * dx + dy * dy)

    }

    /**
     * Heuristic function used by A*.
     * We use Euclidean distance because our graph
     * nodes already have X and Y coordinates.
     */
    private fun heuristic(
        current: GraphNode,
        goal: GraphNode
    ): Float {

        return distance(current, goal)

    }

    /**
     * Finds the shortest path between two nodes.
     *
     * NOTE:
     * This is only the initial setup.
     * The main A* loop will be added next.
     */
    fun findPath(
        start: GraphNode,
        goal: GraphNode,
        allNodes: List<GraphNode>
    ): List<GraphNode> {

        // Nodes waiting to be explored
        val openSet = mutableSetOf(start)

        // Keeps track of the best parent
        val cameFrom = mutableMapOf<GraphNode, GraphNode>()

        // Cost from start to each node
        val gScore = mutableMapOf<GraphNode, Float>()

        // Estimated total cost
        val fScore = mutableMapOf<GraphNode, Float>()

        // Initialize scores
        allNodes.forEach { node ->

            gScore[node] = Float.POSITIVE_INFINITY
            fScore[node] = Float.POSITIVE_INFINITY

        }

        gScore[start] = 0f
        fScore[start] = heuristic(start, goal)

        // Main A* loop will be added next

        while (openSet.isNotEmpty()) {

            // Node with the lowest fScore
            val current = openSet.minByOrNull {
                fScore[it] ?: Float.POSITIVE_INFINITY
            }!!

            // Goal reached
            if (current == goal) {

                val path = mutableListOf<GraphNode>()
                var currentNode = current

                path.add(currentNode)

                while (cameFrom.containsKey(currentNode)) {

                    currentNode = cameFrom[currentNode]!!
                    path.add(currentNode)

                }

                path.reverse()

                return path
            }

            openSet.remove(current)

            current.neighbors.forEach { neighborId ->

                val neighbor = allNodes.find {
                    it.id == neighborId
                } ?: return@forEach

                val tentativeGScore =
                    gScore[current]!! + distance(current, neighbor)

                if (tentativeGScore < gScore[neighbor]!!) {

                    cameFrom[neighbor] = current

                    gScore[neighbor] = tentativeGScore

                    fScore[neighbor] =
                        tentativeGScore + heuristic(neighbor, goal)

                    openSet.add(neighbor)

                }

            }

        }

        return emptyList()

    }

}