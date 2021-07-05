package web.parser.util;

public interface GenericFileReader<T>{
    T readFromFile(String filePath);
}
