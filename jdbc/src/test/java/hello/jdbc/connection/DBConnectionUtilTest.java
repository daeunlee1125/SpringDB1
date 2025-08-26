/* 커넥션 연결 확인용 테스트 클래스 */

package hello.jdbc.connection;
import static org.assertj.core.api.Assertions.*;
import java.sql.Connection;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DBConnectionUtilTest {
	
	@Test
	void connection() {

        Connection connection = DBConnectionUtil.getConnection();

        /*
		 * 아래에서 Assertions(4라인) 임포트할 때
		 * org.junit.jupiter.api 말고
		 * org.assertj.core.api로 가져와야 함
		 */
		assertThat(connection).isNotNull();
		// connection이 isNotNull이라면 테스트 통과
		// 아니면(연결이 없으면) Assertion Error 발생
	}

}
