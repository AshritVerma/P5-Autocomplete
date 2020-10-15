import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    //key in map = prefix/substring
    //value for each key = weighted-sorted list of Term objects

    @Override
    public List<Term> topMatches(String prefix, int k) {
        return null;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {

    }

    @Override
    public int sizeInBytes() {
        return 0;
    }
}
