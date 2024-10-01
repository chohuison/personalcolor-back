package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.session.SqlSessionFactory;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
public class UserDTO implements MySerializableClass
{
    private String ID;
    private String PW;
    private String name;
    private String age;
    private String gender;
    private String seasonType;

    public UserDTO(){}

    public static UserDTO readUserDTO(DataInputStream dis) throws IOException
    {
        String ID = dis.readUTF();
        String PW = dis.readUTF();
        String name = dis.readUTF();
        String age = dis.readUTF();
        String gender = dis.readUTF();
        String seasonType = dis.readUTF();

        return new UserDTO(ID, PW, name, age, gender, seasonType);
    }

    public UserDTO(String ID, String PW, String name, String age, String gender, String seasonType)
    {
        this.ID = ID;
        this.PW = PW;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seasonType = seasonType;
    }

    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(ID);
        dos.writeUTF(PW);
        dos.writeUTF(name);
        dos.writeUTF(age);
        dos.writeUTF(gender);
        dos.writeUTF(seasonType);

        return buf.toByteArray();
    }
}
