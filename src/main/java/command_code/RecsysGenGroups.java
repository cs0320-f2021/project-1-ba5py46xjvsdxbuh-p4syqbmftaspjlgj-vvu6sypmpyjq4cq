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

public class RecsysGenGroups implements REPLCallable {

  public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands,
                  HashMap<String, Item> traits, List<Skills> skills, KDTree tree,
                  BloomFilterRecommender<Item> filter) {
    // body
    ArrayList<Integer> idQueue = new ArrayList<>();
    for (int i = 0; i < tree.getSize(); i++) {
      idQueue.add(i + 1);
    }
    while (!idQueue.isEmpty()) {
      ArrayList<Node> treeRecs = tree.nearestNeighbors(Integer.parseInt(REPLArguments[1]),
          idQueue.get(0));
      List<Item> filterRecs =
          filter.getTopKRecommendations((Item) traits.get(String.valueOf(idQueue.get(0))),
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
      for (int i = 0; i < combinedRecs.size(); i++) {
        System.out.print(combinedRecs.get(i));
        idQueue.remove(combinedRecs.remove(i));
      }
      System.out.println();
    }
  }

}
