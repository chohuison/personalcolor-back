package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public UserDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<UserDTO> selectAll(){
        List<UserDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.UserMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }

    public List<UserDTO> login(String id, String pw){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<UserDTO> dtos = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("ID",id);
        map.put("PW",pw);
        try{
            dtos = session.selectList("mapper.UserMapper.login",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!`
            session.close();
        }

        return dtos;
    }

    public int signUp(UserDTO userInfo) {
        SqlSession session = sqlSessionFactory.openSession();
        System.out.println(userInfo.getID());
        int result = -1;
        try {
            result = session.insert("mapper.UserMapper.signUp", userInfo);
            if (result == 1) {
                session.commit();
            } else {
                session.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    public int checkID(String id){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<UserDTO> dtos = new ArrayList<>();
        try{
            dtos = session.selectList("mapper.UserMapper.findName", id);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!`
            session.close();
        }
        return dtos.size();
    }

    public List<UserDTO> myPageInfo(String id){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<UserDTO> dtos = new ArrayList<>();
        try{
            dtos = session.selectList("mapper.UserMapper.findName", id);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!`
            session.close();
        }
        return dtos;
    }

    public List<UserDTO> userPersonal(String id)
    {
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<UserDTO> result = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("ID",id);
        try{
            result = session.selectList("mapper.UserMapper.getSeason", id);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!`
            session.close();
        }
        return result;
    }

    public int modifyUserInfo(UserDTO userInfo) {
        SqlSession session = sqlSessionFactory.openSession();
        System.out.println(userInfo.getID());
        int result = -1;
        try {
            result = session.insert("mapper.UserMapper.modifyUserInfo", userInfo);
            if (result == 1) {
                session.commit();
            } else {
                session.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    public int deleteUser(String userID) {
        SqlSession session = sqlSessionFactory.openSession();
        System.out.println(userID);
        int result = -1;
        try {
            result = session.insert("mapper.UserMapper.deleteUser", userID);
            if (result == 1) {
                session.commit();
            } else {
                session.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }
}
