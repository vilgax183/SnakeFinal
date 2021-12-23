import java.util.*;

public class Board {

    // Mapping of snakes which move player positions down from the key to the value
    private Map<Integer, Integer> snakes;
    // Mapping of ladders which move player positions up from the key to the value
    private Map<Integer, Integer> ladders;
    // List of keys for snake and ladder maps
    private List<Integer> endPoints;

    public Board(int snakes, int ladders) {
        endPoints = new ArrayList<>();
        this.snakes = generateSnakes(snakes);
        this.ladders = generateLadders(ladders);
    }

    public Map<Integer, Integer> getSnakes() {
        return snakes;
    }

    public Map<Integer, Integer> getLadders() {
        return ladders;
    }

    public List<Integer> getEndPoints() {
        return endPoints;
    }

    private Map<Integer, Integer> generateSnakes(int snakes) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(40,20);
        map.put(99,19);
        map.put(97,44);
        map.put(95,66);
        map.put(72,12);
        map.put(67,34);
        map.put(56,16);
        map.put(43,3);
        map.put(27,7);
        map.put(93,73);
        return map;
    }

    private Map<Integer, Integer> generateLadders(int ladders) {

        Map<Integer, Integer> map = new HashMap<>();
        map.put(41,60);
        map.put(61,81);
        map.put(4,37);
        map.put(10,90);
        map.put(6,55);
        map.put(8,68);
        map.put(58,98);
        map.put(65,96);
        map.put(74,94);
        map.put(89,92);

        return map;
    }

}
