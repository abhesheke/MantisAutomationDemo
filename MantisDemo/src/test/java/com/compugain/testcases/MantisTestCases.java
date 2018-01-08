package com.compugain.testcases;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ceiwc.compugain.funUtil.FileOperation;
import com.ceiwc.compugain.pageobjects.MantisDashBoardPage;
import com.ceiwc.compugain.pageobjects.MantisEditIssuePage;
import com.ceiwc.compugain.pageobjects.MantisLoginPage;
import com.ceiwc.compugain.pageobjects.MantisReportIssuePage;
import com.ceiwc.compugain.setup.BasePage;
import com.ceiwc.compugain.setup.TestBase;
import com.compugain.Exception.CustomReport;
import com.compugain.beansfactory.BeanFactory;
import com.compugain.dao.EditIssueBean;
import com.compugain.dao.ReportIssueBean;

public class MantisTestCases extends TestBase{


	
	private static Logger logger = Logger.getLogger(MantisTestCases.class);

	@BeforeClass
	public void beforeClass(){
		Properties props = System.getProperties();
		props.setProperty("org.uncommons.reportng.title", "Mantis Automation Report");
	}

	@Parameters("sbrowser")
	@Test(priority=1,description="Create a New Issue",groups="ISSUES")
	public void AT_Verify_ReportNewIssue(@Optional(SBROWSER)String sbrowser){

		String sTestcaseName = new Object(){}.getClass().getEnclosingMethod().getName();
		CustomReport customReport=new CustomReport();
		ArrayList<String> statusValue=new ArrayList<String>();
		WebDriver driver=initializeDriver(sbrowser);
		customReport.reporter("Browser Launched ", sbrowser);
		/*		String browservalue=sbrowser;
		 */		launchURL(AutomationURL,driver);
		 customReport.reporter("URL Entered ", MANTISApplicationURL);
		 BasePage basePage=new BasePage();
		 MantisLoginPage mantisloginpage=basePage.mantisLoginPage(driver, customReport, basePage);
		 MantisDashBoardPage mantisDashBoardPage=mantisloginpage.loginas(MantisUserName,MantisPassword);
		 MantisReportIssuePage mantisreportissuepage=mantisDashBoardPage.clickReportIssue();
		 ReportIssueBean reportissuebean=new ReportIssueBean();
		 BeanFactory bean=new BeanFactory();
		 bean.mantisReportIssue(reportissuebean);
		 mantisreportissuepage.reportIssue(reportissuebean);
		 mantisDashBoardPage=mantisreportissuepage.clickSubmitIssue();
		 mantisDashBoardPage.pause(5000);
		 //ArrayList<String> bugsList=mantisDashBoardPage.verifyReportSummary();
		 //System.out.println("BUGS LIST ::: "+bugsList);
		 customReport.customizedReport(true,mantisDashBoardPage.verify_label(reportissuebean.getSummary()).contains(reportissuebean.getSummary()), statusValue, driver, sTestcaseName);
		 browserQuit(driver);
		 customReport.checkinglist(statusValue);	
	}


	@Parameters("sbrowser")
	@Test(priority=2,description="Update Issue Status",groups="ISSUES")
	public void AT_Verify_UpdateIssueStatus(@Optional(SBROWSER)String sbrowser){

		String sTestcaseName = new Object(){}.getClass().getEnclosingMethod().getName();
		CustomReport customReport=new CustomReport();
		ArrayList<String> statusValue=new ArrayList<String>();
		WebDriver driver=initializeDriver(sbrowser);
		customReport.reporter("Browser Launched ", sbrowser);
		launchURL(MANTISApplicationURL,driver);
		customReport.reporter("URL Entered ", MANTISApplicationURL);
		BasePage basePage=new BasePage();
		MantisLoginPage mantisloginpage=basePage.mantisLoginPage(driver, customReport, basePage);
		MantisDashBoardPage mantisDashBoardPage=mantisloginpage.loginas(MantisUserName,MantisPassword);
		MantisReportIssuePage mantisreportissuepage=mantisDashBoardPage.clickReportIssue();
		ReportIssueBean reportissuebean=new ReportIssueBean();
		BeanFactory bean=new BeanFactory();
		bean.mantisReportIssue(reportissuebean);
		mantisreportissuepage.reportIssue(reportissuebean);
		mantisDashBoardPage=mantisreportissuepage.clickSubmitIssue();
		mantisDashBoardPage.pause(5000);
		//ArrayList<String> bugsList=mantisDashBoardPage.verifyReportSummary();
		//System.out.println("BUGS LIST ::: "+bugsList);
		customReport.customizedReport(true,mantisDashBoardPage.verify_label(reportissuebean.getSummary()).contains(reportissuebean.getSummary()), statusValue, driver, sTestcaseName);
		MantisEditIssuePage mantiseditissuepage=mantisDashBoardPage.clickEditIssue(reportissuebean.getSummary());
		EditIssueBean editissuebean=new EditIssueBean();
		bean.mantisUpdateIssue(editissuebean);
		mantiseditissuepage.selectStatus(editissuebean);
		mantiseditissuepage.clickUpdateInformation();
		customReport.customizedReport(false,mantisDashBoardPage.verifyIssuePresent(reportissuebean.getSummary()), statusValue, driver, sTestcaseName);
		browserQuit(driver);
		customReport.checkinglist(statusValue);	
	}


