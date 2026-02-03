
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class HighScoreManager {

    private HashMap<String, Integer> scoreList = new HashMap<>();

    public boolean addScore(String name, int score) {
        try {
            if (scoreList.get(name) != null) {
                if (score > scoreList.get(name)) {
                    scoreList.put(name, Integer.valueOf(score));
                }
                /*
                    Here, I could have put both conditions in the same if, but then it would have thrown NullPointerExpectation if scoreList.get()
                    actually returned a null
                    as Java would try to box both sides of '>' to compare them to int, and a null on any side would have resulted into a NullPointerExpectation
                 */
            } else {
                scoreList.put(name, Integer.valueOf(score));
            }
        } catch (ClassCastException e) {
            return false;
        }
        return true;
    }

    public int[] getTopScores(int n) {

        List<Integer> scores = new ArrayList<>();
        Collections.sort(scores, Collections.reverseOrder());

        int num = Math.min(n, scoreList.size());
        int[] scoresFinal = new int[num];
        for (int i = 0; i < num; i++) {
            scoresFinal[i] = scores.get(i).intValue(); //autoboxing and unboxing can happen here, so can remove the type conversion!
        }

        return scoresFinal;
    }

    /*
        Returns -1 if the rank was not found or couldn't find player entry
        Else returns the rank
    */

    public int getPlayerRank(String name) {
        int score = 0;
        try {
            score = scoreList.get(name);
        } catch (Exception e) {
            return -1;
        }

        int rank = 1;
        for (int score_i : scoreList.values()) {
            if(score_i > score){
                rank++;
            } 
        }

        return rank;
    }

    public int getScore(String name)
    {
        return scoreList.get(name);
    }

}
