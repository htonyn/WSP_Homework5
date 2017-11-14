package wsp_homework5;

public class MVC_State {
    public static final MVC_State IDLE = new MVC_State("idle");
    public static final MVC_State DOWNLOADING = new MVC_State("download");
    public static final MVC_State ERROR = new MVC_State("error");
    
    private String mvcState;
    
    public String toString() {
        return this.mvcState;
    }
    
    private MVC_State(String event) {
        this.mvcState = event;
    }
}
