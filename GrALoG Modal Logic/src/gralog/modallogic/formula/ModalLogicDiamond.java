package gralog.modallogic.formula;

import gralog.modallogic.KripkeStructure;
import gralog.modallogic.Action;
import gralog.modallogic.World;
import gralog.structure.*;
import java.util.HashSet;

public class ModalLogicDiamond extends ModalLogicFormula {

    String transitiontype;
    ModalLogicFormula subformula;

    public ModalLogicDiamond(ModalLogicFormula subformula) {
        this(null, subformula);
    }

    public ModalLogicDiamond(String transitiontype, ModalLogicFormula subformula) {
        this.transitiontype = transitiontype;
        this.subformula = subformula;
    }

    @Override
    public HashSet<World> interpretation(KripkeStructure structure) {
        HashSet<World> result = new HashSet<>();
        HashSet<World> subresult = subformula.interpretation(structure);

        for (Vertex v : structure.getVertices()) {
            if (!(v instanceof World))
                continue;
            World w = (World) v;

            boolean mustAdd = false;
            for (Edge e : structure.getEdges()) {
                if (e.getSource() != v)
                    continue;
                if (!(e instanceof Action))
                    continue;
                Action a = (Action) e;
                if (this.transitiontype != null)
                    if (!this.transitiontype.equals(a.Name))
                        continue;

                if (subresult.contains(a.getTarget())) {
                    mustAdd = true;
                    break; // no need to search any further
                }
            }

            if (mustAdd)
                result.add(w);
        }
        return result;
    }
}
