package main.java.shared.socket;

import main.java.client.view.View;
import main.java.shared.message.ClientMessage;
import main.java.shared.message.Message;
import main.java.shared.message.MessageTypes;
import main.java.shared.observer.Observable;
import main.java.shared.observer.Observer;
import main.java.shared.utils.UnlockInterface;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;








/**
 * the Client class communicates directly with the virtual view through socket
 * It is both observer and observable of the view which can either be a viewCLI or viewGUI
 */



public class Client extends Observable<Message> implements Observer<ClientMessage> {

    private static final Logger LOGGER =  Logger.getLogger(Client.class.getName());


    private final Unlock readLock;

    private final Unlock sendLock;

    private final ObjectInputStream is;

    private final ObjectOutputStream os;

    private Socket s;

    private View view;



    private ClientMessage response;






    /**
     * this class is responsible for the synchronization in both the server and client
     */

    public static class Unlock implements UnlockInterface {

        CountDownLatch latch = new CountDownLatch(1);

        public void reset() {   // Idealmente questo dovrebbe vederlo client ma non socketclientconnection
            latch = new CountDownLatch(1);
        }

        public void await() throws InterruptedException {   // Idealmente questo dovrebbe vederlo client ma non socketclientconnection
            latch.await();
        }

        public long getCount() {return latch.getCount();}

        public void countdown(){
            latch.countDown();
        }

        public void setLatch(int numThreads){
            latch =  new CountDownLatch(numThreads);}

        @Override
        public void unLock() {
            latch.countDown();
        }
    }





    /**
     * Client's constructor
     * @param v view which can be viewCLI or viewGUI
     * @param ip ip address
     * @param port
     * @throws IOException
     */

    public Client(View v, String ip, int port) throws IOException {
        this.readLock = new Unlock();
        this.sendLock = new Unlock();
        this.view = v;

        this.addObserver(v);
        v.addObserver(this);
        v.setClient(this);

        s = new Socket(ip, port);

        this.os = new ObjectOutputStream(s.getOutputStream());
        this.is = new ObjectInputStream(s.getInputStream());



        Thread r = new Thread((Runnable) v);
        r.start();
    }









    /**
     * The sendMessage thread sends the messages from the View to the VirtualView through socket while the
     * readMessage thread receives them the other way round.
     */


    public void start() {



        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        try{
                            sendLock.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(response.getMessageType() == MessageTypes.INFORMATION || response.getMessageType() == MessageTypes.RESPONSE){
                            os.writeObject(response);
                            os.flush();
                            os.reset();
                        }
                        readLock.unLock();
                        sendLock.reset();
                        readLock.reset();
                    } catch (IOException e) {
                        LOGGER.log(Level.INFO, "Message not sent");
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message question;

                        question = (Message) is.readObject();
                        Client.this.notify(question);

                        try {
                            readLock.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }





    /**
     * Being the observer, it gets notified once the player answers
     * @param message
     */


    @Override
    public void update(ClientMessage message) {
        response = message;
        sendLock.unLock();
    }


}



