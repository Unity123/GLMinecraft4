package p0nki.glmc4.tag;

import p0nki.glmc4.stream.IInputStream;
import p0nki.glmc4.stream.IOutputStream;

import java.io.IOException;

public interface TagSerializer {

    void write(IOutputStream output, Tag element) throws IOException;

    Tag read(IInputStream input) throws IOException;

}

