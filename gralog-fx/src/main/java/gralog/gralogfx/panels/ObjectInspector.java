/* This file is part of Gralog, Copyright (c) 2016-2017 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later. */
package gralog.gralogfx.panels;

import gralog.gralogfx.ExceptionBox;
import gralog.gralogfx.StructurePane;
import gralog.gralogfx.Tabs;
import gralog.gralogfx.views.View;
import gralog.gralogfx.views.ViewManager;

import java.util.Collection;
import java.util.function.Consumer;

import gralog.structure.Highlights;
import gralog.structure.Structure;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 *
 */
public class ObjectInspector extends AnchorPane implements GralogWindow{

    private View view;
    private Tabs tabView;

    public ObjectInspector(){

    }

    public ObjectInspector (Tabs tabView){
        this.tabView = tabView;
        this.tabView.subscribe(this);
    }

    public void setObject(Collection<?> list) throws Exception {
        setObject(list, (b) -> {
        });
    }

    public void setObject(Collection<?> list, Consumer<Boolean> submitPossible)
            throws Exception {

        this.getChildren().clear();

        ScrollPane sp = new ScrollPane();
        sp.setStyle("-fx-background-color:transparent;");

        if (list == null || list.isEmpty()) {
            this.getChildren().add(sp);
            return;
        }

        Object obj = list.iterator().next();

        view = ViewManager.instantiateView(obj.getClass());

        if (view == null)
            return;
        if (!(view instanceof Node))
            throw new Exception("Class " + view.getClass().getName() + " is not derived from javafx.scene.Node");

        view.setObject(obj, submitPossible);

        Node viewNode = (Node) view;
        sp.setContent(viewNode);

        AnchorPane.setTopAnchor(sp, 4.0);
        AnchorPane.setRightAnchor(sp, 4.0);
        AnchorPane.setBottomAnchor(sp, 4.0);
        AnchorPane.setLeftAnchor(sp, 4.0);

        this.getChildren().add(sp);

    }

    @Deprecated
    public void setObject(Object obj, StructurePane pane){
        //has been replaced with setObject(Collection<?> list)
    }
    @Deprecated
    public void setObject(Object obj, StructurePane pane, Consumer<Boolean> submitPossible){
        //has been replaced with setObject(Collection<?> list)
    }
    public Node getNode(){
        return null;
    }
    /**
     * This event handler is called when the stage is about to be closed.
     */
    public final void onClose() {
        if (view != null)
            view.onClose();
    }

    @Override
    public void notifyStructureChange(Structure structure) {
        //not relevant
    }

    @Override
    public void notifyHighlightChange(Highlights highlights) {
        try{
            setObject(highlights.getSelection());
        }catch(Exception e){
            ExceptionBox xBox = new ExceptionBox();
            xBox.showAndWait(e);
        }
    }
}