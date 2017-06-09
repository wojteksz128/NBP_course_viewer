package pl.parser.nbp.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech Szczepaniak on 09.06.2017.
 */
public class NBPXMLs {

    private static final String EXCHANGE_RATE_URL_PART = "http://www.nbp.pl/kursy/xml/";
    private static final String DIR_FILENAME = "dir";
    private static final String DIR_EXTENSION = ".txt";
    private static final String EXCHANGE_RATE_TABLE_EXTENSION = ".xml";

    public static List<URL> getXMLDataBetween(LocalDate beginDate, LocalDate endDate) throws IOException {
        return getFileNamesFromDatesBetween(getDirFilesFromYearsBetween(beginDate, endDate), beginDate, endDate).stream()
                .parallel()
                .map(name -> {
                    try {
                        return new URL(EXCHANGE_RATE_URL_PART + name + EXCHANGE_RATE_TABLE_EXTENSION);
                    } catch (MalformedURLException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private static List<String> getFileNamesFromDatesBetween(List<URL> dirFiles, LocalDate beginDate, LocalDate endDate) throws IOException {
        List<String> fileNames = new ArrayList<>();

        for (URL dirFile : dirFiles) {
            BufferedReader br = new BufferedReader(new InputStreamReader(dirFile.openStream()));

            fileNames.addAll(br.lines().parallel().filter(str -> {
                if (!str.startsWith("c")) {
                    return false;
                }
                LocalDate date = LocalDate.parse(str.substring(5, 11), DateTimeFormatter.ofPattern("yyMMdd"));
                return (date.isAfter(beginDate) || date.isEqual(beginDate)) && (date.isBefore(endDate) || date.isEqual(endDate));
            }).collect(Collectors.toList()));
        }

        return fileNames;
    }

    private static List<URL> getDirFilesFromYearsBetween(LocalDate beginDate, LocalDate endDate) throws MalformedURLException {
        List<URL> files = new ArrayList<>();

        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Begin date (" + beginDate + ") is after end date (" + endDate + ").");
        }

        if (beginDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Begin date (" + beginDate + ") is after current date (" + LocalDate.now() + ").");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date (" + endDate + ") is after current date (" + LocalDate.now() + ").");
        }

        Year currYear = Year.from(beginDate);

        while (!currYear.isAfter(Year.from(endDate))) {
            files.add(new URL(EXCHANGE_RATE_URL_PART + DIR_FILENAME + (currYear.equals(Year.now()) ? "" : currYear) + DIR_EXTENSION));
            currYear = currYear.plusYears(1);
        }

        return files;
    }
}
