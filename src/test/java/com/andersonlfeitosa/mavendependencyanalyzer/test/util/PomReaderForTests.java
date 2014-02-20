package com.andersonlfeitosa.mavendependencyanalyzer.test.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class PomReaderForTests {
	
	private File pom;
	
	private DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	
	private DocumentBuilder docBuilder = null;
	
	private Document doc = null;
	
	private XPathFactory xpf = XPathFactory.newInstance();
	
	public PomReaderForTests(String pom) throws ParserConfigurationException, SAXException, IOException {
		this.pom = new File(pom);
		init();
	}

	private void init() throws ParserConfigurationException, SAXException, IOException {
		docFactory.newDocumentBuilder();
		doc = docBuilder.parse(pom);
	}
	
	public String getValue(String xpath) throws XPathExpressionException {
        XPath xPath = xpf.newXPath();
        Node node = (Node) xPath.evaluate(xpath, doc, XPathConstants.NODE);
        return node != null ? node.getTextContent() : null;
	}
	
}
