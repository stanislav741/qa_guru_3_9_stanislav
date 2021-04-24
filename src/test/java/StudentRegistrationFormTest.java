import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormTest {

    @BeforeAll
    static void setup() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.startMaximized = true;
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach(){
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());

        closeWebDriver();
    }

    @Test
    @DisplayName("Positive registration form fill test")
    void positiveFillFormTest() {

        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = fakeValuesService.bothify("????##@gmail.com");
        String userNumber = fakeValuesService.regexify("[0-9]{10}");
        String address = faker.address().fullAddress();

        String // varFirstName ="Stanislav",
               // varLastName = "Dmitruk",
               // varUserEmail = "stanislavtest@gmail.com",
                varGender = "Other",
               // varUserNumber = "1234567890",
                varBirthDay = "20",
                varBirthMonth = "February",
                varBirthYear = "1991",
                varSubject1Prefix = "e",
                varSubject1Full = "Chemistry",
                varSubject2Prefix = "p",
                varSubject2Full = "Physics",
                varSubject3Prefix = "m",
                varSubject3Full = "Maths",
                varHobby1 = "Sports",
                varHobby2 = "Music",
                varPicture = "Screenshot 2020-11-20 at 02.47.53.png",
               // varCurrentAddress = "Tester Strasse 123, Berlin",
                varState = "Haryana",
                varCity = "Panipat";

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill in the registration form", () -> {
                    $("#firstName").val(firstName);
                    $("#lastName").val(lastName);
                    $("#userEmail").val(userEmail);
                    $("#genterWrapper").$(byText(varGender)).click();
                    $("#userNumber").val(userNumber);

//  "Set Date"
                    $("#dateOfBirthInput").click();
                    $(".react-datepicker__month-select").selectOption(varBirthMonth);
                    $(".react-datepicker__year-select").selectOption(varBirthYear);
                    $(".react-datepicker__day--0" + varBirthDay).click();

//  "Set Subject"
                    $("#subjectsInput").val(varSubject1Prefix);
                    $(".subjects-auto-complete__menu-list").$(byText(varSubject1Full)).click();
                    $("#subjectsInput").val(varSubject2Prefix);
                    $(".subjects-auto-complete__menu-list").$(byText(varSubject2Full)).click();
                    $("#subjectsInput").val(varSubject3Prefix);
                    $(".subjects-auto-complete__menu-list").$(byText(varSubject3Full)).click();

                    $("#hobbiesWrapper").$(byText(varHobby1)).click();
                    $("#hobbiesWrapper").$(byText(varHobby2)).click();

                    $("#uploadPicture").uploadFile(new File("src/test/resources/" + varPicture));
                    $("#currentAddress").val(address);

                    $x("//div[@id='state']").scrollTo().click();
                    $("#stateCity-wrapper").$(byText(varState)).click();
                    $x("//div[@id='city']").click();
                    $("#stateCity-wrapper").$(byText(varCity)).click();

                    $("#submit").click();
        });

        step("Assert the data", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
            $x("//td[text()='Student Email']").parent().shouldHave(text(userEmail));
            $x("//td[text()='Gender']").parent().shouldHave(text(varGender));
            $x("//td[text()='Mobile']").parent().shouldHave(text(userNumber));
            $x("//td[text()='Date of Birth']").parent().shouldHave(text(varBirthDay + " " + varBirthMonth + "," + varBirthYear));
            $x("//td[text()='Subjects']").parent().shouldHave(text(varSubject1Full + "," + " " + varSubject2Full + "," + " " + varSubject3Full));
            $x("//td[text()='Hobbies']").parent().shouldHave(text(varHobby1 + "," + " " + varHobby2));
            $x("//td[text()='Picture']").parent().shouldHave(text(varPicture));
            $x("//td[text()='Address']").parent().shouldHave(text(address));
            $x("//td[text()='State and City']").parent().shouldHave(text(varState + " " + varCity));
        });

    }

    @Test
    @DisplayName("Negative registration form fill test with short phone number")
    void negativeFillFormTest() {

        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = fakeValuesService.bothify("????##@gmail.com");
        String userNumber = fakeValuesService.regexify("[0-9]{9}");
        String address = faker.address().fullAddress();

        String // varFirstName ="Stanislav",
                // varLastName = "Dmitruk",
                // varUserEmail = "stanislavtest@gmail.com",
                varGender = "Other",
                // varUserNumber = "1234567890",
                varBirthDay = "20",
                varBirthMonth = "February",
                varBirthYear = "1991",
                varSubject1Prefix = "e",
                varSubject1Full = "Chemistry",
                varSubject2Prefix = "p",
                varSubject2Full = "Physics",
                varSubject3Prefix = "m",
                varSubject3Full = "Maths",
                varHobby1 = "Sports",
                varHobby2 = "Music",
                varPicture = "Screenshot 2020-11-20 at 02.47.53.png",
                // varCurrentAddress = "Tester Strasse 123, Berlin",
                varState = "Haryana",
                varCity = "Panipat";

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill in the registration form", () -> {
            $("#firstName").val(firstName);
            $("#lastName").val(lastName);
            $("#userEmail").val(userEmail);
            $("#genterWrapper").$(byText(varGender)).click();
            $("#userNumber").val(userNumber);

//  "Set Date"
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption(varBirthMonth);
            $(".react-datepicker__year-select").selectOption(varBirthYear);
            $(".react-datepicker__day--0" + varBirthDay).click();

//  "Set Subject"
            $("#subjectsInput").val(varSubject1Prefix);
            $(".subjects-auto-complete__menu-list").$(byText(varSubject1Full)).click();
            $("#subjectsInput").val(varSubject2Prefix);
            $(".subjects-auto-complete__menu-list").$(byText(varSubject2Full)).click();
            $("#subjectsInput").val(varSubject3Prefix);
            $(".subjects-auto-complete__menu-list").$(byText(varSubject3Full)).click();

            $("#hobbiesWrapper").$(byText(varHobby1)).click();
            $("#hobbiesWrapper").$(byText(varHobby2)).click();

            $("#uploadPicture").uploadFile(new File("src/test/resources/" + varPicture));
            $("#currentAddress").val(address);

            $x("//div[@id='state']").scrollTo().click();
            $("#stateCity-wrapper").$(byText(varState)).click();
            $x("//div[@id='city']").click();
            $("#stateCity-wrapper").$(byText(varCity)).click();

            $("#submit").click();
        });

        step("Assert the data", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
            $x("//td[text()='Student Email']").parent().shouldHave(text(userEmail));
            $x("//td[text()='Gender']").parent().shouldHave(text(varGender));
            $x("//td[text()='Mobile']").parent().shouldHave(text(userNumber));
            $x("//td[text()='Date of Birth']").parent().shouldHave(text(varBirthDay + " " + varBirthMonth + "," + varBirthYear));
            $x("//td[text()='Subjects']").parent().shouldHave(text(varSubject1Full + "," + " " + varSubject2Full + "," + " " + varSubject3Full));
            $x("//td[text()='Hobbies']").parent().shouldHave(text(varHobby1 + "," + " " + varHobby2));
            $x("//td[text()='Picture']").parent().shouldHave(text(varPicture));
            $x("//td[text()='Address']").parent().shouldHave(text(address));
            $x("//td[text()='State and City']").parent().shouldHave(text(varState + " " + varCity));
        });

    }
}
