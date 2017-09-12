import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ccccProcessor {
	//omen xml
	//convert to one xml 
	//store new xml
	
	//try to find cccc.xml 
	//if no - error
	public ccccProcessor(String folderName) {

		File file = new File(folderName);
	    
		if (file.isDirectory()) {
		    //our main file
		    file = new File(folderName+"/cccc.xml");
		    
		    if (file.exists()) {
		    	//start processing main file
		    	processMainFile(file);
		    }
		    else {
		    	System.out.println("file not exists");
		    }
		    
		    
		} // 
		else {
			System.out.println("Files is not a dir!");
		}
	}
	
	public void processMainFile(File file) {
		System.out.println("Main file processing");
		//work with xml 
		
		System.out.println("Main file processed");
		//open main cccc main and create new file 
		
	}
	public void processModules() {}
	
	 public static void main(String[] args) {
			new ccccProcessor(  "/home/innopolis/Downloads/tensorflow-master/tensorflow/cc/client/.cccc");
	    }
}
