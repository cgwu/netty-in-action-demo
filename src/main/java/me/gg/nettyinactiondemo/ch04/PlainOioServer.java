package me.gg.nettyinactiondemo.ch04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by danny on 2019/1/22.
 */
public class PlainOioServer {

    public void serve(int port) throws Exception {
        final ServerSocket socket = new ServerSocket(port);
        try{
            for(;;){
                final Socket clientSocket=socket.accept();
                System.out.println("Accept connection from "+clientSocket);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try{
                            out = clientSocket.getOutputStream();
                            out.write("Hi!中国\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        PlainOioServer svr = new PlainOioServer();
        svr.serve(8888);
    }
}
