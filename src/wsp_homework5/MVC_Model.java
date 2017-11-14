package wsp_homework5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MVC_Model {
    private MVC_State modelState;
    private Path targetPath;
    private URL downloadTarget;
    private String error;
    private List listeners = new ArrayList();
    public MVC_Model() {
        this.modelState = MVC_State.IDLE;
    }
    
    protected void downloadFile(String url, String target) throws IOException {
        targetPath = Paths.get(target);
        downloadTarget = new URL(url);
        modelState = MVC_State.DOWNLOADING;
        fireEvent();
        Thread thread = new Thread() {
            public void run() {
                System.out.println("Model: Running download in separate thread");
                StringWriter sw = new StringWriter();
                try {
                    Thread.sleep(2500);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                try (InputStream in = downloadTarget.openStream()) {
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (FileNotFoundException e) {
                    modelState = MVC_State.ERROR;
                    e.printStackTrace(new PrintWriter(sw));
                    error = sw.toString();
                } catch (MalformedURLException e) {
                    modelState = MVC_State.ERROR;
                    e.printStackTrace(new PrintWriter(sw));
                    error = sw.toString();
                } catch (IOException e) {
                    modelState = MVC_State.ERROR;
                    e.printStackTrace(new PrintWriter(sw));
                    error = sw.toString();
                } finally {
                    if (modelState != MVC_State.ERROR) {
                        System.out.println("Model: Finished downloading...");
                        modelState = MVC_State.IDLE;
                    }
                }
            }
        };
        thread.start();
        System.out.println(modelState.toString());
        fireEvent();
        
        
    }
    
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }
    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }
    
    private synchronized void fireEvent() {
        MVC_Event event = new MVC_Event(this, modelState);
        Iterator listeners = this.listeners.iterator();
        while (listeners.hasNext()) {
            ((EventListener) listeners.next()).eventModelReceived(modelState);
        }
    }
    protected String returnError() {
        return error;
    }
}

