package Automation_Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.sl.usermodel.Sheet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test_Prog extends ParameterizationTestData{

	public static void main(String[] args) throws InterruptedException, EncryptedDocumentException, IOException {

		
		ParameterizationTestData p = new ParameterizationTestData();
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.get("http://sanbox.undostres.com.mx");
		Thread.sleep(2000);
		
		
		//Extra Add-on
		
		WebDriverWait w= new WebDriverWait(driver,90);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Actions act = new Actions(driver);
		
		
		//1.Giving  Input for recharge
		
		WebElement operator = driver.findElement(By.xpath("//input[@data-qa='celular-operator']"));
		operator.click();
		
		WebElement telcel = driver.findElement(By.xpath("//div/ul/li[@data-id='1']/a"));
		js.executeScript("arguments[0].click();", telcel);
		
		WebElement mobno = driver.findElement(By.xpath("//input[@suggestmobile='mobile-numbers']"));
		mobno.sendKeys(p.securedinfo(0, 1));
		
		WebElement rechamt = driver.findElement(By.xpath("//input[@suggest='mobile-operator_amount']"));
		rechamt.click();
		
		WebElement rechvalue = driver.findElement(By.xpath("//li[@data-id='1']/a"));
		js.executeScript("arguments[0].click();", rechvalue);
		WebElement submit = driver.findElement(By.xpath("//button[@data-qa='celular-pay']"));
		submit.click();
		
		
		//2.verify Navigation to payments page
		
		String Actual = driver.getCurrentUrl();
		System.out.println(Actual);
		String Required = "https://sanbox.undostres.com.mx/payment.php";
		
		if(Actual.equals(Required))
		{
			System.out.println("Successfully navigated to Payment page");
		}
		else
		{
			System.out.println("please check your details again");
		}
		
		
		//3.card Details for Payment
		
		
		
		WebElement tarjeta = driver.findElement(By.xpath("//p[ text()='Tarjeta']"));
			tarjeta.click();
			WebElement nueva = driver.findElement(By.xpath("//label[@class='radio-custom-label']"));
			w.until(ExpectedConditions.elementToBeClickable(nueva));
			nueva.click();
			WebElement cardno = driver.findElement(By.xpath("//input[@id='cardnumberunique']"));
			cardno.sendKeys(p.securedinfo(0,0));
			WebElement mm = driver.findElement(By.xpath("//input[@data-qa='mes-input']"));
			mm.sendKeys(p.securedinfo(1,0));
			WebElement yy = driver.findElement(By.xpath("//input[@data-qa='expyear-input']"));
			yy.sendKeys(p.securedinfo(2,0));
			WebElement cvv = driver.findElement(By.xpath("//input[@data-qa='cvv-input']"));
			cvv.sendKeys(p.securedinfo(3,0));
			WebElement email = driver.findElement(By.xpath("//div/div/input[@type='email' and @name ='txtEmail']"));
			email.sendKeys(p.securedinfo(4,0));
			WebElement submit1 = driver.findElement(By.xpath("//div[@id='new-card-button-desktop']"));
			submit1.click();
			
			
			//4.Switching to payment popup
			String popup = driver.getWindowHandle();
			driver.switchTo().window(popup);
			
			WebElement username = driver.findElement(By.xpath("//input[@id='usrname']"));
			username.sendKeys(p.securedinfo(5,0));
			WebElement psw = driver.findElement(By.xpath("//input[@id='psw']")) ;
			psw.sendKeys(p.securedinfo(6,0));
			
			//re-CAPTCHA
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='reCAPTCHA'])[1]")));
			WebElement framechkbox = w.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[@id='recaptcha-anchor-label'])[1]")));
			act.moveToElement(framechkbox).click().build().perform();
			
			WebElement rechsubmit = driver.findElement(By.xpath("//button[@id='loginBtn']")); 
			rechsubmit.click();
			
			//5.Confirmation about Recharge
			
			driver.switchTo().alert().accept();
			WebElement dopayment = driver.findElement(By.xpath("//button[@id='paylimitcardsaved']"));
			dopayment.click();
			WebElement status = driver.findElement(By.xpath("//div[@class='visual-message']"));
			String msg = status.getText();
			
			String requiredtext = "¡Falló la recarga!";
			
			if(msg.equals(requiredtext))
			{
				System.out.println("Recharge Failed");
			}
			else
			{
				System.out.println("Recharge Successful");
			}
		
		
  
	  driver.close();
	 
	}

}
