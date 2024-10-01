package control.action;

import control.type.RequestController;
import control.type.ResponseController;
import protocol.Header;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class ReadController {
    private RequestController requestController;
    private ResponseController responseController;

    public ReadController(RequestController requestController, ResponseController responseController)
    {
        this.requestController = requestController;
        this.responseController = responseController;
    }

    public void handleRead(Header header, DataInputStream bodyReader, DataOutputStream outputStream) throws IOException, EOFException
    {
        switch (header.type)
        {
            case Header.TYPE_REQUEST:
                requestController.handleRead(header, bodyReader, outputStream);
                break;
            case Header.TYPE_RESPONSE:
                responseController.handleRead(header, bodyReader, outputStream);
                break;
        }
    }
}

