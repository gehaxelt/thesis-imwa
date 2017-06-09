package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;

/**
 * Class for information about Wikipedia contributors.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class Contributor implements IContributor {


    /**
     * The contributor's username.
     */
    private String username = null;

    /**
     * The contributor's ID.
     * Default for not set or invald IDs should be -1.
     */
    private int ID = -1;

    /**
     * The contributor's IP. This is usually set, when an anonymous edit was made.
     */
    private String IP = null;

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String getIP() {
        return this.IP;
    }

    @Override
    public void setIP(String IP) {
        this.IP = IP;
    }

    @Override
    public void postParsing() {
    }

    @Override
    public long getIdentifier() {
        return this.getHash();
    }

    @Override
    public String toString() {
        return this.username + ", " + String.valueOf(this.ID) + ", " + this.IP;
    }

    /**
     * Calculate a hash from the contributor's username.
     *
     * @return the generated hash.
     */
    private long getHash() {
        long hash = 0;
        int count = 1;
        String data;

        if (this.username == null) {
            // If the user is anonymous, only use the IP address.
            // This is not used when the imaginary username is set in setIP()
            data = this.IP;
        } else {
            data = this.username;
        }

        // For every byte in the username multiply it with its index.
        for (byte c : data.getBytes()) {
            hash += c * count;
            count++;
        }

        return hash;
    }
}
