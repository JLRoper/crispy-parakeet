/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jacob
 */
public class CsvFileWriter {

    private Path workingDir = Paths.get("C:/Scriptlets/");
    private Path saveFile = Paths.get("TESTSAVE.csv");

    public static void main(String[] args) throws Exception {

        FileWriter writer = new FileWriter(Paths.get("C:/Scriptlets/tempCsv.csv").toFile());

        writer.append("THIS IS A TEST, CSV1, CSV3");
        writer.write("THIS IS USING WRITE");
        writer.close();
    }

    public class MyFile {

        private Path path;
        private File file;
        private FileWriter writer;

        public void MyFile(String fileName) {
            this.path = Paths.get("C:/Scriptlets/datadump/" + fileName);
            this.file = this.path.toFile();
            System.out.println("File Name: " + path.getFileName());
            System.out.println("File System: " + path.getFileSystem());
            System.out.println("Root: " + path.getRoot());
            System.out.println("Parent: " + path.getParent());
        }
        
        public void write(Object writeMe){
            
            
        }
        
    }
}
