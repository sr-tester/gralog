/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.automaton;

import gralog.plugins.XmlName;
import gralog.rendering.*;
import gralog.structure.*;

import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author viktor
 */
@XmlName(name = "state")
public class State extends Vertex {

    public Boolean startState = false;
    public Boolean finalState = false;

    public Double initialMarkAngle = 0d; // degrees
    public Double initialMarkLength = 0.7d; // cm
    public Double initialMarkWidth = 2.54 / 96; // cm
    public Double initialMarkHeadAngle = 40d; // degrees
    public Double initialMarkHeadLength = 0.4d; // cm
    public GralogColor initialMarkColor = GralogColor.BLACK;

    @Override
    public Element toXml(Document doc, String id) throws Exception {
        Element vnode = super.toXml(doc, id);
        vnode.setAttribute("initial", startState ? "true" : "false");
        vnode.setAttribute("final", finalState ? "true" : "false");
        vnode.setAttribute("initialmarkangle", initialMarkAngle.toString());
        vnode.setAttribute("initialmarklength", initialMarkLength.toString());
        vnode.setAttribute("initialmarkwidth", initialMarkWidth.toString());
        vnode.setAttribute("initialmarkheadangle", initialMarkHeadAngle.toString());
        vnode.setAttribute("initialmarkheadlength", initialMarkHeadLength.toString());
        vnode.setAttribute("initialmarkcolor", initialMarkColor.toHtmlString());
        return vnode;
    }

    @Override
    public String fromXml(Element vnode) {
        String id = super.fromXml(vnode);
        startState = (vnode.getAttribute("initial").equals("true"));
        finalState = (vnode.getAttribute("final").equals("true"));
        if (vnode.hasAttribute("initialmarkangle"))
            initialMarkAngle = Double.parseDouble(vnode.getAttribute("initialmarkangle"));
        if (vnode.hasAttribute("initialmarklength"))
            initialMarkLength = Double.parseDouble(vnode.getAttribute("initialmarklength"));
        if (vnode.hasAttribute("initialmarkwidth"))
            initialMarkWidth = Double.parseDouble(vnode.getAttribute("initialmarkwidth"));
        if (vnode.hasAttribute("initialmarkheadangle"))
            initialMarkHeadAngle = Double.parseDouble(vnode.getAttribute("initialmarkheadangle"));
        if (vnode.hasAttribute("initialmarkheadlength"))
            initialMarkHeadLength = Double.parseDouble(vnode.getAttribute("initialmarkheadlength"));
        if (vnode.hasAttribute("initialmarkcolor"))
            initialMarkColor = GralogColor.parseColor(vnode.getAttribute("initialmarkcolor"));

        return id;
    }

    @Override
    public void render(GralogGraphicsContext gc, Set<Object> highlights) {
        if (highlights != null && highlights.contains(this))
            gc.circle(coordinates.get(0), coordinates.get(1), radius + 0.07, GralogColor.RED);

        gc.circle(coordinates.get(0), coordinates.get(1), radius, strokeColor);
        gc.circle(coordinates.get(0), coordinates.get(1), radius - strokeWidth, fillColor);
        if (this.finalState) {
            gc.circle(coordinates.get(0), coordinates.get(1), radius - 3 * strokeWidth, strokeColor);
            gc.circle(coordinates.get(0), coordinates.get(1), radius - 4 * strokeWidth, fillColor);
        }

        if (startState) {
            Vector2D center = coordinates;
            Vector2D intersectionOffset = new Vector2D(-radius * Math.cos(initialMarkAngle / 180 * Math.PI),
                                                       -radius * Math.sin(initialMarkAngle / 180 * Math.PI));
            Vector2D intersection = center.plus(intersectionOffset);
            Vector2D headStart = intersection.plus(intersectionOffset.normalized().multiply(initialMarkLength));

            gc.arrow(headStart.getX(), headStart.getY(), intersection.getX(), intersection.getY(),
                     initialMarkHeadAngle, initialMarkHeadLength, initialMarkColor, initialMarkWidth);
        }

        gc.putText(coordinates.get(0), coordinates.get(1), label, textHeight, fillColor.inverse());
    }
}
