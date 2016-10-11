/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.structure;

import gralog.plugins.*;
import gralog.events.*;
import gralog.rendering.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author viktor
 */
@XmlName(name = "edge")
public class Edge extends XmlMarshallable implements IMovable {

    Set<EdgeListener> listeners = new HashSet<>();

    public String label = "";
    public double cost = 1.0d;

    public Boolean isDirected = true;

    public double arrowHeadLength = 0.4d; // cm
    public double arrowHeadAngle = 40d; // degrees
    public double width = 2.54 / 96; // cm
    public GralogColor color = GralogColor.BLACK;

    public ArrayList<EdgeIntermediatePoint> intermediatePoints = new ArrayList<>();

    private Vertex source = null;
    private Vertex target = null;

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        if (this.source != null)
            this.source.disconnectEdge(this);
        this.source = source;
        if (source != null)
            this.source.connectEdge(this);
    }

    public Vertex getTarget() {
        return target;
    }

    public void setTarget(Vertex target) {
        if (this.target != null)
            this.target.disconnectEdge(this);
        this.target = target;
        if (target != null)
            this.target.connectEdge(this);
    }

    public double maximumCoordinate(int dimension) {
        double result = Double.NEGATIVE_INFINITY;
        for (EdgeIntermediatePoint between : intermediatePoints)
            result = Math.max(result, between.get(dimension));
        return result;
    }

    @Override
    public void move(Vector2D offset) {
        for (EdgeIntermediatePoint between : intermediatePoints)
            between.move(offset);
    }

    public void snapToGrid(double GridSize) {
        for (EdgeIntermediatePoint between : intermediatePoints)
            between.snapToGrid(GridSize);
    }

    public IMovable findObject(double x, double y) {
        for (EdgeIntermediatePoint p : intermediatePoints)
            if (p.containsCoordinate(x, y))
                return p;

        if (this.containsCoordinate(x, y))
            return this;

        return null;
    }

    protected double binomialCoefficients(int n, int k) {
        double result = 1.0;
        for (int i = 1; i <= k; i++)
            result = result * (n + 1 - i) / i;
        return result;
    }

    protected Vector2D bezierCurve(double t) {
        int n = intermediatePoints.size() + 1;

        Vector2D result = this.source.coordinates.multiply(Math.pow(1 - t, n));
        result = result.plus(this.target.coordinates.multiply(Math.pow(t, n)));

        int i = 1;
        for (EdgeIntermediatePoint between : intermediatePoints) {
            result = result.plus(between.coordinates.multiply(binomialCoefficients(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i)
            ));
            i++;
        }

        return result;
    }

    public void render(GralogGraphicsContext gc, Set<Object> highlights) {
        double fromX = source.coordinates.get(0);
        double fromY = source.coordinates.get(1);
        double toX = target.coordinates.get(0);
        double toY = target.coordinates.get(1);

        /*
        if(intermediatePoints.size() > 0)
        {
            double steps = 1/40d;

            for(double t = steps; t < 1; t += steps) {
                VectorND next = BezierCurve(t);
                double tempX = next.get(0);
                double tempY = next.get(1);

                gc.Line(fromX, fromY, tempX, tempY, this.Color, this.Width);
                fromX = tempX;
                fromY = tempY;
            }
            gc.Line(fromX, fromY, toX, toY, this.Color, this.Width);

            //if(highlights != null && highlights.contains(this))
                for(EdgeIntermediatePoint between : intermediatePoints)
                    gc.Rectangle(between.get(0)-0.1, between.get(1)-0.1, between.get(0)+0.1, between.get(1)+0.1, this.Color, 2.54/96);
                
        } else {
            gc.Line(fromX, fromY, toX, toY, this.Color, this.Width);
        }
         */
        double tempX = fromX;
        double tempY = fromY;

        GralogColor color = this.color;
        if (highlights != null && highlights.contains(this))
            color = GralogColor.RED;

        for (EdgeIntermediatePoint between : intermediatePoints) {
            fromX = tempX;
            fromY = tempY;
            tempX = between.getX();
            tempY = between.getY();
            gc.line(fromX, fromY, tempX, tempY, color, width);

            if (highlights != null && highlights.contains(this))
                gc.rectangle(tempX - 0.1, tempY - 0.1, tempX + 0.1, tempY + 0.1, color, 2.54 / 96);
        }

        if (isDirected) {
            Vector2D intersection = target.intersection(new Vector2D(tempX, tempY), new Vector2D(toX, toY));
            gc.arrow(tempX, tempY, intersection.getX(), intersection.getY(), arrowHeadAngle, arrowHeadLength, color, width);
        }
        else
            gc.line(tempX, tempY, toX, toY, color, width);
    }

    public boolean containsCoordinate(double x, double y) {
        double fromX = source.coordinates.get(0);
        double fromY = source.coordinates.get(1);
        double nextfromX = fromX;
        double nextfromY = fromY;

        for (EdgeIntermediatePoint between : intermediatePoints) {
            fromX = nextfromX;
            fromY = nextfromY;
            nextfromX = between.getX();
            nextfromY = between.getY();
            if (Vector2D.distancePointToLine(x, y, fromX, fromY, nextfromX, nextfromY) < 0.3)
                return true;
        }

        double toX = target.coordinates.get(0);
        double toY = target.coordinates.get(1);
        return Vector2D.distancePointToLine(x, y, nextfromX, nextfromY, toX, toY) < 0.3;
    }

    public EdgeIntermediatePoint addIntermediatePoint(double x, double y) {
        double fromX = source.coordinates.get(0);
        double fromY = source.coordinates.get(1);
        double nextfromX = fromX;
        double nextfromY = fromY;

        int i = 0, insertionIndex = 0;
        double MinDistance = Double.MAX_VALUE;

        for (EdgeIntermediatePoint between : intermediatePoints) {
            fromX = nextfromX;
            fromY = nextfromY;
            nextfromX = between.getX();
            nextfromY = between.getY();

            double distanceTemp = Vector2D.distancePointToLine(x, y, fromX, fromY, nextfromX, nextfromY);
            if (distanceTemp < MinDistance) {
                insertionIndex = i;
                MinDistance = distanceTemp;
            }
            i++;
        }

        double toX = target.coordinates.get(0);
        double toY = target.coordinates.get(1);

        double distanceTemp = Vector2D.distancePointToLine(x, y, nextfromX, nextfromY, toX, toY);
        if (distanceTemp < MinDistance) {
            insertionIndex = intermediatePoints.size();
        }

        EdgeIntermediatePoint result = new EdgeIntermediatePoint(x, y);
        intermediatePoints.add(insertionIndex, result);
        return result;
    }

    public boolean containsVertex(Vertex v) {
        return source == v || target == v;
    }

    public double length() {
        Vector2D from = this.source.coordinates;
        Vector2D to = this.target.coordinates;

        double result = 0.0;
        for (EdgeIntermediatePoint between : this.intermediatePoints) {
            result += between.coordinates.minus(from).length();
            from = between.coordinates;
        }
        return result + to.minus(from).length();
    }

    public Element toXml(Document doc, HashMap<Vertex, String> ids) throws Exception {
        Element enode = super.toXml(doc);
        enode.setAttribute("source", ids.get(source));
        enode.setAttribute("target", ids.get(target));
        enode.setAttribute("isdirected", isDirected ? "true" : "false");
        enode.setAttribute("label", label);
        enode.setAttribute("cost", Double.toString(cost));
        enode.setAttribute("width", Double.toString(width));
        enode.setAttribute("arrowheadlength", Double.toString(arrowHeadLength));
        enode.setAttribute("arrowheadangle", Double.toString(arrowHeadAngle));
        enode.setAttribute("color", color.toHtmlString());

        for (EdgeIntermediatePoint p : intermediatePoints) {
            Element e = p.toXml(doc);
            if (e != null)
                enode.appendChild(e);
        }

        return enode;
    }

    public void fromXml(Element enode, HashMap<String, Vertex> ids) throws Exception {
        source = ids.get(enode.getAttribute("source"));
        target = ids.get(enode.getAttribute("target"));

        if (enode.hasAttribute("isdirected"))
            isDirected = enode.getAttribute("isdirected").equals("true");
        label = enode.getAttribute("label");
        if (enode.hasAttribute("cost"))
            cost = Double.parseDouble(enode.getAttribute("cost"));

        if (enode.hasAttribute("width"))
            width = Double.parseDouble(enode.getAttribute("width"));

        if (enode.hasAttribute("arrowheadlength"))
            arrowHeadLength = Double.parseDouble(enode.getAttribute("arrowheadlength"));
        if (enode.hasAttribute("arrowheadangle"))
            arrowHeadAngle = Double.parseDouble(enode.getAttribute("arrowheadangle"));
        if (enode.hasAttribute("color"))
            color = GralogColor.parseColor(enode.getAttribute("color"));

        NodeList children = enode.getChildNodes(); // load intermediate points
        for (int i = 0; i < children.getLength(); ++i) {
            Node childNode = children.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element child = (Element) childNode;
            Object obj = PluginManager.instantiateClass(child.getTagName());
            if (obj instanceof EdgeIntermediatePoint) {
                EdgeIntermediatePoint p = (EdgeIntermediatePoint) obj;
                p.fromXml(child);
                this.intermediatePoints.add(p);
            }
        }
    }

    protected void notifyEdgeListeners() {
        for (EdgeListener listener : listeners)
            listener.EdgeChanged(new EdgeEvent(this));
    }

    public void addEdgeListener(EdgeListener listener) {
        listeners.add(listener);
    }

    public void removeEdgeListener(EdgeListener listener) {
        listeners.remove(listener);
    }
}
