package it.neef.tub.dima.ba.imwa.interfaces.data;

/**
 * Interface for a contributor class.
 * This is another {@link IDataType} which is created during the parsing of the wikipedia dump.
 * It contains information about the author of a revision, such as the username or IP and her ID.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IContributor extends IDataType {

    /**
     * Getter for the contributor's username.
     * This might be null for anonymous edits. In that case {@link #getID()} should be used.
     * The username is only captured, when the user is logged in.
     *
     * @return the contributor's username.
     */
    String getUsername();

    /**
     * Setter for the contributor's username.
     *
     * @param username the new username.
     */
    void setUsername(String username);

    /**
     * Getter for the contributor's ID.
     * All accounts have an unique ID.
     *
     * @return the contributor's ID.
     */
    int getID();

    /**
     * Setter for the contributor's ID.
     * The value should be positive.
     *
     * @param ID the new ID.
     */
    void setID(int ID);

    /**
     * Getter for the contributor's IP address.
     * This is used as an identification factor when the contributor
     * is not logged in when editing the wikipedia.
     *
     * @return the contributor's IP.
     */
    String getIP();

    /**
     * Setter for the contributor's IP.
     *
     * @param IP the new IP.
     */
    void setIP(String IP);

    /**
     * This method is called after the parsing of the object has finished.
     */
    void postParsing();
}
