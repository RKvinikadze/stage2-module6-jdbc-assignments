package jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CustomConnector {
    public Connection getConnection(String url) throws IOException, SQLException {
        CustomDataSource customDataSource = CustomDataSource.getInstance();
        return customDataSource.getConnection(url);
    }

    public Connection getConnection(String url, String user, String password) throws IOException, SQLException {
        CustomDataSource customDataSource = CustomDataSource.getInstance();
        return customDataSource.getConnection(url, user, password);
    }
}
