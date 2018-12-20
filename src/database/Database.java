package database;

import model.Measurement;

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
            String createSql = "CREATE TABLE IF NOT EXISTS MEASUREMENTS " +
                    "(id INTEGER not NULL, " +
                    " station_number INTEGER not NULL, " +
                    " date DATE not NULL, " +
                    " time TIME not NULL, " +
                    " temperature FLOAT, " +
                    " dew_point FLOAT, " +
                    " stp FLOAT, " +
                    " slp FLOAT, " +
                    " visibility FLOAT, " +
                    " wind_speed FLOAT, " +
                    " precipitate FLOAT, " +
                    " snow FLOAT, " +
                    " frshtt INTEGER, " +
                    " clouds_percentage FLOAT, " +
                    " wind_direction INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMeasurement(Measurement m) {
        try {
            String sql = "INSERT INTO MEASUREMENTS " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,1);
            stmt.setInt(2, m.getStationNumber());
            stmt.setDate(3, m.getDate());
            stmt.setTime(4, m.getTime());
            stmt.setFloat(5, m.getTemperature());
            stmt.setFloat(6, m.getDewPoint());
            stmt.setFloat(7, m.getStp());
            stmt.setFloat(8, m.getSlp());
            stmt.setFloat(9, m.getVisibility());
            stmt.setFloat(10, m.getWindSpeed());
            stmt.setFloat(11, m.getPrecipitate());
            stmt.setFloat(12, m.getSnow());
            stmt.setInt(13, m.getFrshtt());
            stmt.setFloat(14, m.getCloudsPercentage());
            stmt.setInt(15, m.getWindDirection());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
