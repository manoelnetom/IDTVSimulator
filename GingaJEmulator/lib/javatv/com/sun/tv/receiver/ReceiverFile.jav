/**
 * @(#)ReceiverFile.java	1.00 2000/04/05
 *
 * Copyright 2000 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 *
 */

package com.sun.tv.receiver;

import java.io.*;
import java.util.*;
import java.text.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
// import com.sun.xml.*;
// import com.sun.xml.tree.*;
// import com.sun.xml.parser.*;
import com.sun.tv.*;
import com.sun.tv.si.*;
import javax.tv.locator.*;
import javax.tv.service.*;
import javax.tv.service.navigation.*;

public class ReceiverFile implements SampleDataInterface {

private transient static SimpleDateFormat dateTimeFormat =
		 new SimpleDateFormat("MM/dd/yyyy HH:mm" );

private SIEmulator emulator = null;

	private long last_modified = 0;
	private File sample_file = null;

//--------------------------------------------------------------------
public ReceiverFile() {
}

public void play(SIEmulator emulator, String args[]) {
	this.emulator = emulator;

	String filename = Settings.SampleFile;
	if (args != null && args.length >= 1 && args[0] != null) {
		filename = args[0];
	}

 	setFile(sample_file = new File(filename));

 	last_modified = sample_file.lastModified();
 
 	WatcherThread t = new WatcherThread();
 
 	t.start();
}

public void finish() {
	emulator.isCaughtUp();
}

public boolean verify() {
	return true;
}

//--------------------------------------------------------------------
private void setFile(File file) {
	if (file != null) {
		Document document = createDocument(file);
		processDocument(document);
	}
}

//--------------------------------------------------------------------
private Document createDocument(File file) {
	Document document = null;

	/* Jeff::
	try {
		InputSource source = Resolver.createInputSource(file);
		document = XmlDocument.createXmlDocument(source, true);
	} catch (SAXException se) {
		document = null;
		System.out.println( se.getMessage() );
	} catch ( IOException e ) {
		System.out.println( e.getMessage() );
	}
	*/

	return document;
}

//--------------------------------------------------------------------
private long toDate(String str) {
	if (str == null || str.length() == 0) {
		return System.currentTimeMillis();
	} else if (str.equalsIgnoreCase("sysdate")) {
		return System.currentTimeMillis();
	} else if (str.equalsIgnoreCase("current")) {
		return System.currentTimeMillis();
	} else if (str.startsWith("+")) {
		return System.currentTimeMillis() + toLong(str.substring(1)) * 60 * 1000;
	} else if (str.startsWith("-")) {
		return System.currentTimeMillis() - toLong(str.substring(1)) * 60 * 1000;
	}


	try {
		return dateTimeFormat.parse(str).getTime();
	} catch (Exception e) {
		System.err.println("toDate: "+e + ": " + str);
        }
	return 0;
}

//--------------------------------------------------------------------
private long toTransmitTime(String str) {
	if (str.startsWith("+")) {
		return toLong(str.substring(1));
	} else if (str.startsWith("-")) {
		return -toLong(str.substring(1));
	}

/**
	try {
		return dateTimeFormat.parse(str).getTime() / 1000;
	} catch (Exception e) {
		System.err.println("toDate: "+e + ": " + str);
        }
 **/
	return 0;
}

//--------------------------------------------------------------------
private long toLong(String str) {
	try {
		return Long.parseLong(str);
	} catch (Exception e) {
		System.err.println("toLong: "+e + ": " + str);
        }
	return 0;
}

//--------------------------------------------------------------------
private int toInt(String str) {
	if (str == null || str.length() == 0) {
		return 0;
	}

	try {
		return Integer.parseInt(str);
	} catch (Exception e) {
		System.err.println("toInt: "+e + ": " + str);
        }
	return 0;
}

//--------------------------------------------------------------------
private boolean toBoolean(String str) {
	return new Boolean(str).booleanValue();
}

//--------------------------------------------------------------------
private void processDocument(Document document) {
	NodeList nodes = document.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("TestData".equalsIgnoreCase(tagname)) {
			processTestData(element);

		}
	}
}

//--------------------------------------------------------------------
private void processTestData(Element root) {
	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("DataBundle".equalsIgnoreCase(tagname)) {
			processDataBundle(element);
		}
	}
}

