public class Student {
  private int id;
  private String[] interests;
  private String[] positives;
  private String[] negatives;
  private int[] skills;

  public Student(int id, String[] interests, String[] positives, String[] negatives, int[] skills) {
    this.id = id;
    this.interests = interests;
    this.positives = positives;
    this.negatives = negatives;
    this.skills = skills;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String[] getInterests() {
    return interests;
  }

  public void setInterests(String[] interests) {
    this.interests = interests;
  }

  public String[] getPositives() {
    return positives;
  }

  public void setPositives(String[] positives) {
    this.positives = positives;
  }

  public String[] getNegatives() {
    return negatives;
  }

  public void setNegatives(String[] negatives) {
    this.negatives = negatives;
  }

  public int[] getSkills() {
    return skills;
  }

  public void setSkills(int[] skills) {
    this.skills = skills;
  }
}
