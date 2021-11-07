package main.java.server;

import main.java.server.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server  {

    private static final int PORT = 5555;

    private final Controller controller;

    private final ServerSocket serverSocket;

    private int numPlayers = 0;

    private boolean isActive = true;




    /**Server's constructor*/

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        this.controller = new Controller(this);
    }





    /**Server's run that accepts connections*/

    public void run() {

        int i = 1;

        while (isActive) {
            try {

                System.out.println("Server listening on port: " + PORT);

                Socket connection = serverSocket.accept();
                System.out.println("Connected one client");
                if (numPlayers == 0) {
                    numPlayers = controller.addFirstPlayer(connection);

                }
                else if (i <= numPlayers){
                    controller.addPlayer(connection);
                    i++;

                    if(i == numPlayers){

                        controller.main();
                        i++;
                    }
                }


            } catch (Exception e) {
                System.out.println("Cannot accept connections");
            }
        }



    }


}