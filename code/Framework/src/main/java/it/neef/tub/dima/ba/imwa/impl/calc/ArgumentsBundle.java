package it.neef.tub.dima.ba.imwa.impl.calc;

import it.neef.tub.dima.ba.imwa.interfaces.calc.IArgumentsBundle;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class for passing arguments to Calculation functions.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public class ArgumentsBundle implements IArgumentsBundle {

    /**
     * The bundle where all serializable objects are stored.
     */
    private HashMap<String, Serializable> arguments;

    /**
     * Constructor to initialize the HashMap.
     */
    public ArgumentsBundle() {
        this.arguments = new HashMap<>();
    }

    @Override
    public void addArgument(String key, Serializable object) throws Exception {
        this.arguments.put(key, object);
    }

    @Override
    public Serializable getArgument(String key) throws Exception {
        return this.arguments.get(key);
    }

    @Override
    public int argumentCount() {
        return this.arguments.size();
    }
}
