package com.ceiwc.compugain.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ceiwc.compugain.funUtil.WebElements;
import com.ceiwc.compugain.setup.BasePage;
import com.compugain.Exception.CustomReport;

public class MantisLoginPage extends WebElements{

	
	private WebDriver driver;
	private CustomReport customReport;
	private BasePage basePage;
	private static Logger logger = Logger.getLogger(MantisLoginPage.class);
	public MantisLoginPage(WebDriver driver,CustomReport customReport, BasePage basePage) {
		super(driver);
		this.driver = driver;
		this.customReport= customReport;
		this.basePage=basePage;
		// TODO Auto-generated constructor stub
	}
	
	By userNameLocator=By.id("username");
	By passwordLocator=By.id("password");
	By loginButtonLocator=By.xpath("//input[@type='submit']");
	
	
	public MantisDashBoardPage loginas(String userName,String password){
		enterUserName(userName);
		customReport.reporter("UserName Entered ", userName);
		logger.info("UserName Entered "+userName);
		enterPassword(password);
		customReport.reporter("Password Entered ", password);
		logger.info("Password Entered "+password);
		clickLogin();	
		customReport.reporter("Clicked on Login Button","");
		logger.info("Clicked on Login Button");
		return basePage.mantisDashBoardPage(driver, customReport, basePage);
	}
	
	public void enterUserName(String userName){
		enterText(userNameLocator, userName);
	}
	public void enterPassword(String password){
		enterText(passwordLocator, password);
	}
	public void clickLogin(){
		click(loginButtonLocator);
	}
	
	
	
	
}
