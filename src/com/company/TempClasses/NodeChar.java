package com.company.TempClasses;

public class NodeChar {
    boolean isNode;
    NodeChar left;
    NodeChar right;
    Character byteValue;
    Integer weight;

    public NodeChar(int weight,char byteValue){
        isNode = false;
        this.weight = weight;
        this.byteValue = byteValue;
    }

    public NodeChar(NodeChar left, NodeChar right){
        this.left = left;
        this.right = right;
        isNode = true;
        weight = left.weight + right.weight;
    }

}
