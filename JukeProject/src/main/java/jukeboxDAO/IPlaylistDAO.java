package jukeboxDAO;
import MODEL.Song;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface IPlaylistDAO extends AutoCloseable{

    String playlistIdCreation(Connection conn) throws SQLException;

    void createUserPlaylist(Connection conn, String username);

    void insertSongsIntoPlayList(String playlistID, Connection conn) throws SQLException;

    List<Song> viewSongsInPlaylist(Connection conn, String username);
}
