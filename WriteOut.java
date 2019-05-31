import java.io.BufferedWriter;
import java.io.IOException;

public class WriteOut{
    public void printConsole(String s) {
	System.out.println(s);
    }
    public void appendFile(BufferedWriter bw, String str) {
    	try {
    	    bw.append(str);
    	    
    		
    	    bw.append("\n");
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
        }
    
}