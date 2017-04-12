package com.ceiwc.compugain.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ceiwc.compugain.funUtil.WebElements;
import com.ceiwc.compugain.setup.BasePage;
import com.compugain.Exception.CustomReport;
import com.compugain.dao.EditIssueBean;

public class MantisEditIssuePage extends WebElements{
	
	
	private WebDriver driver;
	private CustomReport customReport;
	private BasePage basepage;
	
	By updateinformationlocator=By.xpath("//input[@value='Update Information']");
	By statusdropdownlocator=By.id("status");
	
	
	public MantisEditIssuePage(WebDriver driver,CustomReport customReport,BasePage basepage) {
		super(driver);
		this.driver=driver;
		this.customReport=customReport;
		this.basepage=basepage;
	}
	
	
	
	public void selectStatus(EditIssueBean editissuebean){
		selectValuefromDropDown(statusdropdownlocator, editissuebean.getStatus());
		customReport.reporter("Selected Value From Status Dropdown",editissuebean.getStatus());
	}

	
	public void clickUpdateInformation(){
		
		
		click(updateinformationlocator);
		customReport.reporter("Clicked Update Information option"," ");
		
	}
	
}