	@Parameters("sbrowser")
	@Test(priority=3,description="Delete Issue from Mantis",groups="ISSUES")
	public void AT_Verify_DleteIssue(@Optional(SBROWSER)String sbrowser){

		String sTestcaseName = new Object(){}.getClass().getEnclosingMethod().getName();
		CustomReport customReport=new CustomReport();
		ArrayList<String> statusValue=new ArrayList<String>();
		WebDriver driver=initializeDriver(sbrowser);
		customReport.reporter("Browser Launched ", sbrowser);
		launchURL(MANTISApplicationURL,driver);
		customReport.reporter("URL Entered ", MANTISApplicationURL);
		BasePage basePage=new BasePage();
		MantisLoginPage mantisloginpage=basePage.mantisLoginPage(driver, customReport, basePage);
		MantisDashBoardPage mantisDashBoardPage=mantisloginpage.loginas(MantisUserName,MantisPassword);
		MantisReportIssuePage mantisreportissuepage=mantisDashBoardPage.clickReportIssue();
		ReportIssueBean reportissuebean=new ReportIssueBean();
		BeanFactory bean=new BeanFactory();
		bean.mantisReportIssue(reportissuebean);
		mantisreportissuepage.reportIssue(reportissuebean);
		mantisDashBoardPage=mantisreportissuepage.clickSubmitIssue();
		mantisDashBoardPage.pause(5000);
		//ArrayList<String> bugsList=mantisDashBoardPage.verifyReportSummary();
		//System.out.println("BUGS LIST ::: "+bugsList);
		customReport.customizedReport(true,mantisDashBoardPage.verify_label(reportissuebean.getSummary()).contains(reportissuebean.getSummary()), statusValue, driver, sTestcaseName);
		mantisDashBoardPage.CheckIssueCheckBox(reportissuebean.getSummary());
		mantisDashBoardPage.selectdashboarddropdownvalue("Delete");
		mantisDashBoardPage.clickOk();
		mantisDashBoardPage.clickDeleteIssue();
		mantisDashBoardPage.pause(3000);
		customReport.customizedReport(false,mantisDashBoardPage.verifyIssuePresent(reportissuebean.getSummary()), statusValue, driver, sTestcaseName);
		browserQuit(driver);
		customReport.checkinglist(statusValue);	
	}
	
	@Parameters("sbrowser")
	@Test(priority=4,description="Export Issues into CSV",groups="ISSUES")
	public void AT_Verify_ExportIssuesintoCSV(@Optional(SBROWSER)String sbrowser){
		
		if(FileOperation.fileExist(getFilePath(DOWNLOADSPATH+"/MantisDemo.csv"))) {
			FileOperation.deleteFile(getFilePath(DOWNLOADSPATH+"/MantisDemo.csv"));	
			logger.info("File Deleted");
		}else {
			logger.info("File is not avalible");
		}
		
		String sTestcaseName = new Object(){}.getClass().getEnclosingMethod().getName();
		CustomReport customReport=new CustomReport();
		ArrayList<String> statusValue=new ArrayList<String>();
		WebDriver driver=initializeDriver(sbrowser);
		customReport.reporter("Browser Launched ", sbrowser);
		launchURL(MANTISApplicationURL,driver);
		customReport.reporter("URL Entered ", MANTISApplicationURL);
		BasePage basePage=new BasePage();
		MantisLoginPage mantisloginpage=basePage.mantisLoginPage(driver, customReport, basePage);
		MantisDashBoardPage mantisDashBoardPage=mantisloginpage.loginas(MantisUserName,MantisPassword);
		MantisReportIssuePage mantisreportissuepage=mantisDashBoardPage.clickReportIssue();
		ReportIssueBean reportissuebean=new ReportIssueBean();
		BeanFactory bean=new BeanFactory();
		bean.mantisReportIssue(reportissuebean);
		mantisreportissuepage.reportIssue(reportissuebean);
		mantisDashBoardPage=mantisreportissuepage.clickSubmitIssue();
		mantisDashBoardPage.pause(5000);
		//ArrayList<String> bugsList=mantisDashBoardPage.verifyReportSummary();
		//System.out.println("BUGS LIST ::: "+bugsList);
		//customReport.customizedReport(true,mantisDashBoardPage.verify_label(reportissuebean.getSummary()).contains(reportissuebean.getSummary()), statusValue, driver, sTestcaseName);
		
		int expectedBugCount=mantisDashBoardPage.getIssueCount();
		logger.info("Expetced Bug Count ::::: "+expectedBugCount);
		
		mantisDashBoardPage.clickCSVExport();
		//changes made by me 
		
		
		mantisDashBoardPage.pause(7000);
		ArrayList<String> csvfiledata=FileOperation.ReadCSVFile(getFilePath(DOWNLOADSPATH+"/MantisDemo.csv"));
		
		ArrayList<String> bugsSize=new ArrayList<String>();
		
		for(int i=0;i<csvfiledata.size();i++) {
		
			if(csvfiledata.get(i).contains("Summary")) {
				bugsSize.add(csvfiledata.get(i));
			}
		}	
		logger.info("CSV Bug Count ::::: "+bugsSize.size());
		customReport.customizedReport(expectedBugCount, bugsSize.size()-1, statusValue, driver, sTestcaseName);
		browserQuit(driver);
		customReport.checkinglist(statusValue);
	}
}
