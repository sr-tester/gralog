
package gralog.algorithm;

import java.util.Set;

import gralog.progresshandler.ProgressHandler;
import gralog.structure.*;
import gralog.treedecomposition.*;

@AlgorithmDescription(
  name="Fake Directed Tree Decomposition",
  text="Creates a (fake) directed tree decomposition",
  url="http://dx.doi.org/10.1006/jctb.2000.2031"
)
public class FakeDirectedTreeDecomposition extends Algorithm {
    
    @Override
    public AlgorithmParameters GetParameters(Structure s)
    {
        return null; // new DirectedTreeDecompositionParameters();
    }

    protected Bag FakeTreeDecomposition(Structure s, int depth, int width, int children)
    {
        Bag result = new Bag();
        Set<Vertex> V = s.getVertices();
        
        for(int i = 0; i < width; i++)
        {
            int idx = (int)(Math.random() * V.size());
            for(Vertex v : V)
                if(--idx < 0)
                {
                    result.Nodes.add(v);
                    break;
                }
        }
        
        if(depth > 0)
            for(int i = 0; i < children; i++)
                result.ChildBags.add(FakeTreeDecomposition(s, depth-1, width, children));
        
        return result;
    }

    public Object Run(Structure s, AlgorithmParameters ap, ProgressHandler onprogress) throws Exception
    {
        // DirectedTreeDecompositionParameters p = (DirectedTreeDecompositionParameters)ap;
        
        TreeDecomposition dtdecomp = new TreeDecomposition();
        
        dtdecomp.rootBag = FakeTreeDecomposition(s, 5, 6, 2);

        return dtdecomp;
    }
    
}
