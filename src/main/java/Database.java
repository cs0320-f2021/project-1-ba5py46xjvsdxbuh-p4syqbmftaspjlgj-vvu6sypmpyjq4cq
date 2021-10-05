package edu.brown.cs.student.main;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Autocorrect but with databases.
 * <p>
 * Chooses to pass SQL exceptions on to the class that instantiates it.
 */
public class Database {


  private static Connection conn = null;
  private static List<Rent> rentList = new ArrayList<>();

  /**
   * Instantiates the database, creating tables if necessary.
   * Automatically loads files.
   *
   * @param filename file name of SQLite3 database to open.
   * @throws SQLException if an error occurs in any SQL query.
   */
  Database(String filename) throws SQLException, ClassNotFoundException {
    // this line loads the driver manager class, and must be
    // present for everything else to work properly
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
    // these two lines tell the database to enforce foreign keys during operations, and should be present
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
    stat.close();
  }

  public void insert(Object newData) throws SQLException, NoSuchFieldException, IllegalAccessException {
    //this is assuming we know that newData's fields match our Database's fields –– I think otherwise,
    //this implementation wouldn't work? I was a little confused about how specific/generic
    //these commands need to be because the handout says our ORM implementation and Java Class
    //would fit
    Class clas = newData.getClass();
    Field[] fields = clas.getDeclaredFields();
    PreparedStatement prep = conn.prepareStatement("INSERT INTO rent (fit, user_id, item_id, rating, rented_for, category, size, id) VALUES (?)");
    //this returns the id field of this data object...process can be repeated to get all the fields
    //this for loop goes through all the newData's fields and inserts the values into the existing
    //sql table
    for (int i = 0; i < fields.length; i++) {
      prep.setString(i, (String) fields[i].get(newData));
    }
    prep.executeUpdate();
    prep.close();
  }

  public void delete(Object newData)
      throws NoSuchFieldException, IllegalAccessException, SQLException {
    //assuming the user_id is unique, we can delete from the table by specifying the user_id we want to delete
    Class clas = newData.getClass();
    String deletedField = clas.getDeclaredField("user_id").get(newData).toString();
    PreparedStatement prep = conn.prepareStatement("DELETE FROM rent WHERE user_id=?");
    prep.setString(1, deletedField);
    prep.execute();
  }

  public List<Rent> where(String pred, String predEquals) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(
            "SELECT * FROM rent WHERE" + pred + ";");
    prep.setString(1, predEquals);
    ResultSet rs = prep.executeQuery();
    while (rs.next()) {
      //String fit = rs.findColumn;
      rentList.add(new Rent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
          rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
    }
    prep.close();
    rs.close();
    return rentList;
  }

  public void update(Object datum, String field, String newVal)
      throws NoSuchFieldException, SQLException, IllegalAccessException {
    Class clas = datum.getClass();
    Field fieldToUpdate = clas.getDeclaredField(field); //gets simple name of field to update
    String oldVal = fieldToUpdate.get(datum).toString();
    PreparedStatement prep = conn.prepareStatement("UPDATE rent SET" + fieldToUpdate + " = " +
        newVal + "WHERE" + fieldToUpdate + " = " + oldVal);
    prep.executeUpdate();
    prep.close();

  }

  public void sql(String rawSQLQuery) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(rawSQLQuery);
    prep.executeQuery();
    prep.close();
  }


}
