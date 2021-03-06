/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jacob
 */
public class JamesTest {

    public static void main(String args[]) {
        List<String> testFileList = new ArrayList<>();
        testFileList.add("test.txt");
        testFileList.add("test3.txt");
        testFileList.add("ttt.txt");

        new JamesTest().findAllFiles("C:\\TestDirectory\\", testFileList);
    }

    private JamesTest() {
    }

    public static List<File> findAllFiles(String rootDir, List<String> filesNames) {

        List<File> fileList = new ArrayList<>();
        Map<String, File> tempMap = new HashMap<>();
        Collection<File> fileColl = FileUtils.listFiles(new File(rootDir), null, true);

        fileColl.stream().forEach((file) -> {
            tempMap.put(file.getName().toUpperCase(), file);
        });

        File tmpFile;
        for (String fileName : filesNames) {
            tmpFile = tempMap.get(fileName.toUpperCase());
            if (tmpFile == null) {
                System.out.println("Unabale to find " + fileName + " in " + rootDir);

            } else {
                System.out.println("Found file: " + tmpFile.getPath());

                fileList.add(tmpFile);
            }
        }

        return fileList;
    }

}
