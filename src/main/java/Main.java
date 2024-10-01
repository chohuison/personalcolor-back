import control.action.ActionController;
import protocol.Header;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.io.IOException;

import java.net.ServerSocket;

public class Main {
    public static final int PORT = 3000;

    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("ServerSocket created. \nWaiting for connection ...\n\n");
        Socket socket = ss.accept();

        System.out.println("Client connected!\n");

        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        boolean isContinue = true;

        while(isContinue)
        {
            Header header = Header.readHeader(is);
            byte[] body = new byte[header.bodySize];

            is.read(body);
            DataInputStream bodyReader = new DataInputStream(new ByteArrayInputStream(body));

            ActionController controller = new ActionController();
            controller.handleRequest(header, bodyReader, os);
        }
    }
}
