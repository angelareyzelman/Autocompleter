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
     * Compares two terms by letters
     * @param v Term one
     * @param w Term two
     * @return a negative value, positive value, or 0 depending on how the two terms compare
     */
    @Override
    public int compare(Term v, Term w) {
        //find first three
        //can't use arraylist?
        int min = 0;
        int minsize = 0;
        String vpref;
        String wpref;
        String vp = v.getWord();
        String wp = w.getWord();
        min = Math.min(vp.length(), wp.length());
        minsize = Math.min(min, myPrefixSize);
        //min between vp and wp
        //take the min of the shorter one and prefix size (minsize)
        //times to iterate through , for loop using minsize
        //as long as they both have length that are greater than prefix size, equal
        //return legnth(v) - length(w)

        if (vp.length() < myPrefixSize){
            vpref = vp;}
        else {
            vpref = vp.substring(0, myPrefixSize);}

        if (wp.length() < myPrefixSize){
            wpref = wp;}
        else {
            wpref = wp.substring(0, myPrefixSize);
        }
        for (int k=0; k < minsize; k += 1) {
            int x = vpref.charAt(k) - wpref.charAt(k);
            if (x != 0)
                return x;
        }

        if (vp.length() >= myPrefixSize && wp.length() >= myPrefixSize)
            return 0;

        return vp.length() - wp.length();
    }
}
