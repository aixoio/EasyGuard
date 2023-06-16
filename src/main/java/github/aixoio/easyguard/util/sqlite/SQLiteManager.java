package github.aixoio.easyguard.util.sqlite;

import github.aixoio.easyguard.util.sqlite.data.SQLiteClaimData;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;

public class SQLiteManager {

    private final String path;
    private Connection con;

    public SQLiteManager(final String path) throws ClassNotFoundException, SQLException {

        this.path = path;

        Class.forName("org.sqlite.JDBC");

        File file = new File(this.path);

        boolean isNew = !file.exists();

        this.con = DriverManager.getConnection("jdbc:sqlite:" + this.path);

        if (isNew) this.createTables();


    }

    public SQLiteClaimData getClaim(String uuid, String name) throws SQLException {

        PreparedStatement statement = this.con.prepareStatement("SELECT * FROM claims WHERE uuid = ? AND name = ? LIMIT 1");
        statement.setString(1, uuid);
        statement.setString(2, name);

        ResultSet results = statement.executeQuery();

        if (results.next()) {

            final int id = results.getInt("id");
            final String uuidFromDB = results.getString("uuid");
            final String nameFromDB = results.getString("name");
            final int x = results.getInt("x");
            final int y = results.getInt("y");
            final int z = results.getInt("z");
            final String world = results.getString("world");
            final String truename = results.getString("truename");

            SQLiteClaimData data = new SQLiteClaimData(id, uuidFromDB, nameFromDB, x, y, z, world, truename);

            return data;

        }

        return null;


    }

    public LinkedList<SQLiteClaimData> getClaims(String uuid) throws SQLException {

        PreparedStatement statement = this.con.prepareStatement("SELECT * FROM claims WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet results = statement.executeQuery();

        LinkedList<SQLiteClaimData> sqLiteClaimDataList = new LinkedList<SQLiteClaimData>();

        while (results.next()) {

            final int id = results.getInt("id");
            final String uuidFromDB = results.getString("uuid");
            final String nameFromDB = results.getString("name");
            final int x = results.getInt("x");
            final int y = results.getInt("y");
            final int z = results.getInt("z");
            final String world = results.getString("world");
            final String truename = results.getString("truename");

            SQLiteClaimData data = new SQLiteClaimData(id, uuidFromDB, nameFromDB, x, y, z, world, truename);

            sqLiteClaimDataList.add(data);

        }

        return sqLiteClaimDataList;


    }

    public boolean claimExists(String uuid, String name) throws SQLException {

        PreparedStatement statement = this.con.prepareStatement("SELECT * FROM claims WHERE uuid = ? AND name = ? LIMIT 1");
        statement.setString(1, uuid);
        statement.setString(2, name);

        ResultSet results = statement.executeQuery();

        if (results.next()) return true;

        return false;

    }


    public boolean setClaim(SQLiteClaimData data) throws SQLException {

        SQLiteDataMode dataMode = null;


        boolean claimExists = this.claimExists(data.getUuid(), data.getName());

        if (claimExists) dataMode = SQLiteDataMode.UPDATE;
        if (!claimExists) dataMode = SQLiteDataMode.INSERT;

        if (dataMode == null) return false;

        if (dataMode == SQLiteDataMode.INSERT) {

            PreparedStatement statement = this.con.prepareStatement("INSERT INTO claims (uuid, name, x, y, z, world, truename) VALUES (?, ?, ?, ?, ?, ?, ?);");

            statement.setString(1, data.getUuid());
            statement.setString(2, data.getName());
            statement.setInt(3, data.getX());
            statement.setInt(4, data.getY());
            statement.setInt(5, data.getZ());
            statement.setString(6, data.getWorld());
            statement.setString(7, data.getTruename());

            statement.executeUpdate();

            return true;

        }

        if (dataMode == SQLiteDataMode.UPDATE) {

            if (data.getId() == -1) return false;

            PreparedStatement statement = this.con.prepareStatement("UPDATE claims SET x = ?, y = ?, z = ?, world = ? WHERE id = ?");

            statement.setInt(1, data.getX());
            statement.setInt(2, data.getY());
            statement.setInt(3, data.getZ());
            statement.setString(4, data.getWorld());
            statement.setInt(5, data.getId());

            statement.executeUpdate();

            return true;

        }

        return false;

    }

    public boolean setClaim(SQLiteClaimData data, SQLiteDataMode dataMode) throws SQLException {

        if (dataMode == null) return false;

        if (dataMode == SQLiteDataMode.INSERT) {

            PreparedStatement statement = this.con.prepareStatement("INSERT INTO claims (uuid, name, x, y, z, world, truename) VALUES (?, ?, ?, ?, ?, ?, ?);");

            statement.setString(1, data.getUuid());
            statement.setString(2, data.getName());
            statement.setInt(3, data.getX());
            statement.setInt(4, data.getY());
            statement.setInt(5, data.getZ());
            statement.setString(6, data.getWorld());
            statement.setString(7, data.getTruename());

            statement.executeUpdate();

            return true;

        }

        if (dataMode == SQLiteDataMode.UPDATE) {

            if (data.getId() == -1) return false;

            PreparedStatement statement = this.con.prepareStatement("UPDATE claims SET x = ?, y = ?, z = ?, world = ? WHERE id = ?");

            statement.setInt(1, data.getX());
            statement.setInt(2, data.getY());
            statement.setInt(3, data.getZ());
            statement.setString(4, data.getWorld());
            statement.setInt(5, data.getId());

            statement.executeUpdate();

            return true;

        }

        return false;

    }

    public boolean deleteClaim(final int claimID) throws SQLException {

        if (claimID == -1) return false;

        PreparedStatement statement = this.con.prepareStatement("DELETE FROM claims WHERE id = ?");

        statement.setInt(1, claimID);

        statement.executeUpdate();

        return true;

    }

    public Connection getConnection() {
        return this.con;
    }

    public void closeConnection() throws SQLException {
        if (this.con != null && !this.con.isClosed()) {
            this.con.close();
        }
    }

    private void createTables() throws SQLException {
        this.con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS claims (\n" +
                "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"uuid\"\tTEXT NOT NULL,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"x\"\tINTEGER NOT NULL,\n" +
                "\t\"y\"\tINTEGER NOT NULL,\n" +
                "\t\"z\"\tINTEGER NOT NULL,\n" +
                "\t\"world\"\tTEXT NOT NULL,\n" +
                "\t\"truename\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");");
    }

}
