package org.ufrpe.inovagovlab.decisoestce.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DelimitersDetRec {

    private List<String> delimitadoresDetRec;

    public DelimitersDetRec(String path) throws FileNotFoundException {
        this.delimitadoresDetRec = this.preencheDelimitadores(path);
    }

    private List<String> preencheDelimitadores(String path) throws FileNotFoundException {
        try {
            File file = new File(path + "/DelimitadoresDetRec.txt");
            Scanner sc = new Scanner(file);
            List<String> delim = new ArrayList<>();

            while (sc.hasNextLine()) {
                delim.add(sc.nextLine());
            }
            sc.close();
            return delim;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getDelimitadoresDetRec() {
        return delimitadoresDetRec;
    }
}
