package wsp_homework5;

import java.awt.event.ActionListener;

public class MVC_Controller implements EventListener {
    private MVC_View view;
    private MVC_Model model;
    
    public MVC_Controller(MVC_View view, MVC_Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void eventViewReceived(MVC_State state) {
        System.out.println("Controller: Received event");
        if (state.toString() == "download") {
            model.modelAccess();
        } else {
            model.modelAccess();
        }
    }
    
    @Override
    public void eventModelReceived(MVC_State state) {
        if (state.toString() == "yes") {
            state = MVC_State.END;
        }
    }
}