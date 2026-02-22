package ma.enset.presentation;

import ma.enset.framework.MiniApplicationContext;
import ma.enset.metier.IMetier;

public class Main {
    public static void main(String[] args) throws Exception {

        MiniApplicationContext context =
                new MiniApplicationContext("src/main/resources/config/beans.xml");

        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("Résultat = " + metier.calcul());
    }
}