package br.com.zenon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public void readAsStream(String nome_arquivo, Consumer<Transaction> acao)
    {
        Path arquivo = Path.of(nome_arquivo);
        try(Stream<String> stream = Files.lines(arquivo))
        {
            stream.skip(1).limit(10000).map(TransactionIngestor::linhaToTransactiton).forEach(acao);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    public void readBatch(String nome_arquivo, Consumer<List<Transaction>> acao)
    {
        Path arquivo = Path.of(nome_arquivo);

        final int TAMANHO_LOTE = 10000;
        boolean tem_registro = true;

        int pula = 1;

        while (tem_registro) {

            try (Stream<String> stream = Files.lines(arquivo)) {
                List<Transaction> lista = stream.skip(pula).limit(TAMANHO_LOTE).map(TransactionIngestor::linhaToTransactiton).toList();
                if(lista.size() == 0)
                    tem_registro = false;
                else {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                acao.accept(lista);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });

                }

                pula += TAMANHO_LOTE;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}


