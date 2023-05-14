package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthorizationPage {
    protected WebDriver driver;

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()='Sign In']")
    private WebElement auth;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement nameId;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@name='signon']")
    private WebElement entrance;

    @FindBy(xpath = "//div[@id='WelcomeContent']")
    private WebElement welcomeContent;

    @FindBy(xpath = "//*[@id='Content']/ul/li")
    private WebElement invalidMessage;

    @FindBy(xpath = "//a[text()='Sign Out']")
    private WebElement logout;

    @FindBy(xpath = "//div[@id='Catalog']/form/p[1]")
    private WebElement promptEnterFields;

    public void clickSignIn(){
        auth.click();
    }

    public void clearFields(){
        nameId.click();
        nameId.clear();
        password.click();
        password.clear();
    }
    public void inputNameId(String nameInput){
        nameId.sendKeys(nameInput);
    }

    public void inputPassword(String passwordInput){
        password.sendKeys(passwordInput);
    }

    public void clickLogin(){
        entrance.click();
    }

    public void authorize(String nameInput, String passwordInput){
        clearFields();
        inputNameId(nameInput);
        inputPassword(passwordInput);
        clickLogin();
    }

    public String welcomeText(){
        return welcomeContent.getText();
    }

    public String invalidMessage(){
        return invalidMessage.getText();
    }

    public String textEnterFields(){
        return promptEnterFields.getText();
    }

    public void logout(){
        logout.click();
    }
}
