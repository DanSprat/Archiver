package com.company.PriorityHuffmanTree;

public class Node {
    Node left;
    Node right;
    int byteValue;
    int weight;

    public Node(Node left,Node right){
        this.left = left;
        this.right = right;
        this.weight = left.weight + right.weight;
    }

    public Node(int weight,int byteValue){
        this.weight =  weight;
        this.byteValue = byteValue;
    }



}
