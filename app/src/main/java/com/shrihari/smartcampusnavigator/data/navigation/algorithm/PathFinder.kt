package com.shrihari.smartcampusnavigator.data.navigation.algorithm

import com.shrihari.smartcampusnavigator.data.navigation.graph.GraphNode
import com.shrihari.smartcampusnavigator.data.navigation.graph.GraphRepository

object PathFinder {

    fun findPath(
        startNodeId: String,
        destinationNodeId: String
    ): List<GraphNode> {

        val nodes = GraphRepository.nodes

        val startNode = nodes.find { it.id == startNodeId } ?: return emptyList()
        val destinationNode = nodes.find { it.id == destinationNodeId } ?: return emptyList()

        return AStarAlgorithm.findPath(
            start = startNode,
            goal = destinationNode,
            allNodes = nodes
        )
    }
}