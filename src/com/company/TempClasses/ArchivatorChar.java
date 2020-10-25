package com.company.TempClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class ArchivatorChar  {
    public static void compress(String str) throws IOException {
        HuffmanTreeChar huffmanTreeChar = new HuffmanTreeChar();
        Path path = Paths.get(str);
        String s = Files.readString(path);
        HashMap<Character,Integer> mapOfChars= new HashMap<>();
        for (Character character:s.toCharArray()){
            if (mapOfChars.containsKey(character)){
                mapOfChars.replace(character,mapOfChars.get(character)+1);
            } else {
                mapOfChars.put(character,1);
            }
        }
        for (var it:mapOfChars.entrySet()){
            huffmanTreeChar.add(new NodeChar(it.getValue(),it.getKey()));
        }
        huffmanTreeChar.toTree();
        HashMap <Character,String> mapOfBytes = huffmanTreeChar.BinaryAlphabet();
        String bitString = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<s.length();i++){
            //bitString+=mapOfBytes.get(bytes[i]);
            stringBuilder.append(mapOfBytes.get(s.charAt(i)));
        }
        String s1="";
        byte a [] = new byte[stringBuilder.length()/8+1];
        for(int i=0;i<stringBuilder.length();i+=8){
            if (i+8<stringBuilder.length()){
                a[i/8] =  (byte) Integer.parseInt(stringBuilder.substring(i,i+8),2);
            }  else {
                a[i/8] =  (byte) Integer.parseInt(stringBuilder.substring(i),2);
                s1+=stringBuilder.substring(i).length();
            }
        }
        s1+=s.length();
        //s1+=mapOfBytes.toString();

        String keys ="";
        String values="";
        for (var it: mapOfBytes.entrySet()){
            keys+=it.getKey();
            values+=(it.getValue()+' ');
        }

        Files.createFile(path.toAbsolutePath().getParent().resolve("keys.txt"));
        Files.createFile(path.toAbsolutePath().getParent().resolve("values.txt"));
        Files.createFile(path.toAbsolutePath().getParent().resolve("maps.txt"));

        Files.writeString(path.toAbsolutePath().getParent().resolve("keys.txt"),keys);
        Files.writeString(path.toAbsolutePath().getParent().resolve("values.txt"),values.trim());
        Files.writeString(path.toAbsolutePath().getParent().resolve("maps.txt"),s1);
        Path pathFile = path.toAbsolutePath().getParent().resolve("zipped.bin");
        Files.createFile(pathFile);
        Files.write(pathFile,a);
    }

    public static void extract() throws IOException {
        Path pathOfFile = Paths.get("zipped.bin");
        byte [] a = Files.readAllBytes(pathOfFile);
        Path path = Paths.get("maps.txt");
        String s = Files.readString(path);

        String keys= Files.readString(Paths.get("keys.txt"));
        String values = Files.readString(Paths.get("values.txt"));
        String [] vals = values.split(" ");
        HashMap <String,Character> testmap =new HashMap<>();
        for (int i =0;i<vals.length;i++){
            testmap.put(vals[i],keys.charAt(i));
        }
        int lengthOfLastByte = Integer.parseInt(s.substring(0,1));
        s = s.substring(1);
        int countOfBytes = Integer.parseInt(s);
        /*s = s.substring(s.indexOf('{'));
        s = s.substring(0,s.lastIndexOf('{'));
        s = s.replaceAll("[{ }]","");
        String [] strings = s.split(",");
        HashMap<String,String> StringByteHash = new HashMap<>();
        for (String string:strings){
            String [] ss = string.split("=");
            StringByteHash.put(ss[1],ss[0]);
        }*/
        String newString = "";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0;i<a.length-1;i++){
            String s1 = Integer.toBinaryString(a[i] & 0xFF);
            int s1Length = s1.length();
            if (s1Length!=8){
                for (int j=s1.length();j<8;j++){
                    s1 = "0"+s1;
                }
            }
            stringBuilder.append(s1);
        }
        String lastByte = Integer.toBinaryString(a[a.length-1] & 0xFF);
        for (int i=0;i<lengthOfLastByte - lastByte.length();i++){
            lastByte="0"+lastByte;
        }
        //newString+=lastByte;
        stringBuilder.append(lastByte);

        StringBuilder stringBuilderFile = new StringBuilder();
        String key="";
        for (Character bit: stringBuilder.toString().toCharArray()){
            key+=bit;
            if (testmap.containsKey(key)){
                stringBuilderFile.append(testmap.get(key));
                key="";
            }
        }
        Files.createFile(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\original1.txt"));
        Files.writeString(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\original1.txt"),stringBuilderFile).toString();
    }

    public static void main(String[] args) throws IOException {
        compress("Test.txt");
        extract();
    }
}
