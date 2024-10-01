package persistence;

import persistence.dao.ColorDAO;
import persistence.dao.CosmeticDAO;
import persistence.dao.TipDAO;
import persistence.dao.UserDAO;
import persistence.service.UserService;

public class Main {
    public static void main(String[] args) {
//        UserDAO userDao = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
//        UserService userService = new UserService(userDao);
////        userService.login();//로그인 부분
//        ColorDAO colorDAO = new ColorDAO(MyBatisConnectionFactory.getSqlSessionFactory());
////        System.out.println(colorDAO.selectAll());
////        System.out.println(colorDAO.findSeasonType("spring"));//색별로 색차트보여주기
//
//        CosmeticDAO cosmeticDAO = new CosmeticDAO(MyBatisConnectionFactory.getSqlSessionFactory());
//        System.out.println(cosmeticDAO.findColor("spring"));//색별로 화장품 보여주기

        TipDAO tipDAO = new TipDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        System.out.println(tipDAO.findTip("spring","fashion"));

    }
}
