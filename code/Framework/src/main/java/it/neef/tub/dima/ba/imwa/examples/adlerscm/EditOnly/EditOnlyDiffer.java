package it.neef.tub.dima.ba.imwa.examples.adlerscm.EditOnly;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Differ for Adler's EditOnly contribution measure, by implementing the d(v_i, v_j).
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 11.01.17.
 */
public class EditOnlyDiffer implements IDiffer<IDifference<Double>, IRevision> {

    /**
     * Calculates the edit distance using the number of moved/inserted/deleted words.
     *
     * @param M Number of moved words.
     * @param I Number of inserted words.
     * @param D Number of deleted words.
     * @return the edit distance.
     */
    private double calcEditDiff(double M, double I, double D) {
        return Math.max(I, D) - 0.5 * Math.min(I, D) + M;
    }


    /**
     * Calculates the edit distance d(v_i, v_j), also d(r) = d(v_i-1, v_i)
     *
     * @param current the current revision from which to start (usually r_i)
     * @param next    the next revision to diff against (usually r_(i+1)) or null if it is not needed.
     * @return the edit distance between v_i and v_j
     */
    @Override
    public IDifference<Double> calculateDiff(IRevision current, IRevision next) {
        // Initialize everything
        double M = 0, I = 0, D = 0;
        IDifference ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();


        if (current == null) {
            //current is empty, so everything is a big insert:
            I = next.getText().split("\\s+").length;
            ld.setDifference(this.calcEditDiff(M, I, D));
            return ld;
        }

        ArrayList<String> nextWords = new ArrayList<>();
        ArrayList<String> currentWords = new ArrayList<>();
        ArrayList<String> doneWords = new ArrayList<>();
        Collections.addAll(nextWords, next.getText().split("\\s+"));
        Collections.addAll(currentWords, current.getText().split("\\s+"));

        String marker = "    ";
        doneWords.add(marker); //Three spaces is our marker for already matched words. Text is split at whitespaces, so those shouldn't be in the word-lists.
        for (String word : currentWords) {
            if (doneWords.contains(word)) {
                //Match every word only once
                continue;
            }

            if (nextWords.contains(word)) {
                //The next revision still contains the word
                int nextWord_idx, currentWord_idx;
                while (nextWords.indexOf(word) != -1) {
                    //There's still a word in the text
                    nextWord_idx = nextWords.indexOf(word);
                    currentWord_idx = currentWords.indexOf(word);

                    if (nextWord_idx == currentWord_idx) {
                        //Nothing moved. Mark c_idx as visited and continue
                        nextWords.set(nextWord_idx, marker);
                        continue;
                    }

                    //The word has moved.
                    nextWords.set(nextWord_idx, marker);
                    double nextWords_length = nextWords.size();
                    double currentWords_length = currentWords.size();
                    M += ((nextWord_idx * currentWord_idx) / Math.max(nextWords_length, currentWords_length));
                }
            } else {
                // Word is not present anymore. It was removed.
                D++;
            }
            doneWords.add(word);
        }

        //Reverse-Count for inserted words
        for (String word : nextWords) {
            if (!doneWords.contains(word)) {
                I++;
            }
        }

        ld.setDifference(this.calcEditDiff(M, I, D));


        return ld;
    }
}
