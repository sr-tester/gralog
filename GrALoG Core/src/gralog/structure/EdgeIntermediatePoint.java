/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.structure;

import gralog.plugins.XmlMarshallable;
import gralog.plugins.XmlName;
import gralog.rendering.Vector2D;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author viktor
 */
@XmlName(name = "intermediatepoint")
public class EdgeIntermediatePoint extends XmlMarshallable implements IMovable {

    public Vector2D coordinates;

    public EdgeIntermediatePoint() {
        coordinates = new Vector2D(0d, 0d);
    }

    public EdgeIntermediatePoint(Double x, Double y) {
        coordinates = new Vector2D(x, y);
    }

    public EdgeIntermediatePoint(Vector2D coords) {
        coordinates = coords;
    }

    public boolean containsCoordinate(double x, double y) {
        return (getX() - x) * (getX() - x)
               + (getY() - y) * (getY() - y) < 0.15 * 0.15;
    }

    @Override
    public void move(Vector2D offset) {
        coordinates = coordinates.plus(offset);
    }

    public void snapToGrid(double GridSize) {
        coordinates = coordinates.snapToGrid(GridSize);
    }

    public double get(int dimension) {
        if (dimension < coordinates.dimensions())
            return coordinates.get(dimension);
        return 0.0d;
    }

    public double getX() {
        return coordinates.getX();
    }

    public double getY() {
        return coordinates.getY();
    }

    @Override
    public Element toXml(Document doc) throws Exception {
        Element enode = super.toXml(doc);
        enode.setAttribute("x", Double.toString(getX()));
        enode.setAttribute("y", Double.toString(getY()));
        return enode;
    }

    public void fromXml(Element enode) {
        double x = Double.parseDouble(enode.getAttribute("x"));
        double y = Double.parseDouble(enode.getAttribute("y"));
        coordinates = new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }
}
