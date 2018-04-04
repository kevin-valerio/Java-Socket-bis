
import java.io.*;
import java.net.Socket;

public class ThreadServeur extends Thread {

    Socket client;

    public ThreadServeur(Socket client) {
        this.client = client;
    }

    public void run() {

        try {

            client.setSoTimeout(20000);

            BufferedReader inServeur = new BufferedReader(new InputStreamReader(client.getInputStream())) ;

            OutputStream outServeur = client.getOutputStream();

            String fileName = inServeur.readLine();

            FileInputStream file = new FileInputStream(fileName);

            byte buf[] = new byte[512];
            int nbOctets;

            while ((nbOctets = file.read(buf)) != -1) {
                outServeur.write(buf, 0, nbOctets);

            }

            outServeur.close();
            inServeur.close();
            file.close();
            client.close();

        } catch (IOException e) {
             e.printStackTrace();
        }

    }

}