package com.company.PriorityHuffmanTree;

import com.company.Exceptions.NotFoundMapException;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Archiver {
    private static void compressFile(Path pathFile,Path outRoot,Path outPlace) {
        try {
            HuffmanTree huffmanTree = new HuffmanTree();
            byte[] bytes = Files.readAllBytes(pathFile);
            int[] numbOfBytes = new int[256];
            for (byte b : bytes) {
                numbOfBytes[b & 0xFF]++;
            }
            for (int i = 0; i < 256; i++) {
                if (numbOfBytes[i] != 0)
                    huffmanTree.add(new Node(numbOfBytes[i], i));
            }
            huffmanTree.toTree();
            HashMap<Integer, String> alphabetMap = huffmanTree.toAlphabet();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                stringBuilder.append(alphabetMap.get(b & 0xFF));
            }
            String stringFileName = pathFile.getFileName().toString();
            int posOfPoint =stringFileName.indexOf('.');
            String nameZippedFile =  stringFileName.substring(0,posOfPoint)+".bin";
            String s1 = "$"+nameZippedFile+":"+stringFileName.substring(posOfPoint)+":";
            byte a[] = new byte[stringBuilder.length() / 8 + (1-(8-stringBuilder.length()%8)/8)];
            for (int i = 0; i < stringBuilder.length(); i += 8) {
                if (i + 8 < stringBuilder.length()) {
                    a[i / 8] = (byte) Integer.parseInt(stringBuilder.substring(i, i + 8), 2);
                } else {
                    a[i / 8] = (byte) Integer.parseInt(stringBuilder.substring(i), 2);
                    s1 += stringBuilder.substring(i).length()+":";
                }
            }
            s1 += bytes.length;
            s1 += alphabetMap.toString();
            Files.writeString(outRoot.resolve("maps.txt"), s1,StandardOpenOption.APPEND);
            Path pathFileBin = outPlace.toAbsolutePath().resolve(nameZippedFile); //и тут
            Files.createFile(pathFileBin);
            Files.write(pathFileBin, a);
            System.out.println("Compressed: "+pathFile.toAbsolutePath());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void compress(String path,String outDir,String nameZIP)  {
        try {
            Path outDirPath; // Каталог архива
            if (outDir == null){
                outDirPath = Paths.get(path).toAbsolutePath().getParent().getFileName();
            } else {
                outDirPath = Paths.get(outDir);
            }
            Path pathFile = Paths.get(path); // Путь исходного файла / каталога
            System.err.println("Start compressing: "+pathFile.toAbsolutePath());
            Path pathOfArchive; // Путь архива
            if (!Files.isDirectory(pathFile)){
                if (nameZIP == null) {
                    pathOfArchive = outDirPath.resolve(pathFile.getFileName().toString().replaceAll(".", "") + "ZIP");
                } else {
                    pathOfArchive = outDirPath.resolve(nameZIP+"ZIP");
                }
                Files.createDirectory(pathOfArchive);
                Files.createFile(pathOfArchive.resolve("maps.txt"));
                compressFile(pathFile,pathOfArchive,pathOfArchive);
            } else {
                if (nameZIP == null){
                    pathOfArchive = outDirPath.resolve(pathFile.getFileName().toString()+"ZIP");
                } else {
                    pathOfArchive = outDirPath.resolve(nameZIP+"ZIP");
                }
                Files.createDirectory(pathOfArchive);
                Files.createFile(pathOfArchive.resolve("maps.txt"));
                Files.walkFileTree(pathFile, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
                        Path relPath = pathFile.relativize(path);
                        Files.createDirectories(pathOfArchive.resolve(pathFile.getFileName().toString()+"/"+relPath.toString()));
                        return FileVisitResult.CONTINUE;
                    }
                });
                PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**");
                Files.walkFileTree(pathFile, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
                        if (pathMatcher.matches(path)) {
                            Path relPath = pathFile.relativize(path);
                            Path pathZippedFile = pathOfArchive.resolve(pathFile.getFileName().toString()+"/"+relPath.toString()).toAbsolutePath().getParent(); // тут были изменения
                            compressFile(path,pathOfArchive,pathZippedFile);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path path, IOException ex) {
                        return FileVisitResult.CONTINUE;
                    }

                });
            }
            System.err.println("End compressing: "+pathFile.toAbsolutePath());
        } catch(IOException ex){
            System.out.println(ex);
        }
    }

    private static void extractFile(Path pathFile,Path outRoot,Path outPlace,Path mapPath){
        try {
            byte[] byteOfFile = Files.readAllBytes(pathFile); // считываем файл
            String info = Files.readString(mapPath); // считываем maps.txt
            String fileName = pathFile.getFileName().toString();
            if (!fileName.equals("maps.txt")) {
                info = info.substring(info.indexOf(fileName) + fileName.length());
                info = info.substring(1, info.indexOf("}"));
                String[] fileInfo = info.substring(0, info.indexOf("{")).split(":");
                fileName = fileName.substring(0, fileName.indexOf('.')) + fileInfo[0];
                int lengthOfLastByte = Integer.parseInt(fileInfo[1]);
                String[] furtherMap = info.substring(info.indexOf("{") + 1).trim().split(",");
                int countOfBytes = Integer.parseInt(fileInfo[2]);
                HashMap<String, Integer> StringByteHash = new HashMap<>();
                for (String string : furtherMap) {
                    String[] ss = string.split("=");
                    StringByteHash.put(ss[1], Integer.parseInt(ss[0].trim()));
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < byteOfFile.length - 1; i++) {
                    String s1 = Integer.toBinaryString(byteOfFile[i] & 0xFF);
                    int s1Length = s1.length();
                    if (s1Length != 8) {
                        for (int j = s1.length(); j < 8; j++) {
                            s1 = "0" + s1;
                        }
                    }
                    stringBuilder.append(s1);
                }
                String lastByte = Integer.toBinaryString(byteOfFile[byteOfFile.length - 1] & 0xFF);
                for (int i = 0; i < lengthOfLastByte - lastByte.length(); i++) {
                    lastByte = "0" + lastByte;
                }
                stringBuilder.append(lastByte);
                byte[] byteFile = new byte[countOfBytes];
                int pos = 0;
                String key = "";
                for (Character bit : stringBuilder.toString().toCharArray()) {
                    key += bit;
                    if (StringByteHash.containsKey(key)) {
                        byteFile[pos++] = StringByteHash.get(key).byteValue();
                        key = "";
                    }
                }
                Path newFilePath = outPlace.toAbsolutePath().resolve(fileName);
                Files.createFile(newFilePath);
                Files.write(newFilePath, byteFile);
                System.out.println("Extracted: "+pathFile);
            }
        } catch (IOException ex){

        }
    }
    public static void extract(String stringArchivePath,String stringOutDirPath) throws IOException,NotFoundMapException{
            Path ArchivePath = Paths.get(stringArchivePath); // Путь архива
            System.err.println("Start extracting: "+ArchivePath.toAbsolutePath());
            Path OutDirPath = Paths.get(stringOutDirPath);
            PathMatcher pathMatcher  = FileSystems.getDefault().getPathMatcher("glob:**/maps.txt");
            final Path[] mapsPath = new Path[1];
            Files.walkFileTree(ArchivePath, Collections.emptySet(),2,new SimpleFileVisitor<>(){
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
                    if (pathMatcher.matches(path)) {
                      mapsPath[0] = path;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path path, IOException ex) {
                    return FileVisitResult.CONTINUE;
                }
            });// Ищем maps.txt
            if (mapsPath[0]==null){
                throw new NotFoundMapException(ArchivePath.toString());
            }
            Files.walkFileTree(ArchivePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    Path relPath = ArchivePath.relativize(path);
                    Files.createDirectories(OutDirPath.resolve(relPath.toString()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
                    return null;
                }

            }); // Создаем "скелет"
            PathMatcher pathMatcherNew = FileSystems.getDefault().getPathMatcher("glob:**");
            Files.walkFileTree(ArchivePath,new SimpleFileVisitor<>(){
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
                    if (pathMatcherNew.matches(path) && !pathMatcher.matches(path)) {
                     Path relPath = ArchivePath.relativize(path);
                     Path pathUnZippedFile = OutDirPath.resolve(relPath.toString()).toAbsolutePath().getParent();
                     extractFile(path,OutDirPath,pathUnZippedFile,mapsPath[0]);
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path path, IOException ex) {
                    return FileVisitResult.CONTINUE;
                }
            });
            System.err.println("End extracting: "+ArchivePath.toAbsolutePath());
    }

    public static void main(String[] args) throws Exception{
        compress("Test","C:\\Users\\Work\\Documents\\Tests","First");
        extract("C:\\Users\\Work\\Documents\\Tests\\FirstZIP","C:\\Users\\Work\\Documents\\Tests");

    }
}
