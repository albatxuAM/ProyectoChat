package Common.Mangers;

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

    public String getIconPath() {
        return getPropertyValue("icon_path");
    }

    public String getServerIP() {
        return getPropertyValue("server_ip");
    }

    public int getServerPort() {
        String propertyValue = getPropertyValue("server_port");
        return Integer.parseInt(propertyValue);
    }

    public String getBroadcastIP() {
        return getPropertyValue("broadcast_ip");
    }

    public int getBroadcastPort() {
        String propertyValue = getPropertyValue("broadcast_port");
        return Integer.parseInt(propertyValue);
    }

    public Boolean getDebug() {
        String propertyValue = getPropertyValue("debug");
        return propertyValue.equalsIgnoreCase("true");
    }

}

