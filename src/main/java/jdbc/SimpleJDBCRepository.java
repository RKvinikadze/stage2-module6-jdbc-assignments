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

    private final CustomDataSource ds = CustomDataSource.getInstance();
    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "insert into myuser(firstName, lastName, age) values (?, ?, ?)";
    private static final String updateUserSQL = "update myuser set firstName = ?, lastName = ?, age = ? where id = ?";
    private static final String deleteUser = "delete from myuser where id = ?";
    private static final String findUserByIdSQL = "select * from myuser where id = ?";
    private static final String findUserByNameSQL = "select * from myuser where firstname = ?";
    private static final String findAllUserSQL = "select * from myuser";

    public Long createUser(User user){
        try{
            connection = ds.getConnection();
            ps = connection.prepareStatement(createUserSQL);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return rs.getLong("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public User findUserById(Long userId) {
        try{
            connection = ds.getConnection();
            ps = connection.prepareStatement(findUserByIdSQL);
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();

            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age"));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public User findUserByName(String userName){
        try {
            connection = ds.getConnection();
            ps = connection.prepareStatement(findUserByNameSQL);
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();

            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("age")
            );
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<User> findAllUser(){
        List<User> users = new ArrayList<>();
        try {
            connection = ds.getConnection();
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(findAllUserSQL);

            while (resultSet.next()){
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age")
                ));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public User updateUser(User user){
        try {
            connection = ds.getConnection();
            ps = connection.prepareStatement(updateUserSQL);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getFirstName());
            ps.setInt(3, user.getAge());
            ps.setLong(4, user.getId());
            if (ps.executeUpdate() != 0){
                return findUserById(user.getId());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteUser(Long userId) {
        try{
            connection = ds.getConnection();
            ps = connection.prepareStatement(deleteUser);
            ps.setLong(1, userId);
            ps.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
