/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.structure;

import gralog.plugins.XmlName;

/**
 *
 */
@StructureDescription(
        name = "Directed Graph",
        text = "",
        url = "https://en.wikipedia.org/wiki/Directed_graph"
)
@XmlName(name = "digraph")
public class DirectedGraph extends Structure<Vertex, Edge> {

    @Override
    public Vertex createVertex() {
        return new Vertex();
    }

    @Override
    public Edge createEdge() {
        return new Edge();
    }
}
