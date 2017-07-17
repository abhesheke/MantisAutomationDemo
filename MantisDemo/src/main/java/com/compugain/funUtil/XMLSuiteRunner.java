package com.ceiwc.compugain.funUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import com.ceiwc.compugain.setup.Setup;




public class XMLSuiteRunner implements Setup{

	private static Logger logger = Logger.getLogger(XMLSuiteRunner.class);

	private DocumentBuilderFactory docFactory ;
	private DocumentBuilder docBuilder;
	private Document doc;
	private DOMImplementation domImpl;

	FileOperation fileObj;
	ReadTestData testData,testData2;
	String suiteName;

	private XMLSuiteRunner() throws ParserConfigurationException, IOException {

		docFactory = DocumentBuilderFactory.newInstance(); 
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
		domImpl = doc.getImplementation();
		//fileObj=new FileOperation();
		//	testData2.readConfigsheet("Config", 0);

	}

	public Element rootTag(String a) {
		Element elementName=doc.createElement(a);
		doc.appendChild(elementName);
		return elementName;
	}

	public Element subTag(Element rootTagName,String childTagName)
	{
		Element childElement =doc.createElement(childTagName);
		rootTagName.appendChild(childElement);
		return childElement;
	}

	public void setAttributes(String sattributeKey, String sattributeValue, Element tagName){

		Attr attr=doc.createAttribute(sattributeKey);
		attr.setValue(sattributeValue);
		tagName.setAttributeNode(attr);

	}

	public void createXML(final String filename) throws TransformerException, IOException{
		
		
		if(null!=filename && !filename.trim().equals("")) {
			//String[] filePartsArr=FilenameUtils.removeExtension(filename).split("/");
			String newfilname= filename.replace("\\","/");
			System.out.println("newfilename" +newfilname);
			suiteName=newfilname.substring(newfilname.lastIndexOf("/")+1, newfilname.length());
			//System.out.println("Before Split"+suiteName);
			//suiteName=	 fileName[fileName.length-1];
			System.out.println("After Split"+suiteName);
		} else {
			System.out.println("SRC ::::: "+testcasefile);
			String[] filePartsArr=FilenameUtils.removeExtension(testcasefile).split("/");
			suiteName = filePartsArr[filePartsArr.length-1];
		}

		// System.out.println("the last element is"+items[items.length-1]);
		DocumentType doctype = domImpl.createDocumentType("doctype", "suite","http://testng.org/testng-1.0.dtd");
		Element suiteTag=rootTag("suite");
		setAttributes("name", suiteName, suiteTag);
		setAttributes("thread-count", "10", suiteTag);
		setAttributes("parallel","classes",suiteTag);

		Element listnersTag=subTag(suiteTag, "listeners");


		Element listnerTag1=subTag(listnersTag,"listener");
		Element listnerTag2=subTag(listnersTag,"listener");
		Element listnerTag3=subTag(listnersTag,"listener");

		setAttributes("class-name", "org.uncommons.reportng.HTMLReporter", listnerTag1);
		setAttributes("class-name", "org.uncommons.reportng.JUnitXMLReporter", listnerTag2);
		setAttributes("class-name", "com.ceiwc.compugain.funUtil.CustomReporter", listnerTag3);

		Element testTag=subTag(suiteTag,"test");
		setAttributes("name",suiteName , testTag);
		Element	classesTag	=subTag(testTag, "classes");
		testData=new ReadTestData(filename);
		TreeMap<String,LinkedList> xmlMap=testData.readTestCaseExcel();

		Element classTag;
		Element methodTag;
		for(Map.Entry<String, LinkedList> mapIterator : xmlMap.entrySet()) {



			LinkedList methodArray = mapIterator.getValue();

			System.out.println("Method Array Size :::: "+methodArray.size());

			for(int i=0;i<methodArray.size();i++) {

				String arrayValues=(String) methodArray.get(i);
				String methodName=arrayValues.split("@!@")[0];
				String flag=arrayValues.split("@!@")[1];
				System.out.println("FLAG ::::::::: "+flag);

				if(flag.equalsIgnoreCase("y")) {
					classTag=subTag(classesTag, "class");

					setAttributes("name", mapIterator.getKey(), classTag);

					methodTag=subTag(classTag, "methods");

					Element includeTag=subTag(methodTag, "include");
					setAttributes("name", methodName, includeTag);
					System.out.println("Method name"+methodName);

				}

			}

		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
		DOMSource source = new DOMSource(doc);
		



		StreamResult result = new StreamResult(new File(suiteName.replace(".xls","")+".xml"));
		transformer.transform(source, result);
		System.out.println("File Created");

	}

	/*public void setemailConfiguration() throws IOException{

		HashMap<String,String> emailMap=testData2.readConfigsheet("Config",0,1);
		System.out.println("EmailMAP $$$$ "+emailMap.get("From"));
		Properties pop = new Properties();
		pop.load(new FileInputStream( FileOperation.getFilePath(EMAILCONFIG)));
		pop.put("UserName", emailMap.get("From"));
		pop.put("recipients", emailMap.get("To"));
		pop.put("bccrecipients", emailMap.get("Bcc"));
		pop.put("sender", emailMap.get("From"));
		FileOutputStream output = new FileOutputStream(FileOperation.getFilePath(EMAILCONFIG));
		pop.store(output, null);
	}*/


	public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
		XMLSuiteRunner xml=new XMLSuiteRunner();
		//xml.setemailConfiguration();
		if(args[0]!=null) {
			System.out.println(" Parameter ::::: 1");
			xml.createXML(args[0]);
		}else {
			System.out.println("Parameter :::: 2");
			xml.createXML(null);
		}
		/*String a="C:"+"'\'"+"test.txt";
		if(a.contains("\\"))
		System.out.println("@@@@"+a.replace("\\", "/"));
		else
			System.out.println("@@@@"+a.replace("'\'", "/"));*/

	}
}
