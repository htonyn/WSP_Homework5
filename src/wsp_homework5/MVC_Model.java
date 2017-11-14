package wsp_homework5;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EventObject;

public class MVC_Model {
    private MVC_State modelState;
    public MVC_Model() {
        this.modelState = MVC_State.IDLE;
    }
    
    protected void modelAccess() {
        System.out.println("Model: Starting downloading...");
    }

    private void downloadFile(String url, Path target) throws IOException {
        URL downloadTarget = new URL(url);        
        try (InputStream in = downloadTarget.openStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
