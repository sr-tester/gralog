/*
 * This file is part of GrALoG FX, Copyright (c) 2016 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later.
 */
package gralog.generator;

import gralog.algorithm.AlgorithmParameters;
import gralog.algorithm.IntSyntaxChecker;
import gralog.algorithm.StringAlgorithmParameter;
import gralog.rendering.Vector2D;
import gralog.structure.*;
import java.util.ArrayList;

/**
 *
 */
@GeneratorDescription(
        name = "Grid",
        text = "Generates a Grid-Graph",
        url = "https://en.wikipedia.org/wiki/Lattice_graph"
)
public class Grid extends Generator {

    @Override
    public AlgorithmParameters getParameters() {
        return new StringAlgorithmParameter(
                "Size", "5",
                new IntSyntaxChecker(1, Integer.MAX_VALUE),
                "");
    }

    @Override
    public Structure generate(AlgorithmParameters p) throws Exception {
        int n = Integer.parseInt(((StringAlgorithmParameter) p).parameter);

        UndirectedGraph result = new UndirectedGraph();

        ArrayList<Vertex> last = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            Vertex temp = result.createVertex();
            last.add(temp);
            temp.coordinates = new Vector2D(1d, 2d * j + 1d);
            if (j > 0)
                result.addEdge(result.createEdge(last.get(j - 1), temp));
            result.addVertex(temp);
        }

        for (int i = 1; i < n; i++) {
            ArrayList<Vertex> next = new ArrayList<>();
            Vertex lasttemp = null;
            for (int j = 0; j < n; j++) {
                Vertex temp = result.createVertex();
                next.add(temp);
                temp.coordinates = new Vector2D(2d * i + 1d, 2d * j + 1d);
                if (j > 0)
                    result.addEdge(result.createEdge(lasttemp, temp));
                result.addEdge(result.createEdge(last.get(j), temp));

                result.addVertex(temp);
                lasttemp = temp;
            }

            last = next;
        }

        return result;
    }
}
