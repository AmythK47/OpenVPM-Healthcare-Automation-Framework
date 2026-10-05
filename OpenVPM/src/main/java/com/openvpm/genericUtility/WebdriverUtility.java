package com.openvpm.genericUtility;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebdriverUtility {
	
	public WebDriver driver;

	// 1. BROWSER ACTION
	// 1.1 Launch Browser
	public WebDriver launchBrowser(String browser) {
		// create the instance of browser according to properties file
		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();

			final Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false); // Disables save password prompt
			prefs.put("profile.password_manager_enabled", false); // Disables password manager
			prefs.put("profile.password_manager_leak_detection", false); // Disables "Change password" popup

			options.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox"))
			driver = new FirefoxDriver();
		else if (browser.equalsIgnoreCase("edge"))
			driver = new EdgeDriver();
		else if (browser.equalsIgnoreCase("safari"))
			driver = new SafariDriver();
		else
			driver = new ChromeDriver();

		return driver;
	}

	// 1.2 Maximize Browser
	public void maximizeBrowser(WebDriver driver) {
		driver.manage().window().maximize();
	}

	// 1.3 Minimize Browser
	public void minimizeBrowser(WebDriver driver) {
		driver.manage().window().minimize();
	}

	// 1.4 FullScreen
	public void fullScreen(WebDriver driver) {
		driver.manage().window().fullscreen();
	}
	
	public void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}

	// 2. SYNCHRONIZATION
	// 2.1 implicitWait
	public void implicitWait(WebDriver driver, int timeInSec) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSec));
	}

	// 2.2 ExplicitWait
	// 2.2.1 Wait till element is visible
	public void waitTillVisibilityOfElement(WebDriver driver, WebElement ele, int timeInSec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	// 2.2.2 wait till element to be clickable
	public void waitTillElementToBeClickable(WebDriver driver, WebElement ele, int timeInSec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}

	// 2.2.3 wait till page title contains
	public void waitTillPageTitleContains(WebDriver driver, String title, int timeInSec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
		wait.until(ExpectedConditions.titleContains(title));
	}

	public void waitTillElementContainsText(int timeInSec, WebElement ele, String text)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
		wait.until(ExpectedConditions.textToBePresentInElement(ele, text));
	}
	
	
	
	
	// 3. HANDLING FRAME AND WINDOWS
	// 3.1 Handle Windows
	// 3.1.1 HandleWindows By Title
	public void switchToWindow(WebDriver driver, String title) {
		Set<String> windowId = driver.getWindowHandles();
		for (String id : windowId) {
			driver.switchTo().window(id);
			if (driver.getTitle().contains(title))
				break;
		}
	}

	// 3.1.2 HandleWindows By Current URL
	public void switchToWindow(String currentURL, WebDriver driver) {
		Set<String> windowId = driver.getWindowHandles();
		for (String id : windowId) {
			driver.switchTo().window(id);
			if (driver.getCurrentUrl().contains(currentURL))
				break;
		}
	}

	// 3.2 Handle Frames
	// 3.2.1 Handle frame by index
	public void switchToFrame(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}

	// 3.2.2 Handle Frames by id or Name
	public void switchToFrame(WebDriver driver, String idOrName) {
		driver.switchTo().frame(idOrName);
	}

	// 3.2.3 Handle Frames by element
	public void switchToFrame(WebDriver driver, WebElement ele) {
		driver.switchTo().frame(ele);
	}

	// 3.2.4 Switch back to parent
	public void switchToParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}

	// 3.2.4 Switch back to Default Frame
	public void switchToDefaultFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	// 4. SELECT CLASS AND ACTIONS CLASS
	// 4.1.1 Select Class by Text
	public void selectDropdownByText(WebElement ele, String visibleText) {
		Select dd = new Select(ele);
		dd.selectByContainsVisibleText(visibleText);
	}

	// 4.1.2 Select Class by value
	public void selectDropdownByvalue(WebElement ele, String valueText) {
		Select dd = new Select(ele);
		dd.selectByValue(valueText);
	}

	// 4.1.3 Select Class by Index
	public void selectDropdownByIndex(WebElement ele, int index) {
		Select dd = new Select(ele);
		dd.selectByIndex(index);
	}

	// 4.1.4 deselect DD by Text
	public void deselectDropdownByText(WebElement ele, String visibleText) {
		Select dd = new Select(ele);
		dd.deSelectByContainsVisibleText(visibleText);
	}

	// 4.1.5 deselect Class by value
	public void deselectDropdownByvalue(WebElement ele, String valueText) {
		Select dd = new Select(ele);
		dd.deselectByValue(valueText);
	}

	// 4.1.6 deselect Class by Index
	public void deselectDropdownByIndex(WebElement ele, int index) {
		Select dd = new Select(ele);
		dd.deselectByIndex(index);
	}

	// 4.1.6 deselect All
	public void deselectAll(WebElement ele) {
		Select dd = new Select(ele);
		dd.deselectAll();
	}

	// 4.1.7 getOptions
	public List<WebElement> getAllOptionsFromDropdown(WebElement ele) {
		Select dd = new Select(ele);
		return dd.getOptions();
	}

	// 4.1.7 getWrapped Element
	public WebElement getWrappedElementFromDropdown(WebElement ele) {
		Select dd = new Select(ele);
		return dd.getWrappedElement();
	}

	// 4.1. 8 check if its ListBox
	public boolean isDropdownMultiSelect(WebElement ele) {
		Select dd = new Select(ele);
		return dd.isMultiple();
	}

	// 4.2 Actions class
	// 4.2.1 Move To Element
	public void moveToElement(WebDriver driver, WebElement ele) {
		Actions a = new Actions(driver);
		a.moveToElement(ele).build().perform();
	}

	// 4.2.2 Drag and Drop
	public void dragAndDrop(WebDriver driver, WebElement source, WebElement target) {
		Actions a = new Actions(driver);
		a.dragAndDrop(source, target).build().perform();
	}

	// 4.2.3 Drag and Drop By
	public void dragAndDropBySlider(WebDriver driver, WebElement ele, int x, int y) {
		Actions a = new Actions(driver);
		a.dragAndDropBy(ele, x, y).build().perform();
	}

	// 4.2.4 Drag and Drop CLick and Hold
	public void dragAndDropByClicknHold(WebDriver driver, WebElement ele, WebElement target) {
		Actions a = new Actions(driver);
		a.clickAndHold(ele).pause(Duration.ofSeconds(1)).moveToElement(target).pause(Duration.ofSeconds(1)).release()
				.build().perform();
	}

	// 4.2.5 Scroll to Element
	public void scrollToElement(WebDriver driver, WebElement ele) {
		Actions a = new Actions(driver);
		a.scrollToElement(ele).build().perform();
	}

	// 4.2.6 Right click
	public void rightClick(WebDriver driver, WebElement ele) {
		Actions a = new Actions(driver);
		a.contextClick(ele).build().perform();
	}

	// 4.2.7 double click
	public void doubleClick(WebDriver driver, WebElement ele) {
		Actions a = new Actions(driver);
		a.doubleClick(ele).build().perform();
	}
	
	// 4.2.8 Move To Element and click
	public void moveToElementAndClick(WebDriver driver, WebElement ele) {
		Actions a = new Actions(driver);
		a.moveToElement(ele).pause(Duration.ofSeconds(2)).click().build().perform();
	}

	// 5. JAVASCRIPTEXECUTOR
	// 5.1 click
	public void jsClick(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", ele);
	}

	// 5.2 pass Value
	public void jsSetValue(WebDriver driver, WebElement ele, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + value + "';", ele);
	}

	// 5.3 Scroll into View
	public void jsScrollIntoView(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	// 5.4 Scroll by
	public void jsScrollBy(WebDriver driver, int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + x + ", " + y + ");");
	}

	// 5.5 Scroll To
	public void jsScrollTo(WebDriver driver, int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(" + x + ", " + y + ");");
	}

	// 6. TAKESSCREENSHOT
	// 6.1 WebElement Screenshot
	public void takeElementScreenshot(WebElement ele, String eleName) throws Exception {
		File src = ele.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshots/" + eleName + ".png");
		FileHandler.copy(src, dest);

	}

	// 6.2 WebPage Screenshot
	public void takePageScreenshot(WebDriver driver, String pageName) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshots/" + pageName + ".png");
		FileHandler.copy(src, dest);
	}

	// 7. POPUPS
	// 7.1 Switch to Alert and accept
	public void acceptAlert(WebDriver driver) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
	}

	// 7.2 Switch to alert and dismiss
	public void dismissAlert(WebDriver driver) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().dismiss();
	}

	// 7.3 Get Text from alert
	public String getTextAlert(WebDriver driver) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.alertIsPresent());
		 return driver.switchTo().alert().getText();	
	}

	// 7.4 Switch to alert and sendKeys
	public void sendKeysToAlert(WebDriver driver, String text) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().sendKeys(text);
	}

	// 7.5 File Upload
	public void uploadFile(WebElement ele, String filePath) {
		ele.sendKeys(filePath);
	}
	

}
