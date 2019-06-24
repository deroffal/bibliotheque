package fr.deroffal.portail.webapp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestUtils {

	public static String readFile(String filePath) {
		try {
			final Path path = Paths.get(TestUtils.class.getResource(filePath).toURI());
			return Files.lines(path).map(String::strip).collect(Collectors.joining());
		} catch (URISyntaxException | IOException e) {
			return null;
		}
	}

}
