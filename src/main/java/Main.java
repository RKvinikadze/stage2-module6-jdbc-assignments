import jdbc.SimpleJDBCRepository;
import jdbc.User;

public class Main {

    public static void main(String[] args) {
        SimpleJDBCRepository simpleJDBCRepository = new SimpleJDBCRepository();

//        Long userID = simpleJDBCRepository.createUser(new User(
//           120L,
//           "Vinme",
//           "Rame",
//           12
//        ));

        User user = simpleJDBCRepository.updateUser(new User(100L,
           "Vinmeeee",
           "Rame",
           12
        ));

        System.out.println(user.getFirstName());
    }
}
