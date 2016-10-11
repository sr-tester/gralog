package gralog.modallogic.formula;

import gralog.modallogic.KripkeStructure;
import gralog.modallogic.World;
import java.util.HashSet;

public class ModalLogicAnd extends ModalLogicFormula {

    ModalLogicFormula left;
    ModalLogicFormula right;

    public ModalLogicAnd(ModalLogicFormula left, ModalLogicFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public HashSet<World> interpretation(KripkeStructure structure) {
        HashSet<World> lresult = left.interpretation(structure);
        HashSet<World> rresult = right.interpretation(structure);

        // intersection
        HashSet<World> result = new HashSet<>();
        for (World w : lresult)
            if (rresult.contains(w))
                result.add(w);

        return result;
    }
}
