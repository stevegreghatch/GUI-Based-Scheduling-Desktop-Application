package DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQuery {

    private static PreparedStatement preparedStatement;

    /**
     *
     * @param conn current connection to mySQL database
     * @param sqlStatement raw sql statement (ex. insert, update, delete)
     * @throws SQLException if exception has occurred
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    // return statement object

    /**
     *
     * @return the prepared statement created
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
