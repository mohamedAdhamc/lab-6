/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exceptionhandlingassiignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author mohamed
 */
public class ExceptionHandlingAssiignment {

    static String inputFileStringHack = "";
    static int locationFirstContainer = 0;
    
    public static void main(String[] args) {
        
        //no file provided ToDO:  user defined unchecked exception“EmptyAutosarFileException”
        if (args.length <= 0){
             System.out.println("No file name provided.");
             return;
        }
         
        String fileName = args[0];
        
        //wrong file format -- ToDo: user defined check exception "NotValidAutosarFileException"
        if (!fileName.endsWith(".xml")) {
             System.out.println("invalid file");
             return;
        }
        
        
        try{
            
            FileReader fileReader = new FileReader(new File(fileName));
            String inputFileString = "";
            int i = 0;
            while ((i = fileReader.read()) != -1)
                    inputFileString += (char)i;
            String outputFileString = reorganizeXmlStringAlpha(inputFileString);
            //test
            System.out.println(inputFileString);
            fileReader.close();
            } catch (IOException e) {
                // i/o error
        }
    }
        
    static String reorganizeXmlStringAlpha(String inputXml){
        ArrayList<String> containers = sliceUpIntoContainers(inputXml);
        //update inputXml to remove container element from it
        inputXml = inputFileStringHack;
        //logic to then organize these containers based on their whatever
        
        //test
        {
//            System.out.println("attempting to write the containers");
//            for (String container: containers)
//                System.out.println(container);
//                System.out.println("done to write the containers");
        }
        
        TreeMap<String, String> orderedMap = makeContainersOrderedMap(containers);
        //test
        {
//            System.out.println("iterating through containers map");
//            for (String key : orderedMap.keySet()) {
//                System.out.println(key + " => " + orderedMap.get(key));
//            }
        }
        
        //reorder the inpuXml
        for (String key : orderedMap.keySet()) {
                inputXml = inputXml.substring(0, locationFirstContainer) + orderedMap.get(key) +  inputXml.substring(locationFirstContainer);
                locationFirstContainer = locationFirstContainer + orderedMap.get(key).length();
        }
        System.out.println("reordered xml");
        System.out.println(inputXml);
        
        return "blabla";
    }
    
    // a nested container breaks this function.
    // ToDO: handle it or just use a freaking xml parser for the sake of god
    static ArrayList<String> sliceUpIntoContainers(String inputXml){
        //a container always starts with <CONTAINER and ends with </CONTAINER>
        //ToDo: spaces probably don't matter
        boolean firstTime = true;
        ArrayList<String> containers = new ArrayList<String>();
        for (int i=0; i<inputXml.length(); ++i){
            //beginning of an element
            if (inputXml.charAt(i) == '<'){
                //if the next letters are CONTAINER then we have found a container element
                
                //first make sure we won't be stepping out of bounds
                if (i+9 > inputXml.length())
                    continue;
                //make sure we are a container element
                if(!"<CONTAINER".equals(inputXml.substring(i, i+10)))
                    continue;
                
                int seek = i;
                while (seek<inputXml.length() && !"</CONTAINER>".equals(inputXml.substring(seek,seek+12)))
                    ++seek;
                if (seek>inputXml.length())
                    continue;
                containers.add(inputXml.substring(i-1, seek+13));
                if (firstTime){
                    locationFirstContainer = i-1;
                    firstTime = false;
                }
                //delete the container from the inputXml
                inputXml = inputXml.substring(0,i) + inputXml.substring(seek+13, inputXml.length());
            } //else not the beginning of an element so we don't care
        }
        inputFileStringHack = inputXml;
        return containers;
    }
    
    static TreeMap<String, String> makeContainersOrderedMap(ArrayList<String> containers){
        TreeMap<String, String> mapeo = new TreeMap<String, String>();
        for (String container: containers){
            mapeo.put( getContainerShortName(container), container);
        }
        return mapeo;
    }
    
    static String getContainerShortName(String container){
        String shortname = "";
        for (int i = 0 ; i<container.length(); ++i){
            if (container.charAt(i) == '<'){
                //make sure we are a shortname element
                if(!"<SHORT-NAME".equals(container.substring(i, i+11)))
                    continue;
                int seek = i;
                while (seek<container.length() && !"</SHORT-NAME>".equals(container.substring(seek,seek+13)))
                    ++seek;
                if (seek>container.length())
                    continue;
                shortname = container.substring(i, seek+13);
                break;
            }
        }
        return shortname;
    }
        
}
