import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class KDTree {
  private Node root;
  private int size;
  private int depth;
  private int dimensions;
  private ArrayList<Node> nodes;

///////////////////////////////////////////////////////////////////////////////////
  public class Node implements Comparable<Node> {
    private int id;
    private int[] point;
    private String sign;
    Node left;
    Node right;
    private int depth;
    private double distance;


    public Node(int id, int x, int y, int z, String sign) {
      this.id = id;
      int[] point = {x, y, z};
      this.point = point;
      this.sign = sign;
    }
    public Node(int x, int y, int z){
      int[] point = {x, y, z};
      this.point = point;
    }
    public int getDepth() {
      return depth;
    }
    public void setDepth(int depth) {
      this.depth = depth % dimensions;
    }
    private int[] getPoint(){
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
    public void setId(int id) {
      this.id = id;
    }
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

    @Override
    public String toString(){
      return "ID: " + id;
          //+ " Coordinates: " + point[0] + ", " + point[1] + ", " + point[2]
    }

    @Override
    public int compareTo(Node o) {
      if (this.getDistance() < o.getDistance()) return -1;
      else if (this.getDistance() > o.getDistance()) return 1;
      return 0;
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////////
  //End of nested Node Class
  public KDTree() {
    this.root = null;
    this.dimensions = 3;
  }
  public KDTree(int n){
    this.root = null;
    this.dimensions = n;
  }

  public void insertNode(Node n){
    if ((int) (Math.log(size)/Math.log(2)) != (int) (Math.log(size+1)/Math.log(2))) {
      size++;
      this.updateDepth();
      this.root = insert(this.root, n);
    }
    else{
      this.root = insert(this.root, n);
      size++;
      this.updateDepth();
    }
    n.setDepth(this.depth);
  }

  private Node insert(Node source, Node insert){
    int dim;
    if (depth == 0) dim = 0;
    else dim = (depth - 1) % dimensions;
    if (source == null) return insert;
    else if (insert.getPoint()[dim] < source.getPoint()[dim]){
      source.left = insert(source.left, insert);
    }
    else if (insert.getPoint()[dim] > source.getPoint()[dim]){
      source.right = insert(source.right, insert);
    }
    else{
      if (source.equals(insert)){
        throw new IllegalArgumentException("Node already exists in the tree");
      }
      source.right = insert(source.right,  insert);
      //handle nodes with equal values of a given dimension
    }
    return source;
  }

  public ArrayList<Node> nearestNeighbors(int k, int weight, int height, int age){
    Node input = new Node( weight, height, age);
    input.setDistance(Double.MAX_VALUE);
    this.nodes = new ArrayList<>(k);
    nodeSearch(k, this.root, input);
    return this.nodes;
  }
  public ArrayList<Node> nearestNeighbors(int k, int id){
    this.nodes = new ArrayList<>(k);
    Node input = findNodeByID(id);
    if (input == null){
      throw new NullPointerException("The requested node could not be found");
    }
    nodeSearch(k, this.root, input);
    return this.nodes;
  }
  public Node findNodeByID(int id) {
    LinkedList<Node> queue = new LinkedList<>();
    queue.add(this.root);
    while (queue.size() != 0){
      Node n = queue.poll();
      if (n.getId() == id) return n;
      if (n.left != null) queue.add(n.left);
      if (n.right != null) queue.add(n.right);
    }
    return null;
  }

  public void nodeSearch(int k, Node source, Node target) {
    if (source == null){
      return;
    }
    double distance = Math.sqrt(Math.pow(source.getPoint()[0] - target.getPoint()[0], 2)
        + Math.pow(source.getPoint()[1] - target.getPoint()[1], 2)
        + Math.pow(source.getPoint()[2] - target.getPoint()[2], 2));
    source.setDistance(distance);
    if (this.nodes.size() < k && target.getId() != source.getId()) this.nodes.add(source);
    else {
      for (int i = 0; i < nodes.size(); i++) {
        if (source.compareTo(nodes.get(i)) <= 0 && target.getId() != source.getId()) {
          nodes.add(i, source);
          nodes.remove(nodes.size()-1);
          break;
        }
      }
    }
    if (nodes.size()  != 0 && nodes.toArray(new Node[k])[nodes.size()-1].getDistance() >
        target.getPoint()[target.getDepth()]){
      nodeSearch(k, source.left, target);
      nodeSearch(k, source.right, target);
    }
    else{
      if (source.getPoint()[target.getDepth()] <= target.getPoint()[target.getDepth()]){
        nodeSearch(k, source.right, target);
      }
      else nodeSearch(k, source.left, target);
      }
    }


  public void updateDepth(){
    this.depth = (int) (Math.log(size)/Math.log(2));
  }

  public int getSize(){
    return this.size;
  }

  //Prints out nodes in breadth first search order
  @Override
  public String toString() {
    String output = "";
    LinkedList<Node> queue = new LinkedList<>();
    queue.add(this.root);
    while (queue.size() != 0){
      Node n = queue.poll();
      output += n.toString() + " ";
      if (n.left != null) queue.add(n.left);
      if (n.right != null) queue.add(n.right);
    }
    return output;
  }

}
