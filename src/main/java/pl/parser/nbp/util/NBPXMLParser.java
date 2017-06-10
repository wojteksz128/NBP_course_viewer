package pl.parser.nbp.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.parser.nbp.model.DayCurrency;
import pl.parser.nbp.model.ExchangeRateTable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class NBPXMLParser {

    public static List<ExchangeRateTable> getDataFromXMLs(List<URL> xmls) {
        return xmls.stream()
                .parallel()
                .map(NBPXMLParser::parseXMLFile)
                .collect(Collectors.toList());
    }

    private static ExchangeRateTable parseXMLFile(URL xmlUrl) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;

        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            dom = db.parse(xmlUrl.openStream());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        assert dom != null;
        return parseDocument(dom.getDocumentElement());
    }

    private static ExchangeRateTable parseDocument(Element doc) {
        String tableNo = null;
        LocalDate noteDate = null;
        LocalDate publishDate = null;
        List<DayCurrency> dayCurrencyList = new ArrayList<>();

        NodeList exchangeRateTableNodes = doc.getChildNodes();

        for (int i = 0; i < exchangeRateTableNodes.getLength(); ++i) {
            Node node = exchangeRateTableNodes.item(i);
            switch (node.getNodeName()) {
                case "numer_tabeli":
                    tableNo = node.getTextContent();
                    break;

                case "data_notowania":
                    noteDate = LocalDate.parse(node.getTextContent());
                    break;

                case "data_publikacji":
                    publishDate = LocalDate.parse(node.getTextContent());
                    break;

                case "pozycja":
                    dayCurrencyList.add(getDayCurrency(node));
                    break;

            }
        }

        return new ExchangeRateTable(tableNo, noteDate, publishDate, dayCurrencyList);
    }

    private static DayCurrency getDayCurrency(Node node) {
        String name = null;
        BigDecimal converter = null;
        String code = null;
        BigDecimal purchaseRate = null;
        BigDecimal sellingDate = null;

        NodeList positions = node.getChildNodes();
        for (int i = 0; i < positions.getLength(); ++i) {
            Node x = positions.item(i);
            switch (x.getNodeName()) {
                case "nazwa_waluty":
                    name = x.getTextContent();
                    break;

                case "przelicznik":
                    converter = new BigDecimal(x.getTextContent().replace(',', '.'));
                    break;

                case "kod_waluty":
                    code = x.getTextContent();
                    break;

                case "kurs_kupna":
                    purchaseRate = new BigDecimal(x.getTextContent().replace(',', '.'));
                    break;

                case "kurs_sprzedazy":
                    sellingDate = new BigDecimal(x.getTextContent().replace(',', '.'));
                    break;
            }
        }

        return new DayCurrency(name, converter, code, purchaseRate, sellingDate);
    }
}
