package it.neef.tub.dima.ba.imwa.interfaces.data;

/**
 * Interface for forcing {@link IDataType} objects to be uniquely identifiable.
 * <p>
 * Created by gehaxelt on 23.10.16.
 */
public interface IIdentifiable {
    /**
     * This should return an unique identifier, so that the same
     * object can be recognized in a DataSet.
     * Care has to be taken for objects that are based on, for example, strings, so that
     * their identifier is based on their content.
     *
     * @return an unique identifier.
     */
    long getIdentifier();
}
