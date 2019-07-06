package Duelyst.Utility;

import org.ini4j.Ini;

import java.io.File;
import java.io.FileReader;

public class NetworkConfiguration {
    private static int port = 8000;
    private static String host = "localhost";


    public static void loadFromIni()
    {
        try {
            Ini ini  = new Ini(new FileReader(new File("./src/Duelyst/Utility/config.ini")));
            Ini.Section section = ini.get("server");

            String port = section.get("port");
            String host = section.get("host");

            setPort(port);
            setHost(host);

        } catch (Exception e) {
            port=8000;
            host="localhost";
        }

    }


    public static int getPort() {
        return port;
    }

    private static void setPort(String port) {
            NetworkConfiguration.port = Integer.parseInt(port);

    }

    public static String getHost() {
        return host;
    }

    private static void setHost(String host) {
        if (host.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost$")) {
            NetworkConfiguration.host = host;
        }
    }
}
