package chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurChat {

    private  int port ;
    private String ip;

    public ServeurChat(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }


    private void start() {

        try {

            ServerSocket serveur = new ServerSocket(port);
            Socket client = serveur.accept();

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    client.getOutputStream()));

            BufferedReader in = new BufferedReader(new InputStreamReader(client
                    .getInputStream()));
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Client : " + client.getInetAddress().getHostAddress());
            String bufSend, bufReceived;


            String chaine;
            while ((chaine = in.readLine()) != null) {
                out.write(chaine + "\n");
                out.newLine();
                out.flush();
                System.out.print("Client a écrit : " + chaine + "\n");
            }

            while(true){

                bufSend = clavier.readLine();
                if(bufSend.equals("quit")) break;

                else{

                    out.write(bufSend);
                    out.newLine();
                    out.flush();
                    bufReceived = in.readLine();

                    System.out.print("Serveur a écrit : " + bufReceived + "\n");

                }
            }

            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServeurChat serveur = new ServeurChat("127.0.0.1", 9000);
        serveur.start();



    }
}