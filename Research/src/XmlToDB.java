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
import java.util.Iterator;
import java.util.logging.*;

public class XmlToDB {
    String metricTable = "metric_store";
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
            try {
                Class.forName("org.postgresql.Driver");

                connection.setAutoCommit(false);
                System.out.println("Opened database successfully");

                stmt = connection.createStatement();
                String sql = "INSERT INTO "+metricTable+" (region_id,metric_code,value) "
                        + "VALUES (1, 1, 32 );";
                stmt.executeUpdate(sql);
            }
            catch (Exception e){
                System.out.println(e.getMessage());


            }

            Reader fileReader = new FileReader(file);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileReader);
            System.out.print("we are ready to process");


            while (xmlEventReader.hasNext()) {

                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    // Get event as start element.
                    StartElement startElement = xmlEvent.asStartElement();
                    System.out.println("Start Element: " + startElement.getName());

                    if (startElement.getName().getLocalPart().equals("SMEF")) {
                        // Get the 'id' attribute from Employee element

                        StartElement startElementmetrics = xmlEvent.asStartElement();
                        Iterator<Attribute> attributes = startElementmetrics.getAttributes();
                        xmlEvent = xmlEventReader.nextEvent();
                    }

                    if (startElement.getName().getLocalPart().equals("Metric")) {
                        // Get the 'id' attribute from Employee element
                        //sss
                        xmlEvent = xmlEventReader.nextEvent();
                    }

                }
                else{

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


        //open file
        //connect to db
        //

    }


    public static void main(String[] args) {
        XmlToDB xmlToDBInstance = new XmlToDB("output2.xml");

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String name = "postgres";
        String password = "1";

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

            statement = connection.createStatement();
            //Выполним запрос
            // ResultSet result1 = statement.executeQuery(
            //          "SELECT * FROM users where id >2 and id <10");
            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать
            //метод next() , с помощью которого переходим к следующему элементу
            // System.out.println("Выводим statement");
            // while (result1.next()) {
            //      System.out.println("Номер в выборке #" + result1.getRow()
            //              + "\t Номер в базе #" + result1.getInt("id")
            //              + "\t" + result1.getString("username"));
            //   }
            // Вставить запись
            // statement.executeUpdate(
            //         "INSERT INTO users(username) values('name')");
            //Обновить запись
            // statement.executeUpdate(
            //         "UPDATE users SET username = 'admin' where id = 1");


            //2.PreparedStatement: предварительно компилирует запросы,
            //которые могут содержать входные параметры
            // PreparedStatement preparedStatement = null;
            // ? - место вставки нашего значеня
            //  preparedStatement = connection.prepareStatement(
            //          "SELECT * FROM users where id > ? and id < ?");
            //Устанавливаем в нужную позицию значения определённого типа
            //   preparedStatement.setInt(1, 2);
            //  preparedStatement.setInt(2, 10);
            //выполняем запрос
            //  ResultSet result2 = preparedStatement.executeQuery();

            //   System.out.println("Выводим PreparedStatement");
            //   while (result2.next()) {
            //        System.out.println("Номер в выборке #" + result2.getRow()
            //                + "\t Номер в базе #" + result2.getInt("id")
            //               + "\t" + result2.getString("username"));
            //    }

            //    preparedStatement = connection.prepareStatement(
            //            "INSERT INTO users(username) values(?)");
            //     preparedStatement.setString(1, "user_name");
            //метод принимает значение без параметров
            //темже способом можно сделать и UPDATE
            //    preparedStatement.executeUpdate();


            //3.CallableStatement: используется для вызова хранимых функций,
            // которые могут содержать входные и выходные параметры
            //    CallableStatement callableStatement = null;
            //Вызываем функцию myFunc (хранится в БД)
            //    callableStatement = connection.prepareCall(
            //           " { call myfunc(?,?) } ");
            //Задаём входные параметры
            //    callableStatement.setString(1, "Dima");
            //    callableStatement.setString(2, "Alex");
            //Выполняем запрос
            //    ResultSet result3 = callableStatement.executeQuery();
            //Если CallableStatement возвращает несколько объектов ResultSet,
            //то нужно выводить данные в цикле с помощью метода next
            //у меня функция возвращает один объект
            //     result3.next();
            //    System.out.println(result3.getString("MESSAGE"));
            //если функция вставляет или обновляет, то используется метод executeUpdate()

        } catch (Exception ex) {
            System.out.print(ex.getMessage().toString());
            //выводим наиболее значимые сообщения

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {

                }
            }
        }

    }

}
