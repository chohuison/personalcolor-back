package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccessoryDTO;

import java.util.ArrayList;
import java.util.List;

public class AccessoryDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public AccessoryDAO (SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<AccessoryDTO> findAccessory(String seasonType){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<AccessoryDTO> dtos = new ArrayList<>();
        try{
            dtos = session.selectList("mapper.AccessoryMapper.findAccessory", seasonType);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!`
            session.close();
        }
        return dtos;
    }

}
