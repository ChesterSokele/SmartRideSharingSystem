package util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public DatabaseConnection() {
    }

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/rss", "root", "bigbro");
            System.out.println("✅ Connected to the database.");


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Database connection failed.");
        }

    }
}
