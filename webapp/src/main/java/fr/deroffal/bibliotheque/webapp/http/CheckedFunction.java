package fr.deroffal.bibliotheque.webapp.http;

import java.io.IOException;

@FunctionalInterface
public interface CheckedFunction<T, R> {

    R apply(T t) throws IOException;
}
