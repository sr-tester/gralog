package gralog.gralogfx.windows;

import gralog.preferences.Preferences;
import gralog.rendering.GralogColor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Spawns a stage that contains all Gralog-relevant preferences
 *
 * Loads and stores all preferences (editor config, structure default vars, etc)
 * back in the user configuration file from gralog.preferences.
 *
 * All changes are being stored offline in the persistent configuration, NOT in
 * a runtime object.
 *
 */
public class ChooseFileForPipingWindow extends Stage {

    private static final double WINDOW_WIDTH = 700;
    private static final double WINDOW_HEIGHT = 420;
    FileChooser pipingSourceFileChooser;
    File pipingFile = null;
    File newPipingFile = null;
    public String fileName = null;
    public TextField fileUrlTextField;


    public ChooseFileForPipingWindow() {
    

        this.pipingSourceFileChooser = new FileChooser();

        Parent root = new Pane();
        Pane generalPage = new Pane();
        Pane structurePage = new Pane();

        try {
            URL fileChooserWindow = getClass().getClassLoader().getResource("fxml/pipingFileChooserLayout.fxml");
      
            if(fileChooserWindow == null) {
                System.err.println("The preference-fxml URL is null. Does the file exist?");
            }else {
                System.out.println("naught null");
                root = FXMLLoader.load(fileChooserWindow);

            }
        }catch(IOException e) {
            e.printStackTrace();
        }
       
        Button cancelButton = findButton(root, "cancel");
        cancelButton.setOnAction(e -> {
            hide();
        });
        
        Scene s = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        Button submitButton = findButton(root, "submitButton");
        submitButton.setOnAction(e -> {
            setTitle("n00beus");
            this.fileName = this.fileUrlTextField.getText();
            hide();
        });

        Button chooseFileButton = findButton(root,"chooseFileButton");

        this.fileUrlTextField = findTextField(root,"fileUrlTextField");

        
        chooseFileButton.setOnAction(e -> {
            File newPipingFile = this.pipingSourceFileChooser.showOpenDialog(this);
            fileUrlTextField.setText(newPipingFile.getPath());
        });




        setTitle("Preferences");
        setWidth(WINDOW_WIDTH);
        setHeight(WINDOW_HEIGHT);
        setScene(s);

        show();
        centerOnScreen();
    }

    private Button findButton(Parent root, String name) {
        System.out.println("gettin the childrens" + getChildrenRecursively(root).size());
        for(Parent p : getChildrenRecursively(root)) {
            System.out.println("Parent: " + p);
            if(p instanceof Button && p.getId().equals(name)) {
                return (Button)p;
            }
        }
        return null;
    }

    private TextField findTextField(Parent root, String name) {

        for(Parent p : getChildrenRecursively(root)) {
            if(p instanceof TextField && p.getId().equals(name)) {
                return (TextField)p;
            }
        }
        return null;
    }

    /**
     * Loads all values from the configuration file into the
     * value fields of the given node.
     *
     * Example: TextField with ID k=StructurePane-hasGrid. If the
     * preference file has a key k then the value from k gets loaded
     * into the TextField.
     */
    private void loadPreferences(Parent root) {
        for(Node node : getChildrenRecursively(root)) {
            String id = node.getId();
            if(id != null && !id.isEmpty()) {
                if(node instanceof CheckBox) {
                    Boolean b = Preferences.getBoolean(node.getId(), false);
                    ((CheckBox)node).setSelected(b);
                }
                if(node instanceof TextField) {
                    Double d = Preferences.getDouble(node.getId(), 0);
                    ((TextField)node).setText(d.toString());
                }
                if(node instanceof ColorPicker) {
                    GralogColor c = Preferences.getColor(node.getId(), GralogColor.BLACK);
                    ((ColorPicker)node).setValue(Color.rgb(c.r, c.g, c.b));
                }
                if(node instanceof Button) {
                    System.out.println("lehishtamesh");
                    Button button = (Button)node;
                    this.pipingFile = Preferences.getFile(node.getId(),null);
                    String simpleName = this.pipingFile.getName();
                    button.setText(simpleName);
                    System.out.println("we got from tha config: " + this.pipingFile.getName());
                    button.setOnAction(e -> {
                        this.newPipingFile = this.pipingSourceFileChooser.showOpenDialog(this);
                        button.setText(newPipingFile.getName());
                    });
                }

            }
        }
    }

    /**
     * Saves all preferences corresponding to the IDs to the children
     * of the given root nodes
     * @param root
     */
    private void savePreferences(Parent... root) {
        for(int i = 0; i < root.length; i++) {
            for(Node node : getChildrenRecursively(root[i])) {
                String id = node.getId();
                if(id != null && !id.isEmpty()) {
                    if(node instanceof CheckBox) {
                        Boolean b = ((CheckBox)node).isSelected();
                        Preferences.setBoolean(node.getId(), b);
                    }
                    if(node instanceof TextField) {
                        Double d = Double.parseDouble(((TextField)node).getText());
                        Preferences.setDouble(node.getId(),  d);
                    }
                    if(node instanceof ColorPicker) {
                        GralogColor c = GralogColor.parseColorAlpha(((ColorPicker)node).getValue().toString());
                        Preferences.setColor(node.getId(), c);
                    }
                    if(node instanceof Button) {
                        if (this.newPipingFile != null) {
                            this.pipingFile = this.newPipingFile;
                        }
                        Preferences.setFile(node.getId(),this.pipingFile);
                        
                    }
                }
            }
        }

        hide();
    }

    /**
     * Gets all nodes below the given one
     */
    private LinkedList<Parent> getChildrenRecursively(Parent root) {
        LinkedList<Parent> result = new LinkedList<>();
        for(Node x : root.getChildrenUnmodifiable()) {
            if(x instanceof Parent) {
                result.add((Parent)x);
                result.addAll(getChildrenRecursively((Parent)x));
            }
        }
        if (root instanceof TitledPane) {
            TitledPane aP = (TitledPane)root;
            Parent p = (Parent)aP.getContent();
         
            result.add((Parent)root);
            result.addAll(getChildrenRecursively(p));
    
        }
        
        return result;
    }
    /**
     * Sets up the toggle buttons of the preference window to be
     * combined into a ToggleGroup
     */
    private void setupToggleGroups(Scene mainScene, Node generalPage, Node structurePage) {

        ToggleGroup tgroup = new ToggleGroup();

        ToggleButton structureButton = (ToggleButton) mainScene.lookup("#structureButton");
        ToggleButton generalButton = (ToggleButton) mainScene.lookup("#generalButton");

        tgroup.getToggles().addAll(structureButton, generalButton);

        Pane container = (Pane) mainScene.lookup("#container");

        tgroup.selectedToggleProperty().addListener((e, oldVal, newVal) -> {
            if(newVal == null) {
                oldVal.setSelected(true);
            }else {
                container.getChildren().clear();
                if(newVal == generalButton) {
                    container.getChildren().add(generalPage);
                }else if(newVal == structureButton) {
                    container.getChildren().addAll(structurePage);
                }
            }
        });

        container.getChildren().add(structurePage); // default
        structureButton.setSelected(true);
    }

}
