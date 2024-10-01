package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.TipDTO;
import protocol.MySerializableClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public TipDAO (SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<TipDTO> findTip(String seasonType, String tipType){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<TipDTO> dtos = new ArrayList<>();
        Map<String, String> map = new HashMap<>(); // Map 선언
        map.put("seasonType",seasonType);
        map.put("tipType",tipType);


        try{
            dtos = session.selectList("mapper.TipMapper.findTip", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!`
            session.close();
        }
        return dtos;
    }
}
