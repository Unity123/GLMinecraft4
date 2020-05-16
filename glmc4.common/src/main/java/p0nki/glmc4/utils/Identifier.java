package p0nki.glmc4.utils;

import java.util.Objects;

public class Identifier {

    private final String namespace;
    private final String path;

    public Identifier(String namespace, String path) {
        if (!isValidPortion(namespace)) throw new IllegalArgumentException("Namespace " + namespace);
        if (!isValidPortion(path)) throw new IllegalArgumentException("Path " + path);
        this.namespace = namespace;
        this.path = path;
    }

    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    public Identifier(String id) {
        String[] split = id.split(":");
        if (split.length != 2) throw new IllegalArgumentException(id);
        if (!isValidPortion(split[0])) throw new IllegalArgumentException("Namespace " + split[0]);
        if (!isValidPortion(split[1])) throw new IllegalArgumentException("Path " + split[1]);
        namespace = split[0];
        path = split[1];
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        if (!Objects.equals(namespace, that.namespace)) return false;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    public static boolean isValidIdentifier(String id) {
        String[] split = id.split(":");
        if (split.length != 2) return false;
        if (!isValidPortion(split[0])) return false;
        return isValidPortion(split[1]);
    }

    public static boolean isValidPortion(String value) {
        return value.chars().allMatch(x -> (Character.isLowerCase(x) && Character.isAlphabetic(x)) || Character.isDigit(x) || x == '_');
    }

}
