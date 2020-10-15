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

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if(myMap.containsKey(prefix.substring(MAX_PREFIX)))
        {
            List<Term> all = myMap.get(prefix.substring(MAX_PREFIX));
            return all.subList(0, Math.min(k, all.size()));
        }
        return null;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myTerms = new Term[terms.length];

        for (int i = 0; i < terms.length; i++)
            myTerms[i] = new Term(terms[i], weights[i]);

        //Arrays.sort(myTerms);

        for(Term term : myTerms)
        {
            myMap.putIfAbsent(term.getWord().substring(MAX_PREFIX), new ArrayList<Term>());
            myMap.get(term).add(term);
        }

        for(String element : myMap.keySet())
        {
            List<Term> value = myMap.get(element);
            Collections.sort(value,Comparator.comparing(Term::getWeight).reversed());
        }
    }

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
