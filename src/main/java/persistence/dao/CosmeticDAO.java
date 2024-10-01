package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.CosmeticDTO;

import java.util.ArrayList;
import java.util.List;

public class CosmeticDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public CosmeticDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<CosmeticDTO> selectAll(){
        List<CosmeticDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.CosmeticMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }
    public List<CosmeticDTO> findCosmetic(String seasonType){
        List<CosmeticDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.CosmeticMapper.findCosmetic",seasonType);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }

}
