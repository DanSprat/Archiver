package com.company;


import com.company.Exceptions.ArchiverException;
import com.company.PriorityHuffmanTree.Archiver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ArchiverException {
        String pathFile = ""; // Путь к файлу или папке для архивации
        String outDir =""; // Место создания архива
        String name="";//Название архива
        Archiver.compress(pathFile,outDir,name);

        String Archive = ""; // Путь к архиву
        String out=""; // Путь к месту разархивации
        Archiver.extract(Archive,out);
    }
}
