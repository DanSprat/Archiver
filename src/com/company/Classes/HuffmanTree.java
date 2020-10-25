package com.company.Classes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HuffmanTree {
    private LinkedList<Node> nodeList;
    private HashMap<Byte,String> mapOfBytes;

    public HuffmanTree(){
        nodeList = new LinkedList<>();
        mapOfBytes = new HashMap<>();
    }
    public void add(Node node){
        if (nodeList.size()==0){
            nodeList.add(node);
        } else {
          int pos=0;
          for (Node nodeL : nodeList){
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
                this.add(new Node(nodeList.pollFirst(),nodeList.pollFirst()));
            } else {
                this.add(new Node(nodeList.pollFirst(),nodeList.pollLast()));
            }
        }
        return  true;
    }
    HashMap <Byte,String> BinaryAlphabet(String s,Node node){
        if (node.right!=null) {
            BinaryAlphabet( s+1,node.right);
        }
        if (node.isNode == false) {
            mapOfBytes.put(node.byteValue,s);
            System.out.println(s);
        }
        if (node.left!=null) {
            BinaryAlphabet(s+0,node.left);
        }
        return mapOfBytes;
    }

    public HashMap<Byte,String> BinaryAlphabet(){
        return BinaryAlphabet("",nodeList.peekFirst());
    }

    public static void main(String[] args) {
        System.out.println(Byte.parseByte("100"));
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.add(new Node(2,(byte) 123));
        huffmanTree.add(new Node(1,(byte) 121));
        huffmanTree.add(new Node(1,(byte) 122));
        huffmanTree.add(new Node(2,(byte) 124));
        huffmanTree.add(new Node(4,(byte) 125));
        huffmanTree.toTree();
        System.out.println(huffmanTree.BinaryAlphabet());
    }
}
