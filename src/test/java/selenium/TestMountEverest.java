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
		driver.get("https://www.google.com/");

		// accept cookies
		driver.findElement(By.xpath("//*[@id=\"L2AGLb\"]")).click();

		// write inside searchbox
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input")).sendKeys("mount everest height");

		// click search
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[3]/center/input[1]")).click();


		// read
		WebElement heightBox = driver.findElement(By.xpath("/html/body/div[7]/div/div[10]/div[1]/div[2]/div[2]/div/div/div[1]/div/div[1]/div/div[1]/div[1]/div/div[2]/div/div/div/div[1]"));
		String output = heightBox.getText();

		assertEquals("8.849 m", output);

		driver.close();
	}
}
