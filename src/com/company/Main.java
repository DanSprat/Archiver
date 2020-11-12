package com.company;


import com.company.Exceptions.ArchiverException;
import com.company.PriorityHuffmanTree.Archiver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ArchiverException {
        if (args.length == 3){
            // args[0] - Путь к файлу или папке для архивации
            // args[1] - Место создания архива
            // args[2] - Название архива
            Archiver.compress(args[0],args[1],args[2]);
        } else if (args.length == 2){
            // args[0] - Путь к архиву
            // args[1] - Путь к месту разархивации
            Archiver.extract(args[0],args[1]);
        }

       /* String pathFile = "C:\\Users\\Work\\IdeaProjects\\Archivator\\Test"; // Путь к файлу или папке для архивации
        String outDir ="C:\\Users\\Work\\Documents\\Tests"; // Место создания архива
        String name="Test";//Название архива
        Archiver.compress(pathFile,outDir,name);

        String Archive = "C:\\Users\\Work\\Documents\\Tests\\TestZIP"; // Путь к архиву
        String out="C:\\Users\\Work\\Documents\\матан"; // Путь к месту разархивации
        Archiver.extract(Archive,out);*/
    }
}
