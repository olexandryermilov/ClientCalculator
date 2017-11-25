package com.company;

import Logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    private final static int DEFAULT_PORT_NUMBER = 1034;
    private final static String DEFAULT_HOST_NAME="127.0.0.1";
    private final static Logger logger = new Logger();
    private static int portNumber=DEFAULT_PORT_NUMBER;
    private static String hostName=DEFAULT_HOST_NAME;
    private static boolean isCorrectIp(String ip){
        String[] blocks = ip.split("\\.");
        System.out.println(blocks);
        if(blocks.length!=4)return false;
        int[] blocksAsInts = new int[blocks.length];
        for(int i=0;i<blocks.length;i++){
            try{
                blocksAsInts[i] = Integer.parseInt(blocks[i]);
                if(blocksAsInts[i]>255||blocksAsInts[i]<0)return false;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }
    private static boolean isCorrectPort(String port){
        try{
            int portAsInt = Integer.parseInt(port);
            if(portAsInt<1024||portAsInt>49151){
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    private static boolean isCorrectIpPort(String line){
        int dotIndex = line.indexOf((int)':');
        if(dotIndex==-1)return false;
        String ip = line.substring(0,dotIndex);
        String port = line.substring(dotIndex+1,line.length());
        System.out.println(ip);
        System.out.println(port);
        if(isCorrectIp(ip)&&isCorrectPort(port)){
            hostName=ip;
            portNumber=Integer.parseInt(port);
            return true;
        }
        return false;
    }
    private static void getPort(){
        Scanner in = new Scanner(System.in);
        System.out.println("If you want to choose your port number and host name, please enter it in ip:port format, otherwise just enter -1");
        String input = in.nextLine();
        if(input.equals("-1"))return;
        while(!isCorrectIpPort(input)) {
            System.out.println("Wrong host name, should be ip:port");
            input=in.nextLine();
        }
        return;
    }
    public static void main(String[] args) {
        getPort();
        try{
            Socket echoSocket = new Socket(hostName,portNumber);
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
            logger.logMessage("Wrong host "+hostName);
            System.out.println("Wrong host "+hostName);
            System.exit(1);
        } catch (IOException e) {
            logger.logMessage("Couldn't get I/O for connection "+hostName);
            System.out.println("Couldn't get I/O for connection "+hostName);
            System.exit(1);
        }
    }
}
