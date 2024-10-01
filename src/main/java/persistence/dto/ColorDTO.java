package persistence.dto;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ColorDTO implements MySerializableClass {
    int R;
    int G;
    int B;
    String seasonType;

    public ColorDTO (int R, int G, int B, String seasonType) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.seasonType = seasonType;
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(R);
        dos.writeInt(G);
        dos.writeInt(B);
        dos.writeUTF(seasonType);

        return buf.toByteArray();
    }
}
