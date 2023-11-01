package gralog.algorithm;

import gralog.structure.Edge;
import gralog.structure.Vertex;
import gralog.structure.Structure;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FordFulkersonAlgorithm extends Algorithm {

    private Map<Edge, Integer> edgeToResidualCapacityMap = new HashMap<>();

    @Override
    public Object run(Structure structure, AlgorithmParameters params, Set<Object> selection, ProgressHandler onprogress) throws Exception {
        Vertex source = params.getSource();
        Vertex sink = params.getSink();

        while (hasAugmentingPath(structure, source, sink)) {
            int pathFlow = Integer.MAX_VALUE;
            for (Vertex v = sink; v != source; v = edgeTo[v]) {
                Edge e = edgeToResidualCapacityMap.get(v);
                pathFlow = Math.min(pathFlow, e.residualCapacityTo(v));
            }

            for (Vertex v = sink; v != source; v = edgeTo[v]) {
                Edge e = edgeToResidualCapacityMap.get(v);
                e.addResidualFlowTo(v, pathFlow);
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private boolean hasAugmentingPath(Structure structure, Vertex source, Vertex sink) {
        edgeToResidualCapacityMap = new HashMap<>();

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(source);

        while (!queue.isEmpty() && edgeToResidualCapacityMap.get(sink) == null) {
            Vertex v = queue.remove();

            for (Edge e : v.getIncidentEdges()) {
                Vertex w = e.other(v);

                if (e.residualCapacityTo(w) > 0) {
                    if (edgeToResidualCapacityMap.get(w) == null) {
                        edgeToResidualCapacityMap.put(w, e);
                        queue.add(w);
                    }
                }
            }
        }

        return edgeToResidualCapacityMap.get(sink) != null;
    }
}
