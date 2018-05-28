package gralog.structure;

import gralog.rendering.Vector2D;
import gralog.structure.controlpoints.ControlPoint;
import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void controlPointCreation(){

        Structure s = new DirectedGraph();

        Vertex first = s.createVertex();
        Vertex secnd = s.createVertex();

        ControlPoint ctrl1, ctrl2, ctrl3;

        first.coordinates = new Vector2D(0, 0);
        secnd.coordinates = new Vector2D(1, 0);

        s.addVertex(first);
        s.addVertex(secnd);

        Edge e = s.addEdge(first, secnd);
        e.edgeType = Edge.EdgeType.SHARP;

        ctrl1 = e.addControlPoint(new Vector2D(0,1), new Vector2D(0.5, 0));
        ctrl2 = e.addControlPoint(new Vector2D(1,1), new Vector2D(0.5, 0.5));

        /*
        current state
        o - - - o
        |       |
        |       |
        |       |
        c - - - c
        */

        ctrl3 = e.addControlPoint(new Vector2D(2,0), new Vector2D(1, 0.5));
        assertEquals(2, e.controlPoints.indexOf(ctrl3));

        /*
        v1 ---- v2 -- c3
        |           /
        |          /
        |         /
        c1 ---- c2
        */

        ctrl3 = e.addControlPoint(new Vector2D(0, 2), new Vector2D(0.5, 1));
        assertEquals(1, e.controlPoints.indexOf(ctrl3));
        assertEquals(2, e.controlPoints.indexOf(ctrl2));

        /*
        v1 ---- v2 -- c3
        |           /
        |          /
        |         /
        c1      c2
        |      /
        |     /
        |    /
        |   /
        c3'
        */
    }
}