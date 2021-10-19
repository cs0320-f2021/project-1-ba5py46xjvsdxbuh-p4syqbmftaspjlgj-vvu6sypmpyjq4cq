import java.util.Map;

public class Positive {
  private Integer id;
  private String trait;

  public Positive(Map<String, String> map) {
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
