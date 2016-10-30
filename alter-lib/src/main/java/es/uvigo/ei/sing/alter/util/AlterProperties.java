package es.uvigo.ei.sing.alter.util;

import java.io.IOException;
import java.util.Properties;

public class AlterProperties extends Properties {

	private static final AlterProperties _singleton = new AlterProperties();

	public AlterProperties() {
		try {
			this.load(AlterProperties.class.getResourceAsStream("/alter.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static AlterProperties getInstance() {
		return _singleton;
	}
}
