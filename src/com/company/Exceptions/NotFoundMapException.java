package com.company.Exceptions;

public class NotFoundMapException extends ExtractException{
    String ArchiveName;
    public NotFoundMapException(String path){
        super("Map not found in");
        ArchiveName =  path;
    }
    @Override
    public String toString() {
        return super.getMessage() + ArchiveName;
    }
}
