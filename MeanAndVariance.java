
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class MeanAndVariance {

 
    public static int N_MAX;
    public static List<Double> fnList = new LinkedList<Double>();
    public static Double u[] = new Double[10010];
    public static Double s2[] = new Double[10010];
    public static int Narray[]=new int[10010];
    public static void main(String[] args) throws IOException {
    	
        readFile();
        calcU();
        calcS2();
        writeFile();
    }

    public static void readFile() throws FileNotFoundException, IOException {
        File f = new File("histogram");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String s = "";
        
        int k=0;
        while ((s = br.readLine()) != null) {
            
            int n = Integer.parseInt(s.split(",")[0]);
            Narray[k]=n;
            double fn = Double.parseDouble(s.split(",")[1]);
            
            fnList.add(fn);
            k++;
        }
        br.close();
        N_MAX=fnList.size();
    }

    public static void writeFile() throws IOException {
        File f = new File("k_mean_variance.txt");
        //System.out.println("write");
        FileWriter fr = new FileWriter(f,true);
        BufferedWriter br = new BufferedWriter(fr);
        System.out.println("Value of k: ");
        Scanner reader = new Scanner(System.in);
		int simNumber = reader.nextInt();
             
            br.write(simNumber+","+u[N_MAX]+","+s2[N_MAX]);
            br.newLine();
        reader.close();
        br.close();
        //System.out.println("write");
    }

    private static void calcU() {
        u[0] = 0.0;
        for (int n = 1; n <= N_MAX; n++) {
            u[n] = u[n - 1] + (Narray[n-1] * fnList.get(n - 1));
        }
    }

    private static void calcS2() {
        s2[0] = 0.0;
        for (int n = 1; n <= N_MAX; n++) {
            s2[n] = s2[n - 1] + (Narray[n-1] * Narray[n-1] * fnList.get(n - 1));
        }
       
            s2[N_MAX] = s2[N_MAX] - (u[N_MAX] * u[N_MAX]);
        
    }
    
}





