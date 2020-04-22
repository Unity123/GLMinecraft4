package p0nki.glmc4.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteInputStream implements IInputStream {

    //TODO: float,double,long,short,byte, nbt classes

    private final InputStream stream;

    public ByteInputStream(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public int readInt() throws IOException {
        return ByteBuffer.wrap(stream.readNBytes(Integer.BYTES)).getInt();
    }

    @Override
    public float readFloat() throws IOException {
        return ByteBuffer.wrap(stream.readNBytes(Float.BYTES)).getFloat();
    }

    @Override
    public long readLong() throws IOException {
        return ByteBuffer.wrap(stream.readNBytes(Long.BYTES)).getLong();
    }

    @Override
    public byte[] readByteArray() throws IOException {
        return stream.readNBytes(readInt());
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public double readDouble() throws IOException {
        return ByteBuffer.wrap(stream.readNBytes(Double.BYTES)).getDouble();
    }

    @Override
    public char readChar() throws IOException {
        return ByteBuffer.wrap(stream.readNBytes(Character.BYTES)).getChar();
    }

}
