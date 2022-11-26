package smart_buy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SmartBuy {

	public WebDriver driver;
	public double discount_percent_Dell = 0.0646;

	SoftAssert softassertprocess = new SoftAssert();

	@BeforeTest
	public void this_is_before_test() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://smartbuy-me.com/smartbuystore/");

		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();
	}

	@Test
	public void check_discount_on_percent_Dell() {
		String price_now = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String[] split_price_now = price_now.split("JOD");
		String new_price_now = split_price_now[0].trim();
		String replace_new_price_now = new_price_now.replace(",", ".");

		double double_price_now = Double.parseDouble(replace_new_price_now);

		String price_before_discount = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();
		String[] split_price_before_discount = price_before_discount.split("JOD");
		String new_price_before_discount = split_price_before_discount[0].trim();

		String replace_price_before_discount = new_price_before_discount.replace(",", ".");

		double double_price_before_discount = Double.parseDouble(replace_price_before_discount);

		double calc = double_price_before_discount * discount_percent_Dell;
		double last_calc = double_price_before_discount - calc;

		BigDecimal bd = new BigDecimal(last_calc).setScale(3, RoundingMode.HALF_DOWN);

		double updated = bd.doubleValue();


		softassertprocess.assertEquals(double_price_now, updated);
		
		softassertprocess.assertAll();
	}

}
