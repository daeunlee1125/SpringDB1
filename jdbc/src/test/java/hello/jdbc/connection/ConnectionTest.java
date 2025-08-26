package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static hello.jdbc.connection.ConnectionConst.*;
import static java.sql.DriverManager.*;


@Slf4j
public class ConnectionTest {

    // driverManager에서는 커넥션 하나 만들 때마다 url, username, password 넣어줘야 함
    @Test
    void driverManager() throws SQLException {
        // conn0, conn1 획득
        Connection con1 = getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = getConnection(URL, USERNAME, PASSWORD);
        
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // conn2, conn3 획득
        useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // 히카리 커넥션풀이 스프링부트 기본 내장 풀
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
    }

    // 근데 데이터소스 사용하면?
    // 데이터소스 만들 때 URL, USERNAME, PASSWORD 가져오니까
    // getConnection에 인자가 없어도 됨
    // 그럼 다른 커넥션풀로 바꿀 때 모든 getConnection 건드릴 필요 없이
    // 단순히 데이터소스 구현체 하나 갈아끼우면 됨
    // 설정과 기능이 분리된다!
    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }
}
