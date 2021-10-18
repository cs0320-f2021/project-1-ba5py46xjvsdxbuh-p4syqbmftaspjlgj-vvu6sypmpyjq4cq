import java.util.Objects;
public class Node implements Comparable<Node> {
  private int id;
  private final double[] point;
  private String sign;
  private Node left;
  private Node right;
  private int depth;
  private double distance;

  //Constructor used for loaded data
  public Node(int id, int x, int y, int z, String sign) {
    this.id = id;
    int[] p = {x, y, z};
    this.point = Node.intToDoubleArray(p);
    this.sign = sign;
  }

  //Constructor for nodes based on target data
  public Node(int x, int y, int z) {
    int[] p = {x, y, z};
    this.point = Node.intToDoubleArray(p);
  }

  //Most general constructor
  public Node(int id, int[] objects) {
    this.id = id;
    this.point = Node.intToDoubleArray(objects);
  }

  //Most general constructor with data conversion algorithm for a student
  public Node(int id, int[] objects, boolean deriveSkills) {
    this.id = id;
    // if this constructor was used but what it's being passed is not
    // a student whose derived skills we want (to store in k-d tree)
    // then it should create the Node in the same way as the regular
    // "Most general constructor"
    if (!deriveSkills) {
      this.point = Node.intToDoubleArray(objects);
    } else {
      // given that the Node is representing a student to be placed in the k-d tree
      // we have to transform it's skills (which is the objects param) so that our
      // algorithm finds students with complementary relative best skills as being near
      double[] derivedSkills = Node.skillConversion(objects);
      this.point = derivedSkills;
    }
  }

  // This function takes in a students raw skills and then converts it into z-scores
  // of their additive inverse (mod 10) so that students are partnered up with others
  // that have relative confidence in skills opposite as them
  private static double[] skillConversion(int[] objects) {
    // make double-type defensive copy (shallow clone okay because int is primitive)
    double[] skills = intToDoubleArray(objects);

    // adding the ln of the number to itself before converting all of them to z-scores
    // makes it so that the Students raw estimate of their skill is still incorporated
    // a little bit and the derivedSkill isn't totally agnostic to raw estimate; also
    // swap numbers with their additive inverse (mod 10) so that Students get matched
    // with partners good at the skills they're bad at
    for (int i = 0; i < skills.length; i++) {
      double scaled = skills[i] + Math.log(skills[i]);
      skills[i] = 10-scaled;
    }

    // convert numbers to z-score relative to the array
    double mean = 0;
      double stdev = 0;
      for (double num : skills) {
        mean += num;
      }
      mean /= (1.0*skills.length);
      for (double num : skills) {
        stdev += Math.pow(num-mean, 2);
      }
      stdev = Math.sqrt(stdev/(1.0*skills.length));
      double[] convertedSkills = new double[skills.length];
      for (int i = 0; i < skills.length; i++) {
        convertedSkills[i] = (skills[i]-mean)/stdev;
      }

      // return the calculated array
      return convertedSkills;
  }

  // Changes an integer array into a double array, added because Node originally
  // handled integer arrays but our k-nearest algorithm relies on doubles due
  // to the incorporation of z-scores
  private static double[] intToDoubleArray(int[] objects) {
      double[] newArray = new double[objects.length];
      for (int i = 0; i < objects.length; i++) {
          newArray[i] = (double)objects[i];
      }
      return newArray;
  }


  //Setters and getters for Node instance variables
  public int getDepth() {
    return depth;
  }

  public void setDepth(int d, int dimensions) {
    this.depth = d % dimensions;
  }

  public double[] getPoint() {
    return this.point;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public int getId() {
    return id;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public double distanceTo(Node n){
    double sum = 0;
    if (n.getPoint().length != this.getPoint().length){
      throw new IllegalArgumentException();
    }
    for (int i  = 0; i < this.point.length; i++){
      sum += Math.pow(this.point[i] - n.getPoint()[i], 2);
    }
    sum = Math.sqrt(sum);
    return sum;
  }

  //Equality based on node ID.
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return id == node.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  //Converts the node to the ID. More could be added to this based on changes in what is needed
  @Override
  public String toString() {
    return "ID: " + id;
  }

  //Comparison is based purely on distance. This is used in the nearest neighbors search.
  @Override
  public int compareTo(Node o) {
    if (this.getDistance() < o.getDistance()) {
      return -1;
    } else if (this.getDistance() > o.getDistance()) {
      return 1;
    }
    return 0;
  }
}
