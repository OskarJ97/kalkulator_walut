package program;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.InputMismatchException;

public class currency_calc {

    public static void main(String[] args) {
        //declaring arrays with fixed length
        String currency[] = new String[32];
        Double rate[] = new Double[32];

        try {
            //Loading the .xml file with data
            File stocks = new File("./eurofxref-daily.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stocks);
            doc.getDocumentElement().normalize();
            //parsing xml file to get to the nested nodes
            System.out.println("root of xml file " + doc.getDocumentElement().getNodeName());
            NodeList nodes = doc.getElementsByTagName("Cube");
            System.out.println("==========================");
            //parsing xml file to get to the deeper nested nodes
            nodes = ((Element) nodes.item(0)).getElementsByTagName("Cube");
            System.out.println("==========================");

            nodes = ((Element) nodes.item(0)).getElementsByTagName("Cube");
            //filling arrays with data from .xml file
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                currency[i] = ((Element) nodes.item(i)).getAttribute("currency");
                rate[i] = Double.parseDouble(((Element) nodes.item(i)).getAttribute("rate"));
            }
        } catch (FileNotFoundException noFile_err) {
            System.out.println("Umieść plik we właściwej lokalizacji i uruchom program ponownie");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Scanner amount_in = new Scanner(System.in);
        boolean properValue = false;
        Double eur_amount = 0.0d;
        //getting EUR amount to calculate
        while (!properValue) {
            try {
                System.out.println("Podaj kwotę w EUR: ");
                eur_amount = Double.parseDouble(amount_in.nextLine());         //waiting for input of EUR amount
                System.out.println("Wybrana kwota EUR: " + eur_amount);
                properValue = true;
            } catch (InputMismatchException err1) {                            //Error handling in case of improper input
                System.out.println("Niepoprawna liczba. Spróbuj ponownie");
                continue;
            } catch (NumberFormatException err1) {                             //Error handling in case of improper input
                System.out.println("Niepoprawna liczba. Spróbuj ponownie");
                continue;
            }
        }

        Scanner currency_in = new Scanner(System.in);
        String foreign_currency = " ";
        boolean properValue2 = false;
        //getting desired currency
        while (!properValue2) {
            try {
                System.out.println("Możliwe do wybrania waluty: " + Arrays.toString(currency));
                foreign_currency = currency_in.nextLine();
                //checking if desired currency is present in currency[] array
                for (int i = 0; i < currency.length; i++) {
                    if (foreign_currency.equalsIgnoreCase(currency[i])) {
                        System.out.println("Kwota po przewalutowaniu EUR na: " + foreign_currency.toUpperCase() +
                                " równa się: " + eur_amount * rate[i]); //calculating final exchange value
                        properValue2 = true;
                        if (properValue2 = true) return;
                    }
                }
                throw new Exception("Podaj poprawną walutę");
            } catch (Exception e) {
                System.out.println(e.getMessage());                           //Error handling in case of improper input
                continue;
            }
        }
    }
}