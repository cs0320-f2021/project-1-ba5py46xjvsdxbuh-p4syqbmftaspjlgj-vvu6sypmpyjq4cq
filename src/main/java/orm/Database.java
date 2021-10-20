package orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Autocorrect but with databases.
 * <p>
 * Chooses to pass SQL exceptions on to the class that instantiates it.
 */
public class Database {


  private static Connection conn = null;
  private static List<Object> objectList = new ArrayList<>();

  /**
   * Instantiates the database, creating tables if necessary.
   * Automatically loads files.
   *
   * @param filename file name of SQLite3 database to open.
   * @throws SQLException if an error occurs in any SQL query.
   */
  public Database(String filename) throws SQLException, ClassNotFoundException {
    // this line loads the driver manager class, and must be
    // present for everything else to work properly
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
    // these two lines tell the database to enforce foreign keys during operations, and should be present
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
    stat.close();
    //PreparedStatement prep = conn.prepareStatement("INSERT INTO rent VALUES ('good', '1', '2', '4', '5', '6', '7','9')");
    //prep.executeUpdate();
    //prep.close();
  }

  /**
   * inserts a given object to the sql databse
   *
   * @param newData object to be inserted in SQLite3 database .
   * @throws SQLException           if an error occurs in any SQL query.
   * @throws IllegalAccessException if the field of an object is called that doesn't exist.
   */
//  public void insert(Object newData) throws SQLException, IllegalAccessException {
//    try {
//      if (!objectList.contains(newData)) {
//        Class clas = newData.getClass();
//        Field[] fields = clas.getDeclaredFields();
//        PreparedStatement prep = conn.prepareStatement("INSERT INTO " +
//            "rent VALUES (?,?,?,?,?,?,?,?);");
//        for (int i = 0; i < fields.length; i++) {
//          prep.setString(i + 1, (String) fields[i].get(newData));
//        }
//        prep.addBatch();
//        prep.executeBatch();
//        prep.close();
//        objectList.add((Rent) newData);
//      } else {
//        System.out.println("Item is already in database!");
//      }
//    } catch (SQLException e) {
//      System.out.println("Item is already in database!");
//    } catch (IllegalAccessException e) {
//      System.out.println("Incorrect fields used.");
//    }
//  }

  /**
   * deletes a given object to the sql databse
   *
   * @param data object to be deleted in SQLite3 database .
   * @throws SQLException           if an error occurs in any SQL query.
   * @throws IllegalAccessException if the field of an object cannot be accessed
   * @throws NoSuchFieldException   if a field of an object is called that doesn't exist.
   */

  public void delete(Object data)
      throws NoSuchFieldException, IllegalAccessException, SQLException {
    //assuming the id is the primary key, we can delete from the table by specifying the id we want to delete
    //in order to make this more general, we'd just replace "id" with whatever the primary key is
    Class clas = data.getClass();
    String deletedField = clas.getDeclaredField("id").get(data).toString();
    PreparedStatement prep = conn.prepareStatement("DELETE FROM rent WHERE id=?");
    prep.setString(1, deletedField);
    prep.execute();
    prep.close();
    objectList.remove(data);
  }

  public <T> List<T> select(Class<T> c, Map<String, String> queryParams) throws SQLException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
    String tableName = c.getSimpleName().toLowerCase();
    if (queryParams.isEmpty()) {
      String sql = "SELECT * FROM " + tableName + ";";
      return sqlListQuery(c, sql, Collections.emptyList());
    } else {
      List<Object> params = new ArrayList<>();
      Set<String> keys = queryParams.keySet();
      String wheres = "";
      int counter = 0;
      for (String key : keys) {
        counter += 1;
        params.add(queryParams.get(key));
        if (counter != keys.size()) {
          wheres += (key + "=? AND");
        } else {
          wheres += (key + "=?");
        }
      }
      String sql = "SELECT * FROM " + tableName + " WHERE " + wheres + ";";
      System.out.println(sql);
      return sqlListQuery(c, sql, params);
    }
  }

  private <T> List<T> sqlListQuery(Class<T> c, String sql, List<Object> insertValues) throws
      SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
    PreparedStatement prep = conn.prepareStatement(sql);
    if (!insertValues.isEmpty()) {
      setParameters(prep, insertValues);
    }
    List<T> output = new ArrayList<>();
    ResultSet res = prep.executeQuery();
    Field[] attributes = c.getDeclaredFields();
    Map<String, String> mapper = new HashMap<>();
    while (res.next()) {
      for (Field field : attributes) {
        field.setAccessible(true);
        String fieldName = field.getName();
        int column = res.findColumn(fieldName);
        mapper.put(fieldName, res.getString(column));
      }
      T node = (T) (c.getDeclaredConstructor(Map.class).newInstance(mapper));
      output.add(node);
    }
    //System.out.println(output.size());
    return output;
  }

  private void setParameters(PreparedStatement prep, List<Object> parameters) throws SQLException {
    int counter = 1;
    for (Object o : parameters) {
      prep.setObject(counter, o);
      counter += 1;
    }
  }



/*  public void where(String table, String pred, String predEquals) throws SQLException {
    List<orm.Student> selectList = new ArrayList<>();
    PreparedStatement prep = conn.prepareStatement("SELECT * FROM " + table);
    //"SELECT * FROM rent WHERE" + pred + ";");
    //prep.setString(1, predEquals);
    ResultSet rs = prep.executeQuery();
    while (rs.next()) {
      if (rs.getInt(1)).
      selectList.add(makeNewRent(rs));
    }
    rs.close();
    prep.close();
    //printRentHeader();
    for (int i = 0; i < selectList.size();  i++) {
      printRent(selectList.get(i));
    }
  }*/



  /**
   * selects the data in a table where a given predicate is true. Returns a table
   * of the selected data
   *
   * @param datum  datum to be updated; we use the primary key from this object to update th necessary object
   * @param field  field thta we will be updating
   * @param newVal value of the field that we are updating
   * @throws SQLException           if an error occurs in any SQL query.
   * @throws IllegalAccessException if the field of an object cannot be accessed
   * @throws NoSuchFieldException   if a field of an object is called that doesn't exist.
   */
  public void update(Object datum, String field, String newVal)
      throws NoSuchFieldException, SQLException, IllegalAccessException {
    Class clas = datum.getClass();
    Field fieldToUpdate =
        clas.getDeclaredField("id"); //gets simple name of field to update using the primary key
    String oldVal = fieldToUpdate.get(datum).toString();
    PreparedStatement prep = conn.prepareStatement("UPDATE rent SET "
        + field + " = '" +
        newVal + "' WHERE id=" + oldVal);
    prep.executeUpdate();
    prep.close();
  }

  /**
   * allows us to execute any explicit sql query from the given statement
   *
   * @param rawSQLQuery sql statement to be executed
   * @throws SQLException if an error occurs in any SQL query.
   */
  public void sql(String rawSQLQuery) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(rawSQLQuery);
    prep.execute();
    prep.close();
  }


}

