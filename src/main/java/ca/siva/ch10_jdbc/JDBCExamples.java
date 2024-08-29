package ca.siva.ch10_jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/*
NOTE:
1) Driver, Connection, Statement, PreparedStatement, CallableStatement, and ResultSet are interfaces. DriverManager is a factory class, not an interface.
For all these interfaces, implementation should be provided by the driver.
2) preparedStatement.setNull(2, Types.VARCHAR) it is required and has to provide the type in db.
3) rollback() is ignored when autocommit mode is set to true.
4) An unclosed ResultSet may consume memory but it will not cause an SQLException to be thrown.
5) This is true because you don't have to write any SQL query in Java code. You just use the name of the stored procedure.
The queries are already there inside the stored procedure, which exists in the Database and not in JDBC code.
6) Once a Connection object is closed, you cannot access any of the subsequent objects such as Statement and ResultSet that are retrieved from that Connection.
*/

@Slf4j
public class JDBCExamples {

    /**
     * Establishes a connection to the database using DriverManager.
     * @return Connection object.
     * Input: No input, but relies on the connection properties (URL, user, password) to establish a connection.
     * Output: Returns a Connection object. Logs an error if the connection fails.
     */
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "password";
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Example of executing a simple query using PreparedStatement. getConnection() throws checked SQLException.
     * Input: No direct input, but internally uses "Sales" as the department filter in the SQL query.
     * Output: Logs the employee ID and name for all employees in the Sales department.
     */
    public static void executeSimpleQuery() {
        String query = "SELECT id, name FROM employees WHERE department = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "Sales");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    log.info("Employee ID: {}, Name: {}", id, name);
                }
            }
        } catch (SQLException e) {
            log.error("Error executing query", e);
        }
    }

    /**
     * Example of inserting data using PreparedStatement.
     * Input: Inserts an employee with name "John Doe" and department "Engineering".
     * Output: Logs the number of rows inserted (should be 1).
     */
    public static void insertData() {
        String insertQuery = "INSERT INTO employees (name, department) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, "John Doe");
            preparedStatement.setString(2, "Engineering");

            int rowsInserted = preparedStatement.executeUpdate();
            log.info("Rows inserted: {}", rowsInserted);

        } catch (SQLException e) {
            log.error("Error inserting data", e);
        }
    }

    /**
     * Example of inserting data with NULL values using setNull().
     * Input: Inserts an employee with name "Jane Doe" and department set to NULL.
     * Output: Logs the number of rows inserted (should be 1).
     */
    public static void insertDataWithNull() {
        String insertQuery = "INSERT INTO employees (name, department) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, "Jane Doe");
            preparedStatement.setNull(2, Types.VARCHAR);  // Inserting NULL for department

            int rowsInserted = preparedStatement.executeUpdate();
            log.info("Rows inserted with NULL: {}", rowsInserted);

        } catch (SQLException e) {
            log.error("Error inserting data with NULL", e);
        }
    }

    /**
     * Example of calling a stored procedure using CallableStatement.
     * Input: Calls the stored procedure "increase_salary" with Employee ID 1 and a salary increment of 5000.00.
     * Output: Logs whether the stored procedure executed successfully. Depending on the stored procedure, may also log other information.
     */
    public static void callStoredProcedure() {
        String storedProcedure = "{CALL increase_salary(?, ?)}";
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {

            callableStatement.setInt(1, 1); // Employee ID
            callableStatement.setDouble(2, 5000.00); // Increment amount

            boolean hadResults = callableStatement.execute();
            if (hadResults) {
                log.info("Stored procedure executed successfully");
            }

        } catch (SQLException e) {
            log.error("Error calling stored procedure", e);
        }
    }

    /**
     * Example of batch updates using PreparedStatement.
     * Input: Increases the salary of employees in the Sales department by 1000.00, and in the Engineering department by 1500.00.
     * Output: Logs the number of batch update operations executed (should be 2).
     */
    public static void batchUpdate() {
        String updateQuery = "UPDATE employees SET salary = salary + ? WHERE department = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            connection.setAutoCommit(false); // Start transaction

            // Increase salary for Sales department
            preparedStatement.setDouble(1, 1000.00);
            preparedStatement.setString(2, "Sales");
            preparedStatement.addBatch();

            // Increase salary for Engineering department
            preparedStatement.setDouble(1, 1500.00);
            preparedStatement.setString(2, "Engineering");
            preparedStatement.addBatch();

            int[] updateCounts = preparedStatement.executeBatch();
            connection.commit(); // Commit transaction

            log.info("Batch update completed. Rows affected: {}", updateCounts.length);

        } catch (SQLException e) {
            log.error("Error executing batch update", e);
        }
    }

    /**
     * Example of using execute() to execute a SQL statement.
     * Input: Executes a SQL statement to create a table if it doesn't exist.
     * Output: Logs whether a ResultSet was returned or logs the update count for the SQL statement.
     */
    public static void executeExample() {
        String sql = "CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(50))";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            boolean isResultSet = statement.execute(sql);

            if (isResultSet) {
                log.info("A result set was returned.");
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        log.info("Result: {}", resultSet.getString(1));
                    }
                }
            } else {
                int updateCount = statement.getUpdateCount();
                log.info("Update count: {}", updateCount);
            }

        } catch (SQLException e) {
            log.error("Error executing SQL", e);
        }
    }

    /**
     * Example of transaction management with Savepoint and rollback.
     * Input: Inserts two employees into the database and creates a savepoint after the first insert.
     *        If an error is simulated, it rolls back to the savepoint, undoing the second insert but keeping the first.
     * Output: Logs the transaction status and the rollback information. Commits the transaction if successful.
     */
    public static void transactionWithSavepoint() {
        String insertQuery1 = "INSERT INTO employees (name, department) VALUES (?, ?)";
        String insertQuery2 = "INSERT INTO employees (name, department) VALUES (?, ?)";

        try (Connection connection = getConnection()) {

            connection.setAutoCommit(false); // Begin transaction

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery1)) {
                preparedStatement.setString(1, "Alice");
                preparedStatement.setString(2, "HR");
                preparedStatement.executeUpdate();
            }

            // Create a Savepoint after the first insert
            Savepoint savepoint1 = connection.setSavepoint("Savepoint1");

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery2)) {
                preparedStatement.setString(1, "Bob");
                preparedStatement.setString(2, "Marketing");
                preparedStatement.executeUpdate();
            }

            // Simulate an error to demonstrate rollback
            if (true) {
                log.warn("Simulating an error, rolling back to Savepoint1...");
                connection.rollback(savepoint1); // Rollback to savepoint
            }

            connection.commit(); // Commit the transaction

        } catch (SQLException e) {
            log.error("Error during transaction, rolling back.", e);
            try (Connection connection = getConnection()) {
                connection.rollback(); // Rollback the entire transaction if something went wrong
            } catch (SQLException rollbackEx) {
                log.error("Error during rollback", rollbackEx);
            }
        }
    }

    /**
     * Example of using TYPE_SCROLL_INSENSITIVE and CONCUR_UPDATABLE with ResultSet.
     * Input: No direct input, but modifies an employee's name by scrolling through the ResultSet.
     * Output: Logs the original and updated name of the first employee in the Sales department.
     */
    public static void scrollableAndUpdatableResultSet() {
        String query = "SELECT id, name FROM employees WHERE department = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            preparedStatement.setString(1, "Sales");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String originalName = resultSet.getString("name");
                    log.info("Original Employee Name: {}", originalName);

                    // Update the name in the ResultSet (this will update the database too)
                    resultSet.updateString("name", originalName + " Updated");
                    resultSet.updateRow();

                    // Scroll back to the first row to verify the update
                    resultSet.beforeFirst();
                    if (resultSet.next()) {
                        String updatedName = resultSet.getString("name");
                        log.info("Updated Employee Name: {}", updatedName);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Error working with scrollable and updatable ResultSet", e);
        }
    }
    public static void main(String[] args) {
        executeSimpleQuery();
        insertData();
        insertDataWithNull();
        callStoredProcedure();
        batchUpdate();
        executeExample();
        transactionWithSavepoint();
        scrollableAndUpdatableResultSet();
    }
}
