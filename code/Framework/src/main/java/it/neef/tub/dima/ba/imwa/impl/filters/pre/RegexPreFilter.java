package it.neef.tub.dima.ba.imwa.impl.filters.pre;

import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IRegexPreFilter;

import java.util.regex.Pattern;

/**
 * PreFilter class using regular expressions.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class RegexPreFilter implements IRegexPreFilter {

    /**
     * Regular expression used for filtering.
     */
    private String regex;

    @Override
    public boolean filter(String pageXML) {
        // Return true if the regular expression matches the XML string.
        return Pattern.matches(regex, pageXML);
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }
}
