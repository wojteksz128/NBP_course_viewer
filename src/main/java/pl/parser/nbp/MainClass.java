package pl.parser.nbp;

import pl.parser.nbp.util.XMLFilesDownloader;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.Year;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class MainClass {

    public static void main(String[] args) throws MalformedURLException {
        XMLFilesDownloader.downloadXMLsBetweenDates(LocalDate.now().minusYears(1), LocalDate.now());
    }
}
