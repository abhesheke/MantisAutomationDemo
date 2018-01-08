package com.ceiwc.compugain.funUtil;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ceiwc.compugain.setup.Configuration;
import com.ceiwc.compugain.setup.Setup;
import com.compugain.beansfactory.BeanFactory;



/**
 *Method SendMail for sending mail form one location to another
 *
 */
public class  SendMail implements Setup
{    
	private static Logger logger = Logger.getLogger(SendMail.class);
    BeanFactory beanfactory = new BeanFactory();
	DateTime dateTime= new DateTime();
	public String getFilePath(String sFilepath) {
		char cforwardslash = (char) 47;
		char cbackslash = (char) 92;
		String sPath = System.getProperty("user.dir").replace(cbackslash,
				cforwardslash)
				+ sFilepath;

		File file = new File(sPath);
		if (file.exists()) {
			sPath = file.getAbsolutePath();
			//logger.info("The File Path is " + sPath);
		} else {
			logger.info("File not Found");
		}
		return sPath;
	}
	
	public static String getEnvironment(String sURL)
	{
		String sEnv="31";
		if(sURL.contains("22"))
		{
			return "22";
		}
		return sEnv;
	}

	public static String BuildNumber(String sURL) {
		String urlText;
		String sENV=getEnvironment(sURL);
		if(sURL.endsWith("/"))
			urlText = sURL + "js/version.js";
			else
				urlText = sURL + "/js/version.js";
		String sbuildNo = null;
		BufferedReader in = null;
		try {
			URL url = new URL(urlText);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			int i = 0;
			while ((inputLine = in.readLine()) != null) {
				if (i == 3) {
					sbuildNo = inputLine;
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("THe build no is"+sbuildNo);
		return sENV+"--"+sbuildNo.replace("buildNumber:","::" );
	}

	public void postMail(Map<String, String> reportOutput, Map<String, Integer> passedgroupmap, Map<String, Integer> failedgroupmap, Map<String, Integer> skippedgroupmap,Map<String, Integer> allmap) throws Exception
	{
		/**
		 *Properties Of the Mail Header
		 *
		 */

		String sSourceFolder=getFilePath(SOURCE_REPORTSFOLDER);
		File file = new File(sSourceFolder);
		for(int i=0;i<5;i++){
		if (file.exists()) 
			logger.info("Report Created in Test-output------------------------------------------");
		else {
			logger.info("Report NOT Created----------------------------");
			//Thread.sleep(5000);
		}
		}
		
		logger.info("source file path is" +sSourceFolder);
		FolderZip folderZip = new FolderZip();
		logger.info("-----------------2-----------------");
		folderZip.generateFileList(new File(sSourceFolder),sSourceFolder);
		logger.info("-----------------3-----------------");

		if (new File(getFilePath(OUTPUT_ZIP_FILE_REPORTS)).exists()) {
			logger.info("dest folder is " + getFilePath(OUTPUT_ZIP_FILE));
			folderZip.zipIt(getFilePath(OUTPUT_ZIP_FILE), sSourceFolder);
		} else {
			new File(getFilePath(OUTPUT_ZIP_FILE_REPORTS)).mkdir();

			folderZip.zipIt(getFilePath(OUTPUT_ZIP_FILE), sSourceFolder);
		}

		final Properties props = new Properties();
		props.load(new FileInputStream(getFilePath(EMAILCONFIG)));
		props.put("mail.smtp.user", "vivek.kundarapu@compugain.com");
		props.put("mail.smtp.host", "smtp.outlook.com");
		props.put("mail.smtp.port", "25");
		//props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.socketFactory.port", "587");
		/**
		 *Session and Authentication Of the Message, by default 
		 * Authentication is disabled
		 */

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props
						.getProperty("UserName"), props
						.getProperty("Password"));
			}
		});
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(props.getProperty("sender"));
		msg.setFrom(addressFrom);
		msg.setText(props.getProperty("emailMessage"));
		msg.setSubject(reportOutput.get("SuiteName")+" on "+Configuration.getAutomationBrowser().toUpperCase() );
		String[] recipient =null;
		if(Configuration.getEmailIdS()==null){
			 recipient =  props.getProperty("recipients").split(",");
		}else {
			
			recipient=Configuration.getEmailIdS().split(",");
		}
		
