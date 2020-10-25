package com.company.Classes;

import java.util.HashMap;
import java.util.List;

public class TreeList {
     class Node {
        public boolean isNode;
        public Node left;
        public Node right;
        public Node previous;
        public Node next;
        public Integer weight;
        public Character character;

        public Node(){
        }

        public Node(Node left, Node right){
            this.left = left;
            this.right =right;
            isNode = true;
            weight = left.weight + right.weight;
        }
        public Node(int value,boolean isNode){
            this.isNode =isNode;
            this.weight = value;
        }
        public Node(boolean isNode){
            this.isNode = isNode;
        }
         public Node(int value,boolean isNode,Character character){
             this.isNode = false;
             this.weight=value;
             this.character = character;
         }
    }

    private Integer size;
    private Node First;
    private Node Last;

    public TreeList(){
        size =0;
        First = null;
        Last = null;
    }

    private void add (Node node){
        if (size == 0){
            First = node;
            Last = node;
        } else {
            Node template = First;
            while (template!=Last){
                if (template.weight<node.weight){
                    template = template.next;
                } else break;
            }
            if (template == First){
                if (node.weight < template.weight){
                    First.previous = node;
                    node.next = First;
                    First = node;
                } else {
                    node.next = First.next;
                    First.next = node;
                    node.previous = First;
                    if (First == Last)
                        Last = First.next;
                }
            }
            else  if (template == Last){
                if (template.weight < node.weight){
                    node.previous =Last;
                    Last.next = node;
                    node.next=null;
                    Last = node;
                } else {
                    node.previous =Last.previous;
                    Last.previous.next = node;
                    node.next =Last;
                    Last.previous =node;
                }
            } else {
                node.next = template;
                node.previous = template.previous;
                template.previous.next = node;
               node.next.previous=node;
            }
        }
        size++;
    }
    public void add(int value,boolean isNode,Character character){
        Node node = new Node(value,isNode,character);
        this.add(node);
    }

    public void stabilize(){
        while (size!=1){
            if (size == 2){
                size-=2;
                this.add(new Node(First,Last));
            } else {
                Node template1  = First;
                Node template2 = First.next;
                if (template1.weight == template2.weight && template1.weight == template2.next.weight){
                    First = First.next;
                    First.previous =null;
                    template2 = Last;
                    Last = Last.previous;
                    Last.next = null;
                } else {
                    template2.next.previous = null;
                    First = template2.next;
                    First.previous = null;
                }
                size-=2;
                this.add(new Node(template1,template2));
            }
        }
    }
    HashMap <Character,String> BinaryAlphabet(HashMap <Character,String> hashMap,String s,Node node){
      if (node.right!=null) {
          BinaryAlphabet(hashMap, s+1,node.right);
      }
        if (node.isNode == false) {
            hashMap.put(node.character,s);
            System.out.println(s);
        }
        if (node.left!=null) {
           BinaryAlphabet(hashMap, s+0,node.left);
        }
        return hashMap;
    }

    public HashMap <Character,String> BinaryAlphabet(HashMap <Character,String> hashMap,String s){
        return BinaryAlphabet(hashMap,s,First);
    }

    @Override
    public String toString() {
        String s="";
        Node template = First;
        while (template!=Last){
            s+=String.valueOf(template.weight);
            template = template.next;
        }
        s+=String.valueOf(template.weight);
        return s;
    }

    public static void main(String[] args) {
        Byte b = (byte) Integer.parseInt("11111111",2);
        System.out.println(Integer.toBinaryString(b & 0xFF));
        TreeList treeList = new TreeList();
        treeList.add(1,false,'a');
        treeList.add(2,false,'b');
        treeList.add(3,false,'c');
        treeList.stabilize();
        String s="";
        HashMap <Character,String> hashMap = new HashMap<>();
        treeList.BinaryAlphabet(hashMap,s);
    }
}
