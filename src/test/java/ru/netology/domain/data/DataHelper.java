package ru.netology.domain.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {

    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        String password = faker.internet().password();
        return new AuthInfo(login, password);
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var runner = new QueryRunner();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass")) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return new VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void clearDB() {
        var deleteCode = "DELETE FROM auth_codes";
        var deleteTransaction = "DELETE FROM card_transactions";
        var deleteCard = "DELETE FROM cards";
        var deleteUser = "DELETE FROM users";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass")
        ) {
            runner.update(conn, deleteCode);
            runner.update(conn, deleteTransaction);
            runner.update(conn, deleteCard);
            runner.update(conn, deleteUser);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

}
