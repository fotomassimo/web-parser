package web.parser.service;

public interface GenericContentLoader<T> {
    T getContent(String source);
}
