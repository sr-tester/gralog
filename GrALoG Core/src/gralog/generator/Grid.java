/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.generator;

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

    // null means it has no parameters
    @Override
    public GeneratorParameters getParameters() {
        return new StringGeneratorParameter("5");
    }

    @Override
    public Structure generate(GeneratorParameters p) throws Exception {
        Integer n = Integer.parseInt(((StringGeneratorParameter) p).parameter);

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
