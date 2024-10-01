package persistence.dto;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LookBookDTO implements MySerializableClass {
    private String lbRoot;
    private String lbExplanation;
    private String seasonType;
    private String userID;
    private int lbShareCheck;
    public LookBookDTO(String lbRoot, String lbExplanation,String seasonType,String userID,int lbShareCheck){
        this.lbRoot=lbRoot;
        this.lbExplanation=lbExplanation;
        this.seasonType=seasonType;
        this.userID=userID;
        this.lbShareCheck=lbShareCheck;
    }

    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(lbRoot);
        dos.writeUTF(lbExplanation);
        dos.writeUTF(seasonType);
        dos.writeUTF(userID);
        dos.writeInt(lbShareCheck);

        return buf.toByteArray();
    }
}
