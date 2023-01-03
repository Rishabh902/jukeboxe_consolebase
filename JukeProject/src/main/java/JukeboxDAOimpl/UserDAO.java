package JukeboxDAOimpl;
import jukeboxDAO.IUserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserDAO implements IUserDAO {

    Scanner s=new Scanner(System.in);
    @Override
    public void createUser(Connection conn) {
        PreparedStatement prepared= null;
        try {
            prepared = conn.prepareStatement("Insert into users values(?,?,?,?,?)");

            System.out.println("Enter your username = ");
            String username = s.next();
            prepared.setString(1, username);
            System.out.println("Enter your password = ");
            String password = s.next();
            prepared.setString(2, password);
            System.out.println("Enter your Age = ");
            int age = s.nextInt();
            prepared.setInt(3, age);
            System.out.println("Enter your Gender = ");
            String gender = s.next();
            prepared.setString(4, gender);
            System.out.println("Enter your Mobile Number = ");
            String mobile = s.next();
            prepared.setString(5, mobile);
            if (Pattern.matches("[0-9]{10}", mobile)) {
                int inserted = prepared.executeUpdate();
                prepared.close();
                if (inserted > 0) {
                    System.out.println("User Succesfully. You can login with the same Username and Password");
                } else {
                    System.out.println("Cannot add user");
                }
            } else {
                System.out.println("\n\t\t Invalid mobile number");
                createUser(conn);
            }
        }
        catch (SQLException e) {
            System.out.println("Duplicate Entry");
            createUser(conn);
        }
    }

    @Override
    public boolean checkUsername(Connection conn, String username, String password) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("Select * from users");
        boolean validation = false;
        while (rs.next()) {
            if (rs.getString(1).equals(username)) {
                if (rs.getString(2).equals(password)) {
                    validation = true;
                    break;
                }
            } else {
                validation = false;
            }
        }

        return validation;

    }

    @Override
    public void close() throws Exception {

    }

}
