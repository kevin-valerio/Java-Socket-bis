import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientPop3 {

    private
    String serveurSmtp;
    private int port;
    private String hostname; // l'adresse IP (sous format text) ou le nom de la machine
    // de l'emetteur de l'email qu'on utilise pour la commande ehlo

    private ClientPop3(String serveSmtp, int port, String hostname) {
        this.serveurSmtp = serveSmtp;
        this.port = port;
        this.hostname = hostname;

    }

    private boolean send(String from, String to, String subject, String body) {

        try {

            Socket client = new Socket(serveurSmtp, port);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    client.getOutputStream()));

            BufferedReader in = new BufferedReader(new InputStreamReader(client
                    .getInputStream()));


            out.write("ehlo " + hostname + " \n");
            out.flush();


            if (analyserReponseServeur(in) == false)
                return false;


            out.close();

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean analyserReponseServeur(BufferedReader in) throws IOException {

        String reponseSeveur;

         while (true) {
            reponseSeveur = in.readLine();
            if (reponseSeveur.charAt(3) != '-')
                break;
        }

        if (reponseSeveur.charAt(0) == '4' || reponseSeveur.charAt(0) == '5') {
            System.out.println(reponseSeveur);
            return false;
        }

        return true;

    }

    public static void main(String[] args) throws UnknownHostException,
            SocketException {

         ClientPop3 client = new ClientPop3("192.168.1.90", 25,
                "127.0.0.1");

        if (client.send("garfield", "jon",
                "Coucou ", "Bonjour Jon \nJe veux des lasagnes !\n merci."))
            System.out.println("Message envoye avec succes !");

    }

}