package gralog.gralogfx.piping;
import gralog.structure.*;
import gralog.rendering.*;


public class MessageCommand extends CommandForGralogToExecute {
	


    // String neighbourString;



	public MessageCommand(String[] externalCommandSegments,Structure structure) {
		this.externalCommandSegments = externalCommandSegments;
        this.structure = structure;

        System.out.println("messagecommand");
	}


	public void handle() {
        return;
	}

}