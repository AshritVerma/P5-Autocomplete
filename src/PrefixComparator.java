import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }

    /**
     * Overriden method compare which compares Term v and w objects with myPrefixSize implemented
     * @param v Term object passed with String word and int weight
     * @param w Term object passed with String word and int weight
     * @return difference of vchar and wchar rounded to 1 or -1 by Comparator interface or 0 if both v and w words greater than myPrefixSize
     */
    @Override
    public int compare(Term v, Term w) {
        int vwordSize = v.getWord().length();
        int wwordSize = w.getWord().length();

        int x = Math.min(v.getWord().length(), w.getWord().length());
        x = Math.min(x, myPrefixSize);


        for(int i = 0; i < x; i++)
        {
            char vchar = v.getWord().charAt(i);
            char wchar = w.getWord().charAt(i);

            if(vchar - wchar != 0)
                return vchar - wchar;
        }
        if(vwordSize >= myPrefixSize && wwordSize >= myPrefixSize)
            return 0;

        return vwordSize - wwordSize;
    }
}
