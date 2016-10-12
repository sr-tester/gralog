/*
 * This file is part of GrALoG FX, Copyright (c) 2016 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later.
 */
package gralog.importfilter;

import gralog.rendering.Vector2D;
import gralog.structure.*;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
 *
 */
@ImportFilterDescription(
        name = "Trivial Graph Format",
        text = "",
        url = "https://en.wikipedia.org/wiki/Trivial_Graph_Format",
        fileExtension = "tgf"
)
public class TrivialGraphFormatImport extends ImportFilter {

    @Override
    public Structure importGraph(InputStream stream,
            ImportFilterParameters params) throws Exception {
        DirectedGraph result = new DirectedGraph();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String s = br.readLine();

            HashMap<String, Vertex> NodeIndex = new HashMap<>();

            while (s != null) {

                s = s.trim();

                // ignore blank lines.
                if (s.equals("")) {
                    s = br.readLine();
                    continue;
                }

                // ignore lines that start with a comment marker ("#").
                if (s.substring(0, 1).equals("#")) {
                    s = br.readLine();
                    continue;
                }

                StringTokenizer tokenizer = new StringTokenizer(s, " ");
                String from = tokenizer.nextToken();
                String to = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

                if (to == null) {
                    Vertex newnode = result.createVertex();
                    newnode.label = from;
                    if (NodeIndex.containsKey(from))
                        throw new Exception("Vertex-identifier \"" + from + "\" multiply defined");
                    NodeIndex.put(from, newnode);
                }
                else {
                    Vertex nodeA = NodeIndex.containsKey(from) ? NodeIndex.get(from) : null;
                    Vertex nodeB = NodeIndex.containsKey(to) ? NodeIndex.get(to) : null;

                    if (nodeA == null)
                        throw new Exception("Edge containing undefined Vertex-identifier \"" + from + "\"");
                    if (nodeB == null)
                        throw new Exception("Edge containing undefined Vertex-identifier \"" + to + "\"");

                    Edge e = result.createEdge(nodeA, nodeB);
                    result.addEdge(e);
                }
                s = br.readLine();
            }

            for (Vertex newnode : NodeIndex.values()) {
                newnode.coordinates = new Vector2D(
                        Math.random() * 3d * NodeIndex.size(),
                        Math.random() * 3d * NodeIndex.size());
                result.addVertex(newnode);
            }
        }
        return result;
    }
}
