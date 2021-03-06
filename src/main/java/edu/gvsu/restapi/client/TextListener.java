package edu.gvsu.restapi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TextListener implements Runnable{
    ServerSocket echoServer = null;
    Socket clientSocket = null;
    String clientUsername;

    public TextListener(ServerSocket serverSocket, String clientUsername){
        this.echoServer = serverSocket;
        this.clientUsername = clientUsername;
    }

    @Override
    public void run() {
        while (true){
            String line;
            BufferedReader is;
            try {
                // Put client to listen for messages
                clientSocket = echoServer.accept();
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while(true) {
                    line = is.readLine();
                    if(line == null) {
                        break;
                    }
                    System.out.println();
                    System.out.println((char)27 + "[38;5;202m"+ line + (char)27 + "[0m");
                    System.out.print((char)27 + "[38;5;118m"+ this.clientUsername + " ➜ " + (char)27 + "[0m");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
