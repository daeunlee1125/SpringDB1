package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // new member insert
        Member member = new Member("memberV4", 10000);
        repository.save(member);
        
        // 방금 삽입한 멤버 아이디로 조회
        Member findMember = repository.findById(member.getMemeberId());
        
        // 요기서 객체 참조값이 아니라 toString 결과 보이는 건 lombok이 해준 거임
        log.info("{} : {}", member.getMemeberId(), findMember);
    
        log.info("member == findMember {}", member==findMember); // false
        log.info("member equals findMember {}", member.equals(findMember)); // true
        // 여기서 정확히는 다른 인스턴스인데 equal이라고 뜨는 것도 lombok이 해준 거임 (내부적으로 equals 호출)
        assertThat(findMember).isEqualTo(member); // true
    }
}