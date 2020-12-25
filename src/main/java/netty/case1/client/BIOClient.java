package netty.case1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Anthony.liu
 * @Description
 * @Date 2020/12/25
 */

public class BIOClient {
    private ExecutorService es = Executors.newSingleThreadExecutor();

    public void start(String ip, int port) throws Exception{
        final Socket socket = new Socket(ip, port);

        // 开辟一个线程专门轮询，用于接受消息并输出
        es.execute(new ReadMsg(socket));

        // 主线程发送消息
        while (true){
            final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(new Scanner(System.in).next());
            printWriter.flush();
        }
    }

    private class ReadMsg implements Runnable{
        private Socket socket;

        ReadMsg(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try(final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line = null;
                while ((line = in.readLine()) != null){
                    System.out.printf("recive message:%s\n", line);
                }
            } catch (SocketException e) {
                System.out.printf("%s\n", "服务器断开了你的连接");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                clear();
            }
        }

        //必要的资源清理工作
        private void clear() {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
