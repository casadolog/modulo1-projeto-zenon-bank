package br.com.zenon;

import java.math.BigDecimal;

public record Transaction(int step,	TransactionType type, BigDecimal amount, String nameOrig, BigDecimal oldbalanceOrg,
                          BigDecimal newbalanceOrig, String nameDest, BigDecimal oldbalanceDest, BigDecimal	newbalanceDest,
                          boolean isFraud, boolean isFlaggedFraud) {

    @Override
    public String toString() {
        return "Transaction{" +
                "step=" + step +
                ", type=" + type +
                ", amount=" + amount +
                ", nameOrig='" + nameOrig + '\'' +
                ", oldbalanceOrg=" + oldbalanceOrg +
                ", newbalanceOrig=" + newbalanceOrig +
                ", nameDest='" + nameDest + '\'' +
                ", oldbalanceDest=" + oldbalanceDest +
                ", newbalanceDest=" + newbalanceDest +
                ", isFraud=" + isFraud +
                ", isFlaggedFraud=" + isFlaggedFraud +
                '}';
    }
}
