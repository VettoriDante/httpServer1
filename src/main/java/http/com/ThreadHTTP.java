package http.com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadHTTP extends Thread{
    private Socket s;
    private DataOutputStream out;
    private BufferedReader in;

    public ThreadHTTP(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        //prevent and handle every error
        try {
            out = new DataOutputStream(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String tmp = "";
            do{
                tmp = in.readLine();
                System.out.println(tmp);
            }while(!tmp.equals(""));

            String close = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Length: 0\r\n" +
            "\r\n";

            out.writeBytes(close);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

}
