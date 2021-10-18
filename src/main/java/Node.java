import java.util.Objects;
public class Node implements Comparable<Node> {
  private int id;
  private final int[] point;
  private String sign;
  private Node left;
  private Node right;
  private int depth;
  private double distance;

  //Constructor used for loaded data
  public Node(int id, int x, int y, int z, String sign) {
    this.id = id;
    int[] p = {x, y, z};
    this.point = p;
    this.sign = sign;
  }

  //Most general constructor
  public Node(int id, int[] objects) {
    this.id = id;
    this.point = objects;
  }

  //Constructor for nodes based on target data
  public Node(int x, int y, int z) {
    int[] p = {x, y, z};
    this.point = p;
  }

  //Setters and getters for Node instance variables
  public int getDepth() {
    return depth;
  }

  public void setDepth(int d, int dimensions) {
    this.depth = d % dimensions;
  }

  public int[] getPoint() {
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
