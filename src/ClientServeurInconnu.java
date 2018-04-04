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

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    client.getOutputStream()));

            FileWriter fw = null;
            BufferedWriter bw = null;


            BufferedReader in = new BufferedReader(new InputStreamReader(client
                    .getInputStream()));

            out.flush();
            out.write("image1.jpeg\n");
            out.flush();

            for(int i = 0; i < in.read(); ++i){
               file.append(in.readLine());
             }

            System.out.print(file.toString());

            fw = new FileWriter("a.jpeg");
            bw = new BufferedWriter(fw);
            bw.write(file.toString());

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