package wsp_homework5;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class MVC_View extends GridPane {
    private MVC_State viewState;
    private List listeners = new ArrayList();
    
    private TextField sourceField;
    private TextField targetField;
    private Button downloadButton;
    public MVC_View() {
        viewState = MVC_State.IDLE;
        sourceField = new TextField();
        sourceField.setStyle("-fx-control-inner-background: #000000");
        targetField = new TextField();
        targetField.setEditable(false);
        targetField.setStyle("-fx-control-inner-background: #000000");
        Button sourceButton = new Button("Get URL");
        Button targetButton = new Button("Get Target");
        add(new Text("Source: "), 0, 0);
        add(sourceField, 1, 0);
        add(sourceButton, 2, 0);
//        sourceButton.setMinWidth(Double.MAX_VALUE);
        sourceButton.setOnAction(
            (event) -> {
                // Get URL Button
            }
        );
        add(new Text("Target: "), 0, 1);
        add(targetField, 1, 1);
        add(targetButton, 2, 1);
        targetButton.setOnAction(
            (event) -> {
                FileChooser chooseFile = new FileChooser();
                chooseFile.setTitle("Saving: Pick a Destination File");
                chooseFile.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*")
                );
                
                File file = chooseFile.showSaveDialog(getOwnerWindow());
                if (file != null) {
                    targetField.setText(file.getPath());
                } else {
                    System.out.println("Nothing was selected");
                }
            }
        );
        Region r = new Region();
        add(r, 3, 0);
        setHgrow(r, Priority.ALWAYS);
        downloadButton = new Button("Start Download");
        downloadButton.setOnAction(
            (event) -> {
                if (sourceField.getText().length() == 0) {
                    // Error
                } else {
                    viewState = MVC_State.DOWNLOADING;
                    fireEvent();
                }                
            }
        );
        Region r2 = new Region();
        add(r2, 0, 4);
        add(downloadButton, 3, 5);
    }
    // All events listed in the view should be on the receiving end
    public synchronized void downloadComplete() {
        if (viewState == MVC_State.DOWNLOADING) {
            viewState = MVC_State.END;
            System.out.println("View: Download Completed!");
            fireEvent();
            // Enable Download Button
        }
    }
    
    public synchronized void downloadStarting() {
        if (viewState == MVC_State.IDLE) {
            viewState = MVC_State.DOWNLOADING;
            fireEvent();
        }
    }
    
    private synchronized void fireEvent() {
        MVC_Event event = new MVC_Event(this, viewState);
        Iterator listeners = this.listeners.iterator();
        while (listeners.hasNext()) {
            ((EventListener) listeners.next()).eventViewReceived(viewState);
        }
    }
    
    public synchronized void addListener(EventListener listener) {
        listeners.add(listener);
    }
    public synchronized void removeListener(EventListener listener) {
        listeners.remove(listener);
    }
    
    private Window getOwnerWindow() {
        Scene parentScene = this.getScene();
        if (parentScene != null) {
            return parentScene.getWindow();
        }
        return null;
    }
}
