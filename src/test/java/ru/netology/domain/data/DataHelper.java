package ru.netology.domain.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    public static class CardInfo {
        private String cardNumber;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
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

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559000000000001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559000000000002");
    }

    public static void clearDB() throws SQLException {
        String deleteFromCardTransactions = "DELETE FROM card_transactions";
        String deleteFromAuthCodes = "DELETE FROM auth_codes";
        String deleteFromCards = "DELETE FROM cards";
        String deleteFromUsers = "DELETE FROM users";

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
        PreparedStatement cardTransactions = connection.prepareStatement(deleteFromCardTransactions);
        PreparedStatement authCodes = connection.prepareStatement(deleteFromAuthCodes);
        PreparedStatement cards = connection.prepareStatement(deleteFromCards);
        PreparedStatement users = connection.prepareStatement(deleteFromUsers);
        cardTransactions.executeUpdate();
        authCodes.executeUpdate();
        cards.executeUpdate();
        users.executeUpdate();
    }

    public static String getInvalidVerificationCode() {
        Faker faker = new Faker(Locale.ENGLISH);
        return String.valueOf(faker.number());
    }

}
