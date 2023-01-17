

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;
import org.testng.ITestResult;
import org.sikuli.basics.Settings;

public class Common {
	
	protected WebDriver driver;
	protected WebDriverWait Wait;
	public static String PrimarywindowHandle;
	
	public Common(WebDriver driver){
		this.driver = driver;
		 Settings.MinSimilarity = 0.95;
	}
	Actions builder;
	
	public void ClickElementandCloseAdditionalWindow(WebElement Ele1, By Locator1){
		//close the newly opened window if its not required and then move the focus to the main window
		String PrimaryWindow = driver.getWindowHandle();
		System.out.println("Current Window Handle ---- " +PrimaryWindow);
		Ele1.click();
		Waitforpageload(Locator1);
		Set<String> AllWindowHandles = driver.getWindowHandles();
		System.out.println("List of windows present ---- " +AllWindowHandles);
		for(String Windows : AllWindowHandles){
			driver.switchTo().window(Windows);
			if(Windows!=PrimaryWindow){
				System.out.println("Closing the window ---- "+driver.getTitle());
				driver.close();
			}
		}
		driver.switchTo().window(PrimaryWindow);
		System.out.println("Switched to primary window ---- "+driver.getTitle());
		
	}
	
	public void Waitforpageload(By Locator){
		//Wait until the last element is loaded in that page.
		Wait = new WebDriverWait(driver,20);
//		Wait.until(ExpectedConditions.presenceOfElementLocated(Locator1));
		Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Locator));
		
	}
	
	public void WaitforPresenceofElement(By Locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
	}
	
	public void WaitforElementSelectable(By Locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.elementToBeClickable(Locator));
	}
	
	public void WaitforelementSelectionState(By locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.elementSelectionStateToBe(locator, true));
		
	}
	
	public void Waitforvisibilityofelement(By Locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
	}
		
	public void ElementtoBeVisible(By Locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
	}
	
	public void WaitforAlert(By Locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitforFrameandSwitchtoit(By Locator){
		Wait = new WebDriverWait(driver,20);
		Wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Locator));
	}
	
	public void PollnwaitforElementVisible( By by) throws InterruptedException{
		
		for(int i=0;i<20;i++){
			Thread.sleep(1000);
		int size = driver.findElements(by).size();
		if(size>0){
			System.out.println("Element present and size is "+size);
			break;
		}
		}
	}
	
	public void JSClick(WebElement Clickelement) throws InterruptedException{
		try{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", Clickelement);}
		catch(Exception e){
			e.printStackTrace();
		}
		Thread.sleep(1000);
	}
	
	public void JSClick(By Clickelement) throws InterruptedException{
		WebElement clickelement = driver.findElement(Clickelement);
		try{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickelement);}
		catch(Exception e){
			e.printStackTrace();
		}
		Thread.sleep(1000);
	}
	
	public void DynamicClick(WebElement Element){
		builder = new Actions(driver);

		builder.moveToElement(Element).perform();

		builder.click();
		
		builder.perform();
		
	}
	
	public void DoubleClick(WebElement Element){
		builder = new Actions(driver);
		
		builder.doubleClick(Element).perform();
	}
	
	public void MouseHover(WebElement Element){
//		Actions builder = new Actions(driver);
		builder = new Actions(driver);
		
		builder.moveToElement(Element).perform();
	}
	
	public void MouseHoverbyOffsetnClick(int x, int y){
		builder.moveByOffset(x, y).perform();
		builder.click().perform();
	}
	
	public void JSSendkeys(WebElement ele, String text){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].value='"+text+"';", ele);
	}
	
	public void JSSendkeys(By ele, String text){
		WebElement element = driver.findElement(ele);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].value='"+text+"';", element);
	}
	
	public String JSGetText(){
		String text=null;
		try {
			System.out.println("Inside JS");
			//WebElement element = driver.findElement(elt);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			 text = (String) jse.executeScript("return document.getElementsByClassName('text-area')[0].value");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	public void SwitchWindowBasedonTitle(String WindowTitle){
		
		Set<String> windowhandles = driver.getWindowHandles();
		System.out.println("Window handles "+windowhandles);
		System.out.println("Number of Windows " +windowhandles.size());
		if(windowhandles.size()>1){
			driver.switchTo().window(WindowTitle);
			System.out.println("Switched to Window ---- "+WindowTitle);
		}
		else{
			System.out.println("There's only one window so not switching");
		}
	}
	
	public boolean isAlertPresent() throws InterruptedException{
		try{
			Thread.sleep(4000);
			driver.switchTo().alert();
			return true;
		}
		catch(NoAlertPresentException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void SwitchtoNewWindowAcceptICA(WebElement ele, String DesktopLocation) throws InterruptedException, FindFailed{
		PrimarywindowHandle = driver.getWindowHandle();
		System.out.println("Primary window handle is ---"+PrimarywindowHandle);
		DynamicClick(ele);
		Thread.sleep(10000);
		Set<String> WindowHandles = driver.getWindowHandles();
		System.out.println("All window handles "+WindowHandles);
		for(String handle: WindowHandles){
			
			if(!handle.equals(PrimarywindowHandle)){
				System.out.println("switching to window with handle "+handle);
				driver.switchTo().window(handle);
				Thread.sleep(5000);
				SikuliClickCancelAlert(DesktopLocation);
				System.out.println("current window title is "+driver.getTitle());
				driver.manage().window().maximize();
				break;
			}
		}
		
	}
	
	public void SikuliClickCancelAlert(String Env, String DesktopLocation) throws InterruptedException, FindFailed{
		Thread.sleep(8000);
		Screen s = new Screen();
		System.out.println(ImagePath.getBundlePath()); // print current bundlePath
		ImagePath.setBundlePath(DesktopLocation); // set custom bundlePath

		System.out.println(ImagePath.getBundlePath());
		Thread.sleep(3000);
		for(int i=0;i<3;i++){
			Thread.sleep(1000);
			if(s.exists("Cancel.PNG")!=null){
				s.click("Cancel.PNG");
				Thread.sleep(1000);
			}
		}
		if(Env.equalsIgnoreCase("PD"))
		s.click("Ok.PNG");
		Thread.sleep(2000);
	}
	
	public void SikuliClickCancelAlert(String DesktopLocation) throws InterruptedException, FindFailed{
		Thread.sleep(8000);
		Screen s = new Screen();
		System.out.println(ImagePath.getBundlePath()); // print current bundlePath
		ImagePath.setBundlePath(DesktopLocation); // set custom bundlePath

		System.out.println(ImagePath.getBundlePath());
		Thread.sleep(3000);
		for(int i=0;i<3;i++){
			Thread.sleep(1000);
			if(s.exists("Cancel.PNG")!=null){
				s.click("Cancel.PNG");
				Thread.sleep(1000);
			}
		}

//		s.click("Ok.PNG");

	}
	
	public void SikuliAttachnChoosFilebtn(String DesktopLocation) throws InterruptedException, FindFailed{
		Thread.sleep(2000);
		Screen s = new Screen();
		System.out.println(ImagePath.getBundlePath()); // print current bundlePath
		ImagePath.setBundlePath(DesktopLocation); // set custom bundlePath

		System.out.println(ImagePath.getBundlePath());
		s.click("TestLabelNotes");
		Thread.sleep(1000);
		s.click("attachimg.PNG");
		for(int i=0;i<3;i++){
			Thread.sleep(1000);
			if(s.exists("ChooseFile.PNG")!=null){
				s.click("ChooseFile.PNG");
				Thread.sleep(1000);
			}
		}

	}
	
	public void SikuliClickOkAlert(String PathinDesktop) throws InterruptedException, FindFailed{
		Thread.sleep(5000);
		Screen s = new Screen();
		System.out.println(ImagePath.getBundlePath()); // print current bundlePath
		ImagePath.setBundlePath(PathinDesktop); // set custom bundlePath

		System.out.println(ImagePath.getBundlePath());
		for(int i=0;i<3;i++){
		Thread.sleep(1000);
		if(s.exists("Ok.PNG")!=null){
		s.click("Ok.PNG");
		System.out.println("Sikuli Ok Clicked");
		}
		}
		Thread.sleep(1000);
	}
	
	public void SikuliCloseLayout(String PathinDesktop) throws InterruptedException, FindFailed{
		Thread.sleep(2000);
		
		Screen s = new Screen();
		System.out.println(ImagePath.getBundlePath()); // print current bundlePath
		ImagePath.setBundlePath(PathinDesktop); // set custom bundlePath

		System.out.println(ImagePath.getBundlePath());
		for(int i=0;i<3;i++){
			Thread.sleep(1000);
			if(s.exists("Layout.PNG")!=null){
				s.click("LayoutClose.png");
				System.out.println("Sikuli Close Layout Clicked");
			}
		}
		
	}
	
	public void EnterCredentialsSikuli(String PathinDesktop) throws InterruptedException, FindFailed{
		Screen s = new Screen();
		System.out.println(ImagePath.getBundlePath()); 
		ImagePath.setBundlePath(PathinDesktop);
		for(int i=0;i<3;i++){
			Thread.sleep(1000);
			if(s.exists("Signin.PNG")!=null){
			s.type("kisreddy");
			s.type(Key.TAB);
			s.type("P@$$word20202");
			Thread.sleep(500);
			s.click("Signin.PNG");
			System.out.println("Sikuli signin Clicked");
			}
			}
	}
	
	public void BCRMPageLoadSikuliWait(String PathinDesktop) throws InterruptedException, FindFailed{
		Screen S = new Screen();
		ImagePath.setBundlePath(PathinDesktop);
		System.out.println("-----First Wait starting-----");
		for(int i=0;i<100;i++){
			System.out.println("-----Wait starting-----");
			if(S.exists("BCRMPageLoad.PNG")!=null && S.exists("BCRMPageLoad2.PNG")!=null){
				System.out.println("Waiting for Page load Sikuli");
				Thread.sleep(6000);
				
			}else{
				Thread.sleep(3000);
				System.out.println("******ELSE******");
				if(S.exists("ServerErrorOk.PNG")!=null){
					S.click("ServerErrorOk.PNG");
					System.out.println("Clicked Server Error Ok sikuli");
					Thread.sleep(3000);
					for(int j=0;j<100;j++){
						if(S.exists("BCRMPageLoad.PNG")!=null && S.exists("BCRMPageLoad2.PNG")!=null){
							System.out.println("Waiting for Page load Sikuli");
							Thread.sleep(6000);
						}
						else{
							break;
						}
					}
				}
				break;
			}
		}
	}
	
	public void AlertAccept() throws InterruptedException{
		Thread.sleep(3000);
		if(isAlertPresent()==true){
		
		driver.switchTo().alert().accept();
		
		System.out.println("Browser Alert accepted");
		}
		else{
			System.out.println("Alert not present");
		}
	}
	
	public int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public void SwitchtoNewWindow(WebElement ele) throws InterruptedException{
		PrimarywindowHandle = driver.getWindowHandle();
		System.out.println("Primary window handle is ---"+PrimarywindowHandle);
		DynamicClick(ele);
		Set<String> WindowHandles = driver.getWindowHandles();
		System.out.println("All window handles "+WindowHandles);
		for(String handle: WindowHandles){
			
			if(!handle.equals(PrimarywindowHandle)){
				System.out.println("switching to window with handle "+handle);
				driver.switchTo().window(handle);
				Thread.sleep(5000);
				System.out.println("current window title is "+driver.getTitle());
				driver.manage().window().maximize();
				break;
			}
		}
		
	}
	
	public void closeCurrentWindowSwitchtoOldWindow() throws InterruptedException{
		Set<String> WindowHandles = driver.getWindowHandles();
		System.out.println("All window handles "+WindowHandles);
		driver.close();
		Set<String> WindowHandles2 = driver.getWindowHandles();
		System.out.println("Closed current window and switching to old window "+WindowHandles2);
		for(String win: WindowHandles2){
			driver.switchTo().window(win);
		}
	}
	
	public void getallframes(WebElement elementtoclick){
		
		int frames = driver.findElements(By.tagName("iframe")).size();
		System.out.println("total number of iframes in current page ---" +frames);
		if(frames>1){
			for(int i=0;i<frames;i++){
				driver.switchTo().frame(i);
				
			}
		}
		
	}
	
	public void getPrimarywindowHandle(){
		PrimarywindowHandle = driver.getWindowHandle();
		System.out.println("primary window handle "+PrimarywindowHandle);
	}
	
//	public void SwitchtoNewWindow(){
//		Set<String> handles = driver.getWindowHandles();
//		for(String handle: handles){
//			driver.switchTo().window(handle);
//			if(!handle.equals(PrimarywindowHandle)){
//				System.out.println("Switching to new window");
//				break;
//			}
//		}
//	}
	
	public void MethodName(ITestResult result) {
		  System.out.println("Starting execution of testcase ******" + result.getMethod().getMethodName()+"******");
		}
	
	public void SwitchtoPrimaryWindow(){
		driver.switchTo().window(PrimarywindowHandle);
	}
	
	public void ScrollintoView(WebElement Element) {
		try{
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("arguments[0].scrollIntoView(true);",Element);}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void scrolltoBottomOfPage() throws InterruptedException{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
	}
	
	public void scrollverticallydown() throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,750)", "");

		Thread.sleep(1000);
	}
	
	public void ScrolldownbyVerifyingScrollisatBottom(){
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		for(int i=0;i<20;i++){
//			Long value = (Long) executor.executeScript("return window.pageYOffset;");
		executor.executeScript("window.scrollBy(0,750)", "");
		}
		
		
	}
	
	public void scrollverticallyUp() throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-850)", "");
		Thread.sleep(1000);
	}
		
	public void SelecttextBased(WebElement selectelement, String text){
		Select select = new Select(selectelement);
		select.selectByVisibleText(text);
		System.out.println("Selected value: "+text);
	}
	
	public void SelectValueBased(WebElement selectelement, String value){
		Select select = new Select(selectelement);
		select.selectByValue(value);
		System.out.println("Selected value: "+value);
	}
	
	public void SelectFirstOption(WebElement selectelement){
		Select select = new Select(selectelement);
		select.selectByIndex(1);
	}
	
	public void SelectByIndex(WebElement SelectElement,int index){
		Select select = new Select(SelectElement);
		select.selectByIndex(index);
		System.out.println("value that is selected "+select.getFirstSelectedOption().getText());
	}
	
	public void SelectByTextAutoFill(By ListOfEle, String ExpectedTextSelect){
		List<WebElement> AutoFillEle = driver.findElements(ListOfEle);
		for(WebElement Auto: AutoFillEle){
			String ActualText = Auto.getText();
			if(ActualText.contains(ExpectedTextSelect)){
				Auto.click();
				System.out.println("Selected Location is "+ActualText);
				break;
			}
		}
	}
	
	
	public void TakeScreenshot(String MobNumber, String Foldername){
//		System.out.println("user name is "+ System.getProperty("user.name"));
		String SystemUser = System.getProperty("user.name");
		String DATE_FORMATTER= "yyyy-MM-dd HH-mm-ss";
        LocalDateTime localDateTime = LocalDateTime.now(); //get current date time

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = localDateTime.format(formatter);
//        System.out.println("formatted current time is "+formatDateTime);
		String filepath = "C:/Users/" +SystemUser+ "/"+Foldername+"/"+MobNumber+" "+formatDateTime+".png";
		
		TakesScreenshot scrShot =((TakesScreenshot)driver);

	    File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

	    File DestFile=new File(filepath);

	    try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SwitchtoFrame(By iframe, By elementtobepresent) throws InterruptedException{
		Thread.sleep(3000);
		List<WebElement> IFrames = driver.findElements(iframe);
		for(WebElement frames: IFrames){
			driver.switchTo().frame(frames);
			int size = driver.findElements(elementtobepresent).size();
			if(size>0){
			System.out.println("element present in frame");
			break;
			}
			driver.switchTo().defaultContent();
		}

		
	}
	
	//BestwaytoSwitchFrames************************************************************************
//	public void SwitchtoFrame(By elementtobepresent) throws InterruptedException{
//		Thread.sleep(3000);
//		List<WebElement> IFrames = driver.findElements(By.tagName("iframe"));
//		int framesSize = driver.findElements(By.tagName("iframe")).size();
//		
//	}
	
	public int Randomnumber(){
		Random rand = new Random();
		int randno = rand.nextInt(5000000);
		return randno;
	}
	
	public int Randomnumber2(){
		Random rand = new Random();
		int randno = rand.nextInt(50000);
		return randno;
	}
	
	public String RandomString(){
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		 
	    System.out.println(generatedString);
		    return generatedString;
	}
	
	public String RandomString2(){
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		 
	    System.out.println(generatedString);
		    return generatedString;
	}
	
	public String RandomPhone(){
		Random rand = new Random();
		int randno = rand.nextInt(10000000);
		String randomNo = Integer.toString(randno);
		String phoneno = "050"+""+randomNo;
		System.out.println(phoneno);
		return phoneno;
	}
	
	public static void setClipboardData(String string) {
		//StringSelection is a class that can be used for copy and paste operations.
		   StringSelection stringSelection = new StringSelection(string);
		   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		}
	
	public void uploadFile(String fileLocation) {
        try {
        	//Setting clipboard with file location
        	Thread.sleep(2000);
            setClipboardData(fileLocation);
            //native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();
            
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(2000);
        } catch (Exception exp) {
        	exp.printStackTrace();
        }
    }
	
	public void TakeScreenshotOnFailure(ITestResult testResult) throws IOException{
		String SystemUser = System.getProperty("user.name");
		String filepath = "C:\\Users\\" +SystemUser+ "\\Desktop\\UCaaS\\UCaaSAutomation_ScreenshotnTestDatas\\FailureScreenshots\\";
        LocalDateTime localDateTime = LocalDateTime.now(); //get current date time
		String DATE_FORMATTER= "HH-mm-ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = localDateTime.format(formatter);
		if (testResult.getStatus() == ITestResult.FAILURE) { 
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
			FileUtils.copyFile(scrFile, new File(filepath + testResult.getName() + "-" 
					+ Arrays.toString(testResult.getParameters()) + "-"+formatDateTime+ ".jpg"));
		} 
	}
	

}
