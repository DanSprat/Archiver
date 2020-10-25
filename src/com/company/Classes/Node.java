package com.company.Classes;

public class Node {

    boolean isNode;
    Node left;
    Node right;
    byte byteValue;
    Integer weight;

    public Node(int weight,byte byteValue){
        isNode = false;
        this.weight = weight;
        this.byteValue = byteValue;
    }
    public Node(Node left, Node right){
        this.left = left;
        this.right = right;
        isNode = true;
        weight = left.weight + right.weight;
    }
}
