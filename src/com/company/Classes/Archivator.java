package com.company.Classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Archivator {

    public static void compress(String str) {
        try {
            Path path = Paths.get(str);
            byte [] bytes = Files.readAllBytes(path);
            System.out.println(bytes.length);
            HashMap <Byte,Integer> hashMap = new HashMap<>();
            for (Byte x:bytes){
                if (hashMap.containsKey(x)){
                    hashMap.replace(x,hashMap.get(x)+1);
                } else {
                    hashMap.put(x,1);
                }
            }
            HuffmanTree huffmanTree =new HuffmanTree();
            for (var it: hashMap.entrySet()){
                huffmanTree.add(new Node(it.getValue(),it.getKey()));
            }
            huffmanTree.toTree();
            HashMap <Byte,String> mapOfBytes = huffmanTree.BinaryAlphabet();
            String bitString = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<bytes.length;i++){
                //bitString+=mapOfBytes.get(bytes[i]);
                stringBuilder.append(mapOfBytes.get(bytes[i]));
            }
            String s="";
           /* byte a [] = new byte[bitString.length()/8+1];
            for(int i=0;i<bitString.length();i+=8){
                if (i+8<bitString.length()){
                  a[i/8] =  (byte) Integer.parseInt(bitString.substring(i,i+8),2);
                }  else {
                    a[i/8] =  (byte) Integer.parseInt(bitString.substring(i),2);
                    s+=bitString.substring(i).length();
                }
            }*/
            byte a [] = new byte[stringBuilder.length()/8+1];
            for(int i=0;i<stringBuilder.length();i+=8){
                if (i+8<stringBuilder.length()){
                  a[i/8] =  (byte) Integer.parseInt(stringBuilder.substring(i,i+8),2);
                }  else {
                    a[i/8] =  (byte) Integer.parseInt(stringBuilder.substring(i),2);
                    s+=stringBuilder.substring(i).length();
                }
            }
            s+=bytes.length;
            s+=mapOfBytes.toString();
            Files.createFile(path.toAbsolutePath().getParent().resolve("maps.txt"));
            Files.writeString(path.toAbsolutePath().getParent().resolve("maps.txt"),s);
            Path pathFile = path.toAbsolutePath().getParent().resolve("zipped.bin");
            Files.createFile(pathFile);
            Files.write(pathFile,a);
            System.out.println(a.length);
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.err.println(ex.toString());
        }


    }
    public static void extract(){
        try {
            Path pathOfFile = Paths.get("zipped.bin");
            byte [] a = Files.readAllBytes(pathOfFile);
            Path path = Paths.get("maps.txt");
            String s = Files.readString(path);
            int lengthOfLastByte = Integer.parseInt(s.substring(0,1));
            s = s.substring(1);
            int countOfBytes = Integer.parseInt(s.substring(0,s.indexOf('{')));
            s = s.substring(s.indexOf('{'));
            s = s.replaceAll("[{ }]","");
            String [] strings = s.split(",");
            HashMap<String,Byte> StringByteHash = new HashMap<>();
            for (String string:strings){
                String [] ss = string.split("=");
                StringByteHash.put(ss[1],Byte.parseByte(ss[0]));
            }
            String newString = "";
            /*for (int i =0;i<a.length-1;i++){
                String s1 = Integer.toBinaryString(a[i] & 0xFF);
                int s1Length = s1.length();
                if (s1Length!=8){
                    for (int j=s1.length();j<8;j++){
                        s1 = "0"+s1;
                    }
                }
                newString+=s1;
            }*/
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
            byte [] byteFile = new byte[countOfBytes];
            int pos=0;
            String key="";
            for (Character bit: stringBuilder.toString().toCharArray()){
                key+=bit;
                if (StringByteHash.containsKey(key)){
                    byteFile[pos++]=StringByteHash.get(key);
                    key="";
                }
            }
           // Files.createFile(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\Test1.bin"));
            //Files.write(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\Test1.bin"),byteFile);
            //Files.move(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\Test1.bin"),Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\Test1.txt"));
            Files.createFile(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\original1.txt"));
            Files.write(Paths.get("C:\\Users\\Work\\IdeaProjects\\Archivator\\original1.txt"),byteFile);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args) {
        Archivator.compress("Test.txt");
        System.err.println("Compressed");
        Archivator.extract();
        System.err.println("Extracted");
    }

}
