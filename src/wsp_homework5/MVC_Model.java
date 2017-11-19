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
import java.util.Vector;
import javafx.concurrent.Task;

public class MVC_Model {
    private MVC_State modelState;
    private Path targetPath;
    private URL downloadTarget;
    private String error;
    private List listeners = new ArrayList();
    Task task = null;
    public MVC_Model() {
        this.modelState = MVC_State.IDLE;
    }
    
    protected void downloadFile(String url, String target) throws IOException {
        targetPath = Paths.get(target);
        downloadTarget = new URL(url);
        modelState = MVC_State.DOWNLOADING;
        fireEvent();
        System.out.println("Model - URL: "+url);
        System.out.println("Model - Target: "+target);        
        
        task = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("Model: Running download in separate thread");
                try {
                    Thread.sleep(2500);
                    // In case of small files, this sleep will emulate large
                    // file downloads to demonstrate the disabling of the
                    // start download button
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                StringWriter sw = new StringWriter();
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
                        modelState = MVC_State.IDLE;
                    }
                }
                System.out.println("End of Thread State: "+modelState.toString());
                return 0;
            }
            
            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Thread: Execution succeeded.");
                MVC_Model.this.fireEvent();
                modelState = MVC_State.IDLE;
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("Thread: Execution failed.");
                modelState = MVC_State.IDLE;
                MVC_Model.this.fireEvent();
            }
        };

        Thread th = new Thread(task);
        th.start();
        
        System.out.println(modelState.toString());
    }
    
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }
    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }    
    private synchronized void fireEvent() {
        Iterator listeners = this.listeners.iterator();
        while (listeners.hasNext()) {
            ((EventListener) listeners.next()).eventModelReceived(modelState);
        }
    }
    protected String returnError() {
        return error;
    }
}

