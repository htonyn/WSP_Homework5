package wsp_homework5;

import java.awt.event.ActionListener;

public class MVC_Controller {
    private MVC_View view;
    private MVC_Model model;
    
    public MVC_Controller(MVC_View view, MVC_Model model) {
        this.view = view;
        this.model = model;
        
        this.view.addListener(new CustomListener());
    }
}