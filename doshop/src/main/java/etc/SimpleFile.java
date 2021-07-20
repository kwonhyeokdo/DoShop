package etc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class SimpleFile {
	@Autowired private ResourceLoader resourceLoader;

	public String getStringInFile(String pathInResources) {
		String result = "";
		Resource resource = resourceLoader.getResource("classpath:" + pathInResources);
		if(resource.exists()) {
			try {
				File file = resource.getFile();
				result = new String(Files.readAllBytes(file.toPath()), Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return result;
	}
}
