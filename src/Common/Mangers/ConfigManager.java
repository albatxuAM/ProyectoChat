package Common.Mangers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ConfigManager {
    private static ConfigManager instance;
    private Document document;

    /**
     * Constructor privado del manager para obtener información del archivo config
     */
    private ConfigManager() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse("config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return instancia de el manager
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * @param tagName nombre del tag que se encuentra en el xml config
     * @return
     */
    public String getPropertyValue(String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        Node node = nodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return element.getTextContent();
        }
        return null;
    }

    /**
     * @return valor de la variable icon_path
     */
    public String getIconPath() {
        return getPropertyValue("icon_path");
    }

    /**
     * @return valor de la variable server_ip
     */
    public String getServerIP() {
        return getPropertyValue("server_ip");
    }

    /**
     * @return valor de la variable server_port
     */
    public int getServerPort() {
        String propertyValue = getPropertyValue("server_port");
        return Integer.parseInt(propertyValue);
    }

    /**
     * @return valor de la variable broadcast_ip
     */
    public String getBroadcastIP() {
        return getPropertyValue("broadcast_ip");
    }

    /**
     * @return valor de la variable broadcast_port
     */
    public int getBroadcastPort() {
        String propertyValue = getPropertyValue("broadcast_port");
        return Integer.parseInt(propertyValue);
    }

    /**
     * @return valor de la variable debug
     */
    public Boolean getDebug() {
        String propertyValue = getPropertyValue("debug");
        return propertyValue.equalsIgnoreCase("true");
    }

}

