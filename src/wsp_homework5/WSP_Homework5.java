package wsp_homework5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*
Windowing Systems Programming: Homework 5
* Building a download manager using Model View Controller design pattern
* 
*/
public class WSP_Homework5 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        
        MVC_View view = new MVC_View();
        MVC_Model model = new MVC_Model();
        MVC_Controller controller = new MVC_Controller(view, model);
        view.addListener(controller);
        
        root.add(view, 0, 0);
        Scene scene = new Scene(root, 400, 400);
        
        primaryStage.setTitle("Homework 5 - Hoan Nguyen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
