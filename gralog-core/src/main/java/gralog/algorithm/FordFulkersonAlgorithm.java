package gralog.algorithm;

import gralog.structure.*;
import gralog.progresshandler.ProgressHandler;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

@AlgorithmDescription(
    name = "Ford-Fulkerson Algorithm",
    text = "Finds the maximum flow in a network",
    url = "https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm"
)
public class FordFulkersonAlgorithm extends Algorithm {

    public Object run(Structure structure, Vertex source, Vertex sink, AlgorithmParameters params, ProgressHandler onProgress) {
        Map<Vertex, Edge> augmentingPath;
        double maxFlow = 0;

        while ((augmentingPath = findAugmentingPath(structure, source, sink)) != null) {
            double pathCapacity = findPathCapacity(augmentingPath, sink);
            maxFlow += pathCapacity;
            updateResidualGraph(augmentingPath, sink, pathCapacity);
        }

        return maxFlow;
    }

    private Map<Vertex, Edge> findAugmentingPath(Structure structure, Vertex source, Vertex sink) {
        Map<Vertex, Edge> parent = new HashMap<>();
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex node = queue.poll();
            for (Edge edge : structure.getEdges(node)) {
                Vertex neighbour = edge.getOtherEndpoint(node);
                if (!parent.containsKey(neighbour) && edge.getCapacity() > 0) {
                    parent.put(neighbour, edge);
                    if (neighbour == sink) {
                        return parent;
                    }
                    queue.add(neighbour);
                }
            }
        }
        return null;
    }

    private double findPathCapacity(Map<Vertex, Edge> augmentingPath, Vertex sink) {
        double pathCapacity = Double.MAX_VALUE;
        for (Vertex node = sink; augmentingPath.get(node) != null; node = augmentingPath.get(node).getOtherEndpoint(node)) {
            pathCapacity = Math.min(pathCapacity, augmentingPath.get(node).getCapacity());
        }
        return pathCapacity;
    }

    private void updateResidualGraph(Map<Vertex, Edge> augmentingPath, Vertex sink, double pathCapacity) {
        for (Vertex node = sink; augmentingPath.get(node) != null; node = augmentingPath.get(node).getOtherEndpoint(node)) {
            Edge edge = augmentingPath.get(node);
            edge.setCapacity(edge.getCapacity() - pathCapacity);
            Edge reverseEdge = edge.getReverseEdge();
            if (reverseEdge != null) {
                reverseEdge.setCapacity(reverseEdge.getCapacity() + pathCapacity);
            }
        }
    }
}
