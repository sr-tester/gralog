package gralog.gralogfx.piping;
import gralog.structure.*;
import gralog.rendering.*;
import java.util.Set;


public class GetNeighboursCommand extends CommandForGralogToExecute {
	

	int sourceId;
	Vertex sourceVertex;
    // String neighbourString;



	public GetNeighboursCommand(String[] externalCommandSegments,Structure structure){
		this.externalCommandSegments = externalCommandSegments;
        this.structure = structure;

        try{    
            this.sourceId = Integer.parseInt(externalCommandSegments[2]);
        }catch(NumberFormatException e){
            this.error = e;
            this.fail();
            return;
        }

        this.sourceVertex = this.structure.getVertexById(this.sourceId);

        if (this.sourceVertex == null){
            this.fail();
            this.error = new Exception("error: source vertex with id " + Integer.toString(this.sourceId) + " does not exist");
            return;
        }
	}


	

	public void handle(){

        // int changeId;
       
        
        

        Set<Vertex> neighbours = this.sourceVertex.getNeighbours();

        String neighbourString = "";
        for (Vertex v : neighbours){
            neighbourString = neighbourString + Integer.toString(v.getId()) + "#";
        }
        if (neighbourString.length() > 0 && null != neighbourString){
            neighbourString = neighbourString.substring(0,neighbourString.length()-1);
        }


        this.setResponse(neighbourString);

        return;


        // return v;
	}

}