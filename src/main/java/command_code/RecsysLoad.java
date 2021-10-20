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
      tree.loadData(skills);
      System.out.println(interests);
      for (int i = 0; i < interests.size(); i++) {
        if (traits.containsKey(interests.get(i).getID())) {
          ((Trait) traits.get(interests.get(i).getID())).addToData(interests.get(i).getInterest());
        } else {
          Trait insert = new Trait(interests.get(i));
          traits.put(insert.getId(), insert);
        }
      }
      for (int i = 0; i < positives.size(); i++) {
        ((Trait) traits.get(positives.get(i).getID())).addToData(positives.get(i).getTrait());
      }
      for (int i = 0; i < negatives.size(); i++) {
        ((Trait) traits.get(negatives.get(i).getID())).addToData(negatives.get(i).getTrait());
      }
      filter = new BloomFilterRecommender<Item>(traits, 0.01);
      BloomFilter<String> f = new BloomFilter<>(0.01, traits.size());
      filter.setBloomFilterComparator(new XnorSimilarityComparator(f));
    } catch (Exception e) {
      System.out.println("Error: this request could not be processed");
      System.out.println(e.toString());
    }
    //db.load

  }

}
