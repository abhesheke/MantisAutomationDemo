package com.ceiwc.compugain.setup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ceiwc.compugain.funUtil.FileOperation;
public   class TestBase implements Setup,EnviromentAccounts{

	public int grid=1;
	public WebDriver driver;
	public WebDriver secondDriver,thirdDriver;
	public FirefoxProfile profile;
	public String sUserName,sPassword,sEnvironment,sversion1;
	public String surl,surlA,surlB,surlC,surlD,surlE,surlF;
	public static String language="en";
	private static Logger logger = Logger.getLogger(TestBase.class);
	public String DBSTRING=null;

	



	public WebDriver initializeDriver(String sbrowser, String sgrid)  {
		WebDriver driver=null;
		logger.info("Test Base Browser is"+sbrowser);
		if(sgrid.equalsIgnoreCase(""))
		{ 
			return driver=initializeDriver(sbrowser);
		}
		else if (!sgrid.equalsIgnoreCase("")&&!sbrowser.equalsIgnoreCase(""))
		{
			logger.info("I am in Grid Intialization");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			URL hubUrl = null;
			try {
				hubUrl = new URL("http://localhost:4444/wb/hub");
				if (sbrowser.equalsIgnoreCase("firefox")) {
					FirefoxProfile profile = new FirefoxProfile();
					profile.setPreference("browser.download.folderList", 2);
					capabilities.setBrowserName(DesiredCapabilities.firefox()
							.getBrowserName());
					capabilities.setPlatform(Platform.WINDOWS);
					driver= new RemoteWebDriver(new URL(
							"http://localhost:4444/wd/hub"), capabilities);
					//remoteWebDriver.setFileDetector(new LocalFileDetector());
				}
				if (sbrowser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",
							getFilePath(CHROMEPATH));
					capabilities.setBrowserName(DesiredCapabilities.chrome()
							.getBrowserName());
					//capabilities.setVersion(sversion1);
					capabilities.setPlatform(Platform.WINDOWS);
					driver = new RemoteWebDriver(new URL(
							"http://localhost:4444/wd/hub"), capabilities);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return driver;

	}


	/**
	 *  Initializes web driver object based on the browser type
	 * @param sbrowser : THe browser we need to run
	 * @param surl : the URL of the application
	 * @return : Webdriver object
	 */
	public  WebDriver initializeDriver(String sbrowser/*,String surl*/) {
		WebDriver driver = null;
		logger.info("Browser Name:"+sbrowser);
		//	logger.info(" Application surl:"+surl);
		if(driver==null)
		{
			if (sbrowser.equalsIgnoreCase("firefox")) {
				
				System.setProperty("webdriver.gecko.driver", getFilePath(FIREFOXPATH));
				FirefoxProfile profile = new FirefoxProfile();
				String downloadFilepath = getFilePath(DOWNLOADSPATH);
				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.dir",  downloadFilepath);
				profile.setPreference("browser.helperApps.neverAsk.openFile",
						"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				profile.setPreference("browser.download.manager.focusWhenStarting", false);
				profile.setPreference("browser.download.manager.useWindow", false);
				profile.setPreference("browser.download.manager.showAlertOnComplete", false);
				profile.setPreference("browser.download.manager.closeWhenDone", false);
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				logger.info("Profile Setup done for firefox");
				

				DesiredCapabilities dc = DesiredCapabilities.firefox();
				  dc.setCapability("marionette", true);
				  dc.setCapability(FirefoxDriver.PROFILE, profile);
					//cap.setCapability("firefox_binary","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				driver = new FirefoxDriver(dc);
				

				/*logger.info("The value of Path  is------------->"+System.getProperty("user.dir")+firebugPath);
			File firebug = new File(getFilePath(firebugPath));
			File firepath_new = new File(getFilePath(firepath));
				 */
				//File netExport = new File(getFilePath(netExportPath));

				/*Capabilities cap =  dri.getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			System.out.println("Browser Name"+browserName);
			String os = cap.getPlatform().toString();
			System.out.println("operating system"+os);
			String v = cap.getVersion().toString();
			System.out.println("Version is"+v);
				 *//*try {
				profile.addExtension(firebug);
				profile.addExtension(firepath_new);
				//profile.addExtension(netExport);
			} catch (IOException e) {
				e.printStackTrace();
			}
				  */
				/*	profile.setPreference("app.update.enabled", false);
			profile.setPreference("extensions.firebug.currentVersion", "2.0");
			profile.setPreference("extensions.firebug.addonBarOpened", true);
			profile.setPreference("extensions.firebug.console.enableSites", true);
			profile.setPreference("extensions.firebug.script.enableSites", true);
			profile.setPreference("extensions.firebug.net.enableSites", true);
			profile.setPreference("extensions.firebug.previousPlacement", 1);
			profile.setPreference("extensions.firebug.allPagesActivation", "on");
			profile.setPreference("extensions.firebug.onByDefault", true);
			profile.setPreference("extensions.firebug.defaultPanelName", "net");

			profile.setPreference(
					"extensions.firebug.netexport.alwaysEnableAutoExport", true);
			profile.setPreference("extensions.firebug.netexport.autoExportToFile",true);
			profile.setPreference("extensions.firebug.netexport.Automation", true);
			profile.setPreference("extensions.firebug.netexport.showPreview", false);
			logger.info("The path of the response folder is "+System.getProperty("user.dir")+"\\ResponseFolder\\");

			profile.setPreference("extensions.firebug.netexport.defaultLogDir",getFilePath(responseFileDownloadpath));
				 */		
				//			FirefoxProfile profile = new FirefoxProfile();
				//profile.setEnableNativeEvents(true);
				//logger.info("enabled events are"+profile.areNativeEventsEnabled());
				/*			DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setBrowserName(sbrowser);
			capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);

				 */	
			/*System.setProperty("webdriver.gecko.driver", getFilePath(FIREFOXPATH));
				//String downloadPath = getFilePath(DOWNLOADSPATH);
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.dir",getFilePath( DOWNLOADSPATH));
				profile.setPreference("browser.helperApps.neverAsk.openFile",
						"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				 //  profile.setPreference("browser.download.useDownloadDir", true);
				profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				profile.setPreference("browser.download.manager.focusWhenStarting", false);
				profile.setPreference("browser.download.manager.useWindow", false);
				profile.setPreference("browser.download.manager.showAlertOnComplete", false);
				profile.setPreference("browser.download.manager.closeWhenDone", false);
				profile.setPreference("xpinstall.signatures.required", false);
			//	 profile.setPreference("browser.download.panel.shown",false);
				logger.info("The Profile Setup Data");
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				cap.setCapability("marionette", true);
				cap.setCapability(FirefoxDriver.PROFILE, profile);
				//cap.setCapability("firefox_binary","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				driver = new FirefoxDriver(cap);
				webdriver= new EventFiringWebDriver(driver);
				EventListerners eventListerners=new EventListerners();
				webdriver.register(eventListerners);
				 	String s = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
				 logger.info("Browser name : " + s);*/
			}



			else if(sbrowser.equalsIgnoreCase("chrome")){
				driver = new ChromeDriver(chromeProfileSetUp());
				//System.setProperty("webdriver.gecko.driver", getFilePath(CHROMEPATH));
				//ChromeDriver profile = new ChromeDriver();
				
				/*	logger.info("The Profile set up started @@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.setProperty("webdriver.chrome.driver", getFilePath(CHROMEPATH));
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory",getFilePath(DOWNLOADSPATH));
				ChromeOptions options = new ChromeOptions();
				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--test-type");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				//cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
			   logger.info("Initializing chrome webDriver");
				driver = new ChromeDriver(cap);
				 	
				driver = new ChromeDriver(chromeProfileSetUp());
				//	logger.info("The Profile set up Ended #####################");


				//driver = new ChromeDriver(chromeProfileSetUp());
				/*	 webdriver= new EventFiringWebDriver(driver);
				 EventListerners eventListerners=new EventListerners();
				 webdriver.register(eventListerners);
				 */
			//	String s = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		//		System.out.println("Browser name : " + s.split("Chrome"));
				//		logger.info("Result value is"+result[1].substring(1,6));
				//	setVersionvalue(versionvalue);
			}
			else if(sbrowser.equalsIgnoreCase("IE"))
			{
				/*if (System.getProperty("os.arch").contains("86")) {
				logger.info("System is---"+System.getProperty("os.arch")+"---bit");
			     System.setProperty("webdriver.ie.driver",
			    		 getFilePath("/src/test/resources/Browsers_Binaries/IE/IEDriverServer64.exe"));
			    } 
			else if (System.getProperty("os.arch").contains("64")) {
					logger.info("System is---"+System.getProperty("os.arch")+"---bit");
			     System.setProperty("webdriver.ie.driver",
			    		 getFilePath("/src/test/resources/Browsers_Binaries/IE/IEDriverServer64.exe"));
			   }*/

				driver= new InternetExplorerDriver(ieProfileSetUp());
				/*				 webdriver= new EventFiringWebDriver(driver);
				 EventListerners eventListerners=new EventListerners();
				 webdriver.register(eventListerners);
				 */			}
			else if(sbrowser.equalsIgnoreCase("Safari"))
			{
				DesiredCapabilities capabilities = DesiredCapabilities.safari();
				driver = new SafariDriver(capabilities);
				/*				 webdriver= new EventFiringWebDriver(driver);
				 EventListerners eventListerners=new EventListerners();
				 webdriver.register(eventListerners);
				 */			}

		}
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		return driver;
	}

	public void navigateTo(String surl,WebDriver driver)
	{

		driver.manage().deleteAllCookies();
		driver.navigate().to(surl);
	}

	public void navigateTo(String surl)
	{

		driver.manage().deleteAllCookies();
		driver.navigate().to(surl);
	}

	public void navigateTo_DeleteCookie(String surl,WebDriver  driver)
	{
		driver.manage().deleteAllCookies();
		driver.navigate().to(surl);
	}
	public void launchURL(String surl)
	{
		driver.get(surl);
		logger.info("Application lauched with URL"+surl);
	}

	public void launchURL(String surl,WebDriver driver)
	{
		driver.get(surl);
		logger.info("Application lauched with URL"+surl);
	}

	public void browserQuit()
	{
		driver.quit();
		driver=null;
	}

	public void browserQuit(WebDriver driver)
	{
		try
		{
			driver.quit();
			driver=null;
		}
		catch(Exception e)
		{
			logger.info("Event Driver is Not Initialized");
		}


	}
	public void browserQuit_WithoutLogout()
	{
		driver.quit();
		driver=null;
	}


	public String getFilePath(String sFilepath) {
		char cforwardslash = (char) 47;
		char cbackslash = (char) 92;
		//	logger.info("File path is "+sFilepath);
		String sPath = System.getProperty("user.dir").replace(cbackslash,
				cforwardslash)
				+ sFilepath;

		File file = new File(sPath);
		if (file.exists()) {
			sPath = file.getAbsolutePath();
			//		logger.info("The File Path is " + sPath);
		} else {
		}
		return sPath;
	}
	/**
	 * Loads the Property File
	 * @param prop
	 * @return:Returns the Property Object
	 */
	public void loadPropertiesFile(Properties prop,String language)
	{

		logger.info("In the Load Properties File");
		com.ceiwc.compugain.funUtil.LanguageSelect lanSelect = new com.ceiwc.compugain.funUtil.LanguageSelect();
		try {
			lanSelect.getLanguage(prop, language);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*try {
		logger.info("In the Properties File Method");
		String spropPath="/src/test/TextProperties/TextVerify.properties";
		prop.load(new FileInputStream(getFilePath(spropPath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}
	/**
	 * 
	 * @return : The language present in the HTML.
	 */


	public void resetLicense(WebDriver driver)
	{
		/*logger.info(surl.replaceAll("/$", ""));
		driver.get(surl.replaceAll("/$", "")+":5555/ResetUserLicense");
		driver.get(surl.replaceAll("/$", "")+":5555/UserLicenseAvailableCount.json");
     	driver.quit();
    	driver=null;*/
	}



	public void resetLicense(String sbrowser)
	{
		/*WebDriver driver;
		WindowsUtil windowutil=new WindowsUtil();
		if (sbrowser.equalsIgnoreCase("firefox")) {
			logger.info("firefox driver intialized");
		driver = new FirefoxDriver();
		resetLicense(driver);
		windowutil.runBatchFile(KILLFIREFOX);
		}
		else if(sbrowser.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver",
					getFilePath(CHROMEPATH));
			logger.info("Setting a Chrome Driver.");
			driver= new ChromeDriver();
			resetLicense(driver);
			windowutil.runBatchFile(KILLCHROME);
			windowutil.runBatchFile(KILLFIREFOX);
		}
		else if(sbrowser.equalsIgnoreCase("IE"))
		{
			logger.info("IE driver intialized");
			System.setProperty("webdriver.ie.driver",
		    		 getFilePath(IEPATH));
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(
				    InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				    true);
			caps.setCapability("ignoreZoomSetting", true);
			caps.setCapability("ignoreProtectedModeSettings" , true);
			try {
				Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver= new InternetExplorerDriver(caps);
		   resetLicense(driver);
		   windowutil.runBatchFile(KILLIE);
		   windowutil.runBatchFile(KILLFIREFOX);
		}
		else if(sbrowser.equalsIgnoreCase("Safari"))
		{
			logger.info("IE driver intialized");
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			driver = new SafariDriver(capabilities);
			resetLicense(driver);
			windowutil.runBatchFile(KILLSAFARI);
		}*/
	}

	public void eventBrowserQuit(WebDriver secondDriver)
	{
		if(secondDriver != null)
		{
			try
			{
				secondDriver.quit();
				secondDriver=null;
				logger.info("browser Killed");
			}
			catch(Exception e)
			{
				logger.info("Event Driver is Not Initialized");
			}
		}
		else
			logger.info("No Driver object");
	}


	public  FirefoxProfile firefoxProfileSetUp() {
		FirefoxProfile profile = new FirefoxProfile();
		String downloadFilepath = getFilePath(DOWNLOADSPATH);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir",  downloadFilepath);
		profile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		logger.info("Profile Setup done for firefox");
		return profile;
	}


	public DesiredCapabilities chromeProfileSetUp()
	{
		System.setProperty("webdriver.chrome.driver", getFilePath(CHROMEPATH));
		String downloadFilepath = getFilePath(DOWNLOADSPATH);
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("browser. helperApps. alwaysAsk. force",true);
		chromePrefs.put("browser. helperApps. neverAsk. saveToDisk","text/csv,application/x-msexcel,application/excel");
		chromePrefs.put("browser. helperApps. neverAsk. saveasToDisk","text/csv,application/x-msexcel,application/excel");
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--test-type");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		options.addArguments("--always-authorize-plugins");
		options.addArguments("--ignore-certificate-errors");

		//	logger.info("The Chrome Profile is setup");
		return cap;	
	}

	public DesiredCapabilities ieProfileSetUp()
	{
		System.setProperty("webdriver.ie.driver",
				getFilePath(IEPATH));
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(
				InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);
		caps.setCapability("ignoreZoomSetting", true);
		caps.setCapability("ignoreProtectedModeSettings" , true);
		caps.setCapability("enablePersistentHover", true);
		caps.setCapability("nativeEvents",false);
		try {
			Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return caps;
	}	

}
