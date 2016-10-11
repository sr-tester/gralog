/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.gralogfx;

import gralog.structure.*;
import gralog.importfilter.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;

/**
 *
 */
public class DirectInputStage extends Stage {

    Scene scene;
    BorderPane root;
    HBox hBox;
    Button runButton;
    Button cancelButton;
    Button infoButton;

    TextArea inputArea;
    ComboBox formatComboBox;
    List<String> importFilters;

    Structure dialogResult;
    Application app;

    public DirectInputStage(Application app) throws Exception {
        this.dialogResult = null;
        this.app = app;
        this.importFilters = new ArrayList<>();

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> this.close());
        runButton = new Button("Input");
        runButton.setOnAction(e -> runActivated());
        infoButton = new Button("Info");
        infoButton.setOnAction(e -> infoActivated());
        hBox = new HBox();
        hBox.getChildren().addAll(infoButton, cancelButton, runButton);

        inputArea = new TextArea();

        formatComboBox = new ComboBox();
        for (String format : ImportFilterManager.getImportFilterClasses()) {
            ImportFilterDescription descr = ImportFilterManager.getImportFilterDescription(format);
            formatComboBox.getItems().add(descr.name());
            importFilters.add(format);
        }
        formatComboBox.getSelectionModel().select(0);

        root = new BorderPane();
        root.setTop(formatComboBox);
        root.setCenter(inputArea);
        root.setBottom(hBox);
        scene = new Scene(root, 350, 200);

        this.setScene(scene);
        this.setTitle("Direct Input");
        this.initModality(Modality.APPLICATION_MODAL);
    }

    public void runActivated() {
        try {
            int filterSelection = formatComboBox.getSelectionModel().getSelectedIndex();
            String filterName = importFilters.get(filterSelection);
            ImportFilter importfilter = ImportFilterManager.instantiateImportFilter(filterName);

            ByteArrayInputStream input = new ByteArrayInputStream(inputArea.getText().getBytes("UTF-8"));

            dialogResult = importfilter.importGraph(input, null);

            this.close();
        }
        catch (Exception ex) {
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
    }

    public void infoActivated() {
        try {
            int filterSelection = formatComboBox.getSelectionModel().getSelectedIndex();
            String filterName = importFilters.get(filterSelection);
            ImportFilter importfilter = ImportFilterManager.instantiateImportFilter(filterName);

            ImportFilterDescription descr = importfilter.getDescription();
            String url = descr.url();
            app.getHostServices().showDocument(url);
        }
        catch (Exception ex) {
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
    }
}
