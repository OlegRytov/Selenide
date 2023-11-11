import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {


    @BeforeEach
    void setUP() {
        Selenide.open("http://localhost:9999/");
    }


    @Test
    void shouldFilledInCorrectly() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Алексей");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTheLastNameIsEnteredSeparatedByASpace() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Петров Водкин Алексей");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTheLastNameIsEnteredWithAHyphen() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Петров-Водкин Алексей");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTheSurnameMustBeInLatine() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Petrov");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldPhoneIncorrect() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Петров Алексей");
        form.$("[data-test-id=phone] input").setValue("+7927000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotAgreeToDataProcessing() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Петров Алексей");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        // form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
    @Test
    void shouldIfTheLastNameFieldIsMotFilledIn() {
        SelenideElement form = $(".form");
        // form.$("[data-test-id=name] input").setValue("Петров Алексей");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldIfThePhoneFieldIsNotFilledIn() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Петров Алексей");
        // form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}


