package it.neef.tub.dima.ba.imwa.interfaces.calc;

import java.io.Serializable;

/**
 * Interface for an ArgumentBundle class.
 * This is used to pass a list of arguments index by a key to a {@link ICalculation}.
 * The passed data needs to be {@link Serializable}.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public interface IArgumentsBundle extends Serializable {

    /**
     * Adds an serializable object indexed by a key to the argument bundle.
     *
     * @param key    the key used for indexing.
     * @param object the object to pass to a calcuation.
     * @throws Exception If the argument could not be added.
     * @see Serializable
     */
    void addArgument(String key, Serializable object) throws Exception;

    /**
     * Getter for a passed argument by its key.
     *
     * @param key the key used for adding the argument.
     * @return the stored argument value.
     * @throws Exception If the argument could not be retrieved.
     * @see Serializable
     */
    Serializable getArgument(String key) throws Exception;

    /**
     * Getter for the argument count.
     *
     * @return the amount of arguments in this bundle.
     */
    int argumentCount();
}
