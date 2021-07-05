package web.parser.util;

public interface GenericFormatter<T, S> {
    T createReport(S object);
}
