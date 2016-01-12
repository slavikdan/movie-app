package cz.example.slavk.movieapp.http;

import java.io.InputStream;

/**
 * A function interface for conversion of an InputStream object from a HTTP request to a desired object
 * Created by Daniel Slavík on 12/01/2016.
 */
public interface InputStreamConverter<T> {
    T convert(InputStream inputStream);
}
