package orm;

import recommender.Item;

import java.util.List;
import java.util.Map;

public class Trait implements Item {
  private int id;
  private String[] data = new String[5];
  private int dataLength;

  public Trait(Interests i) {
    this.id = i.getID();
    this.data[0] = i.getInterest();
    this.dataLength = 1;
  }

  public Trait(Positive p) {
    this.id = p.getID();
    this.data[0] = p.getTrait();
    this.dataLength = 1;
  }

  public Trait(Negative n) {
    this.id = n.getID();
    this.data[0] = n.getTrait();
    this.dataLength = 1;
  }

  public String[] getData() {
    return this.data;
  }

  public void addToData(String s) {
    this.data[dataLength++] = s;
  }

  @Override
  public List<String> getVectorRepresentation() {
    return null;
  }

  @Override
  public String getId() {
    return String.valueOf(this.id);
  }
}
