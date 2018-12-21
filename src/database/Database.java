package database;

import model.Measurement;

import java.sql.*;
import java.util.Properties;

public class Database {

    private Connection conn;

    private String username;
    private String password;
    private String serverName;
    private String dbName;
    private int portNumber;

    private Statement stmt = null;

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
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
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
            String sql = "INSERT INTO MEASUREMENTS (station_number, date, time, temperature, dew_point, stp, slp, visibility, wind_speed, precipitate, snow, frshtt, clouds_percentage, wind_direction) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, m.getStationNumber());
            stmt.setDate(2, m.getDate());
            stmt.setTime(3, m.getTime());
            stmt.setFloat(4, m.getTemperature());
            stmt.setFloat(5, m.getDewPoint());
            stmt.setFloat(6, m.getStp());
            stmt.setFloat(7, m.getSlp());
            stmt.setFloat(8, m.getVisibility());
            stmt.setFloat(9, m.getWindSpeed());
            stmt.setFloat(10, m.getPrecipitate());
            stmt.setFloat(11, m.getSnow());
            stmt.setInt(12, m.getFrshtt());
            stmt.setFloat(13, m.getCloudsPercentage());
            stmt.setInt(14, m.getWindDirection());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Measurement getPreviousMeasurement() {
        try {
            String sql = "SELECT id FROM MEASUREMENTS ORDER BY id DESC LIMIT 1";
            ResultSet result = stmt.executeQuery(sql);
            result.next();
            int last = result.getInt(1);
            String previousMeasurementSql = "SELECT * FROM MEASUREMENTS WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(previousMeasurementSql);
            stmt.setInt(1, last > 30 ? last - 30 : 1);
            result = stmt.executeQuery();
            return convertResult(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Measurement convertResult(ResultSet set) {
        try {
            set.next();
            return new Measurement(set.getInt(2), set.getDate(3), set.getTime(4), set.getFloat(5), set.getFloat(6), set.getFloat(7), set.getFloat(8), set.getFloat(9), set.getFloat(10), set.getFloat(11), set.getFloat(12), set.getInt(13), set.getFloat(14), set.getInt(15));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
