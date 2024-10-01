package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ColorDTO;
import persistence.dto.LookBookDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public EnrollDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<LookBookDTO> selectAll(){
        List<LookBookDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.LookBookMapper.selectAll",1);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }
    public void insertLookBook(LookBookDTO lookBookDTo){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("mapper.LookBookMapper.insertLookBook", lookBookDTo);
            session.commit();
        } finally {
            session.rollback();
            session.close();
        }
    }

    public int deleteUserLookBook(String userID) {
        SqlSession session = sqlSessionFactory.openSession();
        int result = -1;
        try {
            result = session.delete("mapper.LookBookMapper.deleteUserLookBook", userID);
            session.commit(); // 커밋을 항상 수행합니다.
        } catch (Exception e) {
            session.rollback(); // 예외 발생 시 롤백합니다.
        } finally {
            session.close();
        }
        return result;
    }

    public int userLookBookCnt(String userId){
        List<LookBookDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.LookBookMapper.selectUser", userId);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos.size();
    }
}
