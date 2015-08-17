package WalmartLabs.HomeworkAssignment;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Hello world!
 *
 */
public class ShoppingCataloge 
{
	public static void main( String[] args ) throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "/home/shikha/workspace/HomeworkAssignment/chromedriver");
		WebDriver driver = new ChromeDriver();

		driver.get("http://www.walmart.com");

		WebElement query = driver.findElement(By.name("query"));
		// Enter something to search for
		String searchItem = "socks";
		query.sendKeys(searchItem);
		query.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());
		waitUntilPageLoad(driver, searchItem + " - Walmart.com");

		List<WebElement> allLinks = driver.findElements(By.className("js-product-title"));
		String pageTitle = null;
		if (allLinks != null && allLinks.size() > 0) {
			pageTitle = allLinks.get(0).getText();
			System.out.println("Inside if: " + pageTitle);
			allLinks.get(0).click();

			//wait untill page is loaded
			waitUntilPageLoad(driver, pageTitle + " - Walmart.com");
		}

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());

		WebElement addToCartBtn = driver.findElement(By.id("WMItemAddToCartBtn"));
		addToCartBtn.click();
		waitUntilJsLoad(driver);

		System.out.println("Cart " + driver.getTitle());
		waitUntilPageLoad(driver, pageTitle + " - Walmart.com");

		waitUntilCheckOutBtnIsDisplayed(driver);

		WebElement checkOutBtn = driver.findElement(By.id("PACCheckoutBtn"));
		waitUntilJsLoad(driver);
		checkOutBtn.click();
	
		waitUntilPageLoad(driver, "Walmart");
		
		Thread.sleep(10000);
		//login with valid account and check for item added in cart
		
		System.out.println("Checkout Page Title : " + driver.getTitle());
		WebElement username = driver.findElement(By.name("login-username"));
		username.sendKeys("taccount@test.com");

		WebElement password = driver.findElement(By.name("login-password"));
		password.sendKeys("test1234");

		WebElement signIn = driver.findElement(By.id("COAC0WelAccntSignInBtn"));
		signIn.submit();
		
		//Close the browser
		Thread.sleep(10_000);
		System.out.println("SignIn Page Title: " + driver.getTitle());
		
		driver.quit();
	}


	private static Boolean waitUntilPageLoad(WebDriver driver, final String pageTitle) {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		Boolean pageLoadedState = waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.getTitle().equalsIgnoreCase(pageTitle);
			}
		});
		waitUntilJsLoad(driver);
		return pageLoadedState;
	}

	private static Boolean waitUntilJsLoad(WebDriver driver) {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		return waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		});
	}

	private static void waitUntilCheckOutBtnIsDisplayed(WebDriver driver) {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement checkoutBtn = driver.findElement(By.id("PACCheckoutBtn"));
				return (checkoutBtn != null && checkoutBtn.isDisplayed());
			}
		});
	}
}
