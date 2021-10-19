import java.util.Map;

public class Skills {
  private Integer id;
  private String name;
  private Integer commenting;
  private Integer testing;
  private Integer OOP;
  private Integer algorithms;
  private Integer teamwork;
  private Integer frontend;

  public Skills(Map<String, String> map) {
    this.id = Integer.valueOf(map.get("id"));
    this.name = map.get("name");
    this.commenting = Integer.valueOf(map.get("commenting"));
    this.testing = Integer.valueOf(map.get("testing"));
    this.OOP = Integer.valueOf(map.get("OOP"));
    this.algorithms = Integer.valueOf(map.get("algorithms"));
    this.teamwork = Integer.valueOf(map.get("teamwork"));
    this.frontend = Integer.valueOf(map.get("frontend"));
  }

  public Integer getID() {
    return id;
  }
  public String getName() {
    return name;
  }
  public Integer getCommenting() {
    return commenting;
  }
  public Integer getTesting() {
    return testing;
  }
  public Integer getOOP() {
    return OOP;
  }
  public Integer getAlgorithms() {
    return algorithms;
  }
  public Integer getTeamwork() {
    return teamwork;
  }
  public Integer getFrontend() {
    return frontend;
  }
}
