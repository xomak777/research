import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
	// open xml
	// convert to one xml
	// store new xml

	// try to find cccc.xml
	// if no - error
	public ccccProcessor(String folderName) {

		File file = new File(folderName);

		if (file.isDirectory()) {
			// our main file
			file = new File(folderName + "/cccc.xml");

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
		int RegionId = 0;
		ArrayList<String> modules = new ArrayList<String>();

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
						HashMap<String, Integer> codeMetricsdict = new HashMap<String, Integer>();
						codeMetricsdict.put("number_of_modules", 1);

						writer.writeStartElement("Region");
						writer.writeAttribute("id", RegionId + "");
						writer.writeAttribute("parentID", "0");
						writer.writeAttribute("language", "C++");
						writer.writeAttribute("type", "Project");
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
								writer.writeAttribute("code", "1");

								// Get event as start element.
								StartElement startElementmetrics = xmlEvent.asStartElement();
								// choose appropriate name using map
								writer.writeAttribute("name", startElementmetrics.getName().toString());

								Iterator<Attribute> attributes = startElementmetrics.getAttributes();
								while (attributes.hasNext()) {
									Attribute myAttribute = attributes.next();
									if (myAttribute.getName().toString().equals("value")) {
										writer.writeAttribute("value", myAttribute.getValue());
									}
								}
								if (startElementmetrics.getName().getLocalPart().equals("lines_of_code")) {

								}
								if (codeMetricsdict.containsKey(startElementmetrics.getName().getLocalPart())) {

									System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
								}

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
								Reader moduleFileReader = new FileReader(file);
								//same as for main 
								
								
								//for each module create region - parent = 1 id = idprev ++ 
								
								///File = new(modName+".xml");
//								<module_summary>
//								<lines_of_code value="0" level="0" />
//								<lines_of_code_per_member_function value="******" level="0" />
//								<McCabes_cyclomatic_complexity value="0" level="0" />
//								<McCabes_cyclomatic_complexity_per_member_function value="******" level="0" />
//								<lines_of_code value="0" level="0" />
//								<lines_of_code_per_member_function value="********" level="0" />
//								<lines_of_code_per_line_of_comment value="------" level="0" />
//								<McCabes_cyclomatic_complexity_per_line_of_comment value="------" level="0" />
//								<weighted_methods_per_class_unity value="0" level="0" />
//								<weighted_methods_per_class_visibility value="0" level="0" />
//								<depth_of_inheritance_tree value="0" level="0" />
//								<number_of_children value="0" level="0" />
//								<coupling_between_objects value="0" level="0" />
//								<IF4 value="0" level="0" />
//								<IF4_per_member_function value="********" level="0" />
//								<IF4_visible value="0" level="0" />
//								<IF4_visible_per_member_function value="********" level="0" />
//								<IF4_concrete value="0" level="0" />
//								<IF4_concrete_per_member_function value="********" level="0" />
//								</module_summary>
								//parse and write to the file
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
