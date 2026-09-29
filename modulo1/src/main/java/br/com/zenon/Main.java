package br.com.zenon;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        DataBase dataBase = new DataBase();
    //    dataBase.getTransationByPosicao(1);


        System.out.println(dataBase.getTransationByPosicao(1));
        System.out.println(dataBase.getTransationByPosicao(3));
    }
}