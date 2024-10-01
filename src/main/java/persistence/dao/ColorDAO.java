package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ColorDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public ColorDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<ColorDTO> selectAll(){
        List<ColorDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.ColorMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }
    public List<ColorDTO>findSeasonType(String seasonType){
        List<ColorDTO>dtos= new ArrayList<>();
        Map<String, Object>map= new HashMap<>();
        map.put("seasonType",seasonType);
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.ColorMapper.findSeasonType",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }

}
