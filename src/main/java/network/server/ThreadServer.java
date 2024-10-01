package network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServer
{
    public  static final int PORT = 3000;

    public static void main(String []args) throws IOException
    {

        ServerSocket ss = null;
        int connectCount=0;
        System.out.println("ServerSocket created. \nWaiting for connection ...\n\n");
        Socket socket = ss.accept();

        System.out.println("Client connected!\n");

        try
        {
            ss = new ServerSocket(PORT);
            while(true)
            {
                Socket connectedClient  = ss.accept();
                InetAddress ia=connectedClient.getInetAddress(); // 접속에 사용된 prot
                int port = connectedClient.getLocalPort();//접속된 client IP
                String ip = ia.getHostAddress(); //접속

                ++connectCount;  //접속자수 카운트
                System.out.print("접속자 수: " + connectCount);
                System.out.print(" 접속-Local Port: "+ port);
                System.out.println(" Client IP: " + ip);

                Server handler = new Server(connectedClient);
                handler.start();
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }finally {
            try
            {
                ss.close();
            }catch(IOException ignored) {

            }

        }
    }
}
