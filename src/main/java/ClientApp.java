package main.java;


import main.java.client.view.cli.ViewCLI;
import main.java.client.view.gui.Main;
import main.java.shared.socket.Client;
import main.java.client.view.cli.CliConstants;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp
{

    private static final Scanner scanner = new Scanner(System.in);
    private static Client client;

    private static String userInput(){
        return scanner.nextLine();
    }




    public static void main( String[] args ) throws IOException {

        String ip=null;
        int port=0;


        if(args.length!=2)
            System.exit(-1);
        else {
            ip = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.exit(-1);
            }
            System.out.println("Server address passed: "+ip+":"+port);
        }

        System.out.println( "Welcome to the game:\n" );

        System.out.println("\u001b[38;5;201m"+ "   .-'''-.    ____    ,---.   .--.,---------.    ,-----.    .-------.   .-./`) ,---.   .--..-./`)  \n" +
                "  / _     \\ .'  __ `. |    \\  |  |\\          \\ .'  .-,  '.  |  _ _   \\  \\ .-.')|    \\  |  |\\ .-.') \n" +
                " (`' )/`--'/   '  \\  \\|  ,  \\ |  | `--.  ,---'/ ,-.|  \\ _ \\ | ( ' )  |  / `-' \\|  ,  \\ |  |/ `-' \\ \n" +
                "(_ o _).   |___|  /  ||  |\\_ \\|  |    |   \\  ;  \\  '_ /  | :|(_ o _) /   `-'`\"`|  |\\_ \\|  | `-'`\"` \n" +
                " (_,_). '.    _.-`   ||  _( )_\\  |    :_ _:  |  _`,/ \\ _/  || (_,_).' __ .---. |  _( )_\\  | .---.  \n" +
                ".---.  \\  :.'   _    || (_ o _)  |    (_I_)  : (  '\\_/ \\   ;|  |\\ \\  |  ||   | | (_ o _)  | |   |  \n" +
                "\\    `-'  ||  _( )_  ||  (_,_)\\  |   (_(=)_)  \\ `\"/  \\  ) / |  | \\ `'   /|   | |  (_,_)\\  | |   |  \n" +
                " \\       / \\ (_ o _) /|  |    |  |    (_I_)    '. \\_/``\".'  |  |  \\    / |   | |  |    |  | |   |  \n" +
                "  `-...-'   '.(_,_).' '--'    '--'    '---'      '-----'    ''-'   `'-'  '---' '--'    '--' '---'  " + "\u001b[0m");

        String answer;


        System.out.println(CliConstants.CLI_OR_GUI);
        answer = userInput().toLowerCase();
        while(!answer.equals(CliConstants.CHOSEN_CLI )&& !answer.equals(CliConstants.CHOSEN_GUI)){
            System.out.println(CliConstants.WRONG_CLI_GUI_CHOICE);
            answer = userInput().toLowerCase();
        }
        if (answer.equals(CliConstants.CHOSEN_CLI)) {
            ViewCLI cli = new ViewCLI();
            client = new Client(cli, ip, port);
            client.start();
        }
        else if (answer.equals(CliConstants.CHOSEN_GUI)) {
            Main.main();
        }
    }
}