//--------------------------------------------------------------------
private void processDataBundle(Element root) {
	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	Element language = null;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();

		if ("TransportStream".equalsIgnoreCase(tagname)) {
			processTransportStream(element);

		} else if ("Network".equalsIgnoreCase(tagname)) {
			processNetwork(element);

		} else if ("Bouquet".equalsIgnoreCase(tagname)) {
			processBouquet(element);

		} else if ("Xlet".equalsIgnoreCase(tagname)) {
			processResidentXlet(element);

		} else if ("FavoriteService".equalsIgnoreCase(tagname)) {
			processFavoriteService(element);

		} else if ("PreferredLanuage".equalsIgnoreCase(tagname)) {
			processPreferredLanguage(element);
			language = element;

		} else if ("RatingDimension".equalsIgnoreCase(tagname)) {
			processRatingDimension(element);

		} else if ("ServiceTransforms".equalsIgnoreCase(tagname)) {
			processServiceTransforms(element);

		} else if ("Service".equalsIgnoreCase(tagname)) {
			processService(element, language);

		} else if ("RemoveList".equalsIgnoreCase(tagname)) {
			processRemoveList(element);

		} else if ("RemoveSIDatabase".equalsIgnoreCase(tagname)) {
			RemoveSIDatabase(element);
		}
	}
}

//--------------------------------------------------------------------
private void processPreferredLanguage(Element root) {
	String language = (String)root.getAttribute("VALUE");

	SIManager siManager = SIManager.createInstance();
	if (siManager != null) {
		try {
			siManager.setPreferredLanguage(language);
		} catch (Exception e) {
			System.out.println("SetPreferredLanguage failed - " + language);
		}
	}
}

//--------------------------------------------------------------------
private void processFavoriteService(Element root) {
	String name = (String)root.getAttribute("NAME");
	String snames = (String)root.getAttribute("SERVICENAMES");
	
    	SIManagerImpl.putFavoriteServices(name, SIEmulator.toStrings(snames, ","));
}

//--------------------------------------------------------------------
private void processRatingDimension(Element root) {
	String ratingName = (String)root.getAttribute("DIMENSIONNAME");

	RatingDimensionImpl rating = new RatingDimensionImpl(ratingName);

	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("Rating".equalsIgnoreCase(tagname)) {
			processRating(element, rating);
		}
	}
}

//--------------------------------------------------------------------
private void processServiceTransforms(Element root) {
	String name = (String)root.getAttribute("NAME");
	String list[] = emulator.toStrings((String)root.getAttribute("LIST"), ",");
	try {
		LocatorFactory factory = LocatorFactory.getInstance();

		Locator locator = factory.createLocator(
			LocatorImpl.ServiceProtocol + name);

		Vector vector = new Vector();
		for (int i = 0; i < list.length; i++) {
			Locator loc = factory.createLocator(
				LocatorImpl.ServiceProtocol + list[i]);
			vector.addElement(loc);
		}
		LocatorImpl.setTransforms(locator, vector);
	} catch (Exception e) {
		System.out.println("ProcessServiceTransforms error: " + e);
	}
}

//--------------------------------------------------------------------
private void processRating(Element root, RatingDimensionImpl rating) {
	String name = (String)root.getAttribute("NAME");
	String desc = (String)root.getAttribute("DESCRIPTION");
	rating.addRatingLevelDescription(name, desc);
}

//--------------------------------------------------------------------
private void processService(Element root, Element language) {
	String serviceName = (String)root.getAttribute("NAME");
	String serviceType = (String)root.getAttribute("SERVICETYPE");
	String sitype = (String)root.getAttribute("SITYPE");
	String url = (String)root.getAttribute("SIMULATION");
	String alternateURL = (String)root.getAttribute("ALTERNATE");
	String desc = (String)root.getAttribute("DESCRIPTION");
	int serviceNumber = toInt((String)root.getAttribute("NUMBER"));
	int serviceMinorNumber = toInt((String)root.getAttribute("MINORNUMBER"));
	String reason = (String)root.getAttribute("CALIMIT");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));

	// TBD: Unused
//	String serviceLanguage = (String)language.getAttribute("LANGUAGE");
//	String serviceLocator = (String)root.getAttribute("LOCATOR");

	emulator.putService(transmitTime,
		serviceName, false, serviceType, sitype,
		serviceNumber, serviceMinorNumber, reason);

	emulator.putServiceDescription(0, serviceName, desc);

	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();

		if ("ServiceDetails".equalsIgnoreCase(tagname)) {
			processServiceDetails(element, serviceName, sitype);
		}
	}

	LocatorImpl.setMediaFile("service:/" + serviceName,  url);
	LocatorImpl.setMediaFile("alternate:/" + serviceName,  alternateURL);
}

