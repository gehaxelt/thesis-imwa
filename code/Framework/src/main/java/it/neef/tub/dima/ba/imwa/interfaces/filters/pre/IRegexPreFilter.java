package it.neef.tub.dima.ba.imwa.interfaces.filters.pre;

/**
 * Interface for a PreFilter using regular expressions.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public interface IRegexPreFilter extends IPreFilter {

    /**
     * Getter for the PreFilter's regular expression string.
     *
     * @return the current regular expression.
     */
    String getRegex();

    /**
     * Setter for the PreFilter's regular expression.
     *
     * @param regex the new regular expression string.
     */
    void setRegex(String regex);
}
