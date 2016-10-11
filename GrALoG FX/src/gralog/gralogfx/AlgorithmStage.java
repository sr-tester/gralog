/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.gralogfx;

import gralog.structure.*;
import gralog.algorithm.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;

/**
 *
 */
public class AlgorithmStage extends Stage {

    Scene scene;
    BorderPane root;
    ObjectInspector objectInspector;
    HBox hBox;
    Button runButton;
    Button cancelButton;
    Button infoButton;

    Algorithm algo;
    AlgorithmParameters params;
    Structure structure;
    boolean dialogResult;

    public AlgorithmStage(Algorithm algo, Structure structure,
            AlgorithmParameters params, Application app) throws Exception {
        this.algo = algo;
        this.structure = structure;
        this.params = params;
        this.dialogResult = false;

        objectInspector = new ObjectInspector();
        try {
            objectInspector.setObject(params, null);
        }
        catch (Exception ex) {
        }
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> this.close());
        runButton = new Button("Run");
        runButton.setOnAction(e -> {
            this.dialogResult = true;
            this.close();
        });
        hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, runButton);
        infoButton = null;
        AlgorithmDescription descr = algo.getDescription();
        String url = descr.url();
        if (url != null && !url.trim().equals("")) {
            infoButton = new Button("Info");
            infoButton.setOnAction(e -> app.getHostServices().showDocument(url));
            hBox.getChildren().add(infoButton);
        }

        root = new BorderPane();
        root.setCenter(objectInspector);
        root.setBottom(hBox);
        scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Run Algorithm");
        this.initModality(Modality.APPLICATION_MODAL);
    }
}
