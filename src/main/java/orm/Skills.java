package orm;

import java.util.Map;

public class Skills {
  private int id;
  private String name;
  private int commenting;
  private int testing;
  private int OOP;
  private int algorithms;
  private int teamwork;
  private int frontend;
  private int[] skills;

  public Skills(Map<String, String> map) {
    this.id = Integer.valueOf(map.get("id"));
    this.name = map.get("name");
    this.commenting = Integer.valueOf(map.get("commenting"));
    this.testing = Integer.valueOf(map.get("testing"));
    this.OOP = Integer.valueOf(map.get("OOP"));
    this.algorithms = Integer.valueOf(map.get("algorithms"));
    this.teamwork = Integer.valueOf(map.get("teamwork"));
    this.frontend = Integer.valueOf(map.get("frontend"));
    this.skills = new int[] {this.commenting, this.testing, this.OOP, this.algorithms, this.teamwork,
        this.frontend};
  }

  public int getID() {
    return id;
  }
  public String getName() {
    return name;
  }
  public int getCommenting() {
    return commenting;
  }
  public int getTesting() {
    return testing;
  }
  public int getOOP() {
    return OOP;
  }
  public int getAlgorithms() {
    return algorithms;
  }
  public int getTeamwork() {
    return teamwork;
  }
  public int getFrontend() {
    return frontend;
  }
  public int[] getSkills() { return this.skills;}
}
