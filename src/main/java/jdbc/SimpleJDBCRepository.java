package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    public SimpleJDBCRepository(Connection connection){
        this.connection = connection;
    }

    private static final String createUserSQL = "insert into myuser(id, firstName, lastName, age) values (?, ?, ?, ?)";
    private static final String updateUserSQL = "update myuser set firstName = ?, lastName = ?, age = ? where id = ?";
    private static final String deleteUser = "delete from myuser where id = ?";
    private static final String findUserByIdSQL = "select * from myuser where id = ?";
    private static final String findUserByNameSQL = "select * from myuser where firstname = ?";
    private static final String findAllUserSQL = "select * from myuser";

    public Long createUser(User user) throws SQLException {
        ps = connection.prepareStatement(createUserSQL);
        ps.setLong(1, user.getId());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setInt(4, user.getAge());
        ps.execute();

        ps = null;

        return user.getId();
    }

    public User findUserById(Long userId) throws SQLException {
        ps = connection.prepareStatement(findUserByIdSQL);
        ps.setLong(1, userId);
        ResultSet resultSet = ps.executeQuery();

        ps = null;

        return new User(
                resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getInt("age")
        );
    }

    public User findUserByName(String userName) throws SQLException {
        ps = connection.prepareStatement(findUserByNameSQL);
        ps.setString(1, userName);
        ResultSet resultSet = ps.executeQuery();

        ps = null;

        return new User(
                resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getInt("age")
        );
    }

    public List<User> findAllUser() throws SQLException {
        st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(findAllUserSQL);
        st = null;

        List<User> users = new ArrayList<>();
        while (resultSet.next()){
            users.add(new User(
                    resultSet.getLong("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age")
            ));
        }

        return users;
    }

    public User updateUser(User user) throws SQLException {
        ps = connection.prepareStatement(updateUserSQL);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getFirstName());
        ps.setInt(3, user.getAge());
        ps.setLong(4, user.getId());

        ps = null;

        return user;
    }

    private void deleteUser(Long userId) throws SQLException {
        ps = connection.prepareStatement(deleteUser);
        ps.setLong(1, userId);

        ps = null;
    }
}
