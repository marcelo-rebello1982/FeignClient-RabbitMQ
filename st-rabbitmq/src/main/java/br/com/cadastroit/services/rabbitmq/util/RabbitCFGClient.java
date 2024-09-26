package br.com.cadastroit.services.rabbitmq.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;
import br.com.cadastroit.services.OsDetect;
import br.com.cadastroit.services.utils.UtilString;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RabbitCFGClient {

	private String HOST_FILE = "/opt/st-rabbit-config/rabbitmq.properties";

	public Map<String, String> environments() {

		if (OsDetect.OS_NAME().contains("windows"))
			HOST_FILE = "C:\\workspace\\st-rabbit-config\\rabbitmq.properties";

		String connections = "";
		String host = "";
		String database = "";
		Integer port = 27017;
		String username = "";
		String password = "";
		String connectionId = "";

		if (System.getenv("PORT") != null) {
			database = new String(Base64.getDecoder().decode(System.getenv("PORT")));
		}
		if (System.getenv("HOST") != null) {
			host = new String(Base64.getDecoder().decode(System.getenv("NOSQL_HOST")));
		}
		if (System.getenv("USERNAME") != null) {
			// username = new String(Base64.getDecoder().decode(System.getenv("NOSQL_USERNAME")));
		}
		if (System.getenv("PASSWORD") != null) {
			// password = new String(Base64.getDecoder().decode(System.getenv("NOSQL_PASSWORD")));
		}
		
		if (System.getenv("connections") == null) {
			File resourceConnection = new File(HOST_FILE);
			Properties properties = new Properties();
			try (InputStream in = new FileInputStream(resourceConnection)) {
				properties.load(in);
				
				host = UtilString.replaceChars(new String(Base64.getDecoder().decode(properties.getProperty("HOST"))));
				username = UtilString.replaceChars(new String(Base64.getDecoder().decode(properties.getProperty("USERNAME"))));
				password = UtilString.replaceChars(new String(Base64.getDecoder().decode(properties.getProperty("PASSWORD"))));
				connections = UtilString.replaceChars(new String(Base64.getDecoder().decode(properties.getProperty("CONNECTIONS"))));
				connectionId = UtilString.replaceChars(new String(Base64.getDecoder().decode(properties.getProperty("CONNECTIONID"))));

				// connections = properties.getProperty("connections");
				// host = properties.getProperty("host");
				// username = properties.getProperty("username");
				// password = properties.getProperty("password");
				// connectionId = properties.getProperty("connectionId");
				
			} catch (IOException ex) {
				System.out.println("Error on read application.properties, [Error] = " + ex.getMessage());
			}
		}

		Map<String, String> mapData = Map.of("connections" , connections, "host", host, 
				"database", database, "port", String.valueOf(port), 
					"username", username, "password", password, "connectionId", connectionId);

		return mapData;
	}
}