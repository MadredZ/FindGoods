/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logic.HistoryEntry;
import logic.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author M
 */
public class XMLBuilder {
    
    public static String createTopQueries(List<HistoryEntry> helist) 
	    throws ParserConfigurationException, TransformerException {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.newDocument();
        
	Element root = dom.createElement("topqueries");
	root.setAttribute("amount", (new Integer(helist.size())).toString());
        dom.appendChild(root);
        
	Iterator it = helist.iterator();
	while (it.hasNext()) {
	    HistoryEntry he = (HistoryEntry) it.next();
	    Element heElem = createHistoryEntryElement(dom, he);
	    root.appendChild(heElem);
	}
	
	return writeDocument(dom);
    }

    private static Element createHistoryEntryElement(Document dom, HistoryEntry he) {
	Element heElem = dom.createElement("query");

	Element query = dom.createElement("text");
	Text queryText = dom.createTextNode(he.getQuery());
	query.appendChild(queryText);
	heElem.appendChild(query);

	Element date = dom.createElement("date");
	String format = (new SimpleDateFormat("dd.MM.yyyy", Locale.UK)).format(he.getDate());
	Text dateText = dom.createTextNode(format);
	date.appendChild(dateText);
	heElem.appendChild(date);

	Element rating = dom.createElement("rating");
	Text ratingText = dom.createTextNode((new Long(he.getRating())).toString());
	rating.appendChild(ratingText);
	heElem.appendChild(rating);

	return heElem;
    }

    private static String writeDocument(Document dom) throws TransformerException {
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	DOMSource domSource = new DOMSource(dom);
	StringWriter stringWriter = new StringWriter();
	StreamResult streamResult = new StreamResult(stringWriter);
	transformer.transform(domSource, streamResult);
	return stringWriter.toString();
    }
    
    
    public static String saveStoreProperties(Map<String, Boolean> stores, String filename) 
	    throws ParserConfigurationException, TransformerException, FileNotFoundException {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.newDocument();
        
	Element root = dom.createElement("stores");
	root.setAttribute("amount", (new Integer(stores.size())).toString());
        dom.appendChild(root);
        
	for (Map.Entry<String, Boolean> entry: stores.entrySet()) {
	    Element heElem = createStoreEntryElement(dom, entry);
	    root.appendChild(heElem);
	}
	
	String xml = writeDocument(dom);
	
	if (filename != null && !filename.isEmpty()) {
	    try (PrintWriter writer = new PrintWriter(filename)) {
		writer.print(xml);
	    }
	}
	
	return xml;
    }

    private static Element createStoreEntryElement(Document dom, Entry<String, Boolean> entry) {
	Element store = dom.createElement("store");
	store.setAttribute("name", entry.getKey());
	store.setAttribute("selected", entry.getValue().toString());
	return store;
    }

    public static String saveProducts(List<Product> products, File file) 
	    throws ParserConfigurationException, TransformerException, IOException {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.newDocument();
        
	Element root = dom.createElement("products");
	root.setAttribute("amount", (new Integer(products.size())).toString());
        dom.appendChild(root);
        
	for (Product product : products) {
	    Element heElem = product.toXML(dom);
	    root.appendChild(heElem);
	}
	
	String xml = writeDocument(dom);
	
	if (file != null) {
	    try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
		writer.print(xml);
	    }
	}
	
	return xml;
    }

    public static void saveProductList(List<Product> products, String filename)
	    throws FileNotFoundException {
	try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))) {
	    encoder.writeObject(products);
	}
    }

}
