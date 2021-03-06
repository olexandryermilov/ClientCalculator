package Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public Logger(){
        try {
            File file = new File("log");
            if(!file.exists())file.mkdir();
            logOutputFile = new File(logOutput + getDateAsString() + ".txt");
            if (!logOutputFile.exists()) logOutputFile.createNewFile();
            logOutputStream = new FileOutputStream(logOutputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static String getDateAsString(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return now.format(formatter);
    }
    private String getDateAsLog(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return now.format(formatter);
    }
    public void logMessage(String message){
        String time = getDateAsLog();
        message=time + " " + message;
        for(int i=0;i<message.length();i++){
            try {
                logOutputStream.write((byte)message.charAt(i));
                if(i==message.length()-1)logOutputStream.write((byte)'\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(message);
    }
    private static final String logOutput = "log\\log";
    private static File logOutputFile;
    private static FileOutputStream logOutputStream;
}
