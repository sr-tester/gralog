/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.npcompleteness.generator;

import gralog.structure.*;
import gralog.generator.*;
import gralog.npcompleteness.propositionallogic.parser.*;
import gralog.npcompleteness.propositionallogic.formula.*;
import gralog.rendering.Vector2D;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

/**
 *
 * @author viktor
 */
@GeneratorDescription(
        name = "SAT to Dominating Set Instance",
        text = "Constructs a Dominating Set Instance from a SAT Formula",
        url = "https://en.wikipedia.org/wiki/Dominating_set"
)
public class SatToDominatingSet extends Generator {

    @Override
    public GeneratorParameters getParameters() {
        return new StringGeneratorParameter("(a \\vee b \\vee c) \\wedge (\\neg a \\vee \\neg b \\vee c) \\wedge (a \\vee \\neg b \\vee \\neg c)");
    }

    @Override
    public Structure generate(GeneratorParameters p) throws Exception {
        StringGeneratorParameter sp = (StringGeneratorParameter) (p);

        PropositionalLogicParser parser = new PropositionalLogicParser();
        PropositionalLogicFormula phi = parser.parseString(sp.parameter);
        PropositionalLogicFormula cnf = phi;
        if (!phi.hasConjunctiveNormalForm())
            cnf = phi.conjunctiveNormalForm();

        UndirectedGraph result = new UndirectedGraph();

        Set<String> vars = new HashSet<>();
        HashMap<String, Vertex> PosNode = new HashMap();
        HashMap<String, Vertex> NegNode = new HashMap();
        cnf.getVariables(vars);

        // create gadgets for the literals
        int i = 0;
        for (String var : vars) {
            Vertex pos = result.createVertex(); // the positive literal
            pos.coordinates = new Vector2D(6d * i, 10d);
            pos.label = var;
            result.addVertex(pos);
            PosNode.put(var, pos);

            Vertex neg = result.createVertex(); // the negative literal
            neg.coordinates = new Vector2D(6d * i + 2, 10d);
            neg.label = "¬" + var;
            result.addVertex(neg);
            NegNode.put(var, neg);

            Vertex dummy1 = result.createVertex(); // 2 dummies
            dummy1.coordinates = new Vector2D(6d * i, 12d);
            dummy1.label = var + "'";
            result.addVertex(dummy1);

            Vertex dummy2 = result.createVertex();
            dummy2.coordinates = new Vector2D(6d * i + 2, 12d);
            dummy2.label = var + "''";
            result.addVertex(dummy2);

            result.addEdge(result.createEdge(pos, neg)); // connections
            result.addEdge(result.createEdge(pos, dummy1));
            result.addEdge(result.createEdge(pos, dummy2));
            result.addEdge(result.createEdge(neg, dummy1));
            result.addEdge(result.createEdge(neg, dummy2));

            i++;
        }

        // create nodes for clauses
        Set<PropositionalLogicFormula> clauses = new HashSet<>();
        cnf.getClauses(clauses);
        Set<PropositionalLogicFormula> literals = new HashSet<>();

        i = 0;
        for (PropositionalLogicFormula clause : clauses) {
            Vertex clauseVert = result.createVertex();
            clauseVert.coordinates = new Vector2D(6d * i, 1d);
            clauseVert.label = clause.toString();
            result.addVertex(clauseVert);

            // connect clause-vertices to the nodes of their member-literals
            literals.clear();
            clause.getLiterals(literals);
            for (PropositionalLogicFormula literal : literals) {
                if (literal instanceof PropositionalLogicVariable) // positive literal
                {
                    PropositionalLogicVariable v = (PropositionalLogicVariable) literal;
                    result.addEdge(result.createEdge(clauseVert, PosNode.get(v.variable)));
                }
                else if (literal instanceof PropositionalLogicNot // negative literal
                         && ((PropositionalLogicNot) literal).subformula instanceof PropositionalLogicVariable) {
                    PropositionalLogicNot plnot = (PropositionalLogicNot) literal;
                    PropositionalLogicVariable v = (PropositionalLogicVariable) plnot.subformula;
                    result.addEdge(result.createEdge(clauseVert, NegNode.get(v.variable)));
                }
                else
                    throw new Exception("Formula is not in Conjunctive Normal Form");
            }
            ++i;
        }

        return result;
    }
}
