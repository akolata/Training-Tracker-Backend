package pl.akolata.trainingtracker.test.util;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ResetDatabaseTestExecutionListener extends AbstractTestExecutionListener {

    @Autowired
    private DataSource dataSource;

    @Override
    public int getOrder() {
        return 2001;
    }

    @Override
    public void beforeTestClass(TestContext testContext) {
        testContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        log.info("### PREPARE TEST INSTANCE");
        cleanupDatabase();
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        log.info("### AFTER TEST CLASS");
        cleanupDatabase();
    }

    private void cleanupDatabase() throws SQLException {
        log.info("### CLEANUP DATABASE START");
        Connection c = dataSource.getConnection();
        Statement s = c.createStatement();


        s.execute("SET REFERENTIAL_INTEGRITY FALSE"); // Disable FK
        truncateAllTables(s);
        restartAllSequences(s);
        s.execute("SET REFERENTIAL_INTEGRITY TRUE"); // Enable FK
        populateDatabaseWithPreparedData(s);

        s.close();
        c.close();
        log.info("### CLEANUP DATABASE END");

    }

    private void populateDatabaseWithPreparedData(Statement s) {
        try {
            String initScript = Resources.toString(Resources.getResource("data-h2.sql"), StandardCharsets.UTF_8);
            s.execute(initScript);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void truncateAllTables(Statement s) throws SQLException {
        Set<String> tables = new HashSet<>();
        ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        rs.close();
        for (String table : tables) {
            s.executeUpdate("TRUNCATE TABLE " + table);
        }
    }

    private void restartAllSequences(Statement s) throws SQLException {
        Set<String> sequences = new HashSet<>();
        ResultSet rs = s.executeQuery("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            sequences.add(rs.getString(1));
        }
        rs.close();
        for (String sequence : sequences) {
            s.executeUpdate("ALTER SEQUENCE " + sequence + " RESTART WITH 1");
        }
    }
}
