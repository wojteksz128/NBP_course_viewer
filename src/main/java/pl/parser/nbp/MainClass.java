package pl.parser.nbp;

import pl.parser.nbp.util.NBPXMLParser;
import pl.parser.nbp.util.NBPXMLs;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println(NBPXMLParser.getDataFromXMLs(NBPXMLs.getXMLDataBetween(LocalDate.now().minusDays(1), LocalDate.now())));
    }
}
