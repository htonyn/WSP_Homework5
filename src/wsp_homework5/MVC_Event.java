package wsp_homework5;

import java.util.EventObject;

public class MVC_Event extends EventObject {
    private MVC_State state;
    
    public MVC_Event(Object source, MVC_State state) {
        super(source);
        this.state = state;
    }
    
    public MVC_State state() {
        return this.state;
    }
}
