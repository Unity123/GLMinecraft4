package p0nki.glmc4.tag;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.stream.IInputStream;
import p0nki.glmc4.stream.IOutputStream;
import p0nki.glmc4.utils.AssertUtils;
import p0nki.glmc4.utils.Identifier;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Tag {

    static TagArray of(Tag... value) {
        return new TagArray(value);
    }

    static TagArray of(Collection<Tag> value) {
        return of(value.toArray(Tag[]::new));
    }

    static TagArray of(Stream<Tag> value) {
        return of(value.collect(Collectors.toList()));
    }

    static TagBoolean of(boolean value) {
        return new TagBoolean(value);
    }

    static TagChar of(char value) {
        return new TagChar(value);
    }

    static TagDouble of(double value) {
        return new TagDouble(value);
    }

    static TagFloat of(float value) {
        return new TagFloat(value);
    }

    static TagInt of(int value) {
        return new TagInt(value);
    }

    static TagLong of(long value) {
        return new TagLong(value);
    }

    static TagMap of(Map<String, Tag> value) {
        return new TagMap(value);
    }

    static TagString of(String value) {
        return new TagString(value);
    }

    static TagIdentifier of(Identifier value) {
        return new TagIdentifier(value);
    }

    String PRETTYPRINT_TAB = "   ";
    int CHAR = 1;
    int INT = 2;
    int LONG = 3;
    int FLOAT = 4;
    int DOUBLE = 5;
    int STRING = 6;
    int BOOLEAN = 7;
    int ARRAY = 8;
    int MAP = 9;
    int BYTE_ARRAY = 10;
    int IDENTIFIER = 11;
    Map<Integer, TagSerializer> SERIALIZERS = ImmutableMap.<Integer, TagSerializer>builder()
            .put(CHAR, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(CHAR);
                    output.writeChar(element.asChar());
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == CHAR);
                    return new TagChar(input.readChar());
                }
            })
            .put(INT, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(INT);
                    output.writeInt(element.asInt());
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == INT);
                    return new TagInt(input.readInt());
                }
            })
            .put(LONG, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(LONG);
                    output.writeLong(element.asLong());
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == LONG);
                    return new TagLong(input.readLong());
                }
            })
            .put(FLOAT, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(FLOAT);
                    output.writeFloat(element.asFloat());
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == FLOAT);
                    return new TagFloat(input.readFloat());
                }
            })
            .put(DOUBLE, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(DOUBLE);
                    output.writeDouble(element.asDouble());
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == DOUBLE);
                    return new TagDouble(input.readDouble());
                }
            })
            .put(STRING, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(STRING);
                    output.writeInt(element.asString().length());
                    for (int i = 0; i < element.asString().length(); i++) {
                        output.writeChar(element.asString().charAt(i));
                    }
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == STRING);
                    int length = input.readInt();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        builder.append(input.readChar());
                    }
                    return new TagString(builder.toString());
                }
            })
            .put(BOOLEAN, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(BOOLEAN);
                    output.writeInt(element.asBoolean() ? 1 : 0);
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == BOOLEAN);
                    return new TagBoolean(input.readInt() == 1);
                }
            })
            .put(ARRAY, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(ARRAY);
                    output.writeInt(element.asArray().length);
                    for (int i = 0; i < element.asArray().length; i++) {
                        output.writeInt(element.asArray()[i].getID());
                        SERIALIZERS.get(element.asArray()[i].getID()).write(output, element.asArray()[i]);
                    }
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == ARRAY);
                    int length = input.readInt();
                    Tag[] value = new Tag[length];
                    for (int i = 0; i < length; i++) {
                        int id = input.readInt();
                        value[i] = SERIALIZERS.get(id).read(input);
                    }
                    return new TagArray(value);
                }
            })
            .put(MAP, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(MAP);
                    output.writeInt(element.asMap().size());
                    for (Map.Entry<String, Tag> entry : element.asMap().entrySet()) {
                        SERIALIZERS.get(STRING).write(output, new TagString(entry.getKey()));
                        output.writeInt(entry.getValue().getID());
                        SERIALIZERS.get(entry.getValue().getID()).write(output, entry.getValue());
                    }
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == MAP);
                    int size = input.readInt();
                    Map<String, Tag> value = new HashMap<>();
                    for (int i = 0; i < size; i++) {
                        String key = SERIALIZERS.get(STRING).read(input).asString();
                        int id = input.readInt();
                        value.put(key, SERIALIZERS.get(id).read(input));
                    }
                    return new TagMap(value);
                }
            })
            .put(BYTE_ARRAY, new TagSerializer() {
                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(BYTE_ARRAY);
                    output.writeByteArray(element.asByteArray());
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == BYTE_ARRAY);
                    return new TagByteArray(input.readByteArray());
                }
            })
            .put(IDENTIFIER, new TagSerializer() {

                @Override
                public void write(IOutputStream output, Tag element) throws IOException {
                    output.writeInt(IDENTIFIER);
                    SERIALIZERS.get(STRING).write(output, new TagString(element.asIdentifier().toString()));
                }

                @Override
                public Tag read(IInputStream input) throws IOException {
                    AssertUtils.shouldBeTrue(input.readInt() == IDENTIFIER);
                    return new TagIdentifier(new Identifier(SERIALIZERS.get(STRING).read(input).asString()));
                }
            }).build();

    static void write(IOutputStream output, Tag element) throws IOException {
        output.writeInt(element.getID());
        SERIALIZERS.get(element.getID()).write(output, element);
    }

    static Tag read(IInputStream input) throws IOException {
        return SERIALIZERS.get(input.readInt()).read(input);
    }

    int getID();

    char asChar();

    int asInt();

    long asLong();

    float asFloat();

    double asDouble();

    String asString();

    boolean asBoolean();

    Tag[] asArray();

    Map<String, Tag> asMap();

    byte[] asByteArray();

    Identifier asIdentifier();

    boolean isChar();

    boolean isInt();

    boolean isLong();

    boolean isFloat();

    boolean isDouble();

    boolean isString();

    boolean isBoolean();

    boolean isArray();

    boolean isMap();

    boolean isByteArray();

    boolean isIdentifier();

    default String prettyprint() {
        return prettyprint("");
    }

    String prettyprint(String indent);

    boolean isSame(Tag other);

}