//--------------------------------------------------------------------
private void processServiceDetails(Element root, String serviceName, String sitype) {

	String longname = (String)root.getAttribute("LONGNAME");
	String providerName = (String)root.getAttribute("PROVIDERNAME");
	DeliverySystemType deliveryType = SIEmulator.toDeliverySystemType(
			(String)root.getAttribute("DELIVERYSYSTEMTYPE"));
	String desc = (String)root.getAttribute("DESCRIPTION");
	String caSystemIDs = (String)root.getAttribute("CASYSTEMIDS");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));

	emulator.putServiceDetails(transmitTime,
		serviceName, providerName, deliveryType,
		longname, caSystemIDs);

	emulator.putServiceDescription(transmitTime, serviceName, desc);

	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("ProgramSchedule".equalsIgnoreCase(tagname)) {
			processProgramSchedule(element, serviceName, sitype);

		} else if ("ServiceComponent".equalsIgnoreCase(tagname)) {
			processServiceComponent(element, serviceName, null, sitype);

		} else if ("ServiceComponentData".equalsIgnoreCase(tagname)) {
			processServiceComponentData(element, serviceName, null, sitype);
		}
	}
}

//--------------------------------------------------------------------
private void processProgramSchedule(Element root, String serviceName, String sitype) {
	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("ProgramEvent".equalsIgnoreCase(tagname)) {
			processProgramEvent(element, serviceName, sitype);

		}
	}
}

//--------------------------------------------------------------------
private void processServiceComponent(Element root, String serviceName, String programName, String sitype) {
	String componentName = (String)root.getAttribute("NAME");
	String language = (String)root.getAttribute("LANGUAGE");
	StreamType streamType = SIEmulator.toStreamType((String)root.getAttribute("STREAM_TYPE"));
	String url = (String)root.getAttribute("SIMULATION");
	String alternateURL = (String)root.getAttribute("ALTERNATE");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));
	boolean autorun = toBoolean((String)root.getAttribute("AUTORUN"));
	String reason = (String)root.getAttribute("CALIMIT");

	emulator.putServiceComponent(transmitTime,
		componentName, language, streamType,
		serviceName, programName, sitype, autorun, reason);

	LocatorImpl.setMediaFile("component:/" + componentName + "service:/" + serviceName,  url);
	LocatorImpl.setMediaFile("alternate:/" + componentName,  alternateURL);
}

//--------------------------------------------------------------------
private void processServiceComponentData(Element root, String serviceName, String programName, String sitype) {

	String xletName = (String)root.getAttribute("NAME");
	String language = (String)root.getAttribute("LANGUAGE");
	StreamType streamType = StreamType.DATA;
	String xletPath = (String)root.getAttribute("PATH");
	String xletArgs = (String)root.getAttribute("ARGS");
	boolean autorun = toBoolean((String)root.getAttribute("AUTORUN"));
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));
	String reason = (String)root.getAttribute("CALIMIT");

	emulator.putServiceComponent(transmitTime,
		xletName, language, streamType,
		serviceName, programName, sitype, autorun, reason);

	emulator.putServiceXlet(xletName, xletPath, xletArgs);
}

//--------------------------------------------------------------------
private void processProgramEvent(Element root, String serviceName, String sitype) {
	String name = (String)root.getAttribute("NAME");
	String desc = (String)root.getAttribute("DESCRIPTION");
	long duration = toLong((String)root.getAttribute("DURATION"));
        long startTime = toDate((String)root.getAttribute("STARTTIME"));
//	long endTime = toDate((String)root.getAttribute("ENDTIME")); // Not Needed
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));

	Vector dimensionNames = new Vector();
	Vector ratingNames = new Vector();
	processContentRatingAdvisory(root, dimensionNames, ratingNames);

	emulator.putProgramEventAbs(transmitTime,
		name, serviceName, startTime, duration, sitype,
		dimensionNames, ratingNames);

	emulator.putProgramEventDescription(transmitTime, name, desc);

	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("ServiceComponent".equalsIgnoreCase(tagname)) {
			processServiceComponent(element, serviceName, name, sitype);
		}
	}
}

