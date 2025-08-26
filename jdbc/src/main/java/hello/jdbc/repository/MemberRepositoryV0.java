package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/* JDBC - DriverManager 사용 */
@Slf4j
public class MemberRepositoryV0 {
    // 새로운 멤버 등록 함수
    public Member save(Member member) throws SQLException {
        String sql = "INSERT INTO member(member_id, money) VALUES (?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemeberId());
            pstmt.setInt(2, member.getMoney());

            pstmt.executeUpdate();
            return member;
        } catch (Exception e){
            log.error("db error", e);
            throw e;
        } finally {
            // 리턴 직전에 죄다 close해준다
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Member> members = new ArrayList<>();

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if (rs.next()){
                Member member = new Member();
                member.setMemeberId(rs.getString(1));
                member.setMoney(rs.getInt(2));

                return member;
            }else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        }  catch (Exception e){
            log.error("db error", e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }

    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "UPDATE member SET money = ? WHERE member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            // resultSize에는 실행으로 영향받은 row 개수 출력
            // 여기서는 PK로 조건 넣었으니까
            // 성공하면 1 실패하면 0이겠죠?
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "DELETE FROM member Where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e){
            log.info("db error", e);
            throw e;
        } finally{
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) throws SQLException {

        if (rs!=null){
            try{
                rs.close();
            } catch (SQLException e){
                log.info("error", e);
            }
        }

        if (stmt!=null){
            try{
                stmt.close();
            } catch (SQLException e){
                log.info("error", e);
            }
        }

        if (con!=null){
            try{
                con.close();
            } catch (SQLException e){
                log.info("error", e);
            }
        }

    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
