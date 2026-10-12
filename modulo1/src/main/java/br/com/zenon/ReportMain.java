package br.com.zenon;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportMain {
    private static Logger logger = Logger.getLogger(ReportMain.class.toString());

    public static void main(String[] args)
    {
        try(Stream<String> stream_total_geral = Files.lines(Paths.get("data/"+"dados.csv"));
            Stream<String> stream_total_fraudes = Files.lines(Paths.get("data/"+"dados.csv"));
            Stream<String> stream_soma_transacoes = Files.lines(Paths.get("data/"+"dados.csv"));
        )
        {
            long totatl_linhas = stream_total_geral.map(linha -> linha.split(","))
                    .skip(1)
                    .count();



            System.out.println("Total de linhas: "+totatl_linhas);

            long totatl_fraudes = stream_total_fraudes.map(linha -> linha.split(","))
                    .skip(1)
                    .filter(v -> {return v[9].equals("1");})
                    .count();

            System.out.println("Total de frades: "+totatl_fraudes);

            Double soma_transacoes = stream_soma_transacoes.map(linha -> linha.split(","))
                    .skip(1)
                            .mapToDouble(v->{return Double.parseDouble(v[2]);})
                                    .sum();

            System.out.println("Total de transacoes: "+((new BigDecimal(soma_transacoes)).toPlainString()));

        }
        catch (Exception e)
        {
            logger.warning("erro ao ler arquivo com stream: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
