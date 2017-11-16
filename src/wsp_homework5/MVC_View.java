package wsp_homework5;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
        Button targetButton = new Button("Select File");
        add(new Text("Source: "), 0, 0);
        add(sourceField, 1, 0);
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
                    System.out.println("View: Enter a URL");
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
    protected void generateError(String error) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.getDialogPane().setMaxHeight(USE_PREF_SIZE);
        alert.setTitle("Error!");
        alert.setHeaderText("An error has occurred.");
        ScrollPane scroll = new ScrollPane();
        scroll.setMaxHeight(600);
        scroll.setContent(new Text(error));
        alert.getDialogPane().setContent(scroll);
        alert.showAndWait();
        viewState = MVC_State.IDLE;
        downloadButton.setDisable(false);
    }
    
    // All events listed in the view should be on the receiving end
    protected synchronized void downloadComplete() {
        viewState = MVC_State.IDLE;
        System.out.println("View: Download Completed!");
        downloadButton.setDisable(false);
    }
    
    public void downloadStarting() {
        viewState = MVC_State.DOWNLOADING;
        downloadButton.setDisable(true);
        System.out.println("View: Download starting...");
    }
    
    private synchronized void fireEvent() {
        MVC_Event event = new MVC_Event(this, viewState);
        Iterator listeners = this.listeners.iterator();
        while (listeners.hasNext()) {
            ((EventListener) listeners.next()).eventViewReceived(viewState);
        }
    }
    
    protected String getURL() {
        return sourceField.getText();
    }
    
    protected String getTarget() {
        return targetField.getText();
    }
    
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }
    public void removeListener(EventListener listener) {
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
