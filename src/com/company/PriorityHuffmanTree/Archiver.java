package com.company.PriorityHuffmanTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Archiver {

    public static void compress(String path) {
        try {
            HuffmanTree huffmanTree = new HuffmanTree();
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            int [] numbOfBytes = new int[256];
            for (byte b:bytes){
                numbOfBytes[b % 0xFF] ++;
            }
            for (int i =0;i<255;i++){
                huffmanTree.add(new Node(numbOfBytes[i],i));
            }
            huffmanTree.toTree();

        } catch(IOException ex){

        }
    }
}
