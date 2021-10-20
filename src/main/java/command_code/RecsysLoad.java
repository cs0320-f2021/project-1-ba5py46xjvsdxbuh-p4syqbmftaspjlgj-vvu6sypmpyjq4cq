package command_code;

import KDTree.KDTree;
import bloomfilter.BloomFilterRecommender;
import orm.Interests;
import orm.Negative;
import orm.Positive;
import orm.Skills;
import orm.Database;
import recommender.Item;

import java.util.*;

public class RecsysLoad implements REPLCallable {

  public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands,
                  HashMap<String, Item> traits, List<Skills> skills, KDTree tree,
                  BloomFilterRecommender<Item> filter) {
    // body
    try {
      Database db = new Database("data/integration.sqlite3");
      List<Interests> interests = db.select(Interests.class, new HashMap<String, String>());
      //System.out.println("hello");
      List<Positive> positives = db.select(Positive.class, new HashMap<String, String>());
      List<Negative> negatives = db.select(Negative.class, new HashMap<String, String>());
      //skills = db.select(Skills.class, new HashMap<String, String>());
      //tree = new KDTree(skills.get(0).getSkills().length);
      filter = new BloomFilterRecommender<Item>(traits, 0.01);
      //filter.setBloomFilterComparator(new XnorSimilarityComparator());
      tree.loadData(skills);
      //System.out.println(interests);
      HashSet<Integer> idSet = new HashSet<Integer>();
      for (int i = 0; i < interests.size(); i++) {
          idSet.add(interests.get(i).getID());
      }
      System.out.println("Loaded Recommender with "+idSet.size()+" students");
    } catch (Exception e) {
      System.out.println("Error: this request could not be processed");
      System.out.println(e.toString());
    }
    //db.load

  }

}
