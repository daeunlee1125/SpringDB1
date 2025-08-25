/* 데이터를 연결해주는 클래스 */

package hello.jdbc.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import static hello.jdbc.connection.ConnectionConst.*;

// 로그 출력하려고 추가 (lombok 어노테이션)
@Slf4j
public class DBConnectionUtil {
	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 이클립스는 노션 보고 lombok 따로 설치해줘야 함
			log.info("get connection={}, class={}", connection, connection.getClass());
            // 여기서 INFO 로그 찍어준다!
			
			return connection;
		} catch (SQLException e) {
			
			throw new IllegalStateException(e);
		}
		
	}

}
