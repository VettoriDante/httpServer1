package http.com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException{
        
        //Server Accept
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("Listening");
        do{
            Socket s = ss.accept();
            new ThreadHTTP(s).start();
        }while(true);
    }
}