package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMountEverest {

	@Test
	public void testHeight() {
		System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://en.wikipedia.org/");

		// write inside search box
		driver.findElement(By.xpath("//*[@id=\"searchInput\"]")).sendKeys("everest");

		// click search
		driver.findElement(By.xpath("//*[@id=\"searchButton\"]")).click();


		// read
		WebElement heightBox = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[5]/div[1]/table[1]/tbody/tr[4]/td"));
		String heightBoxText = heightBox.getText();
		String output = heightBoxText.substring(0, 10);

		driver.close();

		assertEquals("8,848.86 m", output);
	}
}
