package pl.parser.nbp.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class XMLFilesDownloader {

    private static final String EXCHANGE_RATE_URL_PART =  "http://www.nbp.pl/kursy/xml/";
    private static final String DIR_FILENAME = "dir";
    private static final String DIR_EXTENSION = ".txt";

    public static List<File> downloadXMLsBetweenDates(LocalDate beginDate, LocalDate endDate) throws MalformedURLException {
        List<File> files = downloadDirFilesWithFileNames(beginDate, endDate);

        System.out.println(files);

        return null;
    }

    private static List<File> downloadDirFilesWithFileNames(LocalDate beginDate, LocalDate endDate) throws MalformedURLException {
        List<File> files = new ArrayList<>();

        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Begin date (" + beginDate + ") is after end date (" + endDate + ").");
        }

        if (beginDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Begin date is after current date.");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date is after current date.");
        }

        Year currYear = Year.from(beginDate);

        while (!currYear.isAfter(Year.from(endDate))) {
            URL url = new URL(EXCHANGE_RATE_URL_PART + DIR_FILENAME + (currYear.equals(Year.now()) ? "" : currYear) + DIR_EXTENSION);
            files.add(new File(url.getFile()));
            currYear = currYear.plusYears(1);
        }

        return files;
    }
}