//--------------------------------------------------------------------
private void processContentRatingAdvisory(Element root, Vector dimensions, Vector ratings) {
	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	Vector list = new Vector();
	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();
		if ("ContentRatingAdvisory".equalsIgnoreCase(tagname)) {
			String name = (String)element.getAttribute("DIMENSIONNAME");
			String text = (String)element.getAttribute("RATINGNAME");
// TBD: level is unused.
			String level = (String)element.getAttribute("RATINGLEVEL");

			dimensions.addElement(name);
			ratings.addElement(text);
		}
	}
}

//--------------------------------------------------------------------
private void processTransportStream(Element root) {
	int id = toInt((String)root.getAttribute("ID"));
	String name = (String)root.getAttribute("NAME");
	String sitype = (String)root.getAttribute("SITYPE");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));
	int networkID = toInt((String)root.getAttribute("NETWORKID"));

	emulator.putTransportStream(transmitTime, name, id, sitype, networkID);
}

//--------------------------------------------------------------------
private void processNetwork(Element root) {
	int id = toInt((String)root.getAttribute("ID"));
	String name = (String)root.getAttribute("NAME");
	String sitype = (String)root.getAttribute("SITYPE");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));

	emulator.putNetwork(transmitTime, name, id, sitype);

}

//--------------------------------------------------------------------
private void processBouquet(Element root) {
	int id = toInt((String)root.getAttribute("ID"));
	String name = (String)root.getAttribute("NAME");
	String sitype = (String)root.getAttribute("SITYPE");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));

	emulator.putBouquet(transmitTime, name, id, sitype);

}
//--------------------------------------------------------------------
private void processResidentXlet(Element root) {
	String name = (String)root.getAttribute("NAME");
	String path = (String)root.getAttribute("PATH");
	String argStr = (String)root.getAttribute("ARGS");
        long transmitTime = toTransmitTime((String)root.getAttribute("TRANSMITTIME"));

	String args[] = emulator.toStrings(argStr, ",");

	emulator.putResidentXlet(transmitTime, name, path, args);
}

//--------------------------------------------------------------------
private void processRemoveList(Element root) {
	NodeList nodes = root.getChildNodes();
	if (nodes == null)
		return;

	for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
			continue;

		Element element = (Element)node;
		String tagname = element.getTagName();

		if ("RemoveTransportStream".equalsIgnoreCase(tagname)) {
			int id = toInt((String)element.getAttribute("ID"));
			emulator.removeTransportStream(0, id);

		} else if ("RemoveNetwork".equalsIgnoreCase(tagname)) {
			int id = toInt((String)element.getAttribute("ID"));
			emulator.removeNetwork(0, id);

		} else if ("RemoveBouquet".equalsIgnoreCase(tagname)) {
			int id = toInt((String)element.getAttribute("ID"));
			emulator.removeBouquet(0, id);

		} else if ("RemoveXlet".equalsIgnoreCase(tagname)) {
			String name = (String)element.getAttribute("NAME");
			emulator.removeXlet(0, name);

		} else if ("RemoveService".equalsIgnoreCase(tagname)) {
			String name = (String)element.getAttribute("NAME");
			String reason = (String)element.getAttribute("REASON");

			emulator.removeService(0, name, reason);

		} else if ("RemoveServiceDetails".equalsIgnoreCase(tagname)) {
			String name = (String)element.getAttribute("NAME");
			emulator.removeServiceDetails(0, name);

		} else if ("RemoveServiceComponent".equalsIgnoreCase(tagname)) {
			String name = (String)element.getAttribute("NAME");
			emulator.removeServiceComponent(0, name);

		} else if ("RemoveProgramEvent".equalsIgnoreCase(tagname)) {
			String programName = (String)element.getAttribute("PROGRAM_NAME");
			String serviceName = (String)element.getAttribute("SERVICE_NAME");
			emulator.removeProgramEvent(0, serviceName, programName);
			
		}
	}
}

//--------------------------------------------------------------------
private void RemoveSIDatabase(Element root) {
	boolean genEvents = true;
	emulator.RemoveSIDatabase(0, genEvents);
}

class WatcherThread extends Thread {
	public void run(){
	    long sf_mod = 0;

		while(true){
 			try {
 		    		sleep(10000);
 			} catch (InterruptedException e){
			}

			if(last_modified < 
			   (sf_mod = sample_file.lastModified()) ){
 		    		setFile(sample_file);
 		    		emulator.isCaughtUp();
 		    		last_modified = sf_mod;
			}
		}
	}
}
}
