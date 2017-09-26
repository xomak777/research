import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ccccProcessor {
	private String folderName = "";
	// open xml
	// convert to one xml
	// store new xml

	// try to find cccc.xml
	// if no - error
	public ccccProcessor(String folderName) {
		this.folderName=folderName ;
		File file = new File(folderName);

		if (file.isDirectory()) {
			// our main file
			file = new File(this.folderName+"/cccc.xml");

			if (file.exists()) {
				// start processing main file
				processMainFile(file);
			} else {
				System.out.println("Main cccc file does not exists");
			}

		} //
		else {
			System.out.println("Files is not a dir!");
		}
	}

	public void processMainFile(File file) {
		System.out.println("Main file processing");

		boolean Boolproject_summary = false;
		int RegionId = 1;
		ArrayList<String> modules = new ArrayList<String>();

		HashMap<String, Integer> codeMetricsdict = new HashMap<String, Integer>();
		codeMetricsdict.put("number_of_modules", 1);
		codeMetricsdict.put("lines_of_code", 2);
		codeMetricsdict.put("lines_of_code_per_module", 3);
		codeMetricsdict.put("McCabes_cyclomatic_complexity", 4);
		codeMetricsdict.put("McCabes_cyclomatic_complexity_per_module", 5);
		codeMetricsdict.put("lines_of_comment", 6);
		codeMetricsdict.put("lines_of_comment_per_module", 7);
		codeMetricsdict.put("lines_of_code_per_module", 8);
		codeMetricsdict.put("lines_of_code_per_line_of_comment", 9);
		codeMetricsdict.put("McCabes_cyclomatic_complexity_per_line_of_comment", 10);
		codeMetricsdict.put("IF4", 11);
		codeMetricsdict.put("IF4_per_module", 12);
		codeMetricsdict.put("IF4_visible", 13);
		codeMetricsdict.put("IF4_concrete", 14);
		codeMetricsdict.put("IF4_visible_per_module", 15);
		codeMetricsdict.put("rejected_lines_of_code", 16);
		codeMetricsdict.put("lines_of_code_per_member_function", 17);
		codeMetricsdict.put("McCabes_cyclomatic_complexity_per_member_function", 18);
		codeMetricsdict.put("weighted_methods_per_class_unity", 19);
		codeMetricsdict.put("weighted_methods_per_class_visibility", 20);
		codeMetricsdict.put("number_of_children", 21);
		codeMetricsdict.put("coupling_between_objects", 22);
		codeMetricsdict.put("lines_of_code_number_of_childrenper_member_function", 23);
		codeMetricsdict.put("depth_of_inheritance_tree", 24);
		codeMetricsdict.put("IF4_per_member_function", 25);
		codeMetricsdict.put("IF4_concrete_per_member_function", 26);
		codeMetricsdict.put("IF4_visible_per_member_function", 27);

		HashMap<String, Integer> languageDict = new HashMap<String, Integer>();
		languageDict.put("C++",1);

		HashMap<String, Integer> typeDict = new HashMap<String, Integer>();
		typeDict.put("Module",1);






		try {
			// File Path
			// For out
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
			XMLStreamWriter writer = factory.createXMLStreamWriter(new FileWriter("output2.xml", true));

			// Read XML file.
			Reader fileReader = new FileReader(file);

			// Get XMLInputFactory instance.
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			// Create XMLEventReader object.
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileReader);

			// Iterate through events.
			while (xmlEventReader.hasNext()) {
				// Get next event.
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				// Check if event is the start element.
				if (xmlEvent.isStartElement()) {
					// Get event as start element.
					StartElement startElement = xmlEvent.asStartElement();
					System.out.println("Start Element: " + startElement.getName());

					if (startElement.getName().getLocalPart().equals("timestamp")) {
						// Get the 'id' attribute from Employee element
						//sss
						xmlEvent = xmlEventReader.nextEvent();

						// date format in
						// SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMM d H:m:s y");
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd HH:mm:ss yyyy");
						Date date = dateFormat.parse(xmlEvent.asCharacters().getData());

						SimpleDateFormat dateFormatout = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss.S");

						String timestamp = (xmlEvent.asCharacters().getData());
						System.out.println(date);
						System.out.println(dateFormatout.format(date));

						// XMLStreamWriter writer = new
						// IndentingXMLStreamWriter(xmlof.createXMLStreamWriter(out));

						try {

							writer.writeStartDocument();
							writer.writeCharacters(System.getProperty("line.separator"));
							writer.writeStartElement("SMEF");
							writer.writeAttribute("extractionTimestamp", dateFormatout.format(date));

							// writer.flush();
							//// writer = factory.createXMLStreamWriter(
							// new FileWriter(
							// "output2.xml",true));

						} catch (XMLStreamException e) {
							e.printStackTrace();
						}

					}

					if (startElement.getName().getLocalPart().equals("project_summary")) {


						writer.writeStartElement("Region");
						writer.writeAttribute("id", RegionId + "");
						RegionId++;
						writer.writeAttribute("parentID", "0");
						//writer.writeAttribute("language", "C++");
						writer.writeAttribute("language", "1");
						//writer.writeAttribute("type", "Project");
						writer.writeAttribute("type", "1");

						// where take name ?
						writer.writeAttribute("name", "ProjectSummary");
						xmlEvent = xmlEventReader.nextEvent();

						System.out.println(xmlEvent.toString());
						writer.writeCharacters(System.getProperty("line.separator"));
						writer.writeStartElement("Metrics");

						while (!Boolproject_summary) {

							xmlEvent = xmlEventReader.nextEvent();

							if (xmlEvent.isStartElement()) {
								writer.writeCharacters(System.getProperty("line.separator"));
								writer.writeStartElement("Metric");


								// Get event as start element.
								StartElement startElementmetrics = xmlEvent.asStartElement();


								if  (codeMetricsdict.get(startElementmetrics.getName().getLocalPart())!=null)
								{
									writer.writeAttribute("code",codeMetricsdict.get(startElementmetrics.getName().getLocalPart().toString()).toString());
								}
								else {
									writer.writeAttribute("code", "1111111");
								}

								// choose appropriate name using map
								//writer.writeAttribute("name", startElementmetrics.getName().toString());

								Iterator<Attribute> attributes = startElementmetrics.getAttributes();
								while (attributes.hasNext()) {
									Attribute myAttribute = attributes.next();


									if (myAttribute.getName().toString().equals("value")) {

										if (myAttribute.getValue().matches("^\\*+") || myAttribute.getValue().matches("^-+")){
											writer.writeAttribute("value", "0");
										}
										else {
											writer.writeAttribute("value", myAttribute.getValue());
										}



									}
								}
								if (startElementmetrics.getName().getLocalPart().equals("lines_of_code")) {

								}
								//if (codeMetricsdict.containsKey(startElementmetrics.getName().getLocalPart())) {
//
								//	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
								//}

							}
							if (xmlEvent.isEndElement()) {
								writer.writeEndElement();
							}

							// check if we still inside
							if (xmlEvent.isEndElement()) {
								Boolproject_summary = xmlEvent.asEndElement().getName().toString()
										.equals("project_summary");
							}

						}

					}
					// procedural_summary
					// get all modules, iterate for each file
					// open file
					// process file
					// module summary
					// close file
					if (startElement.getName().getLocalPart().equals("procedural_summary")) {
						boolean procSummary = false;

						while (!procSummary) {

							xmlEvent = xmlEventReader.nextEvent();
							if (xmlEvent.isStartElement()) {
								StartElement startElemenmodule = xmlEvent.asStartElement();
								if (startElemenmodule.getName().getLocalPart().equals("name")) {
									xmlEvent = xmlEventReader.nextEvent();

									modules.add(xmlEvent.asCharacters().getData());

									System.out.println(modules.toString());

								}

							}

							if (xmlEvent.isEndElement()) {
								procSummary = xmlEvent.asEndElement().getName().toString().equals("procedural_summary");
							}
						}
						
						if(!modules.isEmpty()) {
							for (String modName : modules) {


								Reader moduleFileReader = new FileReader(this.folderName+"\\"+modName+".xml");

								// Get XMLInputFactory instance.
								XMLInputFactory xmlInputFactoryModule = XMLInputFactory.newInstance();

								// Create XMLEventReader object.
								XMLEventReader xmlEventReaderModuleFile =null;
								xmlEventReaderModuleFile=xmlInputFactoryModule.createXMLEventReader(moduleFileReader);
								//same as for main 

								writer.writeStartElement("Region");
								writer.writeAttribute("id", RegionId + "");
								RegionId++;
								writer.writeAttribute("parentID", "1");
								//writer.writeAttribute("language", "C++");
								writer.writeAttribute("language", "1");
								//writer.writeAttribute("type", "module");
								writer.writeAttribute("type", "2");
								// where take name ?
								writer.writeAttribute("name", modName);

								while (xmlEventReaderModuleFile.hasNext()) {
									// Get next event.
									XMLEvent xmlEventmodule = xmlEventReaderModuleFile.nextEvent();

									if (xmlEventmodule.isStartElement()) {
										// Get event as start element.
										 startElement = xmlEventmodule.asStartElement();

										if (startElement.getName().getLocalPart().equals("module_summary")) {


											xmlEventmodule = xmlEventReaderModuleFile.nextEvent();

											System.out.println(xmlEvent.toString());
											writer.writeCharacters(System.getProperty("line.separator"));
											writer.writeStartElement("Metrics");
											Boolproject_summary=false;
											while (!Boolproject_summary) {
												xmlEventmodule = xmlEventReaderModuleFile.nextEvent();


												if (xmlEventmodule.isStartElement()) {
													writer.writeCharacters(System.getProperty("line.separator"));
													writer.writeStartElement("Metric");
													//
													// writer.writeAttribute("code", "1");




													// Get event as start element.
													StartElement startElementmetrics = xmlEventmodule.asStartElement();
													// choose appropriate name using map
													if  (codeMetricsdict.get(startElementmetrics.getName().getLocalPart())!=null)
													{
														writer.writeAttribute("code",codeMetricsdict.get(startElementmetrics.getName().getLocalPart().toString()).toString());
													}
													else {
														writer.writeAttribute("code", "1111111");
													}

													//writer.writeAttribute("name", startElementmetrics.getName().toString());

													Iterator<Attribute> attributes = startElementmetrics.getAttributes();
													while (attributes.hasNext()) {
														Attribute myAttribute = attributes.next();
														if (myAttribute.getName().toString().equals("value")) {

															if (myAttribute.getValue().matches("^\\*+") ||myAttribute.getValue().matches("^-+")){
																writer.writeAttribute("value", "0");
															}
															else {
																writer.writeAttribute("value", myAttribute.getValue());
															}

														}
													}
												}
												if (xmlEventmodule.isEndElement()) {
													writer.writeEndElement();
												}

												// check if we still inside
												if (xmlEventmodule.isEndElement()) {
													Boolproject_summary = xmlEventmodule.asEndElement().getName().toString()
															.equals("module_summary");
												}
											}
										}
									}
									if (xmlEventmodule.isEndElement()) {

										if (xmlEventmodule.asEndElement().getName().toString().equals("project_summary")) { // end Region
											Boolproject_summary = false;
											writer.writeEndElement();

										}
										// Get event as end element.
										EndElement endElement = xmlEventmodule.asEndElement();
										System.out.println("End Element: " + endElement.getName());
									}

								}


								writer.writeEndElement();

								//write new child region with functions
								//
								
							}
							
							
						}
						///go through each module 
						//open /process // write 
						
						
						
					}
					// Iterate and process attributes.
					Iterator iterator = startElement.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = (Attribute) iterator.next();
						QName name = attribute.getName();
						String value = attribute.getValue();
						System.out.println("Attribute name: " + name);
						System.out.println("Attribute value: " + value);
					}
				}

				// Check if event is the end element.
				if (xmlEvent.isEndElement()) {

					if (xmlEvent.asEndElement().getName().toString().equals("project_summary")) { // end Region
						Boolproject_summary = false;
						writer.writeEndElement();

					}
					// Get event as end element.
					EndElement endElement = xmlEvent.asEndElement();
					System.out.println("End Element: " + endElement.getName());
				}
			}

			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		System.out.println("Main file processed");
		// open main cccc main and create new file
	}

	public void processModules() {
	}

	public static void main(String[] args) {
		// new ccccProcessor(
		// "/home/innopolis/Downloads/tensorflow-master/tensorflow/cc/client/.cccc");
		new ccccProcessor("D:\\\\ProgramFiles\\\\CCCC\\\\.cccc");
	}
}
