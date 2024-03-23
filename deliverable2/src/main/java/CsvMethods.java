import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvMethods {
	public static CsvWriter getFileWriter(String path) {
		CsvWriter writer = new CsvWriter(path);
		  try {
			  CsvReader reader = new CsvReader(path);
			  List<String[]> lines = new ArrayList<>();
			  String[] line;
			  
			  while(reader.readRecord()) {
				  lines.add(reader.getValues());
				  System.out.println(lines);
			  }
			  for(String[] string : lines) {
				  writer.writeRecord(string);
			  }
			  
		  }catch(Exception e) {
			  
		  }
		 
		  return writer;
	}
	
	public static void update(List<String[]> content, String path) {
		try {
			CsvWriter writer = new CsvWriter(path);
			for(String[] string : content) {
				writer.writeRecord(string);
		  }
		}catch(Exception e) {
			
		}
		
	}
	
	public static List<String[]> getFileContent(String path) {

		List<String[]> lines = new ArrayList<>();
		  try {
			  CsvReader reader = new CsvReader(path);
			  String[] line;
			  
			  while(reader.readRecord()) {
				  lines.add(reader.getValues());
			  }
			  
		  }catch(Exception e) {
			  
		  }
		  return lines;
	}
}
