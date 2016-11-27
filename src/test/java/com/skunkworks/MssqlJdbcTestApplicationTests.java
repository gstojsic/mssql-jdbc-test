package com.skunkworks;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class MssqlJdbcTestApplicationTests {
    private static final Logger l = Logger.getLogger(MssqlJdbcTestApplicationTests.class.getName());
    @Autowired
    DataSource dataSource;

    @Sql(
            scripts = "/database/tvpSpringTest.sql",
            config = @SqlConfig(separator = "GO")
    )
    @Test
    public void tvpSpringTest() throws Exception {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String sql = "{call dbo.TestMsDriverTVP(:inputData)}";

        SQLServerDataTable inputDataTable = new SQLServerDataTable();

        inputDataTable.setTvpName("[dbo].[MsDriverSqlType]");
        inputDataTable.addColumnMetadata("ThisId", Types.INTEGER);
        inputDataTable.addColumnMetadata("ThatId", Types.INTEGER);

        inputDataTable.addRow(2, 1);
        inputDataTable.addRow(2, 2);

        final MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("inputData", inputDataTable);

        namedParameterJdbcTemplate.update(sql, source);
        l.info("done.");
    }

    @Sql(
            scripts = "/database/tvpSchemaErrorTest.sql",
            config = @SqlConfig(separator = "GO")
    )
    @Test
    public void tvpSchemaSpringTest() throws Exception {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String sql = "{call tableValued.UpdateMsDriverTest(:inputData)}";

        SQLServerDataTable inputDataTable = new SQLServerDataTable();

        inputDataTable.setTvpName("[tableValued].[TheSqlDataType]");
        inputDataTable.addColumnMetadata("ThisId", Types.INTEGER);
        inputDataTable.addColumnMetadata("ThatId", Types.INTEGER);

        inputDataTable.addRow(2, 1);
        inputDataTable.addRow(2, 2);

        final MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("inputData", inputDataTable);

        namedParameterJdbcTemplate.update(sql, source);
        l.info("done.");
    }

    @Sql(
            scripts = "/database/tvpSchemaErrorTest.sql",
            config = @SqlConfig(separator = "GO")
    )
    @Test
    public void tvpSchemaErrorTest() throws Exception {
        SQLServerDataTable inputDataTable = new SQLServerDataTable();

        //inputDataTable.setTvpName("[tableValued].[TheSqlDataType]");
        inputDataTable.addColumnMetadata("ThisId", Types.INTEGER);
        inputDataTable.addColumnMetadata("ThatId", Types.SMALLINT);

        inputDataTable.addRow(2, 1);
        inputDataTable.addRow(2, 2);

        Connection connection = dataSource.getConnection();

        CallableStatement callableStatement = connection.prepareCall("exec tableValued.UpdateMsDriverTest ?");
        callableStatement.setObject(1, inputDataTable);
        callableStatement.execute();

//        SQLServerCallableStatement pStmt =
//                (SQLServerCallableStatement) connection.prepareCall("exec tableValued.UpdateMsDriverTest ?");
//
//        pStmt.setStructured(1, "tableValued.TheSqlDataType", inputDataTable);
//        pStmt.execute();

        l.info("done.");
    }
}
