

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class TestBaseSetup {

	private WebDriver driver;
	static String IEdriverpath = "C:\\Eclipse\\iedriver_win64";
	static String ChromeDriverpath = "C:\\Eclipse\\chromedriver_win32";
	
	public WebDriver Getdriver(){
		
		return driver;
	}
	
	private void InitBrowser(String BrowserName){
		switch(BrowserName) {
			
		case "chrome":
		driver = InitChromeDriver();
		break;
			
		case "ie":
		driver = InitIEDriver();
		break;
		
		default:
			System.out.println("Going to launch default browser FF");
			driver = InitFFDriver();

		}
	}
	
	private static WebDriver InitChromeDriver(){
		
		System.out.println("Going to launch Chrome browser");
		
		DesiredCapabilities dc = new DesiredCapabilities();
		
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		
		System.setProperty("webdriver.chrome.driver","C:\\Eclipse\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver(dc);
		
		driver.manage().window().maximize();
		
		return driver;
		
	}
	
	private static WebDriver InitIEDriver(){
		System.out.println("Going to launch IE browser");
		
		DesiredCapabilities Caps = DesiredCapabilities.internetExplorer();
		
		Caps.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
		
		Caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		
		Caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		
//		Caps.setCapability("requireWindowFocus", true);

//        Caps.setCapability("enablePersistentHover", false);
		
		System.setProperty("webdriver.ie.driver","C:\\eclipse\\iedriver_win64\\IEDriverServer.exe");
		
		WebDriver driver = new InternetExplorerDriver(Caps);
		
		driver.manage().window().maximize();
		
		return driver;
		
		
	}
	
	private static WebDriver InitFFDriver(){
		System.out.println("Going to launch Firefox browser");
		
		WebDriver driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
		
		return driver;
		
	}
	
	@Parameters({"BrowserName"})
	@BeforeClass	
	public void initialiseBrowser(String BrowserName){
		
		try{
			InitBrowser(BrowserName);
		}
		catch(Exception e){
			System.out.println("unable to launch driver");
			e.printStackTrace();
		}
		
	}
	
//	@AfterClass
//	public void tearDown(){
//		driver.quit();
//	}
	
	
	
	
}
