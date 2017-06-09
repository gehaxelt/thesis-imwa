package it.neef.tub.dima.ba.imwa.examples.adlerscm.TextOnlyWithPageviews;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Differ for Adler's TextOnlyWithPageviews contribution measure, by implementing the txt(v_i, v_j) text contribution.
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 03.01.17.
 */
public class TextOnlyDifferWithPageviews implements IDiffer<IDifference<Double>, IRevision> {

    /**
     * Implements the txt(v_i, v_j) text contribution quality measure, also txt(r_i) = txt(v_i, v_i)
     *
     * @param current the current revision from which to start (usually r_i)
     * @param next    the next revision to diff against (usually r_(i+1)) or null if it is not needed.
     * @return The amount of text (in words) which was introduced in current and is still present in next.
     */
    @Override
    public IDifference<Double> calculateDiff(IRevision current, IRevision next) {
        // Revisions should look like this: parent -> current -> [...] -> next
        IDifference<Double> ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();
        ArrayList<String> newInCurrent = new ArrayList<>();
        double pageviews = next.getPage().getPageview() != null ? next.getPage().getPageview().getRequestCount() : 0;

        //If there is no parent, all new words in current are inserted by current.
        Collections.addAll(newInCurrent, current.getText().split("\\s+"));

        if (current.getParentRevision() != null) {
            //If there is a parent, remove parent's words from current to get new words by current:
            for (String word : current.getParentRevision().getText().split("\\s+")) {
                while (newInCurrent.remove(word)) {
                }
            }
        }
        //Now we have all new inserted words in current in newInCurrent.

        if (current == next) {
            // Case 1: txt(r_i) = txt(v_i, v_i) - We only care about new words in current.
            ld.setDifference(new Double(newInCurrent.size() * pageviews));
            return ld;
        }

        // Now we need to find words which are still present in next!
        //TODO: Implement better authorship tracking
        String nextText = next.getText();
        Double stillPresentCount = 0.0;
        for (String word : newInCurrent) {
            // Check if the word exists in the next revision. If so, increase the counter.
            if (nextText.contains(word)) {
                stillPresentCount++;
            }
        }
        ld.setDifference(new Double(stillPresentCount * pageviews));

        return ld;
    }
}
