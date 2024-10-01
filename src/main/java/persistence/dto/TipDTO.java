package persistence.dto;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TipDTO implements MySerializableClass {
    private String detail;
    private String seasonType;
    private String tipType;

    public TipDTO(String detail, String seasonType,String tipType)
    {
        this.detail = detail;
        this.seasonType = seasonType;
        this.tipType=tipType;

    }
    public static TipDTO readTipDTO(DataInputStream dis) throws IOException
    {
        String detail = dis.readUTF();
        String seasonType = dis.readUTF();
        String tipType = dis.readUTF();
        return new TipDTO(detail, seasonType,tipType);
    }

    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(detail);
        dos.writeUTF(seasonType);
        dos.writeUTF(tipType);


        return buf.toByteArray();
    }
}
