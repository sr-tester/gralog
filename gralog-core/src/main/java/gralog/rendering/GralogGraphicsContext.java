/* This file is part of Gralog, Copyright (c) 2016-2017 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later. */
package gralog.rendering;

import javafx.geometry.Point2D;
import javafx.scene.paint.*;

import java.util.Arrays;

/**
 * This class offers abstract drawing and drawing utility methods.
 *
 * Utilities include drawing tooltips and transparent rectangles for selection boxes.
 *
 */
public abstract class GralogGraphicsContext {

    public abstract void line(double x1, double y1, double x2, double y2,
        GralogColor color, double width);

    public void line(Vector2D from, Vector2D to, GralogColor color, double width) {
        line(from.getX(), from.getY(), to.getX(), to.getY(), color, width);
    }

    public void arrow(Vector2D from, Vector2D to,
        double headAngle, double headLength, GralogColor color, double width) {
        final double x1 = from.getX();
        final double y1 = from.getY();
        final double x2 = to.getX();
        final double y2 = to.getY();

        double arrowHeadAngle = headAngle / 2.0d;
        double arrowHeadLength = headLength;

        double alpha = (new Vector2D(x2 - x1, y2 - y1)).theta();

        double arrowX1 = x2 - arrowHeadLength * Math.cos((alpha - arrowHeadAngle) * Math.PI / 180.0d);
        double arrowY1 = y2 - arrowHeadLength * Math.sin((alpha - arrowHeadAngle) * Math.PI / 180.0d);
        double arrowX2 = x2 - arrowHeadLength * Math.sin((90d - alpha - arrowHeadAngle) * Math.PI / 180.0d);
        double arrowY2 = y2 - arrowHeadLength * Math.cos((90d - alpha - arrowHeadAngle) * Math.PI / 180.0d);

        line(x1, y1, x2, y2, color, width);
        // arrow head
        line(arrowX1, arrowY1, x2, y2, color, width);
        line(arrowX2, arrowY2, x2, y2, color, width);
        line(arrowX1, arrowY1, arrowX2, arrowY2, color, width);
    }
    public void arrow(Vector2D dir, Vector2D pos, Arrow arrowType, double scale, GralogColor color){
        double theta = Math.toRadians(dir.theta());

        double[] xs = Arrays.copyOf(arrowType.xPoints, arrowType.xPoints.length);
        double[] ys = Arrays.copyOf(arrowType.yPoints, arrowType.yPoints.length);

        for (int i = 0; i < arrowType.xPoints.length; i++)
        {
            double oldX = xs[i];
            xs[i] = (xs[i] * Math.cos(theta) - ys[i] * Math.sin(theta)) * scale + pos.getX();
            ys[i] = (oldX  * Math.sin(theta) + ys[i] * Math.cos(theta)) * scale + pos.getY();
        }
        polygon(xs, ys, arrowType.count, color);
    }

    public abstract void polygon(double[] x, double[] y, int count, GralogColor color);

    public abstract void loop(Vector2D pos, double size, GralogColor color, double width);

    public abstract void circle(double centerx, double centery, double radius,
        GralogColor color);

    public void circle(Vector2D center, double radius, GralogColor color) {
        circle(center.getX(), center.getY(), radius, color);
    }
    public void rectangle(double x1, double y1, double x2, double y2,
        GralogColor color, double width) {
        line(x1, y1, x2, y1, color, width);
        line(x2, y1, x2, y2, color, width);
        line(x2, y2, x1, y2, color, width);
        line(x1, y2, x1, y1, color, width);
    }

    public void rectangle(Vector2D corner1, Vector2D corner2,
        GralogColor color, double width) {
        rectangle(corner1.getX(), corner1.getY(),
            corner2.getX(), corner2.getY(),
            color, width);
    }

    public abstract void fillRectangle(
        double x1, double y1, double x2, double y2, GralogColor color);

    public void fillRectangle(Vector2D corner1, Vector2D corner2,
        GralogColor color) {
        fillRectangle(corner1.getX(), corner1.getY(),
            corner2.getX(), corner2.getY(),
            color);
    }

    public abstract void selectionRectangle(Point2D from, Point2D to, Color color);

    public abstract void putText(double centerx, double centery, String text,
        double lineHeightCm, GralogColor color);

    public void putText(Vector2D center, String text,
        double lineHeightCm, GralogColor color) {
        putText(center.getX(), center.getY(), text, lineHeightCm, color);
    }
}
