package database;

import java.sql.*;
import java.util.Properties;

public class Database {

    Connection conn;

    String username;
    String password;
    String serverName;
    String dbName;
    int portNumber;

    Statement stmt = null;

    public Database(String username, String password, String serverName, int portNumber, String dbName) {
        try {
            this.username = username;
            this.password = password;
            this.serverName = serverName;
            this.portNumber = portNumber;
            this.dbName = dbName;
            conn = makeConnection();
            createMeasurementsTable();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

    private Connection makeConnection() throws SQLException {

        Connection conn = null;
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.username);
            connectionProps.put("password", this.password);
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);
            if (conn != null) {
                System.out.println("Successfully connected to MySQL database test");
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void createMeasurementsTable() {
        try {
            stmt = conn.createStatement();
            String sql = "DROP TABLE IF EXISTS `measurements`" +
                    "CREATE TABLE `stations` (\n" +
                    "  `stn` int(10) unsigned NOT NULL,\n" +
                    "  `name` varchar(64) NOT NULL,\n" +
                    "  `country` varchar(64) NOT NULL,\n" +
                    "  `latitude` double NOT NULL,\n" +
                    "  `longitude` double NOT NULL,\n" +
                    "  `elevation` double NOT NULL,\n" +
                    "  PRIMARY KEY (`stn`)\n" +
                    ")";
            boolean success = stmt.execute(sql);

            if(success) {
                System.out.println("Created Measurements table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void testParse() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM stations LIMIT 5";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("name");
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
