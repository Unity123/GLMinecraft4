package p0nki.glmc4.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteOutputStream implements IOutputStream {

    private final OutputStream output;

    public ByteOutputStream(OutputStream output) {
        this.output = output;
    }

    @Override
    public void writeInt(int i) throws IOException {
        output.write(ByteBuffer.allocate(Integer.BYTES).putInt(i).array());
    }

    @Override
    public void writeLong(long l) throws IOException {
        output.write(ByteBuffer.allocate(Long.BYTES).putLong(l).array());
    }

    @Override
    public void close() throws IOException {
        output.close();
    }

    @Override
    public void writeByteArray(byte[] value) throws IOException {
        writeInt(value.length);
        output.write(value);
    }

    @Override
    public void writeChar(char c) throws IOException {
        output.write(ByteBuffer.allocate(Character.BYTES).putChar(c).array());
    }

    @Override
    public void writeFloat(float f) throws IOException {
        output.write(ByteBuffer.allocate(Float.BYTES).putFloat(f).array());
    }

    @Override
    public void writeDouble(double d) throws IOException {
        output.write(ByteBuffer.allocate(Double.BYTES).putDouble(d).array());
    }

}
