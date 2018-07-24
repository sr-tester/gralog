package gralog.gralogfx.panels;

import gralog.dialog.*;
import gralog.gralogfx.StructurePane;
import gralog.gralogfx.Tabs;
import gralog.gralogfx.dialogfx.Dialogfx;
import gralog.structure.Edge;
import gralog.structure.Highlights;
import gralog.structure.Structure;
import gralog.structure.Vertex;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.scene.Node;

import javafx.beans.value.ObservableValue;

import java.util.*;
import java.util.function.Consumer;

import static gralog.dialog.DialogAction.NONE;
import static gralog.dialog.DialogState.*;

public class Console extends VBox implements GralogWindow{

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    private TextField input;
    private ScrollPane output;
    private VBox outputElements;

    private Tabs tabs;
    private Dialog dialog;
    private Dialogfx dialogfx;
    private DialogParser parser;
    private boolean outputAdded = false;


    private String currentlyEntered = "";





    private LinkedList<String> history = new LinkedList<>();
    private int historyPointer = -1;

    private final Set<Consumer<String>> subscribers = new HashSet<>();

    public Console(Tabs tabs){

        this.tabs = tabs;

        input = new TextField();
        input.setMaxHeight(20);
        input.setMinHeight(20);
        input.prefWidthProperty().bind(this.widthProperty());
        input.setFont(Font.font("Monospaced", FontWeight.NORMAL, 11));

        dialogfx = new Dialogfx();
        dialog = new Dialog();
        parser = new DialogParser();


        input.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            String inputText = input.getText();
            if(e.getCode() == KeyCode.ENTER){
                this.output("./> " + inputText + "");

                if(!inputText.isEmpty()){
                    history.add(inputText);
                    onEnter(inputText, tabs.getCurrentStructurePane());
                }
                input.clear();
            }else if(e.getCode() == KeyCode.UP){
                
                System.out.println("up up and awayyyyy");
                if (historyPointer == -1){
                    currentlyEntered = inputText;
                }
                historyPointer = historyPointer + 1;
                try{
                    input.setText(history.get(history.size()-1-historyPointer));
                }catch(IndexOutOfBoundsException ex){
                    historyPointer = historyPointer - 1;
                }
            }else if(e.getCode() == KeyCode.DOWN){
                System.out.println("down donw down");
                if (historyPointer < 0){
                    //shimmy shake
                }else if (historyPointer == 0){
                    input.setText(currentlyEntered);
                    historyPointer = historyPointer - 1;
                }else{
                    historyPointer = historyPointer - 1;
                    input.setText(history.get(history.size()-1-historyPointer));
                }
                
            }
        });

        
        output = new ScrollPane();
        outputElements = new VBox();
        output.setContent(outputElements);
        outputElements.setSpacing(5.0);

        output.vvalueProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (outputAdded){
                outputAdded = false;
                output.setVvalue(1.0);
            }
        });

        
        output.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a horizontal ScrollBar
        output.setFitToWidth(true); // set content width to viewport width
        

        // output.setStyle("-fx-background-color: #123455;");





        outputElements.setFocusTraversable(false);
        outputElements.prefHeightProperty().bind(this.heightProperty().subtract(20));
        outputElements.prefWidthProperty().bind(this.widthProperty().subtract(5));
        // outputElements.setMaxHeight(this.heightProperty()-20);

        // output.setFocusTraversable(false);
        // output.prefHeightProperty().bind(this.heightProperty().subtract(20));
        // output.prefWidthProperty().bind(this.widthProperty());
        // output.setMaxHeight(this.heightProperty().subtract(20));
        
        output.prefHeightProperty().bind(this.heightProperty().subtract(20));
        getChildren().addAll(output, input);

        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("borf diddly");

            List<Text> texts = new ArrayList<Text>();

            for (Node n : this.outputElements.getChildren()){
                if (n instanceof Text){
                    texts.add((Text)n);
                }
            }
            for (Text t : texts){
                System.out.println("t: " + t);;
                t.setWrappingWidth((Double)newVal);
            }
        });


        // output.setWrapText(true);
        // output.setFont(Font.font("Monospaced", FontWeight.NORMAL, 11));
        // output.setEditable(false);
        // output.setFocusTraversable(false);
        // output.prefHeightProperty().bind(this.heightProperty().subtract(20));
        // output.prefWidthProperty().bind(this.widthProperty());



    }

    /**
     * Executes Consumer after console input has been submitted
     */
    public void registerMethod(Consumer<String> c){
        subscribers.add(c);
    }

    public void onSpace(String text, StructurePane currentPane){
        System.out.println("space!");
    }

    public void onEnter(String text, StructurePane currentPane){
        for(Consumer<String> consumer : subscribers){
            consumer.accept(text);
        }

        parser.parse(text);
        System.out.println(ANSI_RED + "\nconsole: parsed" + ANSI_RESET);
        System.out.println("text = [" + text + "], currentPane = [" + currentPane + "]");
        ActionType type = parser.getType(); // draw smth: FX, change graph: CORE
        ArrayList<String> parameters = parser.getParameters();

        System.out.println(ANSI_RED + "console: dialogState=" + parser.getDialogState() + ANSI_RESET);

        // the input was "filter [all] selected"
        // if only vertices or only edges are selected, guess this, don't let the user write it
        // if nothing, abort
        // if both, ask what to select
        if (parser.getDialogState() == FILTER_SELECTED){
            boolean existsSelectedVertex = false;
            for (Object v : currentPane.getHighlights().getSelection())
                if (v instanceof Vertex) {
                    existsSelectedVertex = true;
                    break;
                }
            boolean existsSelectedEdge = false;
            for (Object v : currentPane.getHighlights().getSelection())
                if (v instanceof Edge) {
                    existsSelectedEdge = true;
                    break;
                }
            if (existsSelectedVertex & ! existsSelectedEdge){
                parser.setErrorMsg("");
                parser.setDialogState(FILTER_WHAT);
                parser.addParameter("VERTICES");
                if (text.indexOf('d') == text.length() - 1){ // the input was only "filter [all] selected"
                    output("Specify conditions, start with \"where\".");
                    return;
                }
                String remainingText = text.substring(text.indexOf('d')+1); // more text was entered
                System.out.println("calling parse again, dialogState = " + parser.getDialogState() + ", text = [" + remainingText + "], err = " + parser.getErrorMsg() );
                parser.parse(remainingText);
            }
            if (existsSelectedEdge & ! existsSelectedVertex){
                parser.setErrorMsg("");
                parser.setDialogState(FILTER_WHAT);
                parser.addParameter("EDGES");
                if (text.indexOf('d') == text.length() - 1){
                    output("Specify conditions, start with \"where\".");
                    return;
                }
                String remainingText = text.substring(text.indexOf('d')+1); // more text was entered
                System.out.println("calling parse again, dialogState = " + parser.getDialogState() + ", text = [" + remainingText + "]");
                parser.parse(remainingText);
            }
            if (!existsSelectedEdge & !existsSelectedVertex){
                parser.setErrorMsg("Nothing is selected! Aborting.");
                parser.setDialogState(DONE);
                parser.setDialogAction(NONE);
            }
        }




        errorOutput(parser.getErrorMsg());

        parser.setErrorMsg("");

        if (parser.getDialogState() == DONE){
            switch (parser.getDialogAction()) {
                case SELECT_ALL:                 dialogfx.selectAll(currentPane);
                                                break;
                case SELECT_ALL_VERTICES:       dialogfx.selectAllVertices(currentPane);
                                                break;
                case SELECT_ALL_EDGES:          dialogfx.selectAllEdges(currentPane);
                                                break;
                case DESELECT_ALL:               dialogfx.deselectAll(currentPane);
                                                break;
                case DESELECT_ALL_VERTICES:     dialogfx.deselectAllVertices(currentPane);
                                                break;
                case DESELECT_ALL_EDGES:        dialogfx.deselectAllEdges(currentPane);
                                                break;
                case FILTER:
                                                System.out.println("Calling dialog.filter with parameters=" + parameters);
                                                dialog.filter(parser.getParameters(),
                                                                currentPane.getStructure(),
                                                                currentPane.getHighlights());
                                                errorOutput(dialog.getErrorMsg());
                                                parameters.clear();
                                                break;
                case NONE:                      return;
            }
            parser.setDialogAction(NONE);
            parser.setDialogState(DONE);
            parser.setErrorMsg("");
        }

    }

    public void finalizeTextAdd(Text t){
        t.setWrappingWidth(this.getWidth());
        Platform.runLater(
            () ->{

                outputElements.getChildren().add(t); 
                outputAdded = true;
                output.setVvalue(1.0);
            }
        );
    }

    public void finalizeTextFieldAdd(TextField t){
        t.setMaxWidth(output.getWidth());
        t.getStyleClass().add("helloWorld");    
        // t.setBackground(Background.EMPTY);
        // t.setStyle("-fx-control-inner-background: orange;");
        
        

        Platform.runLater(
            () ->{

                outputElements.getChildren().add(t); 
                outputAdded = true;
                output.setVvalue(1.0);
            }
        );
    }

    public void output(String text){
        
        

        TextField t = new TextField();
        t.setText(text);
        
        t.setFont(Font.font ("Verdana", 12));

        // t.setFill(Color.rgb(82, 86, 91));
        


        finalizeTextFieldAdd(t);

    }

    public void errorOutput(String text){
        
        System.out.println("error output");

        TextField t = new TextField();
        t.setText(text);
        t.setStyle("-fx-text-inner-color: red;");
        

        
        // t.setFont(Font.font ("Verdana", 12));
        

        finalizeTextFieldAdd(t);

    }

    public void output(TextField t){
        

        
        t.setFont(Font.font ("Verdana", 12));

        finalizeTextFieldAdd(t);

    }

    public void clear(){
        if(input != null){
            input.clear();
            historyPointer = -1;
        }
    }

    

    @Override
    public void notifyStructureChange(Structure structure) { 

    }

    @Override
    public void notifyHighlightChange(Highlights highlights) { }
}
