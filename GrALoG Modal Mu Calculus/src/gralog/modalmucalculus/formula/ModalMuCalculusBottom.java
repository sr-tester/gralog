package gralog.modalmucalculus.formula;

import gralog.modallogic.KripkeStructure;
import gralog.modallogic.World;
import gralog.modalmucalculus.structure.ParityGame;
import gralog.modalmucalculus.structure.ParityGamePosition;
import gralog.rendering.Vector2D;
import gralog.structure.*;
import java.util.HashMap;
import java.util.Map;

public class ModalMuCalculusBottom extends ModalMuCalculusFormula {

    public ModalMuCalculusBottom() {
    }

    @Override
    protected ModalMuCalculusFormula negationNormalForm(boolean negated) {
        if (negated)
            return new ModalMuCalculusTop();
        else
            return new ModalMuCalculusBottom();
    }

    @Override
    protected ModalMuCalculusFormula negateVariable(String variable) {
        return new ModalMuCalculusBottom();
    }

    @Override
    public void createParityGamePositions(double scale, double x, double y,
            double w, double h, KripkeStructure s, ParityGame p,
            int NextPriority,
            Map<World, Map<ModalMuCalculusFormula, ParityGamePosition>> index) {
        for (Vertex v : s.getVertices()) {
            ParityGamePosition node = p.createVertex();
            node.coordinates = new Vector2D(
                    scale * w * v.coordinates.getX() + x,
                    scale * h * v.coordinates.getY() + y
            );
            node.label = "⊥";
            node.player1Position = true; // verifier loses
            p.addVertex(node);

            if (!index.containsKey((World) v))
                index.put((World) v, new HashMap<>());
            index.get((World) v).put(this, node);
        }
    }

    @Override
    public void createParityGameTransitions(KripkeStructure s, ParityGame p,
            Map<World, Map<ModalMuCalculusFormula, ParityGamePosition>> index,
            Map<String, ModalMuCalculusFormula> variableDefinitionPoints) {
    }
}