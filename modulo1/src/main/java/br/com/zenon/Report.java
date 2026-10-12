package br.com.zenon;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Report {
    private static Logger logger = Logger.getLogger(Report.class.toString());
    public static Stream<String> transationReport()
    {
        try(Stream<String> stream = Files.lines(Paths.get("data/"+"dados.csv")); )
        {
            return stream;
        }
        catch (Exception e)
        {
            logger.warning("erro ao ler arquivo com stream: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
