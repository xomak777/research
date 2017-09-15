import java.io.File;

public class FileWorker {
 
	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	
	 public static void main(String[] args) {
		 	final File folder = new File("D:\\ProgramFiles\\CCCC\\.cccc");
			//final File folder = new File("/home/innopolis/Desktop");
			listFilesForFolder(folder);
	    }


	
}
