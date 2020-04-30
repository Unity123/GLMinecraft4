package p0nki.glmc4.server;

import java.io.IOException;

public class GLMC4Server {

    public static void main(String[] args) throws IOException {
        ServerInstance instance = new ServerInstance(new ServerConfig(
                3333,
                10,
                1000000
        ));
    }

}
