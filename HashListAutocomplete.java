import java.util.*;

public class HashListAutocomplete implements Autocompletor{
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    /**
     * Given arrays of words and weights, calls method initialize with terms and weights given
     *
     *
     *
     * @param terms
     *            - A list of words to form terms from
     * @param weights
     *            - A corresponding list of weights, such that terms[i] has
     *            weight[i].
     * @return a HashListAutocomplete
     * @throws NullPointerException if either argument passed in is null
     */
    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }

    /**
     * if the prefix is in the map, get the corresponding value
     * and return a sublist of the first k entries
     * @param prefix
     * @param k
     * @return sublist of the first k entries
     */

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (k == 0)
            return new ArrayList<Term>();
        if (!myMap.containsKey(prefix)) {
            return new ArrayList<Term>();
        }
        if (prefix.length() > MAX_PREFIX)
                prefix = prefix.substring(0,MAX_PREFIX);
        List<Term> all = myMap.get(prefix);
        List<Term> list = all.subList(0, Math.min(k, all.size()));
        return list;

    }

    /**
     * create map with keys(prefix) and value(list of terms) + sort every value in the map
     * @param terms is array of Strings for words in each Term
     * @param weights is corresponding weight for word in terms
     */
    @Override
    public void initialize(String[] terms, double[] weights) {
        if (myMap != null)
            myMap.clear();
        else {
            myMap = new HashMap<>();
        }
        for (int i=0; i < terms.length; i++){
            String x = terms[i];
            for (int k=0; k <= Math.min(MAX_PREFIX,x.length()); k++) {
                String pref = x.substring(0,k);
                Term t = new Term(terms[i], weights[i]);
                myMap.putIfAbsent(pref, new ArrayList<Term>());
                myMap.get(pref).add(t);
                //loop through from k= 0 to max prefix (inner loop), max-prefix could also be bigger than
                //loop through each term (outer loop)
                //for each i, find the substring prefix
                //call my map.put if absent if prefix hasnt seen
                //my map.get prefix if its there
                //values = list of terms
                //for each terms, add for each one of its prefixes until max prefix length

            }}
        for (String y: myMap.keySet()) {
            Collections.sort(myMap.get(y), Comparator.comparing(Term::getWeight).reversed());

        }

    }


    /**
     *size in bytes for every Term object and every String/key in the map
     * @return size in bytes
     */
    @Override
    public int sizeInBytes() {
        if (mySize == 0) {

            for(String t : myMap.keySet()) {
                mySize += BYTES_PER_CHAR * t.length();
                for (Term d: myMap.get(t)) {
                    mySize += BYTES_PER_DOUBLE;
                    mySize += BYTES_PER_CHAR * d.getWord().length();


                }


            }


        }
        return mySize;

    }
}
