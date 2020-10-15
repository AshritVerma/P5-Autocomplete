import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private Term[] myTerms;
    private int mySize;
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;

    public HashListAutocomplete(String[] terms, double[] weights){
        if(terms == null || weights == null){
            throw new NullPointerException("One or more arguments null");
        }
        initialize(terms, weights);
    }

    /**
     * Returns a sublist of the first k entries with the corresponding value
     * if the prefix is in the map
     * @param prefix substring of Term's String component that is used as keys
     * @param k number of entries being returned
     * @return
     */
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if(myMap.containsKey(prefix.substring(MAX_PREFIX)))
        {
            List<Term> all = myMap.get(prefix.substring(MAX_PREFIX));
            return all.subList(0, Math.min(k, all.size()));
        }
        return null;
    }

    /**
     * Initializes instance variables by call from constructor
     * @param terms is array of Strings for words in each Term
     * @param weights is corresponding weight for word in terms
     */
    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();

        myTerms = new Term[terms.length];

        for (int i = 0; i < terms.length; i++)
            myTerms[i] = new Term(terms[i], weights[i]);

        //Arrays.sort(myTerms);

        for(Term element : myTerms)
        {
            if(!myMap.containsKey(element.getWord().substring(MAX_PREFIX)))
                myMap.put(element.getWord().substring(MAX_PREFIX), new ArrayList<Term>());
            myMap.get(element).add(element);
        }

        for(String element : myMap.keySet())
        {
            List<Term> value = myMap.get(element);
            Collections.sort(value,Comparator.comparing(Term::getWeight).reversed());
        }
    }

    /**
     * Returns bytes taken up by each string which contributes
     * byter_per_char * length and each double stored, thus
     * accounting for every Term and for all String keys in the map
     * @return size in bytes of memory by accounting for every Term object and
     * calculating length of every key in the map
     */
    @Override
    public int sizeInBytes() {
        if(mySize == 0)
        {
            for(String element : myMap.keySet())
                mySize += BYTES_PER_DOUBLE + BYTES_PER_CHAR*element.length();
        }
        return mySize;
    }
}
