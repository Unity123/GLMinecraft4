package p0nki.glmc4.stream;

import java.io.IOException;

public interface IInputStream {

    char readChar() throws IOException;

    int readInt() throws IOException;

    float readFloat() throws IOException;

    double readDouble() throws IOException;

    long readLong() throws IOException;

    byte[] readByteArray() throws IOException;

    void close() throws IOException;

}
