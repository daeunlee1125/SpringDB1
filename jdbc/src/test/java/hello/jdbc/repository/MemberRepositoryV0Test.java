package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // new member insert
        Member member = new Member("memberV100", 10000);
        repository.save(member);

        // 방금 삽입한 멤버 아이디로 조회
        Member findMember = repository.findById(member.getMemeberId());
        log.info("findMember = {}", member.getMemeberId(), findMember);
        assertThat(findMember).isEqualTo(member);

        // update: money 10000->20000
        repository.update(member.getMemeberId(),20000);
        Member updateMember = repository.findById(member.getMemeberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemeberId());

        /* deleteMember = repository.findById(member.getMemeberId()); */
        // 삭제 성공 체크하는 법!
        // delete된 멤버 조회하려 하면
        // findById에서 else문에 넣어준 NoSuchElementException 터지죠?
        // 아래 assertThatThrownBy는 해당 예외가 발생해야 검증 통과!
        assertThatThrownBy(() -> repository.findById(member.getMemeberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}