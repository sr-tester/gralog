package gralog.gralogfx.views;

import gralog.algorithm.FordFulkersonAlgorithm;
import gralog.structure.Structure;
import gralog.structure.Edge;
import gralog.structure.Vertex;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FordFulkersonView extends View<Structure> {

    private Structure structure;
    private FordFulkersonAlgorithm algorithm;

    @Override
    public void setObject(Structure structure, Consumer<Boolean> submitPossible) {
        this.structure = structure;
        this.algorithm = new FordFulkersonAlgorithm();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFont(new Font(12));
        for (Edge edge : structure.getEdges()) {
            Vertex source = edge.getSource();
            Vertex target = edge.getTarget();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(source.getX(), source.getY(), target.getX(), target.getY());
            gc.setFill(Color.BLACK);
            gc.fillText(edge.getFlow() + "/" + edge.getCapacity(), 
                        (source.getX() + target.getX()) / 2, 
                        (source.getY() + target.getY()) / 2);
        }
    }

    @Override
    public void onClose() {
        // Clean up resources or stop animations if necessary
    }
}
