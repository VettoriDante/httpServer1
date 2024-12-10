package http.com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
            String request = null;
            String firstLine = in.readLine();
            String resource = firstLine.split(" ")[1];

            do{
                request = in.readLine();
                System.out.println(request);
            }while(!request.isEmpty());
            System.out.println("Request Ended");
            System.out.println(resource);
            if(existinPath(resource)){
                Ok200(resource);
            }else{
                Error404();
            }

            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Error404() throws Exception{
        String error = "<h1>Not Found error 404</h1>";
        out.writeBytes("HTTP/1.1 404 Not Found\n");
        out.writeBytes("Content-Type: text/html\n");
        out.writeBytes("Content-Length: " + error.length() + "\n");
        out.writeBytes("\n");
        out.writeBytes(error);
    }

    private boolean Ok200(String path){

        File file = new File("htdocs/" + path);
        String exte = file.getName().split("\\.")[1];
        byte[] buf = new byte[8192];
        InputStream input = null;
        if(getContentType(exte) == null)
        try{
            out.writeBytes("HTTP/1.1 200 OK\n");
            out.writeBytes("Content-Type: " + getContentType(exte) + "\n");
            out.writeBytes("Content-Length: " + file.length() + "\n");
            out.writeBytes("\n");
            input = new FileInputStream(file);
            int n;
            while((n = input.read(buf)) != -1){
                out.write(buf, 0, n);
            }
            input.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }

    private boolean existinPath(String path){
        return new File("htdocs/" + path).exists();
    }

    private String getContentType(String exte){
        switch (exte) {
            case "html":
                return "text/html";
            case "txt":
                return "text/plain";
            case "css":
                return "text/css";
            case "png":
                return "image/png";
            case "jpg":
                return "image/jpg";
            case "webp":
                return "image/webp";
            default:
                return null;
        }
    }

}
