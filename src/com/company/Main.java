package com.company;

import com.company.Classes.TreeList;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public  static void archivate(String str){
        Path path = Paths.get(str);
        try {
            int count =0;
            String s = Files.readString(path, Charset.defaultCharset());
            HashMap <Character,Integer> mapOfChars= new HashMap<>();
            for (Character character:s.toCharArray()){
                if (mapOfChars.containsKey(character)){
                    mapOfChars.replace(character,mapOfChars.get(character)+1);
                } else {
                    mapOfChars.put(character,1);
                }
                count++;
            }
            TreeList treeList = new TreeList();
            for (var it:mapOfChars.entrySet()){
                treeList.add(it.getValue(),false,it.getKey());
            }
            treeList.stabilize();
            HashMap<Character,String> hashMap = new HashMap<>();
            treeList.BinaryAlphabet(hashMap,s="");
            System.out.println(hashMap);
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }


    public static void main(String[] args) throws IOException {
        int [] numbOfBytes = new int[256];
        System.out.println(numbOfBytes);
        System.out.println(  Integer.toBinaryString((byte)-1  & 0xFF));
       int a = 0b1111_1111;
        System.out.println(a);

    }
}
