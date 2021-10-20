package command_code;

import KDTree.KDTree;
import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import bloomfilter.XnorSimilarityComparator;
import orm.Interests;
import orm.Negative;
import orm.Positive;
import orm.Skills;
import orm.Trait;
import orm.Database;
import recommender.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecsysLoad implements REPLCallable {

  public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands,
                  HashMap<String, Item> traits, List<Skills> skills, KDTree tree,
                  BloomFilterRecommender<Item> filter) {
    // body
    try {
      Database db = new Database(REPLArguments[1]);
      List<Interests> interests = db.select(Interests.class, new HashMap<String, String>());
      List<Positive> positives = db.select(Positive.class, new HashMap<String, String>());
      List<Negative> negatives = db.select(Negative.class, new HashMap<String, String>());
      skills = db.select(Skills.class, new HashMap<String, String>());
      tree = new KDTree(skills.get(0).getSkills().length);
      filter = new BloomFilterRecommender<Item>(traits, 0.01);
      //filter.setBloomFilterComparator(new XnorSimilarityComparator());
      tree.loadData(skills);
      System.out.println(interests);
      for (int i = 0; i < interests.size(); i++) {
      }
    } catch (Exception e) {
      System.out.println("Error: this request could not be processed");
      System.out.println(e.toString());
    }
    //db.load

  }

}
