/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.npcompleteness.generator;

import gralog.generator.*;
import gralog.npcompleteness.propositionallogic.formula.PropositionalLogicFormula;
import gralog.npcompleteness.propositionallogic.formula.PropositionalLogicNot;
import gralog.npcompleteness.propositionallogic.formula.PropositionalLogicVariable;
import gralog.npcompleteness.propositionallogic.parser.PropositionalLogicParser;
import gralog.rendering.Vector2D;
import gralog.structure.Structure;
import gralog.structure.UndirectedGraph;
import gralog.structure.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author viktor
 */
@GeneratorDescription(
        name = "SAT to Clique Instance",
        text = "Constructs a Clique Instance from a SAT Formula",
        url = "https://en.wikipedia.org/wiki/Clique_problem"
)
public class SatToClique extends Generator {

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
            pos.coordinates = new Vector2D(
                    6d * i,
                    10d - 2 * Math.sin(2 * i * Math.PI / (2 * vars.size() - 1))
            );
            pos.label = var;
            PosNode.put(var, pos);

            Vertex neg = result.createVertex(); // the negative literal
            neg.coordinates = new Vector2D(
                    6d * i + 2,
                    10d - 2 * Math.sin((2 * i + 1) * Math.PI / (2 * vars.size() - 1))
            );
            neg.label = "¬" + var;
            NegNode.put(var, neg);

            // connect them to all other nodes, but NOT each other (cannot set
            // x=true and x=false at the same time)
            // not connecting to each other is done by adding them AFTER the loop!
            for (Vertex v : result.getVertices()) {
                result.addEdge(result.createEdge(pos, v));
                result.addEdge(result.createEdge(neg, v));
            }
            result.addVertex(pos);
            result.addVertex(neg);

            i++;
        }

        // create nodes for clauses
        Set<PropositionalLogicFormula> clauses = new HashSet<>();
        cnf.getClauses(clauses);
        Set<PropositionalLogicFormula> literals = new HashSet<>();

        i = 0;
        for (PropositionalLogicFormula clause : clauses) {
            literals.clear();
            clause.getLiterals(literals);
            int j = 0;
            Set<Vertex> vertsOfClause = new HashSet<>();
            for (PropositionalLogicFormula literal : literals) {
                Vertex clauseVert = result.createVertex();
                clauseVert.coordinates = new Vector2D(
                        8d * i + 2 * Math.cos(j * Math.PI / (literals.size() - 1)),
                        3d - Math.sin(j * Math.PI / (literals.size() - 1))
                );
                clauseVert.label = literal.toString();
                result.addVertex(clauseVert);
                j++;

                // positive literal of clause connected to negative literal in variables
                // because connection means you cannot choose both together,
                // i.e. you cannot choose x=true and say that a clause with ¬x
                // was satisfied by that literal
                Vertex inverseLiteralVert = null;
                if (literal instanceof PropositionalLogicVariable) {
                    PropositionalLogicVariable v = (PropositionalLogicVariable) literal;
                    inverseLiteralVert = NegNode.get(v.variable);
                }
                else if (literal instanceof PropositionalLogicNot
                         && ((PropositionalLogicNot) literal).subformula instanceof PropositionalLogicVariable) {
                    PropositionalLogicNot plnot = (PropositionalLogicNot) literal;
                    PropositionalLogicVariable v = (PropositionalLogicVariable) plnot.subformula;
                    inverseLiteralVert = PosNode.get(v.variable);
                }
                else
                    throw new Exception("Formula is not in Conjunctive Normal Form");

                // connect vert to all verts except those in the same clause
                // and the literal-vert for the inverse assignment of the variable
                vertsOfClause.add(clauseVert);
                for (Vertex w : result.getVertices()) {
                    if (!vertsOfClause.contains(w)
                        && w != inverseLiteralVert)
                        result.addEdge(result.createEdge(clauseVert, w));
                }
            }
            ++i;
        }

        return result;
    }
}
