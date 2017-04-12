package com.ceiwc.compugain.setup;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;


import com.ceiwc.compugain.pageobjects.MantisDashBoardPage;
import com.ceiwc.compugain.pageobjects.MantisEditIssuePage;
import com.ceiwc.compugain.pageobjects.MantisLoginPage;
import com.ceiwc.compugain.pageobjects.MantisReportIssuePage;
import com.compugain.Exception.CustomReport;

public class BasePage {

	private static Logger logger = Logger.getLogger(BasePage.class);
	private WebDriver driver;
	private CustomReport customReport;
	private BasePage basePage;
	private MantisLoginPage mantisLoginPage;
	private MantisDashBoardPage mantisDashBoardPage;
	private MantisReportIssuePage mantisreportissuepage;
	private MantisEditIssuePage mantisEditIssuePage; 
	
	
	
	public BasePage()
	{
		logger.info("Creation of Constructer of basepage");
	}
	
	public BasePage(WebDriver driver, CustomReport customReport,BasePage basePage)
	{
		this.driver=driver;
		this.customReport=customReport;
		this.basePage=basePage;
	}
	
	
	
	public MantisLoginPage mantisLoginPage(WebDriver driver, CustomReport customReport,BasePage basePage) {
		
		if(mantisLoginPage==null) {
			mantisLoginPage = new MantisLoginPage(driver, customReport, basePage);
		}
		return mantisLoginPage;
	}
	
	public MantisDashBoardPage mantisDashBoardPage(WebDriver driver, CustomReport customReport,BasePage basePage) {
		
		if(mantisDashBoardPage==null) {
			mantisDashBoardPage=new MantisDashBoardPage(driver, customReport, basePage);
		}
		return mantisDashBoardPage;
	}

	public MantisEditIssuePage mantisEditIssuePage(WebDriver driver, CustomReport customReport,BasePage basePage){
		if(mantisEditIssuePage==null) {
			mantisEditIssuePage=new MantisEditIssuePage(driver, customReport, basePage);
		}
		return mantisEditIssuePage;
		
	}
	
	
	
	
	public MantisReportIssuePage mantisreportissuepage(WebDriver driver, CustomReport customReport,BasePage basePage){
		
		if(mantisreportissuepage==null) {
			mantisreportissuepage=new MantisReportIssuePage(driver, customReport, basePage);
		}
		return mantisreportissuepage;
	}
	
	
	}
