public class Main {
  public static void main(String[] args){
    KDTree testTree = new KDTree();
    KDTree.Node n = testTree.new Node(1, 1, 1, 1, "Leo");
    testTree.insertNode(n);
    KDTree.Node n2 = testTree.new Node(2, 2, 2, 2, "Leo");
    KDTree.Node n3 = testTree.new Node(3, 3, 3, 3, "Leo");
    KDTree.Node n4 = testTree.new Node(4, 15, 5, 23, "Leo");
    KDTree.Node n5 = testTree.new Node(5, 15, 8, 23, "Leo");
    KDTree.Node n6 = testTree.new Node(6, 15, 11, 23, "Leo");
    KDTree.Node n7 = testTree.new Node(7, 15, 13, 23, "Leo");
    KDTree.Node n8 = testTree.new Node(8, 15, 11, 21, "Leo");
    KDTree.Node n9 = testTree.new Node(9, 15, 5, 25, "Leo");
    testTree.insertNode(n2);
    testTree.insertNode(n3);
    testTree.insertNode(n4);
    testTree.insertNode(n5);
    testTree.insertNode(n6);
    testTree.insertNode(n7);
    testTree.insertNode(n8);
    testTree.insertNode(n9);
    System.out.println(testTree);
    System.out.println(testTree.getSize());
    KDTree.Node[] ns = testTree.nearestNeighbors(1, 0, 0,0);
    for (int i = 0; i < ns.length; i++){
      System.out.println(ns[i]);
    }
  }
}