		// recipient =  props.getProperty("recipients").split(",");
		
		
        String[] bccrecipient=props.getProperty("bccrecipients").split(",");
        for (String string : bccrecipient) {
        	logger.info("The bcc recipeients are "+string);
			
		}
        
		try
		{
			FileInputStream file1 = new FileInputStream(new File(Configuration.getTestCaseFile()));
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file1);
			XSSFSheet sheet = workbook.getSheetAt(1);
			XSSFRow	row = sheet.getRow(0);
			String[] emailrecipients =row.getCell(1).toString().split(",");
			recipient = (String[]) ArrayUtils.addAll(recipient, emailrecipients);

		}catch(Exception e)
		{
			logger.info("Exception handled in email test cases");
		}



		InternetAddress[] addressTo = new InternetAddress[recipient.length];
		InternetAddress[] bccaddressTo = new InternetAddress[bccrecipient.length];
		

		for (int i = 0; i < recipient.length; i++)
		{
			addressTo[i] = new InternetAddress(recipient[i]);
		}
		for (int i = 0; i < bccrecipient.length; i++)
		{
			bccaddressTo[i] = new InternetAddress(bccrecipient[i]);
		}
	
		
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setRecipients(Message.RecipientType.BCC, bccaddressTo);
		

		//----------------------
		StringBuffer body = new StringBuffer("<html>Please find the execution status below :"+Configuration.getAutomationURL()+"<br>"+"System Executed Ip Address:"+beanfactory.getIpaddress());
		body.append("<img src=\"cid:image1\"/>");
		//body.append("<img src=\"cid:image2\"/><br>");

		int i=1; 
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		String firstpart="";
		String secondpart="";
		String thirdpart="";
		String fourthpart="";
		firstpart= "<html><body>"+body.toString()+
				"+<table style="+"width:50% border=6"+">"+
				"<th colspan =10>"+"CEIWC-Execution Report</th>"+
				"<tr>"+"<b><Center><td>Browser</td>"+
				"<td>Total Cases</td>"+
				"<td>#Passed</td>"+
				"<td>#Failed</td>"+
				"<td>#Skipped</td>"+ 
				"<td>%Passed</td>"+
				"<td>%Failed</td>"+
				"<td>%Skipped</td></b></center>"+"</tr>"+
				"<tr>"+"<td>"+
				reportOutput.get("Browser").toUpperCase()+
				"</td>"+"<td>"+reportOutput.get("totalnoofcases")+
				"</td>"+"<td>"+reportOutput.get("passedcasessize")+
				"</td>"+"<td>"+reportOutput.get("failedcasessize")+
				"</td>"+"<td>"+reportOutput.get("skippedcasessize")+
				"</td>"+"<td>"+reportOutput.get("passedpercentage")+
				"</td>"+"<td>"+reportOutput.get("failedpercentage")+
				"</td>"+"<td>"+reportOutput.get("skippedpercentage")+
				"</td>"+"</tr>"+"</table>"+

				"+<table style="+"width:50% border=6"+">"+
				"<th colspan =10>"+"Group Wise Report</th>"+
				"<tr>"+"<b><Center>" +
				"<td>Sno</td>"+
				"<td>EpicLink</td>"+
				"<td>Total Cases</td>"+
				"<td>#Passed</td>"+
				"<td>#Failed</td>"+
				"<td>#Skipped</td>"+
				"<td>%Passed</td>"+
				"<td>%Failed</td>"+
				"<td>%Skipped</td></b></center>"+"</tr>";

		for (Map.Entry<String, Integer> entry : allmap.entrySet()) {
			logger.info("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
			int passedgrouptestcases=ConvertNullToZeroValue(passedgroupmap.get(entry.getKey()));
			int failedgrouptestcases=ConvertNullToZeroValue(failedgroupmap.get(entry.getKey()));
			int skippedgrouptestcases=ConvertNullToZeroValue(skippedgroupmap.get(entry.getKey()));

			logger.info("------------Group Name is"+entry.getKey());
			logger.info("------------Passed cases in Group"+passedgrouptestcases);
			logger.info("------------Failed cases in Group"+failedgrouptestcases);
			logger.info("------------Skipped cases in Group"+skippedgrouptestcases);
			int totalgroupcases=passedgrouptestcases+failedgrouptestcases+skippedgrouptestcases;
			double passedgrouppercentage=Math.round(((double)passedgrouptestcases/(double)totalgroupcases)*100);
			double failedgrouppercentage=Math.round(((double)failedgrouptestcases/(double)totalgroupcases)*100);
			double skippedgrouppercentage=Math.round(((double)skippedgrouptestcases/(double)totalgroupcases)*100);

			logger.info("@@@@@@@@@@@@@@@@@Passed Percentage for Group"+passedgrouppercentage);
			logger.info("@@@@@@@@@@@@@@@@@Failed Percentage for Group"+failedgrouppercentage);
			logger.info("@@@@@@@@@@@@@@@@@Skipped Percentage for Group"+skippedgrouppercentage);

			String secondpart1=	
					"<tr>"+"<td>"+
							i+
							"</td>"+
							"<td>"+
							entry.getKey()+
							"</td>"+"<td>"+totalgroupcases+
							"</td>"+"<td>"+passedgrouptestcases+
							"</td>"+"<td>"+failedgrouptestcases+
							"</td>"+"<td>"+skippedgrouptestcases+
							"</td>"+"<td>"+passedgrouppercentage+
							"</td>"+"<td>"+failedgrouppercentage+
							"</td>"+"<td>"+skippedgrouppercentage+
							"</td>"+"</tr>";
			logger.info("Completed Creation of Table");
			secondpart=secondpart+secondpart1;
			i++;
		}
		fourthpart= "</table><br>";		
		thirdpart= "</body></html>";
		messageBodyPart.setContent(firstpart+secondpart+thirdpart+fourthpart,"text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		// first part  (the html)

		//   String htmlText = "<H3>Hi All<br></H3> <H3>Please find the execution status below : </H3><img src=\"cid:image1\"><br></H3><img src=\"cid:image2\">";


		//   multipart.addBodyPart(messageBodyPart);
		// second part (the image)
		//     messageBodyPart = new MimeBodyPart();
		// DataSource fds = new FileDataSource (getFilePath(PIECHARTLOCATION));
		//messageBodyPart.setDataHandler(new DataHandler(fds));
		Map<String, String> inlineImages = new HashMap<String, String>();
		inlineImages.put("image1", getFilePath(PIECHARTLOCATION));
		//inlineImages.put("image2", getFilePath(BARCHARTLOCATION));
		//---

		if (inlineImages != null && inlineImages.size() > 0) {
			Set<String> setImageID = inlineImages.keySet();
			logger.info("The images in the set are "+inlineImages.size());
			for (String contentId : setImageID) {
				MimeBodyPart imagePart = new MimeBodyPart();
				imagePart.setHeader("Content-ID", "<" + contentId + ">");
				imagePart.setDisposition(MimeBodyPart.INLINE);

				String imageFilePath = inlineImages.get(contentId);
				try {
					imagePart.attachFile(imageFilePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				multipart.addBodyPart(imagePart);
			}
		}

		// add it
		//  multipart.addBodyPart(messageBodyPart);
		//addAttachment(multipart, EXECUTIONSTATUS);
		if(Integer.valueOf(reportOutput.get("failedcasessize"))>0)
		addAttachment(multipart, FAILEDCASES);
		if(Integer.valueOf(reportOutput.get("passedcasessize"))>0)
		addAttachment(multipart, PASSEDCASES);
		if(Integer.valueOf(reportOutput.get("skippedcasessize"))>0)
		addAttachment(multipart, SKIPPEDCASES);

		//Third part
		messageBodyPart = new MimeBodyPart();
		String filename = getFilePath(OUTPUT_ZIP_FILE);
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(FileUtils.getFile(filename).getName());

		multipart.addBodyPart(messageBodyPart);
		//----------------------
		//      BodyPart messageBodyPart = new MimeBodyPart();
		// Fill the message

		// Create a multipar message
		//    Multipart multipart = new MimeMultipart();

		// Set text message part

		msg.setContent(multipart);
		//   msg.setContent(multipart1);
		Transport.send(msg);
	}

	public Map<String, String> runtimeEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addAttachment(Multipart multipart,String outPutFile) throws MessagingException
	{
		BodyPart messageBodyPart = new MimeBodyPart();
		String  filename= getFilePath(outPutFile);
		DataSource source = new FileDataSource(filename);
		logger.info("The file name is"+FileUtils.getFile(filename));
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(FileUtils.getFile(filename).getName());
		multipart.addBodyPart(messageBodyPart);
	}

	public Integer ConvertNullToZeroValue(Integer value)
	{
		if(value==null)	
			return 0;
		else
			return value;
	}
}