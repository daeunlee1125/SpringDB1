package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);
        // new member insert
        /* 
        * 두 번째 실행할 땐 에러 난다
        * 기본키 컬럼(Member_ID) 중복이라서!
        * 아이디 memberV1로 바꿔서 다시 실행해보면 삽입됨
        * H2 콘솔에서 Select * from Member;으로 확인하기
        */
        repository.save(member);
    }
}