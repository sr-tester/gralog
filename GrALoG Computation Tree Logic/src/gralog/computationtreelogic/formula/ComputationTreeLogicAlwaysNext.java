/*
 * This file is part of GrALoG FX, Copyright (c) 2016 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later.
 */
package gralog.computationtreelogic.formula;

public class ComputationTreeLogicAlwaysNext extends ComputationTreeLogicForwarder {
    // AX phi = not EX not phi

    public ComputationTreeLogicAlwaysNext(ComputationTreeLogicFormula subformula) {
        super(new ComputationTreeLogicNot(
                new ComputationTreeLogicExistsNext(
                        new ComputationTreeLogicNot(subformula))));
    }
}
