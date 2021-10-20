package orm;

import java.util.Map;

public class Negative {
  private Integer id;
  private String trait;

  public Negative(Map<String, String> map) {
    this.id = Integer.valueOf(map.get("id"));
    this.trait = map.get("trait");
  }

  public Integer getID() {
    return id;
  }
  public String getTrait() {
    return trait;
  }
}
