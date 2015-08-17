package WalmartLabs.HomeworkAssignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Unit test for Walmart shopping cart.
 */
public class AppTest 
{
	private WebDriver driver;

	@BeforeClass
	public static void setSystemProperty() {
		System.setProperty("webdriver.chrome.driver", "/home/shikha/workspace/HomeworkAssignment/chromedriver");
	}

	@Before
	public void setTestEnvironment() {
		driver = new ChromeDriver();
	}


	@After
	public void closeDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testCheckoutOneItem() throws InterruptedException {	
		//Load
		driver.get("http://www.walmart.com");
		assertTrue("The page title should be Walmart.com. ", driver.getTitle().startsWith("Walmart.com"));

		//Search
		String searchItem = "socks";
		searchQuery(driver, searchItem);
		assertTrue("Assert that page title should start with search query. ",
				waitUntilPageLoad(driver, searchItem + " - Walmart.com"));

		//Select an item
		String selectedItem = null;
		selectedItem = selectItemFromResults(driver);
		assertEquals("Assert that page title should start with selected item. ", 
				selectedItem + " - Walmart.com", driver.getTitle());

		//Add the selected item to shopping cart
		assertTrue(addItemToCart(driver));
		waitUntilPageLoad(driver, selectedItem + " - Walmart.com");

		checkOutItem(driver);
		assertTrue("Assert that checkout page was loaded completely. ", waitUntilPageLoad(driver, "Walmart"));
		assertTrue(waitForSignupModuleToLoad(driver));
		
		//login with valid account and check for item added in cart
		login(driver);
		assertTrue("Assert that user signed in successfully! ", waitUntilPageLoad(driver, "Walmart"));

		assertItemCheckedOut(driver, selectedItem);

		//Let the tester see that signin happened successfully
		Thread.sleep(10_000);		
		//driver.quit();
	}

	private void assertItemCheckedOut(WebDriver driver, String item) {
		//WebElement text = driver.get
		//String str = driver.findElement(By.xpath("//h4/span[@class='brick-heading brick-product-name js-brick-product-name']")).getText();

		WebElement checkoutSummary= driver.findElement(By.xpath("//div[@class='persistent-order-summary checkout-dropdown-panel']"));
		List<WebElement> elts = checkoutSummary.findElements(By.className("brick-product-name"));
		assertEquals(1, elts.size());
		String str = elts.get(0).getText();
		assertTrue(str.contains(item));
	}


	private void login(WebDriver driver) {
		WebElement username = driver.findElement(By.name("login-username"));
		username.sendKeys("taccount@test.com");

		WebElement password = driver.findElement(By.name("login-password"));
		password.sendKeys("test1234");

		WebElement signIn = driver.findElement(By.id("COAC0WelAccntSignInBtn"));
		signIn.submit();
	}


	private void checkOutItem(WebDriver driver) {
		waitUntilCheckOutBtnIsDisplayed(driver);

		WebElement checkOutBtn = driver.findElement(By.id("PACCheckoutBtn"));
		waitUntilJsLoad(driver);
		checkOutBtn.click();
	}


	private boolean addItemToCart(WebDriver driver) {
		WebElement addToCartBtn = driver.findElement(By.id("WMItemAddToCartBtn"));
		addToCartBtn.click();
		return waitUntilJsLoad(driver); 
	}


	private String selectItemFromResults(WebDriver driver) {
		List<WebElement> allLinks = driver.findElements(By.className("js-product-title"));
		String pageTitle = null;

		if (allLinks != null && allLinks.size() > 0) {

			pageTitle = allLinks.get(0).getText();
			allLinks.get(0).click();
			//wait until page is loaded
			waitUntilPageLoad(driver, pageTitle + " - Walmart.com");
		}
		return pageTitle;
	}


	private void searchQuery(WebDriver driver, String searchItem) {
		WebElement query = driver.findElement(By.name("query"));
		// Enter something to search for
		query.sendKeys(searchItem);
		query.submit();
	}


	private Boolean waitUntilPageLoad(WebDriver driver, final String pageTitle) {
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

	private Boolean waitUntilJsLoad(WebDriver driver) {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		return waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		});
	}

	private void waitUntilCheckOutBtnIsDisplayed(WebDriver driver) {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement checkoutBtn = driver.findElement(By.id("PACCheckoutBtn"));
				return (checkoutBtn != null && checkoutBtn.isDisplayed());
			}
		});
	}
	
	private boolean waitForSignupModuleToLoad(WebDriver driver) {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		return waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement usernameInputElt = driver.findElement(By.name("login-username"));
				return (usernameInputElt != null && usernameInputElt.isDisplayed());
			}
		});
	}
}