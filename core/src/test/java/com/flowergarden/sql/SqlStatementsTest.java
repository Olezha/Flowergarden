package com.flowergarden.sql;

import org.flywaydb.core.Flyway;
import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Connection;

import static org.junit.Assert.*;

public class SqlStatementsTest {

    private Connection connection;

    private SqlStatements sql = new SqlStatementsImpl();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @BeforeClass
    public static void beforeClass() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:test-base.db", null, null);
        flyway.migrate();
    }

    @Before
    public void before() throws Throwable {
        connection = DriverManager.getConnection("jdbc:sqlite:");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("restore from test-base.db");
        }
    }

    @After
    public void after() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bouquetSaveTest() throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(sql.get("BOUQUET_SAVE"))) {
            preparedStatement.setString(1, "bouquet name");
            preparedStatement.setBigDecimal(2, BigDecimal.ONE);
            assertSame(1, preparedStatement.executeUpdate());
        }
    }

    @Test
    public void bouquetFindOneTest() throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(sql.get("BOUQUET_FIND_ONE"))) {
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertSame(1, rowCount);
        }
    }

    @Test
    public void bouquetFindOneZeroTest() throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(sql.get("BOUQUET_FIND_ONE"))) {
            preparedStatement.setInt(1, 225225);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertSame(0, rowCount);
        }
    }

    @Test
    public void bouquetFindAllTest() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql.get("BOUQUET_FIND_ALL"));
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertTrue(rowCount > 0);
        }
    }

    @Test
    public void bouquetPriceTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("BOUQUET_PRICE"))) {
            statement.setInt(1, 1);
            assertTrue(statement.executeQuery().getBigDecimal(1)
                    .compareTo(BigDecimal.valueOf(95)) == 0);
        }
    }

    @Test
    public void bouquetUpdateTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("BOUQUET_UPDATE"))) {
            statement.setBigDecimal(1, BigDecimal.TEN);
            statement.setInt(2, 1);
            assertSame(1, statement.executeUpdate());
        }
    }

    @Test
    public void bouquetDeleteTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("BOUQUET_DELETE"))) {
            statement.setInt(1, 1);
            assertSame(1, statement.executeUpdate());
        }
    }

    @Test
    public void bouquetDeleteAllTest() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            assertTrue(statement.executeUpdate(sql.get("BOUQUET_DELETE_ALL")) > 0);
        }
    }

    @Test
    public void flowerSaveTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_SAVE"))) {
            statement.setString(1, "flower name");
            statement.setInt(2, 25);
            statement.setInt(3, 0);
            statement.setNull(5, Types.INTEGER);
            statement.setString(6, null);
            statement.setBoolean(7, true);
            assertSame(1, statement.executeUpdate());
        }
    }

    @Test
    public void flowerFindOneTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_ONE"))) {
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertSame(1, rowCount);
        }
    }

    @Test
    public void flowerFindOneZeroTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_ONE"))) {
            statement.setInt(1, 225225);
            ResultSet resultSet = statement.executeQuery();
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertSame(0, rowCount);
        }
    }

    @Test
    public void flowerFindAllTest() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql.get("FLOWER_FIND_ALL"));
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertTrue(rowCount > 0);
        }
    }

    @Test
    public void flowerFindBouquetFlowersTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_FIND_BOUQUET_FLOWERS"))) {
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            int rowCount = 0;
            while (resultSet.next())
                rowCount++;
            assertTrue(rowCount > 0);
        }
    }

    @Test
    public void flowerUpdateTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_UPDATE"))) {
            statement.setInt(1, 1);
            statement.setInt(2, 2);
            statement.setBigDecimal(3, BigDecimal.TEN);
            statement.setInt(4, 4);
            statement.setInt(5, 5);
            statement.setInt(6, 1);
            assertSame(1, statement.executeUpdate());
        }
    }

    @Test
    public void flowerDeleteTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE"))) {
            statement.setInt(1, 1);
            assertSame(1, statement.executeUpdate());
        }
    }

    @Test
    public void flowerDeleteAllTest() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            assertTrue(statement.executeUpdate(sql.get("FLOWER_DELETE_ALL")) > 0);
        }
    }

    @Test
    public void flowerDeleteBouquetFlowersTest() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.get("FLOWER_DELETE_BOUQUET_FLOWERS"))) {
            statement.setInt(1, 1);
            assertSame(6, statement.executeUpdate());
        }
    }

    @Test()
    public void sqlStatementsImplWrongResourceNameTest() {
        exit.expectSystemExitWithStatus(512);
        SqlStatements sql = new SqlStatementsImpl("abc-test");
    }
}
