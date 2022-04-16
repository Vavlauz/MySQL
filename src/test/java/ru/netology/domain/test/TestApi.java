package ru.netology.domain.test;

import com.codeborne.selenide.Configuration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class TestApi {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @SneakyThrows
    @Test
    void successEnter() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        dashboardPage.getHeading();
    }

    @SneakyThrows
    @Test
    void wrongEnterPasswordThreeTimes() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(new DataHelper.VerificationCode(verifyInfo));
        open("http://localhost:9999");
        var loginPage2 = new LoginPage();
        var authInfo2 = DataHelper.getAuthInfo();
        var verificationPage2 = loginPage2.validLogin(authInfo2);
        var verifyInfo2 = DataHelper.getInvalidVerificationCode();
        verificationPage2.repeatRequest(new DataHelper.VerificationCode(verifyInfo2));
        open("http://localhost:9999");
        var loginPage3 = new LoginPage();
        var authInfo3 = DataHelper.getAuthInfo();
        var verificationPage3 = loginPage3.validLogin(authInfo3);
        var verifyInfo3 = DataHelper.getInvalidVerificationCode();
        verificationPage3.repeatRequest(new DataHelper.VerificationCode(verifyInfo3));
        open("http://localhost:9999");
        var loginPage4 = new LoginPage();
        var authInfo4 = DataHelper.getAuthInfo();
        var verificationPage4 = loginPage4.validLogin(authInfo4);
        var verifyInfo4 = DataHelper.getInvalidVerificationCode();
        verificationPage4.repeatRequestFinal(new DataHelper.VerificationCode(verifyInfo4));
    }

}
