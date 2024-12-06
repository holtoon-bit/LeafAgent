package leafagent.utils;

import leafagent.info.BaseInfo;

import java.sql.*;

public class SQLiteWritableDAOImpl implements LogWritableDAO {
    private String MAIN_TABLE_NAME = "Calls";

    private Connection conn;
    private Statement stmt;


    public SQLiteWritableDAOImpl(String path) {
        String url = "jdbc:sqlite:"+path;

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            if (!isTableCreated()) {
                String createSQL = "CREATE TABLE " + MAIN_TABLE_NAME + " ( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "parent_id TEXT, " +
                        "name TEXT, " +
                        "desc TEXT, " +
                        "startTime INTEGER, " +
                        "endTime INTEGER );";
                stmt.executeUpdate(createSQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isTableCreated() throws SQLException {
        return stmt.executeQuery(
                "SELECT name FROM sqlite_schema WHERE type='table' AND name='"+MAIN_TABLE_NAME+"'"
            ).getString("name")
            .equals(MAIN_TABLE_NAME);
    }

    @Override
    public BaseInfo create(BaseInfo info) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO " + MAIN_TABLE_NAME +
                            " (name, desc, startTime, endTime) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, info.getName());
            pstmt.setString(2, info.getDesc());
            pstmt.setLong(3, info.getStartMillis());
            pstmt.setLong(4, info.getEndMillis());
            pstmt.executeUpdate();

            ResultSet rs = stmt.executeQuery("SELECT id FROM " + MAIN_TABLE_NAME + " ORDER BY DESC LIMIT 1");
            info.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    @Override
    public BaseInfo get(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + MAIN_TABLE_NAME + " WHERE id = `" + id + "` LIMIT 1");
            return new BaseInfo.Build()
                    .setName(rs.getString("name"))
                    .setDesc(rs.getString("desc"))
                    .setStartMillis(rs.getLong("startTime"))
                    .setEndMillis(rs.getLong("endTime"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(BaseInfo info) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE " + MAIN_TABLE_NAME +
                            " SET name = ?, desc = ?, startTime = ?, endTime = ? WHERE id = ?");
            pstmt.setString(1, info.getName());
            pstmt.setString(2, info.getDesc());
            pstmt.setLong(3, info.getStartMillis());
            pstmt.setLong(4, info.getEndMillis());
            pstmt.setInt(5, info.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(BaseInfo info) {

    }
}
