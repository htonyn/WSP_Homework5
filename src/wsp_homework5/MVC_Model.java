package wsp_homework5;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EventObject;

public class MVC_Model extends EventObject {
    
    public MVC_Model(Object source) {
        super(source);
    }
 
    private void downloadFile(String url, Path target) throws IOException {
        URL downloadTarget = new URL(url);        
        try (InputStream in = downloadTarget.openStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
