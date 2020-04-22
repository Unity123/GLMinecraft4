package p0nki.glmc4.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TextOutputStream implements IOutputStream {

    private final PrintWriter writer;

    public TextOutputStream(OutputStream output) {
        writer = new PrintWriter(output);
    }

    @Override
    public void writeByteArray(byte[] value) {
        writeInt(value.length);
        for (byte b : value) writeInt(b);
    }

    @Override
    public void writeChar(char value) {
        writer.print(value);
        writer.print(" ");
    }

    @Override
    public void writeInt(int value) {
        writer.print(value);
        writer.print(" ");
    }

    @Override
    public void writeFloat(float value) {
        writer.print(value);
        writer.print(" ");
    }

    @Override
    public void writeDouble(double value) {
        writer.print(value);
        writer.print(" ");
    }

    @Override
    public void writeLong(long value) {
        writer.print(value);
        writer.print(" ");
    }

    @Override
    public void close() {
        writer.close();
    }

}
