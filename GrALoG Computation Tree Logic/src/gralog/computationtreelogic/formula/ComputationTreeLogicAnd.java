package gralog.computationtreelogic.formula;

import gralog.modallogic.KripkeStructure;
import gralog.modallogic.World;
import java.util.HashSet;

public class ComputationTreeLogicAnd extends ComputationTreeLogicFormula {

    ComputationTreeLogicFormula left;
    ComputationTreeLogicFormula right;

    public ComputationTreeLogicAnd(ComputationTreeLogicFormula left,
            ComputationTreeLogicFormula right) {
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
