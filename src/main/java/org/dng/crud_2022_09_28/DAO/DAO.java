package org.dng.crud_2022_09_28.DAO;

import org.dng.crud_2022_09_28.Model.VinylRecord;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DAO {
    private static final String INSERT_SQL =
            """
                    INSERT vinyla_db.vinyla_tbl(name, author, year) 
                    VALUES (?, ?, ?)""";
    private static final String SELECT_BY_ID = "select id, name, author, year from vinyla_db.vinyla_tbl where id = ?";
    private static final String SELECT_ALL = "select * from vinyla_db.vinyla_tbl";
    private static final String DELETE_SQL = "delete from vinyla_db.vinyla_tbl where id = ?;";
    private static final String UPDATE_SQL = "update vinyla_db.vinyla_tbl set name = ?, author= ?, year =? where id = ?;";


    public static VinylRecord selectById(int id) {
        VinylRecord item = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                item = new VinylRecord(id, name, author, year);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return item;
    }

    public static List<VinylRecord> selectAll() {
        List<VinylRecord> itemList = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                itemList.add(new VinylRecord(id, name, author, year));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return itemList;
    }

    public static List<VinylRecord> searchByParam(String pName, String pAuthor, int pYear) {
        List<VinylRecord> itemList = new LinkedList<>();
        String SELECT_BY_PARAM= "select id, name, author, year from vinyla_db.vinyla_tbl";
        if (pName!=""&&pAuthor!=""&&pYear>0){
            SELECT_BY_PARAM = "select id, name, author, year from vinyla_db.vinyla_tbl " +
                    "where (name = '"+ pName + "' AND author = '"+ pAuthor+"'' AND  year = "+ pYear+") ";
        } else if (pName!=""&&pAuthor!="") {
            SELECT_BY_PARAM = "select id, name, author, year from vinyla_db.vinyla_tbl " +
                    "where (name = "+ pName + " AND author = '"+ pAuthor+"' ) ";
        } else if (pName!="") {
            SELECT_BY_PARAM = "select id, name, author, year from vinyla_db.vinyla_tbl where (name = '"+ pName+"' ) ";
        } else if (pAuthor!="") {
            SELECT_BY_PARAM = "select id, name, author, year from vinyla_db.vinyla_tbl where (author = '"+ pAuthor+"' ) ";
        } else if (pYear>0) {
            SELECT_BY_PARAM = "select id, name, author, year from vinyla_db.vinyla_tbl where (year = "+ pYear+" ) ";
        }

            try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PARAM)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                itemList.add(new VinylRecord(id, name, author, year));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return itemList;
    }


    public static boolean insert(VinylRecord item) {
        boolean rowInserted = false;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getAuthor());
            preparedStatement.setInt(3, item.getYear());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                rowInserted = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowInserted;
    }

    public static boolean update(VinylRecord item) {
        boolean rowUpdated = false;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getAuthor());
            statement.setInt(3, item.getYear());
            statement.setLong(4, item.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public static boolean deleteById(long id) {
        boolean rowDeleted;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowDeleted;
    }


    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
