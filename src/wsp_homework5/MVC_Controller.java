package wsp_homework5;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MVC_Controller implements EventListener {
    private MVC_View view;
    private MVC_Model model;
    
    public MVC_Controller(MVC_View view, MVC_Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void eventViewReceived(MVC_State state) {
        System.out.println("Controller: Received event from view");
        switch (state.toString()) {
            case "idle":
                break;
            case "download":
                try {
                    model.downloadFile(view.getURL(), view.getTarget());
                } catch (IOException ex) {
                    Logger.getLogger(MVC_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "error":
                System.out.println("Controller: View is in error mode, no action taken.");
                break;
            default:
                System.out.println("Controller: Model State Unidentified");
                break;
        }
    }
    
    @Override
    public void eventModelReceived(MVC_State state) {
        System.out.println("Controller - Received event from model");
        switch (state.toString()) {
            case "download":
                System.out.println("Controller sees model downloading here");
                view.downloadStarting();
                break;
            case "error":
                view.generateError(model.returnError());
                break;
            case "idle":
                System.out.println("Controller sees model idling here");
                view.downloadComplete();
                break;
            default:
                System.out.println("Controller: Model State Unidentified");
                break;
        }
    }
    
}