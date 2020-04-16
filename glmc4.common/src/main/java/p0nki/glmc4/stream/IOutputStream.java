package p0nki.glmc4.stream;

import java.io.IOException;

public interface IOutputStream {

    void writeChar(char value) throws IOException;

    void writeInt(int value) throws IOException;

    void writeFloat(float value) throws IOException;

    void writeDouble(double value) throws IOException;

    void writeLong(long value) throws IOException;

    void close() throws IOException;

}
