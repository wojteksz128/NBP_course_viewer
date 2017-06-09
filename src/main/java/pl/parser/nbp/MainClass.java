package pl.parser.nbp;

import pl.parser.nbp.util.NBPXMLs;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println(NBPXMLs.getXMLDataBetween(LocalDate.now().minusYears(1), LocalDate.now()));
    }
}
