package netty.case1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Anthony.liu
 * @Description
 * @Date 2020/12/25
 */

public class BIOServer {
    private ExecutorService es = Executors.newFixedThreadPool(5);

    public void start(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("waiting connect...");
        while (true){
            Socket client = serverSocket.accept();
            System.out.println("New client connect.");
            es.execute(new BioServerThread(client));
        }
    }

    private class BioServerThread implements Runnable{
        private Socket client;

        BioServerThread(Socket client){
            this.client = client;
        }

        public void run() {
            try(
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream(),true)
            ){
                String message;
                while ((message = in.readLine()) != null){
                    System.out.println("Server accept message:"+message);
                    out.println(1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(client != null){
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
