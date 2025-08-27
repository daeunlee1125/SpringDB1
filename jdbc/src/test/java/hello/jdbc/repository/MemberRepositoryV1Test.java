package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {
    MemberRepositoryV1 repository;

    // beforeEach는 각 테스트 실행 직전에 한 번씩 호출됨
    @BeforeEach
    void beforeEach(){
        // 기본 DriverManager - 항상 새로운 커넥션을 획득
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        // 요 정도만 설정하면
        // maxConnection은 기본값인 10개로 잡히고
        // 나머지 설정들도 다 기본값
        repository = new MemberRepositoryV1(dataSource);

    }
    // 지금 이 설정으로 돌리면, conn0만 사용한다고 로그에 찍힌다!
    // 커넥션0 가져오고 >> 커넥션0 반환하고 >> 가져오고 >> 반환하고 ... 같은 커넥션 재사용됨
    // 실제로는 웹앱에서 동시에 여러 요청이 들어오면서
    // 커넥션 풀에서 여러 커넥션들을 가져갑니당

    @Test
    void crud() throws SQLException {
        // new member insert
        Member member = new Member("memberV100", 10000);
        repository.save(member);

        // 방금 삽입한 멤버 아이디로 조회
        Member findMember = repository.findById(member.getMemeberId());
        log.info("findMember = {}", findMember);
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