package com.company.PriorityHuffmanTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanTree {

    private PriorityQueue <Node> priorityQueue;
    private ArrayList<String> map;
    String [] strings;

    public HuffmanTree(){
        priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return Integer.compare(node.weight,t1.weight);
            }
        });
        map = new ArrayList<>(256);
        strings = new String[256];
    }
    public void add(Node node){
        priorityQueue.offer(node);
    }
    public void toTree(){
        while (priorityQueue.size()!=1){
            priorityQueue.offer(new Node(priorityQueue.poll(),priorityQueue.poll()));
        }
    }

    private String [] toAlphabet(String s,Node node){
        if(node.right!=null){
           toAlphabet(s+"1",node.right);
        }
        if (node.right == null && node.left == null) {
            strings[node.byteValue]=s;
        }
        if (node.left!=null) {
            toAlphabet(s+0,node.left);
        }
        return strings;
    }
    public String [] toAlphabet(){
        return toAlphabet("",priorityQueue.peek());
    }



    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.add(new Node (1,1));
        huffmanTree.add(new Node (1,2));
        huffmanTree.add(new Node (2,3));
        huffmanTree.add(new Node (2,4));
        huffmanTree.add(new Node (2,5));
        huffmanTree.add(new Node (3,6));
        huffmanTree.add(new Node (4,7));
        huffmanTree.toTree();
        String [] arrayList = huffmanTree.toAlphabet();

    }
}
