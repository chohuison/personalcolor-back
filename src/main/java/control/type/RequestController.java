package control.type;

import persistence.MyBatisConnectionFactory;
import persistence.dto.*;
import persistence.dao.*;
import persistence.service.*;
import protocol.BodyMaker;
import protocol.Header;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class RequestController
{
    UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    UserService userService = new UserService(userDAO);
    TipDAO tipDAO = new TipDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    AccessoryDAO accessoryDAO = new AccessoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    ColorDAO colorDAO = new ColorDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    CosmeticDAO cosmeticDAO = new CosmeticDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    EnrollDAO enrollDAO = new EnrollDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    String seasonType;
    public void handleRead(Header header, DataInputStream bodyReader, DataOutputStream outputStream) throws IOException
    {
        switch(header.code) {
            case Header.CODE_REQ_SIGN_UP: // 회원가입
                UserDTO user = UserDTO.readUserDTO(bodyReader);
                signUp(user, outputStream);
                break;

            case Header.CODE_REQ_LOG_IN:
                String id = bodyReader.readUTF();
                String pw = bodyReader.readUTF();

                login(id, pw, outputStream);
                break;

            case Header.CODE_REQ_USER_INFORMATION:
                String userID = bodyReader.readUTF();
                userInfo(userID, outputStream);
                break;

            case Header.CODE_REQ_TIP:
                seasonType=bodyReader.readUTF();
                String tipType=bodyReader.readUTF();
                tip(seasonType,tipType,outputStream);
                break;

            case Header.CODE_REQ_ACCESSORY:
                seasonType = bodyReader.readUTF();
                Accessory(seasonType,outputStream);
                break;

            case Header.CODE_REQ_COLOR:
                String seasonType = bodyReader.readUTF();
                sendColor(seasonType, outputStream);
                break;

            case Header.CODE_REQ_COSMETIC:
                seasonType = bodyReader.readUTF();
                sendCosmetic(seasonType, outputStream);
                break;

            case Header.CODE_REQ_MYPAGE_INFO:
                userID = bodyReader.readUTF();
                myPageInfo(userID, outputStream);
                break;

            case Header.CODE_REQ_MODIFY_INFO:
                UserDTO modifyUser = UserDTO.readUserDTO(bodyReader);
                modifyInfo(modifyUser, outputStream);
                break;

            case Header.CODE_REQ_DELETE_USER:
                userID = bodyReader.readUTF();
                deleteUser(userID, outputStream);
                break;

            case Header.CODE_REQ_LB_INFO:
                sendLookBook(outputStream);
                break;

            case Header.CODE_REQ_LB_ENROLL:
                String img = bodyReader.readUTF();
                String text = bodyReader.readUTF();
                seasonType =bodyReader.readUTF();
                userID = bodyReader.readUTF();
                int lbShareCheck = bodyReader.readInt();
                enroll(img,text,seasonType,userID,lbShareCheck,outputStream);
                break;

        }
    }

    public void signUp(UserDTO user, DataOutputStream outputStream) throws IOException
    {
        int result = 1;

        if(user.getID() == null || user.getPW() == null || user.getName() == null
                || user.getAge() == null || user.getGender() == null || user.getSeasonType() == null)
        {
            result = 2;
        }

        if(userService.checkID(user.getID()) != 0)
        {
            result =  0;
        }

        BodyMaker bodymaker = new BodyMaker();
        bodymaker.addIntBytes(result);

        System.out.println(result);

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(result);
        System.out.println(result);

        byte[] body = bodymaker.getBody();

        userService.signUp(result, user);

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_SIGN_UP,
                body.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(body);
    }
    public void login(String id, String pw, DataOutputStream outputStream) throws IOException
    {
        boolean result = userService.login(id, pw);

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeBoolean(result);

        byte[] body = buf.toByteArray();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_LOG_IN,
                body.length
                );

        outputStream.write(resHeader.getBytes());
        outputStream.write(body);
    }

    public void userInfo(String ID, DataOutputStream outputStream) throws IOException
    {
        List<UserDTO> user = UserService.userPersonal(ID);
        String personalColor = String.valueOf(user.get(0).getSeasonType());
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        BodyMaker bodymaker = new BodyMaker();
        bodymaker.addStringBytes(personalColor);

        byte[] body = bodymaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_USER_INFORMATION,
                body.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(body);
    }
    public void tip(String seasonType, String tipType, DataOutputStream outputStream) throws  IOException{

        List<TipDTO> tips=tipDAO.findTip(seasonType,tipType);
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(tips.size());
          for(TipDTO tip : tips){
            bodyMaker.add(tip);
        }

        byte [] body= bodyMaker.getBody();

        Header header= new Header(Header.TYPE_RESPONSE , Header.CODE_RES_TIP, body.length);
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void Accessory(String seasonType, DataOutputStream outputStream) throws IOException{
        List<AccessoryDTO> accessorys = accessoryDAO.findAccessory(seasonType);
        System.out.println("리스트의 사이즈는" + accessorys.size());
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(accessorys.size());
            for(AccessoryDTO accessory : accessorys){
                bodyMaker.add(accessory);
            }

        byte [] body= bodyMaker.getBody();

         Header header = new Header(Header.TYPE_RESPONSE, Header.CODE_RES_ACCESSORY, body.length);
         outputStream.write(header.getBytes());
         outputStream.write(body);
        System.out.println("정보 보냈삼");
    }

    public void sendColor(String seasonType, DataOutputStream outputStream) throws IOException
    {
        List<ColorDTO> colorDTO = colorDAO.findSeasonType(seasonType);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(colorDTO.size());
        for(ColorDTO color : colorDTO)
            bodyMaker.add(color);


        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_COLOR,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void sendCosmetic(String seasonType, DataOutputStream outputStream) throws IOException
    {
        List<CosmeticDTO> cosmeticDTO = cosmeticDAO.findCosmetic(seasonType);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(cosmeticDTO.size());
        for(CosmeticDTO cosmetic : cosmeticDTO)
            bodyMaker.add(cosmetic);

        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_COSMETIC,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void myPageInfo(String userID, DataOutputStream outputStream) throws IOException
    {
        List<UserDTO> userDTO = userDAO.myPageInfo(userID);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(userDTO.size());
        for(UserDTO user : userDTO)
            bodyMaker.add(user);

        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_MYPAGE_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void modifyInfo(UserDTO user, DataOutputStream outputStream) throws IOException
    {
        int result = userDAO.modifyUserInfo(user);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(result);

        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_MODIFY_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void deleteUser(String userID, DataOutputStream outputStream) throws IOException
    {
        int lookBookCnt = enrollDAO.userLookBookCnt(userID);
        while(lookBookCnt != 0)
        {
            enrollDAO.deleteUserLookBook(userID);
            System.out.println(lookBookCnt + userID);
            lookBookCnt = enrollDAO.userLookBookCnt(userID);
        }
        int result = userDAO.deleteUser(userID);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(result);

        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_DELETE_USER,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void enroll(String img, String text, String seasonType, String userID, int ibShareCheck, DataOutputStream outputStream) throws IOException
    {
        LookBookDTO lookBookDTO = new LookBookDTO(img,text,seasonType,userID,ibShareCheck);
        enrollDAO.insertLookBook(lookBookDTO);
    }

    public void sendLookBook(DataOutputStream outputStream) throws IOException
    {
        List<LookBookDTO> lookBookDTOS = enrollDAO.selectAll();

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(lookBookDTOS.size());

        for(LookBookDTO lookbook : lookBookDTOS)
            bodyMaker.add(lookbook);

        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_COSMETIC,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }


}