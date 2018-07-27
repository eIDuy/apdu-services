
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Compiler.command;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ffaggiani
 */
public class LogUtils {

    private static LogUtils instance = null;
    private String logFile;
    private String logOption = "B"; // B log both Commands and Responses, C log Commands only, R log Responses only

    public static LogUtils getInstance() {
        if (instance == null) {
            instance = new LogUtils();
        }
        return instance;
    }

    private LogUtils() {

    }

    public void configure(String path, String option) throws FileNotFoundException, UnsupportedEncodingException {

        switch (option) {
            case "C":
                this.logOption = "C";

                break;
            case "R":
                this.logOption = "R";

                break;
            default:
                this.logOption = "B";
                break;
        }

        //Create file name with file option in the name
        this.logFile = path;
        //Clear previous log file
        new PrintWriter(this.logFile + "apdu_service_" + this.logOption);
    }

    public void logCommand(String command, String type) throws FileNotFoundException, UnsupportedEncodingException {
        //Log command according to log option
        if (this.logOption.equals("B") || type.equals(this.logOption)) {
                //Regex to replace each two character in the string with same two characted plus space
                command = command.replaceAll("..(?=.)", "$0 ");
                
            //Open file in append mode    
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File(this.logFile + "apdu_service_" + this.logOption),true));
            pw.println(command);
            pw.close();
            
        }
    }
    
    public void logCommandName(String command) throws FileNotFoundException, UnsupportedEncodingException {
        //Log command according to log option
        if (this.logOption.equals("B")) {
               
            //Open file in append mode    
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File(this.logFile + "apdu_service_" + this.logOption),true));
            pw.println(command);
            pw.close();
            
        }
    }

}
