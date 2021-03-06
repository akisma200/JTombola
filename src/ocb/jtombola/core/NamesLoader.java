package ocb.jtombola.core;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;


public class NamesLoader {
    private String fileName;
    private List<String> lstNames;

    public NamesLoader(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void loadFile() {
        try {
            FileReader fr = new FileReader(this.fileName);
            BufferedReader br = new BufferedReader(fr);
            lstNames = new ArrayList<>();
            String name;
            while ((name = br.readLine()) != null) {
                lstNames.add(name);
            }


        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<String> getList() {
        if (lstNames == null) {
            this.loadFile();
            
        }
        return lstNames;
    }
    
    public void saveWinners(List<String> list) {
        int num = 0;
        try {

            FileWriter fw = new FileWriter("winners.txt");
            for (String name : list) {

                num++;
                fw.write(name + " - Prize No. " + num);
                fw.write(System.getProperty("line.separator"));

            }
            fw.close();


        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
