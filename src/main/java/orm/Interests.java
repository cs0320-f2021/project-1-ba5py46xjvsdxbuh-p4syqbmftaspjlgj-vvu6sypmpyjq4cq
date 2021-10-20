package orm;

import java.util.Map;

public class Interests {
  private Integer id;
  private String interest;

  public Interests(Map<String, String> map) {
    this.id = Integer.parseInt(map.get("id"));
    this.interest = map.get("interest");
  }

  public Integer getID() {
    return id;
  }
  public String getInterest() {
    return interest;
  }

}
