package com.company;

import Logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    private final static int PORT_NUMBER = 1034;
    private final static String HOST_NAME="127.0.0.1";
    private final static Logger logger = new Logger();
    public static void main(String[] args) {
        try{
            Socket echoSocket = new Socket(HOST_NAME,PORT_NUMBER);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String userInput,serverAns=in.readLine();

            logger.logMessage(serverAns);
            while( (userInput=stdin.readLine())!=null){
                out.println(userInput);
                logger.logMessage("Sent message: "+userInput);
                if(userInput.equalsIgnoreCase("exit")){
                    logger.logMessage("Received message: ");
                    for(int i=0;i<3;i++){
                        serverAns=in.readLine();
                        System.out.println(serverAns);
                        logger.logMessage(serverAns);
                    }
                    echoSocket.close();
                    break;
                }
                serverAns=in.readLine();
                System.out.println(serverAns);
                logger.logMessage("Received message: "+serverAns);
               //}
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.logMessage("Wrong host "+HOST_NAME);
            System.out.println("Wrong host "+HOST_NAME);
            System.exit(1);
        } catch (IOException e) {
            logger.logMessage("Couldn't get I/O for connection "+HOST_NAME);
            System.out.println("Couldn't get I/O for connection "+HOST_NAME);
            System.exit(1);
        }
    }
}
