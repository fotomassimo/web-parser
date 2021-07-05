package web.parser.util;

public interface GenericFileWriter<T> {
    void writeToFile(T output, String filePath);
}
