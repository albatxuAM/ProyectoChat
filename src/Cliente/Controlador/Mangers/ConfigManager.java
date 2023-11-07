package Cliente.Controlador.Mangers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ConfigManager {
    private static ConfigManager instance;
    private Document document;

    private ConfigManager() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse("config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getPropertyValue(String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        Node node = nodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return element.getTextContent();
        }
        return null;
    }

    public String getImagePath() {
        String path = getPropertyValue("image_path");
        createDirectoryIfNotExists(path);
        return path;
    }

    public String getDataPath() {
        String path = getPropertyValue("data_path");
        createDirectoryIfNotExists(path);
        return path;
    }

    public String getXmlPath() {
        String path = getPropertyValue("xml_path");
        createDirectoryIfNotExists(path);
        return path;
    }

    public String getXmlPathPartidas() {
        String path = getPropertyValue("xml_partidas");
        createDirectoryIfNotExists(path);
        return path;
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directorio creado: " + path);
            } else {
                System.err.println("No se pudo crear el directorio: " + path);
            }
        }
    }
}

