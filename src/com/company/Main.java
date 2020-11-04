package com.company;


import com.company.Exceptions.ArchiverException;
import com.company.PriorityHuffmanTree.Archiver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ArchiverException {
        String pathFile = "C:\\Users\\Work\\IdeaProjects\\Archivator\\Test"; // Путь к файлу или папке для архивации
        String outDir ="C:\\Users\\Work\\Documents\\Tests"; // Место создания архива
        String name="Test";//Название архива
        Archiver.compress(pathFile,outDir,name);

        String Archive = "C:\\Users\\Work\\Documents\\Tests\\TestZIP"; // Путь к архиву
        String out="C:\\Users\\Work\\Documents\\матан"; // Путь к месту разархивации
        Archiver.extract(Archive,out);
    }
}
