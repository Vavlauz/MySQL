package ru.netology.domain.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.Keys.*;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement error = $("[data-test-id=error-notification] .notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        error.shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void repeatRequest(DataHelper.VerificationCode verificationCode) {
        codeField.sendKeys(chord(SHIFT, HOME, BACK_SPACE));
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        error.shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void repeatRequestFinal(DataHelper.VerificationCode verificationCode) {
        codeField.sendKeys(chord(SHIFT, HOME, BACK_SPACE));
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        error.shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Ошибка! Превышено количество попыток ввода кода!"));
    }

}
