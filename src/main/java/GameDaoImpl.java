import java.sql.*;

public class GameDaoImpl implements GameDao {

    public Connection dbconn(){
        final String driver = "org.mariadb.jdbc.Driver";
        final String DB_IP = "localhost";
        final String DB_PORT = "3306";
        final String DB_NAME = "dbdb";
        final String DB_URL =
                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;

        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, "root", "1234");
            if (conn != null) {
                System.out.println("DB 접속 성공");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }
        return conn;
    }
    @Override
    public void save(GameDto gdto) {
        PreparedStatement pstmt = null;
        Connection conn = dbconn();
        try {
            String sql = "INSERT INTO `game` (`userid`, `userpw`, `name`) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gdto.getUserid());
            pstmt.setString(2, gdto.getUserpw());
            pstmt.setString(3, gdto.getName());


            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("데이터 넣기 실패");
            }else {
                System.out.println("데이터 넣기 성공");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public GameDto findIdPw(String userId, String userPw) {
        PreparedStatement pstmt = null;
        Connection conn = dbconn();
        ResultSet rs = null;
        GameDto gdto = new GameDto();

        try {
            String sql = "SELECT * FROM `game` WHERE userid = ? AND userpw = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int marble = rs.getInt("gusle");
                String name = rs.getString("name");
                String userId_2 = rs.getString("userid");
                String userPw_2 = rs.getString("userpw");
                System.out.println(name + " 님 환영합니다.");
                System.out.println("게임을 시작하겠습니다.");
                gdto.setName(name);
                gdto.setUserid(userId_2);
                gdto.setUserpw(userPw_2);
                gdto.setId(id);
                gdto.setGusle(marble);
            } else {
                System.out.println("아이디가 존재하지 않습니다.");
                System.out.println("다시 입력해주세요.");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return gdto;
    }

    @Override
    public void update(int id, int gusle) {
        PreparedStatement pstmt = null;
        Connection conn = dbconn();
        GameDto gdto = new GameDto();

        try {
            String sql = "UPDATE `game` SET `gusle` = ? WHERE `id` = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gusle);
            pstmt.setInt(2, id);

            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("데이터 넣기 실패");
            }else {
                System.out.println("데이터 넣기 성공");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement pstmt = null;
        Connection conn = dbconn();

        try {
            String sql = "DELETE FROM `game` WHERE  `id` = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("데이터 넣기 실패");
            }else {
                System.out.println("데이터 넣기 성공");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
