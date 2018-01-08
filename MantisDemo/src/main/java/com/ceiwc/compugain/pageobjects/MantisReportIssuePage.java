package com.ceiwc.compugain.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ceiwc.compugain.funUtil.WebElements;
import com.ceiwc.compugain.setup.BasePage;
import com.compugain.Exception.CustomReport;
import com.compugain.dao.ReportIssueBean;

public class MantisReportIssuePage extends WebElements{


	By categoryDropdownlocator=By.id("category_id");
	By reproducibilitydropdownlocator=By.id("reproducibility");
	By severitydropdownlocator=By.id("severity");
	By prioritydropdownlocator=By.id("priority");
	By assignTodropdownlocator=By.id("handler_id");
	By summarylocator=By.id("summary");
	By descriptionlocator=By.id("description");
	By stepstoreproducelocator=By.id("steps_to_reproduce");
	By submitissuelocator=By.xpath("//input[@value='Submit Issue']");


	private WebDriver driver;
	private CustomReport customReport;
	private BasePage basePage;

	public MantisReportIssuePage(WebDriver driver, CustomReport customReport,BasePage basePage) {
		super(driver);
		this.driver=driver;
		this.basePage=basePage;
		this.customReport=customReport;

	}

	public void selectCategory(ReportIssueBean reportissuebean) {
		selectValuefromDropDown(categoryDropdownlocator, reportissuebean.getCategory());
	}

	public void selectReproducibility(ReportIssueBean reportissuebean){

		selectValuefromDropDown(reproducibilitydropdownlocator, reportissuebean.getReproducibility());
	}

	public void selectSeverity(ReportIssueBean reportissuebean){
		selectValuefromDropDown(severitydropdownlocator, reportissuebean.getSeverity());
	}


	public void selectPriority(ReportIssueBean reportissuebean){
		selectValuefromDropDown(prioritydropdownlocator, reportissuebean.getPriority());
	}

	public void selectAssignTo(ReportIssueBean reportissuebean){
		selectValuefromDropDown(assignTodropdownlocator, reportissuebean.getAssignTo());
	}
	public void enterSummary(ReportIssueBean reportissuebean){
		enterText(summarylocator, reportissuebean.getSummary());
	}
	public void enterDescription(ReportIssueBean reportissuebean){
		enterText(descriptionlocator, reportissuebean.getDescription());
	}

	public void enterStepsToReproduce(ReportIssueBean reportissuebean){
		enterText(stepstoreproducelocator, reportissuebean.getStepstoreproduce());
	}

	public MantisDashBoardPage clickSubmitIssue(){
		click(submitissuelocator);
		return basePage.mantisDashBoardPage(driver, customReport, basePage);
	}

	public void reportIssue(ReportIssueBean reportissuebean){
		//	selectCategory(reportissuebean);
		selectReproducibility(reportissuebean);
		selectSeverity(reportissuebean);
		selectPriority(reportissuebean);
		//selectAssignTo(reportissuebean);
		enterSummary(reportissuebean);
		enterDescription(reportissuebean);
		enterStepsToReproduce(reportissuebean);

	}

}
