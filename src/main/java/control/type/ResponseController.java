package control.type;

import persistence.dto.*;
import protocol.Header;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ResponseController
{
    public static List<UserDTO> holdUserList = new ArrayList<>();

    public void handleRead(Header header, DataInputStream bodyReader, DataOutputStream outputStream)
    {
        switch (header.code)
        {

        }
    }
}
