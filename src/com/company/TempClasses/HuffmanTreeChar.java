package com.company.TempClasses;

import com.company.Classes.Node;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

public class HuffmanTreeChar {
    private LinkedList<NodeChar> nodeList;
    private HashMap<Character,String> mapOfBytes;

    public HuffmanTreeChar(){
        nodeList = new LinkedList<>();
        mapOfBytes = new HashMap<>();
    }

    public void add(NodeChar node){
        if (nodeList.size()==0){
            nodeList.add(node);
        } else {
            int pos=0;
            for (NodeChar nodeL : nodeList){
                if(nodeL.weight >= node.weight)
                    break;
                pos++;
            }
            nodeList.add(pos,node);
        }
    }

    public boolean toTree(){
        if (nodeList.size() == 1 )
            return false;
        while (nodeList.size()!=1){
            if (nodeList.size()==2 || !(nodeList.get(0).weight == nodeList.get(1).weight && nodeList.get(0).weight == nodeList.get(2).weight)){
                this.add(new NodeChar(nodeList.pollFirst(),nodeList.pollFirst()));
            } else {
                this.add(new NodeChar(nodeList.pollFirst(),nodeList.pollLast()));
            }
        }
        return  true;
    }
    HashMap <Character,String> BinaryAlphabet(String s,NodeChar node){
       if(node.right!=null){
           BinaryAlphabet(s+"1",node.right);
        }
        if (node.isNode == false) {
            mapOfBytes.put(node.byteValue,s);
        }
        if (node.left!=null) {
            BinaryAlphabet(s+0,node.left);
        }
        return mapOfBytes;
    }

    public HashMap<Character,String> BinaryAlphabet(){
        return BinaryAlphabet("",nodeList.peekFirst());
    }


    public static void main(String[] args) throws IOException {

        HuffmanTreeChar huffmanTreeChar = new HuffmanTreeChar();
        Path path = Paths.get("Test.txt");
        String s = Files.readString(path);
        HashMap <Character,Integer> mapOfChars= new HashMap<>();
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
        s1+=mapOfBytes.toString();
        Files.createFile(path.toAbsolutePath().getParent().resolve("maps.txt"));
        Files.writeString(path.toAbsolutePath().getParent().resolve("maps.txt"),s1);
        Path pathFile = path.toAbsolutePath().getParent().resolve("zipped.bin");
        Files.createFile(pathFile);
        Files.write(pathFile,a);

    }

}
