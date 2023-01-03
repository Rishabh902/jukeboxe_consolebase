package jukeboxDAO;
import MODEL.Song;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface ISongDAO extends AutoCloseable {
    List<Song> storeSongs(Connection conn) throws  SQLException;

    void displaySongs(List<Song> songList) throws  SQLException;

    void searchSongs(List<Song> songList);

    List<Song> searchByArtist(List<Song> songList, String artistName);

    List<Song> searchByGenre(List<Song> songList, String genreName);

    List<Song> searchByAlbum(List<Song> songList, String albumName);
}
