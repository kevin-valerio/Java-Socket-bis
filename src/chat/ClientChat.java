package chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {


    private String serveur;
    private int port;

    public ClientChat(String serveSmtp, int port) {
        this.serveur = serveSmtp;
        this.port = port;

    }

    public void start() {

        try {

            Socket echoSocket = new   Socket(serveur, port);
            String bufSend, bufReceived;

            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(echoSocket.getOutputStream()));



            while(true){

                bufSend = clavier.readLine();
                if(bufSend.equals("quit")) break;

                else{

                    out.write(bufSend);
                    out.newLine();
                    out.flush();
                    bufReceived = in.readLine();

                    System.out.print("Client a écrit : " + bufReceived + "\n");

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

     }

    public static void main(String[] args) {
        ClientChat client = new ClientChat("127.0.0.1", 9000);
        client.start();


    }
}