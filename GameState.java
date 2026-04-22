import java.io.*;
import java.util.*;

public class GameState implements Serializable {
    Map<String, Integer> scores = new HashMap<>();
    List<String> answers = Arrays.asList("apple", "banana", "orange", "grape");
    Set<String> found = new HashSet<>();

    public GameState() {
        scores.put("A", 0);
        scores.put("B", 0);
    }

    public synchronized String checkAnswer(String team, String guess) {
        guess = guess.toLowerCase();
        if (answers.contains(guess) && !found.contains(guess)) {
            found.add(guess);
            scores.put(team, scores.get(team) + 10);
            return "CORRECT:" + guess + ":Team " + team +
                    " Score=" + scores.get(team);
        } else {
            return "WRONG:Team " + team;
        }
    }
}