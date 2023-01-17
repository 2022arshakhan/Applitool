

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
//import com.applitools.eyes.selenium.*;

public class AppliToolPoc extends TestBaseSetup {
	String AccPortalUAT = "https://dx20461.etisalat.corp.ae:30006/felix-acc/dashboard/manual-event/manual-pull";
	String AccPortalPROD = "https://au21002.etisalat.corp.ae:8005/felix-acc/sign-in";
	public WebDriver driver;
	//ExcelRead xlread;
	Common common = new Common(driver);
	final static String DELIMITER = ",";
	private static final long TIME_IN_SECONDS = 15;
	int startrow =0;
	int Count = 0;
	String env;
	String BcrmOrderFile = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\BCRM\\ManualPull.xlsx";
	private int eventNumber;
	public Eyes eyes;
	public  ClassicRunner runner; // generic for both runners
	//public  BatchInfo batchInfo;

	@BeforeClass
	public void LaunchDriver() {
		driver = Getdriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		runner = new ClassicRunner();
	}

	@BeforeMethod
	public void GetmethodName(Method method) {
		eyes =new Eyes();
		try {
			System.out.println("\n \n");
			System.out.println("STARTING EXECUTION OF TEST");
			System.out.println("********** " + method.getName() + " **********");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		Configuration conf = eyes.getConfiguration();
		eyes.setApiKey("kpxrEvIyDUSpt6EXb2o101fsJPA166UL97rZyWuF3l5O108I110");
		eyes.setConfiguration(conf);
		
	}


	@Test
	  public void AccPortalEventPush() throws Exception{
			eyes.open(driver);
		//	eyes.open(driver,"AccPortalEventPush", "Login", new RectangleSize(800,600));
		//	eyes.check("Login", Target.window().fully(true));
			
			
			  driver.get(AccPortalUAT);
			  driver.findElement(By.xpath("/html/body/app-root/app-login/main/div/div[2]/form/div[1]/input")).sendKeys("arshakhan");
			  driver.findElement(By.xpath("/html/body/app-root/app-login/main/div/div[2]/form/div[2]/input")).sendKeys("Axwayjan@2023");
			  driver.findElement(By.xpath("/html/body/app-root/app-login/main/div/div[2]/form/button")).click();
			  WebElement DashboardDisplay = driver.findElement(By.xpath("/html/body/app-root/app-dashboard/app-header/nav/button"));
			//*[@id="sidenavAccordion"]/div[1]
			 System.out.println(DashboardDisplay.getText());
			 
			 WebElement menu = driver.findElement(By.xpath("//*[@id='sidenavAccordion']/div[1]"));
			 System.out.println("menu name : "+ menu.getTagName());
			 menu.click();
			
				   
				  }
				   
				   
	
		
	 
		  

	@AfterMethod
	public void ScreenshotonFailure(ITestResult result) throws Exception {
		
		try {
		/*	Common common = new Common(driver);
			common.TakeScreenshotOnFailure(result);
			// setting the count value in first sheet of the file
			ExcelUtil.setExcelFile(BcrmOrderFile,"Prereq");
			System.out.println("Order processed till "+Count+ " line item ");
			ExcelUtil.setCellData(BcrmOrderFile, "Prereq", String.valueOf(Count), 1, 3);
		//	driver.close();
			System.out.println("Closure Time :"+new java.util.Date(System.currentTimeMillis()));
	*/
		//	eyes.close(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@DataProvider(name = "orderNumber")
	public Iterator<Object[]> refDataProvider() {
		try {
			System.out.println("Inside Data provider");
			Scanner scanner = new Scanner(new File(BcrmOrderFile));
			scanner.useDelimiter(DELIMITER);

			return new Iterator<Object[]>() {
				@Override
				public boolean hasNext() {
					System.out.println(scanner.hasNext());
					return scanner.hasNext();
				}

				@Override
				public Object[] next() {
					// apply split here
					return new Object[] { scanner.next().split(";")[1] };
				}
			};
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	

	}
