import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import finegrainedlist.*;

public class ThreadAdd extends Thread {

    public FineGrainList list;

    public ThreadAdd(FineGrainList list) {
        
        this.list = list;
    }
    

    private static final Logger logger = Logger.getLogger(ThreadAdd.class.getName());
    FileHandler fileHandler;

    public void run(){
            try {
                fileHandler = new FileHandler("src/application.log");
                
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
            fileHandler.setFormatter(new SimpleFormatter());

            logger.info("ANDA PLEASE ")


            list.add(1);
            logger.log(Level.ALL, "Added 1");
            list.add(2);
            logger.log(Level.ALL, "Added 2");
            try {
                ThreadAddOL.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(3);
            list.add(4);
            list.add(5);
            list.add(6);
            list.add(7);

        // Close the FileHandler
        fileHandler.close();
    
}

}
