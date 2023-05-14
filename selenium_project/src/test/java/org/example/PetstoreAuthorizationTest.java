package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

/*
Test suite for authorization
https://github.com/Crash-Course-2023-03/team-2/issues/6
 */
@DisplayName("Authorization tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetstoreAuthorizationTest {
    WebDriver driver;
    private AuthorizationPage authorizationPage;
    private String nameInput;
    private String passwordInput;

    @BeforeEach
    public void setUp(){
//        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        final String mainPage = "https://petstore.octoperf.com/actions/Catalog.action";
        driver.get(mainPage);

        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.clickSignIn();
    }

    /*
    [TC-1.9] : Authorization with valid credentials
    https://github.com/Crash-Course-2023-03/team-2/issues/33
     */
    @Test
    @DisplayName("Authorization with valid credentials")
    @Order(1)
    public void validAuthorization(){
        nameInput = "15";
        passwordInput = "test";
        authorizationPage.authorize(nameInput, passwordInput);
        assertTrue(authorizationPage.welcomeText().startsWith("Welcome"));
    }

    /*
    [TC-1.12] : Authorization with invalid password
    https://github.com/Crash-Course-2023-03/team-2/issues/37
     */
    @Test
    @DisplayName("Authorization with invalid password")
    @Order(2)
    public void invalidPassword(){
        nameInput = "15";
        passwordInput = "invalidPassword";
        authorizationPage.authorize(nameInput, passwordInput);
        assertEquals("Invalid username or password. Signon failed.", authorizationPage.invalidMessage());
    }

    /*
    [TC-1.13] : Authorization with invalid username
    https://github.com/Crash-Course-2023-03/team-2/issues/41
     */
    @Test
    @DisplayName("Authorization with invalid name")
    @Order(3)
    public void invalidNameId(){
        nameInput = "666";
        passwordInput = "test";
        authorizationPage.authorize(nameInput, passwordInput);
        assertEquals("Invalid username or password. Signon failed.", authorizationPage.invalidMessage());
    }

    /*
    [TC-1.14] : Authorization with HTML injection
    https://github.com/Crash-Course-2023-03/team-2/issues/48
     */
    @Test
    @DisplayName("Authorization with HTML injection")
    @Order(4)
    public void HtmlInjection(){
        nameInput = "<script>alert('XSS')</script>";
        passwordInput = "test";
        authorizationPage.authorize(nameInput, passwordInput);
        assertEquals("Invalid username or password. Signon failed.", authorizationPage.invalidMessage());
    }

    /*
    [TC-1.15] : Authorization with SQL injection
    https://github.com/Crash-Course-2023-03/team-2/issues/49
     */
    @Test
    @DisplayName("Authorization with SQL injection")
    @Order(5)
    public void SqlInjection(){
        nameInput = "xxx@xxx.xxx";
        passwordInput = "xxx') OR 1 = 1 -- ]";
        authorizationPage.authorize(nameInput, passwordInput);
        assertEquals("Invalid username or password. Signon failed.", authorizationPage.invalidMessage());
    }

    /*
    [TC-1.10] : Logout functionality
    https://github.com/Crash-Course-2023-03/team-2/issues/35
     */
    @Test
    @DisplayName("Logout")
    @Order(6)
    public void logout(){
        nameInput = "15";
        passwordInput = "test";
        authorizationPage.authorize(nameInput, passwordInput);
        authorizationPage.logout();
    }

    /*
    [TC-1.11] : Authorization with empty fields
    https://github.com/Crash-Course-2023-03/team-2/issues/36
     */
    @Test
    @DisplayName("Authorization with empty fields")
    @Order(7)
    public void emptyFields(){
        authorizationPage.clearFields();
        authorizationPage.clickLogin();
        assertEquals(authorizationPage.textEnterFields(), "Please enter your username and password.");
    }

    @AfterEach
    public void exit(){
        driver.quit();
    }
}
