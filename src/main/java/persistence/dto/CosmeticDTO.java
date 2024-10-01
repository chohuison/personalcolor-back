package persistence.dto;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CosmeticDTO implements MySerializableClass {
    private String brandName;
    private String productType;
    private String productName;
    private String cosRoot;
    private String seasonType;

    public CosmeticDTO(String brandName, String productType,String productName, String cosRoot, String seasonType){
        this.brandName = brandName;
        this.productType = productType;
        this.productName = productName;
        this.cosRoot = cosRoot;
        this.seasonType = seasonType;
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(brandName);
        dos.writeUTF(productType);
        dos.writeUTF(productName);
        dos.writeUTF(cosRoot);
        dos.writeUTF(seasonType);

        return buf.toByteArray();
    }
}
