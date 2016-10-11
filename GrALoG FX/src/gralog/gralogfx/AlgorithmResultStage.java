/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.gralogfx;

import gralog.algorithm.Algorithm;
import gralog.algorithm.AlgorithmParameters;
import gralog.structure.Structure;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 *
 */
public class AlgorithmResultStage extends Stage {

    Scene scene;
    BorderPane root;
    ObjectInspector objectInspector;
    Button closeButton;

    Algorithm algo;
    Structure structure;
    StructurePane structurePane;
    AlgorithmParameters params;
    Object result;

    public AlgorithmResultStage(Algorithm algo, Structure structure,
            AlgorithmParameters params, StructurePane structurePane,
            Object result) throws Exception {
        this.algo = algo;
        this.structure = structure;
        this.params = params;
        this.result = result;
        this.structurePane = structurePane;

        objectInspector = new ObjectInspector();

        try {
            objectInspector.setObject(result, structurePane);
        }
        catch (Exception ex) {
        }
        closeButton = new Button("Close");
        closeButton.setOnAction(e -> this.close());

        root = new BorderPane();

        root.setCenter(objectInspector);
        root.setBottom(closeButton);
        scene = new Scene(root, 640, 480);

        this.setScene(scene);
        this.setTitle("Algorithm Result");
    }
}
