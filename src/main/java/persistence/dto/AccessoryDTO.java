package persistence.dto;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AccessoryDTO implements MySerializableClass {
    private String accExplanation;
    private String accRoot;
    private String seasonType;

    public AccessoryDTO(String accExplanation, String accRoot,String seasonType)
    {
        this.accExplanation = accExplanation;
        this.accRoot = accRoot;
        this.seasonType=seasonType;

    }
    public static AccessoryDTO readAccessoryDTO(DataInputStream dis) throws IOException
    {
        String accExplanation = dis.readUTF();
        String accRoot = dis.readUTF();
        String seasonType = dis.readUTF();
        return new AccessoryDTO(accExplanation, accRoot,seasonType);
    }

    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(accExplanation);
        dos.writeUTF(accRoot);
        dos.writeUTF(seasonType);


        return buf.toByteArray();
    }
}
