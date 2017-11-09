package wsp_homework5;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class MVC_View extends GridPane {
    private TextField sourceField;
    private TextField targetField;
    public MVC_View() {
        sourceField = new TextField();
        sourceField.setEditable(false);
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
        add(new Text("Target: "), 0, 1);
        add(targetField, 1, 1);
        add(targetButton, 2, 1);
        Region r = new Region();
        add(r, 3, 0, 1, 5);
        setHgrow(r, Priority.ALWAYS);
        
    }
}
