package jukeboxDAO;
import MODEL.Podcast;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface IPodcastDAO extends AutoCloseable {
    List<Podcast> storePodcastInArray(Connection conn) throws SQLException;

    void displayPodcast(Connection conn) throws SQLException;

    void searchPodcast(Connection conn) throws SQLException;
}
