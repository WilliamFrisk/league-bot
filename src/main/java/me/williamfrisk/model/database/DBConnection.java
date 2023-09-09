package me.williamfrisk.model.database;

import me.williamfrisk.utils.Throw;
import org.apache.commons.collections4.set.PredicatedSet;
import org.json.JSONObject;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private static final String DBNAME = "leaguebot";
    private static final String DATABASE = "jdbc:postgresql://localhost/" + DBNAME;
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = System.getenv("LEAGUE_BOT_DATABASE_PWD");

    private final Connection conn;

    private static DBConnection instance = null;

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            return new DBConnection();
        } else {
            return instance;
        }
    }

    private DBConnection() throws SQLException, ClassNotFoundException {
        this(DATABASE, USERNAME, PASSWORD);
    }

    private DBConnection(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }

    public void registerSummoner(String puuid, String summonerName, String discordName) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Users VALUES (?, ?, ?)")) {
            ps.setString(1, puuid);
            ps.setString(2, summonerName);
            ps.setString(3, discordName);

            ps.executeUpdate();
        }
    }

    public JSONObject getSummoner(String discordName) {
        JSONObject result = new JSONObject();

        try (PreparedStatement ps = conn.prepareStatement("SELECT puuid, summonerName FROM Users WHERE discordName=?")){
            ps.setString(1, discordName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result.put("puuid", rs.getString("puuid"));
                result.put("summonerName", rs.getString("summonerName"));
            } else {
                Throw.asUnchecked(new SQLException(discordName + "does not exist in database"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
