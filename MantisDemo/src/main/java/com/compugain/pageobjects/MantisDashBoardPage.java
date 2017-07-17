package com.ceiwc.compugain.pageobjects;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ceiwc.compugain.funUtil.WebElements;
import com.ceiwc.compugain.setup.BasePage;
import com.compugain.Exception.CustomReport;

public class MantisDashBoardPage extends WebElements{

	private WebDriver driver;
	private CustomReport customReport;
	private BasePage basePage;

	public MantisDashBoardPage(WebDriver driver,CustomReport customReport, BasePage basePage) {
		super(driver);
		this.driver = driver;
		this.customReport= customReport;
		this.basePage=basePage;
		// TODO Auto-generated constructor stub
	}

	By cutsomersLocator=By.xpath("//*[contains(@title,'Customers (c)')]");
	By editpersonalPreferencesLocator=By.xpath("//*[contains(text(),'jabesh kurapati')]");
	By ticketsLocator=By.xpath("//*[contains(text(),'Tickets')]");
	By customerUISearchLocator=By.xpath("//li[contains(@title,'Search')]");
	By reportIssueLocator=By.xpath("//*[contains(text(),' Report Issue')]");
	By dashbaorddropdown=By.xpath("//*[contains(@name,'action')]");
	By okbuttonlocator=By.xpath("//*[contains(@value,'OK')]");
	By clickDeleteIssueLocator=By.xpath("//*[contains(@value,'Delete Issues')]");
	By csvexportlocator=By.xpath("//*[contains(text(),'CSV Export')]");
	
	public MantisReportIssuePage clickReportIssue(){
		click(reportIssueLocator);
		customReport.reporter("Report Issue Option Clicked", "");
		return basePage.mantisreportissuepage(driver, customReport, basePage);
	}
	
	
	public boolean verifyIssuePresent(String issueName){
		By issuename=By.xpath("//*[contains(text(),'"+issueName+"')]");
		return isDisplayedWithoutException(issuename);
	}
	
	public int getIssueCount() {
		By bugsListrowscount=By.xpath("//table[contains(@id,'buglist')]/tbody/tr");
		int count=elements(bugsListrowscount);
		System.out.println("Bugs Count ::: "+count);
		return count;
	}
	
	public void clickCSVExport() {
		click(csvexportlocator);
		customReport.reporter("Clicked CSV File "," ");
	}
	
	
	public ArrayList<String> verifyReportSummary() {
		By bugsListrowscount=By.xpath("//table[contains(@id,'buglist')]/tbody/tr");
		int count=elements(bugsListrowscount);
		System.out.println("Bugs Count ::: "+count);
		String xpath_Start="//table[contains(@id,'buglist')]/tbody/tr[";
		String xpath_End="]/td[11]";
		ArrayList<String> list=new ArrayList<String>();
		for(int i=1;i<count;i++ ) {
		//By	bugsListLocator=By.xpath(xpath_Start+i+xpath_End);
		list.add(getText(By.xpath(xpath_Start+i+xpath_End)));
		//System.out.println("Created Bug Summary :: "+createdBugSummary);
		}
		
		return list;
	}
	
	public MantisEditIssuePage clickEditIssue(String issuename){
		
		By editbuttonlocator=By.xpath("//*[contains(text(),'"+issuename+"')]/../td[2]/a");
		click(editbuttonlocator);
		customReport.reporter("clicked Edit Button","");
		return basePage.mantisEditIssuePage(driver, customReport, basePage);
	}
	
	
	public void CheckIssueCheckBox(String issuename) {
		By checkboxlocator=By.xpath("//*[contains(text(),'"+issuename+"')]/../td[1]/div/label/span");
		check_Checkbox(checkboxlocator);
	}
	
	
	public void selectdashboarddropdownvalue(String dropdownValue){
		selectValuefromDropDown(dashbaorddropdown,dropdownValue);
		customReport.reporter("selected issue checkbox", " ");
	}
	
	public void clickOk(){
		click(okbuttonlocator);
		customReport.reporter("clicked ok button", " ");
	}
	
	public void clickDeleteIssue(){
		click(clickDeleteIssueLocator);
		customReport.reporter("clicked delete issue button", " ");
	}
	
	/*public CustomerSearchPage clickCustomerUISearch(){
		click(customerUISearchLocator);
		customReport.reporter("Clicked Ticket search option", "");
		return basePage.customersearchpage(driver, customReport, basePage);
	}*/

}
