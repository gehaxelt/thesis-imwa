package it.neef.tub.dima.ba.imwa.interfaces.parser;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.impl.configuration.Configuration;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;

/**
 * Interface for a wikipedia dump parser.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDumpParser<P extends IPage> extends Serializable {

    /**
     * This method should read and parse the data from the configured
     * wikipediaDumpPath file.
     *
     * @throws Exception If an error occurs while parsing the dump.
     * @see Configuration
     */
    void parseDumpData() throws Exception;

    /**
     * Getter for the Page DataSet after successful parsing.
     *
     * @return the DataSet containing the parsed page objects.
     */
    DataSet<P> getPages();
}
