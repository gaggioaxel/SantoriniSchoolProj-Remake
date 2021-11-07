package main.java.shared.message;

import main.java.shared.model.WorkerDetails;

public class WorkerModelMessage extends ModelMessage {

    private WorkerDetails workerDetailsConfirmed;


    public WorkerModelMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }

    public void setWorkerDetailsConfirmed(WorkerDetails workerDetailsConfirmed) { this.workerDetailsConfirmed = workerDetailsConfirmed;}

    public WorkerDetails getWorkerDetailsConfirmed() { return workerDetailsConfirmed;}
}
