package p0nki.glmc4.client;

import java.io.IOException;

public class GLMC4Client {

    public static void main(String[] args) throws IOException {
        ClientInstance instance = new ClientInstance(new ClientConfig("localhost", 3333));
    }

}
