package br.com.zenon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {
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
                else
                    acao.accept(lista);

                pula += TAMANHO_LOTE;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}


