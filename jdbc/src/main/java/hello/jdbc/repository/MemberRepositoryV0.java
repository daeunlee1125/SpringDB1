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
