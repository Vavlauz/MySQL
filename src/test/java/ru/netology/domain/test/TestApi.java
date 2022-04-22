package ru.netology.domain.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.domain.data.DataHelper.getAuthInfo;
import static ru.netology.domain.data.DataHelper.getInvalidAuthInfo;


public class TestApi {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }


    @AfterAll
    static void cleanUp() {
        DataHelper.clearDB();
    }

    @Test
    void successEnter() {
        var validUser = getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(validUser);
        var verifyInfo = DataHelper.getVerificationCodeFor();
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        dashboardPage.getHeading();
    }


    @Test
    void wrongEnterPasswordThreeTimes() {
        var invalidUser = getInvalidAuthInfo();
        var loginPage = new LoginPage();
        loginPage.login(invalidUser);
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.blockNotification();
    }

}
