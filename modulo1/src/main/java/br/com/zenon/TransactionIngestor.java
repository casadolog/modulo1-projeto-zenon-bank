package br.com.zenon;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.*;

public class TransactionIngestor {

    private final File arquivo;
    private List<Transaction> lista;

    public TransactionIngestor(String nome_arquivo) throws FileNotFoundException {
        arquivo = getFile(nome_arquivo);
        carregarLinhas(1000);
    }
    private static File getFile(String nome_arquivo)
    {
        return new File(Paths.get("data/"+nome_arquivo).toUri());
    }
    private Transaction linhaToTransactiton(String linha)
    {
        String[] dados = linha.split(",");
        
        int step = Integer.parseInt(dados[0]);
        TransactionType type = TransactionType.valueOf(dados[1]);
        BigDecimal amount = new BigDecimal(dados[2]);
        String nameOrig = dados[3];
        BigDecimal oldbalanceOrg = new BigDecimal(dados[4]);
        BigDecimal newbalanceOrig = new BigDecimal(dados[5]);
        String nameDest = dados[6];
        BigDecimal oldbalanceDest = new BigDecimal(dados[7]);
        BigDecimal	newbalanceDest = new BigDecimal(dados[8]);
        boolean isFraud = dados[9].equals("1");
        boolean isFlaggedFraud = dados[10].equals("1");
        
        return new Transaction(step,type, amount, nameOrig, oldbalanceOrg,
            newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest,
        isFraud, isFlaggedFraud);
    }
    private void carregarLinhas(int numero_linhas) throws FileNotFoundException {
        lista = new ArrayList<>();
        try(Scanner scanner = new Scanner(arquivo))
        {
            int cont = 0;
            while (scanner.hasNext() && cont < numero_linhas) {
                if(cont == 0)
                    scanner.next();
                String linha = scanner.next();
                lista.add(linhaToTransactiton(linha));
                cont++;
            }
        }
    }
    public Transaction getTransationByPosicao(int posicao) throws Exception {
        try(Scanner scanner = new Scanner(arquivo))
        {
            int cont = 0;
            while (scanner.hasNext()) {
                String linha = scanner.next();
               // System.out.println(scanner.next());
                if(cont == posicao)
                    return linhaToTransactiton(linha);
                cont++;
            }
        }
        throw new Exception("Linha não concontrada");
    }

    public List<Transaction> getLista() {
        return lista;
    }
}
