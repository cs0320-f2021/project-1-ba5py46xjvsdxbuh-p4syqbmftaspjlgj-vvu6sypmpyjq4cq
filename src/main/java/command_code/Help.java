package command_code;

import KDTree.KDTree;
import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import orm.Skills;
import orm.Trait;
import recommender.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Help implements REPLCallable {

  public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands,
                  HashMap<String, Item> traits, List<Skills> skills, KDTree tree,
                  BloomFilterRecommender<Item> filter) {
    System.out.println("Available Commands:");
    Set<String> keys = REPLCommands.keySet();
    for (String k : keys) {
      System.out.println("\t" + k);
    }
  }

}
