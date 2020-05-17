package p0nki.glmc4.client.gl;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class BufferData {

    private final int size;
    public List<Double> data;

    public BufferData(int size) {
        this.size = size;
        data = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public BufferData append(List<Double> data) {
        this.data.addAll(data);
        return this;
    }

    public BufferData append2f(List<Vector2f> data) {
        if (size != 2) throw new IllegalStateException("" + size);
        return append(data.stream().flatMapToDouble(v -> DoubleStream.of(v.x, v.y)).boxed().collect(Collectors.toList()));
    }

    public BufferData append3f(List<Vector3f> data) {
        if (size != 3) throw new IllegalStateException("" + size);
        return append(data.stream().flatMapToDouble(v -> DoubleStream.of(v.x, v.y, v.z)).boxed().collect(Collectors.toList()));
    }


}
