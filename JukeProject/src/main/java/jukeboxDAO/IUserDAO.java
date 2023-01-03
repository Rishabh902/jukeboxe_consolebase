package jukeboxDAO;
import java.sql.Connection;
import java.sql.SQLException;
public interface IUserDAO extends AutoCloseable {
    void createUser(Connection conn);
    boolean checkUsername(Connection conn, String username, String password) throws SQLException;

}
