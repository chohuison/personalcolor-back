package persistence.service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;

import java.io.IOException;
import java.util.*;

public class UserService {
    Scanner sc = new Scanner(System.in);
    static UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    public UserService(UserDAO mycustomerDAO) {
        this.userDAO = mycustomerDAO;
    }


    public void login() {
        List<UserDTO> userDTO = new ArrayList<>();
        System.out.println("아이디를 입력해주세요");
        String id=sc.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String pw=sc.nextLine();
        userDTO=userDAO.login(id,pw);
        System.out.println(userDTO.size());
        if(userDTO.size()==1)
            System.out.println("로그인에 성공하셨습니다");
        else{
            System.out.println("로그인에 실패하셨습니다");
        }
    }

    public boolean login(String id, String pw) {
        List<UserDTO> userDTO = new ArrayList<>();
        userDTO=userDAO.login(id,pw);

        System.out.println(userDTO.size());

        if(userDTO.size()==1)
            return true;
        else{
            return false;
        }
    }

    public int signUp(int result, UserDTO userInfo)
    {
        System.out.println(userInfo.getID());
        System.out.println(userInfo.getPW());

        if(result == 1)
        {
            return userDAO.signUp(userInfo);
        }
        else
        {
            return 0;
        }
    }

    public int checkID(String id)
    {
        return userDAO.checkID(id);
    }

    public static List<UserDTO> userPersonal(String id)
    {
        return userDAO.userPersonal(id);
    }
}
