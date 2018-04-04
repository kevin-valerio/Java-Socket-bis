import java.io.*;
import java.net.Socket;

public class ClientServeurInconnu {

    private
    String serveurSmtp;
    private int port;

    private ClientServeurInconnu(String serveSmtp, int port) {
        this.serveurSmtp = serveSmtp;
        this.port = port;

    }

    private void download() {

        try {

            Socket client = new Socket(serveurSmtp, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            InputStream in = client.getInputStream();


            out.flush();
            out.write("image1.jpeg");
            out.newLine();
            out.flush();

            byte arr[]  = new byte[900];
            FileOutputStream fos = new FileOutputStream("image1.jpeg");

            int nbOctets;
            while ((nbOctets = in.read(arr)) != -1) {
                fos.write(arr, 0, nbOctets);
            }

           out.close();
           client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        ClientServeurInconnu client = new ClientServeurInconnu("10.203.9.145", 2000);
        client.download();
    }
}