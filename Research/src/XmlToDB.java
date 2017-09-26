/**
 * Created by 1 on 16.09.2017.
 */

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.*;

public class XmlToDB {
    String metricTable = "metric_store";
    String regionTable = "region";
    String fileName = null;
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String name = "postgres";
    String password = "1";


    public XmlToDB(String fileName) {
        this.fileName = fileName;
        File file = new File(fileName);

        if (file.exists()) {
            // start processing main file
            processFileToDB(file);
        } else {
            System.out.println("Main cccc file does not exists");
        }

    }

    public void processFileToDB(File file) {


        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String name = "postgres";
        String password = "1";

        try {

        } catch (Exception ex) {
            System.out.print(ex.getMessage().toString());
            //выводим наиболее значимые сообщения

        } finally {

        }


        try {


            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            //Создаём соединение
            connection = DriverManager.getConnection(url, name, password);
            System.out.println("Соединение установлено");
            //Для использования SQL запросов существуют 3 типа объектов:
            //1.Statement: используется для простых случаев без параметров
            Statement statement = null;
            // File Path
            // For out
            Statement stmt = null;


            Reader fileReader = new FileReader(file);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileReader);
            System.out.print("we are ready to process");
            HashMap<Integer, Integer> codeMetricsRegions = new HashMap<Integer, Integer>();
            Integer currentID = null;
            Integer parentID = null;
            String regName = "";
            String regLanguage = "";
            String regType = "";
            Date timeStamp=new Timestamp(0);
            SimpleDateFormat dateFormatout = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss.S");





            while (xmlEventReader.hasNext()) {

                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {

                    StartElement startElement = xmlEvent.asStartElement();
                    System.out.println("Start Element: " + startElement.getName());
                    if (startElement.getName().getLocalPart().equals("SMEF")) {
                        StartElement startElementmetrics = xmlEvent.asStartElement();
                        Iterator<Attribute> attributes = startElementmetrics.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute myAttribute = attributes.next();

                            if (myAttribute.getName().toString().equals("extractionTimestamp")) {
                               // String timestamp = (xmlEvent.asCharacters().getData());


                                //timeStamp = myAttribute.getValue();

                                timeStamp = dateFormatout.parse(myAttribute.getValue());
                                System.out.print(timeStamp);
                            }
                        }
                    }

                    if (startElement.getName().getLocalPart().equals("Region")) {
                        StartElement startElementmetrics = xmlEvent.asStartElement();
                        Iterator<Attribute> attributes = startElementmetrics.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute myAttribute = attributes.next();

                            if (myAttribute.getName().toString().equals("parentID")) {
                                parentID = Integer.parseInt(myAttribute.getValue());
                                System.out.print("pid777"+parentID);
                            }
                            if (myAttribute.getName().toString().equals("id")) {
                                currentID = Integer.parseInt(myAttribute.getValue());
                            }
                            if (myAttribute.getName().toString().equals("language")) {
                                regLanguage = myAttribute.getValue();
                            }
                            if (myAttribute.getName().toString().equals("name")) {
                                regName = myAttribute.getValue();
                            }
                            if (myAttribute.getName().toString().equals("type")) {
                                regType = myAttribute.getValue();
                            }
                        }

                        try {
                            Class.forName("org.postgresql.Driver");

                            connection.setAutoCommit(false);
                            System.out.println("Opened database successfully");
                            //prepared statments
                           // PreparedStatement ps = connection.prepareStatement("")
                             //       ps.setTimestamp(1, new Timestamps(xxxx));
                            PreparedStatement preparedStatement = connection.prepareStatement(
                                    "INSERT INTO "+regionTable+
                                            "(parent_id,extraction_time_stamp,language,type,name) VALUES(?,?,?,?,?) RETURNING id;"
                            );
                            System.out.print("PID:"+codeMetricsRegions.get(parentID));
                           if (parentID.equals(0)){preparedStatement.setInt(1, parentID);}
                            else { preparedStatement.setInt(1, codeMetricsRegions.get(parentID));}


                            //preparedStatement.setInt(1, 1);
                            preparedStatement.setTimestamp(2, new Timestamp(timeStamp.getTime()));
                            preparedStatement.setInt(3,Integer.parseInt(regLanguage));
                            preparedStatement.setInt(4,Integer.parseInt(regType));
                            preparedStatement.setString(5,regName);


                            System.out.print(preparedStatement);


                            ResultSet rs = preparedStatement.executeQuery();
                            rs.next();
                            System.out.println("Result" + rs.getInt(1));
                            codeMetricsRegions.put(currentID, rs.getInt(1));


                            stmt.close();

                            connection.commit();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());


                        }


                    }


                    if (startElement.getName().getLocalPart().equals("Metric")) {
                        // Get the 'id' attribute from Employee element
                        //sss


                        String code = "";
                        String value = "";
                        StartElement startElementmetrics = xmlEvent.asStartElement();

                        Iterator<Attribute> attributes = startElementmetrics.getAttributes();

                        while (attributes.hasNext()) {
                            Attribute myAttribute = attributes.next();

                            if (myAttribute.getName().toString().equals("code")) {

                                code = myAttribute.getValue();
                                System.out.println("code" + code);

                            }
                            if (myAttribute.getName().toString().equals("value")) {

                                value = myAttribute.getValue();
                                System.out.println("value" + value);

                            }

                        }


                        try {
                            Class.forName("org.postgresql.Driver");

                            connection.setAutoCommit(false);
                            System.out.println("Opened database successfully");
                            //int currentDbId= codeMetricsRegions.get(currentID);
                            //System.out.println("cuurdbID"+currentDbId);

                            stmt = connection.createStatement();
                            String sql = "INSERT INTO " + metricTable + " (region_id,metric_code,value) "
                                    + "VALUES ("+codeMetricsRegions.get(currentID)+", " + code + ", " + value + " ) RETURNING id;";
                            System.out.print(sql);




                            ResultSet rs = stmt.executeQuery(sql);
                            rs.next();
                            System.out.println("Result" + rs.getInt(1));



                            stmt.close();

                            connection.commit();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());


                        }


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {

                }
            }

        }

    }


    public static void main(String[] args) {
        XmlToDB xmlToDBInstance = new XmlToDB("output2.xml");


    }

}
