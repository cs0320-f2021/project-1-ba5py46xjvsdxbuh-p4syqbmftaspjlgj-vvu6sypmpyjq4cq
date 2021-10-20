package command_code;

import KDTree.*;
import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import orm.Skills;
import orm.Trait;
import recommender.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecsysRec implements REPLCallable {

  public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands,
                  HashMap<String, Item> traits, List<Skills> skills, KDTree tree,
                  BloomFilterRecommender<Item> filter) {
    // body
    ArrayList<Node> treeRecs = tree.nearestNeighbors(Integer.parseInt(REPLArguments[1]),
        Integer.parseInt(REPLArguments[2]));
    List<Item> filterRecs = filter.getTopKRecommendations((Trait) traits.get(REPLArguments[2]),
        Integer.parseInt(REPLArguments[1]));
    ArrayList<Integer> combinedRecs = new ArrayList<>();
    for (int i = 0; i < treeRecs.size(); i++) {
      if (filterRecs.contains(treeRecs.get(i))) {
        combinedRecs.add(treeRecs.get(i).getId());
      } else if (treeRecs.contains(filterRecs.get(i))) {
        combinedRecs.add(Integer.parseInt(filterRecs.get(i).getId()));
      } else {
        double rand = Math.random();
        if (rand > 0.5) {
          combinedRecs.add(Integer.parseInt(filterRecs.get(i).getId()));
        } else {
          combinedRecs.add(treeRecs.get(i).getId());
        }
      }
    }
    System.out.println(combinedRecs);
  }

}
