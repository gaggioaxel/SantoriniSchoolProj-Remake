package it.polimi.ingsw.server.view;


import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.shared.message.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**The virtual view is a class which works as a medium between the client-controller and client-model.
 * It acts on the network without the controller and model getting involved. A virtual view is
 * made for each player. There are 3 different nested classes that either receive or send messages
 * based on the Observer pattern.
 */





public class VirtualView {

    private String username;

    private Controller controller;

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;


    private final ControllerMessageSender controllerMessageSender;
    private final ModelMessageSender modelMessageSender;
    private final PlayerResponse playerResponse;







    /**VirtualView's constructor*/


    public VirtualView(Controller c, Socket s) throws IOException {
        this.controller = c;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.in = new ObjectInputStream(s.getInputStream());
        this.socket = s;


        this.controllerMessageSender = new ControllerMessageSender();
        this.modelMessageSender = new ModelMessageSender();
        this.playerResponse = new PlayerResponse();

        this.playerResponse.addObserver(controller);


        Thread t = new Thread(playerResponse);
        t.start();
    }







    /**The ControllerMessageSender is a class that forwards the messages that are asked by the different controllers
     *(Controller, SetupController, TurnManaging Controller) to the player. Infact it is observer of them.
     *
     */


    public class ControllerMessageSender implements Observer<ControllerMessage> {
        @Override
        public void update(ControllerMessage message) {
            try {

                out.writeObject(message);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }






    /**The ModelMessageSender is a class that forwards the model's updates to the player. It is observer of the model.
     *
     */


    public class ModelMessageSender implements Observer<ModelMessage> {

        @Override
        public void update(ModelMessage message) {
            try {

                out.writeObject(message);
                out.flush();
                out.reset();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }









    /**This class is responsible for receiving the player's messages through the socket.
     * It then forwards them to the observers: controller, setupController, turnManagingController,
     * Model.
     */


    public class PlayerResponse extends Observable<ClientMessage> implements Runnable {
        @Override
        public void run() {

            try {

                while(true) {

                    ClientMessage msg;

                    msg = (ClientMessage) in.readObject();

                    notify(msg);
                }

            } catch (IOException | ClassNotFoundException e) {

                e.printStackTrace();
            }



            /**if a player is disconnected*/

            finally{
                controller.getModel().endGame();
            }


        }
    }




    /**setter*/

    public void setUsername(String username) {
        this.username = username;
    }




    /**getters*/


    public ControllerMessageSender getControllerMessageSender() {
        return controllerMessageSender;
    }

    public ModelMessageSender getModelMessageSender() {
        return modelMessageSender;
    }


    public PlayerResponse getPlayerResponse() {
        return playerResponse;
    }



    public String getUsername() {
        return username;
    }
}

