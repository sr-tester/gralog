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
        name = "Undirected Graph",
        text = "",
        url = "https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)#Undirected_graph"
)
@XmlName(name = "graph")
public class UndirectedGraph extends Structure<Vertex, Edge> {

    @Override
    public Vertex createVertex() {
        return new Vertex();
    }

    @Override
    public Edge createEdge() {
        Edge result = new Edge();
        result.isDirected = false;
        return result;
    }
}
