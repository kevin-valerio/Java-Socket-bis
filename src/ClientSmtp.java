import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientSmtp {

    private
    String serveurSmtp;
    int port;
    String hostname; // l'adresse IP (sous format text) ou le nom de la machine
    // de l'emetteur de l'email qu'on utilise pour la commande ehlo

    public ClientSmtp(String serveSmtp, int port, String hostname) {
        this.serveurSmtp = serveSmtp;
        this.port = port;
        this.hostname = hostname;

    }

    public boolean send(String from, String to, String subject, String body) {

        try {

            Socket client = new Socket(serveurSmtp, port);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    client.getOutputStream()));

            BufferedReader in = new BufferedReader(new InputStreamReader(client
                    .getInputStream()));

            out.write("ehlo " + hostname + " \n");
            out.flush();

            // On analyse les lignes envoyees par le serveur
            // En cas d'erreur, on quitte

            if (!analyserReponseServeur(in))
                return false;

            out.write("mail from:<" + from + ">");
            out.newLine();
            out.flush();
            if (!analyserReponseServeur(in))
                return false;

            out.write("rcpt to:<" + to + ">\n");
            out.flush();
            if (!analyserReponseServeur(in))
                return false;

            out.write("data\n");
            out.flush();
            if (!analyserReponseServeur(in))
                return false;

            out.write("from: " + from + "\n" );
            out.write("to: " + to +"\n");
            out.write("subject: " + subject + "\n");
            out.write(body + "\n");
            out.write(".\n");
            out.flush();

            if (!analyserReponseServeur(in))
                return false;

            out.write("quit \n");
            out.flush();
            if (!analyserReponseServeur(in))
                return false;

            out.close();

            client.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean analyserReponseServeur(BufferedReader in) throws IOException {

        String reponseSeveur;

        // aller jusqu'a  la derniere ligne d'une reponse
        while (true) {
            reponseSeveur = in.readLine();
            if (reponseSeveur.charAt(3) != '-')
                break;
        }

        // analyser le code de la derniere ligne pour voir si c'est un message
        // d'erreur ou pas
        if (reponseSeveur.charAt(0) == '4' || reponseSeveur.charAt(0) == '5') {
            System.out.println(reponseSeveur);
            return false;
        }

        return true;

    }

    public static void main(String[] args) throws UnknownHostException,
            SocketException {

        // Envoi d'un message vers le serveur 192.168.1.90.
        ClientSmtp client = new ClientSmtp("192.168.1.90", 25,
                "127.0.0.1");

        if (client.send("garfield", "jon",
                "Coucou ", "Bonjour Jon \nJe veux des lasagnes !\n merci."))
            System.out.println("Message envoye avec succes !");

    }

}