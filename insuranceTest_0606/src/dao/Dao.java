package dao;

import java.sql.*;

public class Dao {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void connect() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/insurance?serverTimezone=UTC&useSSL=false", "root", "12345678");
        } catch (Exception e) {
            System.out.println("DB가 연결되지 않았습니다.");
//            e.printStackTrace();
        }
    }

    //create
    public void execute(String query) throws Exception {
        try {
            statement = connect.createStatement();
            statement.execute(query);
        } catch (Exception e) {
            System.out.println("쿼리 실행 불가");
//            e.printStackTrace();
            throw e;
        }
    }

    public ResultSet retrieve(String query) throws Exception {
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            System.out.println("목록 불러오기 불가");
//            e.printStackTrace();
            throw e;
        } catch(NullPointerException e) {
            if(connect==null) {
                System.out.println("미연결 오류");
            }
            throw e;
        }
    }

}
