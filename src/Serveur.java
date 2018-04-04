import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

    private
    int port ;
    private int nbClients;

    public Serveur(int port, int nbClients) {
        this.port =  port ;
        this.nbClients = nbClients;
    }

    public void lancer() {
        try {
            ServerSocket serveur = new ServerSocket(port);

            for (int i = 1; i<=nbClients; i++) {

                Socket client = serveur.accept();
                ThreadServeur thread = new ThreadServeur(client);
                thread.start();

            }

        } catch (IOException e) {
             e.printStackTrace();
        }
    }
    public static void main(String[] args) {
         new Serveur(2000, 10).lancer();

    }

}