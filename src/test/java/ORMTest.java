import org.junit.Test;
import orm.Database;
import orm.Rent;

import java.sql.SQLException;

public class ORMTest {

  @Test
  public void testInsertOne() throws SQLException, ClassNotFoundException, IllegalAccessException {
    Database d = new Database("data/emptyEditable");
    //d.insert(new Rent("1", "2", "3", "4", "5", "6", "7", "8"));
  }

}
