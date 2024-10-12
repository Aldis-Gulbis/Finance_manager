//Aldis Gulbis

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Finance_manager {
    private static Scanner sc = new Scanner(System.in);

    private static final String Income_filename = "Income_records.csv";
    private static final String Expenses_filename = "Expenses_records.csv";
    private static final String Savings_filename = "Savings_records.csv";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String string_formatter = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$";

    public static void main(String[] args) {
        while (true) {
            System.out.println("Izvelieties opciju: ");
            //System.out.println("1) Skatit kopsavilkumu;");
            System.out.println("2) Pievienot ienakumus;");
            System.out.println("3) Pievienot izdevumus;");
            System.out.println("4) Krajriks;");
            System.out.println("5) Rediget ierakstu;");
            System.out.println("6) Meklet ierakstu;");
            //System.out.println("7) Algas kalkuklators;")
            System.out.println("8) Iziet.");

            int izvele = sc.nextInt();

            switch (izvele) {
                /*case 1:
                    summary();
                    break;*/

                case 2:
                    income();
                    break;

                case 3:
                    expenses();
                    break;

                case 4:
                    savings();
                    break;

                case 5:
                    edit();
                    break;

                case 6:
                    find();
                    break;

                /*case 7:
                    algas_kalkulators();
                    break;*/

                case 8:
                    sc.close();
                    return;

                default:
                    System.out.println("\nNepareiza opcija, meginiet velreiz.");
            }
        }
    }

    //===================================================================================================== IENAKUMI

    public static void income() {
        File Income_file = new File(Income_filename);

        try{
            if(!Income_file.exists()){
                Income_file.createNewFile();
                Income_records("Ienakumi" + "," + "Kategorija" + "," + "Datums");
            }
        }catch(IOException e){
            System.out.println("\nNespeja izveidot failu/-s");
            e.printStackTrace();
        }

        double ienakumi = 0.0;
        boolean iespejams = false;

        while (!iespejams) {
            System.out.println("\nIevadiet ienakumu daudzumu: ");

            String ievade = sc.next();

            try {
                double temp = Double.parseDouble(ievade);

                ienakumi = Double.parseDouble(String.format("%.2f", temp));
                iespejams = true;
            } catch (NumberFormatException e) {
                System.out.println("\nNepareizi ievaditi ienakumi, meginiet velreiz.");
            }
        }

        System.out.println("\nIevadiet ienakumu kategoriju (viens vards): ");

        String ienakumu_kategorija = sc.next();

        LocalDate tagad = LocalDate.now();
        String datums = tagad.format(formatter);

        Income_records(ienakumi + " €" + "," + ienakumu_kategorija + "," + datums);

        System.out.println();
    }

    //===================================================================================================== IZDEVUMI

    public static void expenses() {
        File Expenses_file = new File(Expenses_filename);

        try{
            if(!Expenses_file.exists()){
                Expenses_file.createNewFile();
                Expenses_records("Izdevumi" + "," + "Kategorija" + "," + "Datums");
            }
        }catch(IOException e){
            System.out.println("Nespeja izveidot failu/-s");
            e.printStackTrace();
        }

        double izdevumi = 0.0;
        boolean iespejams = false;

        while (!iespejams) {
            System.out.println("\nIevadiet izdevumu daudzumu: ");

            String ievade = sc.next();

            try {
                double temp = Double.parseDouble(ievade);

                izdevumi = Double.parseDouble(String.format("%.2f", temp));
                iespejams = true;
            } catch (NumberFormatException e) {
                System.out.println("\nNepareizi ievaditi izdevumi, meginiet velreiz.");
            }
        }

        System.out.println("\nIevadiet izdevumu kategoriju (viens vards): ");

        String izdevumu_kategorija = sc.next();

        LocalDate tagad = LocalDate.now();
        String datums = tagad.format(formatter);

        Expenses_records(izdevumi + " €" + "," + izdevumu_kategorija + "," + datums);

        System.out.println();
    }

    //===================================================================================================== KRAJRIKS

    public static void savings(){
        File Savings_file = new File(Savings_filename);

        try{
            if(!Savings_file.exists()){
                Savings_file.createNewFile();
                Savings_records("Merka summa" + "," + "Sakuma datums" + "," + "Merka datums");
            }
        }catch(IOException e){
            System.out.println("Nespeja izveidot failu/-s");
            e.printStackTrace();
        }

        double merkis = 0.0;
        boolean iespejams_merkis = false;

        while (!iespejams_merkis) {
            System.out.println("\nIevadiet merka summu: ");

            String ievade_merkis = sc.next();

            try {
                double temp = Double.parseDouble(ievade_merkis);

                merkis = Double.parseDouble(String.format("%.2f", temp));
                iespejams_merkis = true;
            } catch (NumberFormatException e) {
                System.out.println("\nNepareizi ievadita merka summa, meginiet velreiz.");
            }
        }

        boolean iespejams_datums = false;
        LocalDate merka_datums = null;

        while (!iespejams_datums) {
            System.out.println("\nIevadiet merka datumu (dd.MM.yyyy): ");

            String ievade_datums = sc.next();

            try {
                merka_datums = LocalDate.parse(ievade_datums, formatter);
                iespejams_datums = true;
            } catch (DateTimeParseException e) {
                System.out.println("\nNepareizi ievadits datums, meginiet velreiz.");
            }
        }

        LocalDate ievades_datums = LocalDate.now();
        String datums = ievades_datums.format(formatter);

        Savings_records(merkis + " €" + "," + datums + "," + merka_datums);
    }

    //===================================================================================================== KOPSAVILKUMS

    /*public static void summary(){

    }*/

    //===================================================================================================== REDIGESANA
    
    private static void edit(){
        System.out.println("\nKo Jus velaties rediget: ");
        System.out.println("1) Ienakumus;");
        System.out.println("2) Izdevumus;");
        System.out.println("3) Krajriku;");
        System.out.println("4) Doties atpakal.");

        int izvele = sc.nextInt();

        System.out.println();

        switch (izvele){
            case 1:
                edit_income();
                break;

            case 2:
                edit_expenses();
                break;

            case 3:
                edit_savings();
                break;

            case 4:
                System.out.println();
                break;

            default:
                System.out.println("\nNepareiza opcija, meginiet velreiz.");
        }
    }

    //----------------------------------------------------------------------------------------------------- Redigesana ienakumos

    private static void edit_income(){
        String fails = "Income_records.csv";
        List<String> lines = new ArrayList<>();

        try(Scanner scanner = new Scanner(new FileReader(fails))){
            int i = 0;
            System.out.print(scanner.nextLine() + "\n");

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.println((i + 1) + ". " + line);
                lines.add(line);
                i++;
            }

        }catch (FileNotFoundException ex){
            System.out.println("\nIeraksti/-s nav atrasti, ludzu parliecienieties, ka esat tadus veikusi.");
            return;
        }

        System.out.println("\nKuru ieraskstu Jus veletos rediget:");
        int izvele = sc.nextInt();

        if(izvele - 1 < 0 || izvele - 1 >= lines.size()){
            System.out.println("\nNepareiza izvele");
            return;
        }

        String[] informacija = lines.get(izvele - 1).split(",");

        System.out.println("\nKo Jus veletos rediget: ");
        System.out.println("1) Summu;");
        System.out.println("2) Kategoriju.");

        int izvele_2 = sc.nextInt();
        sc.nextLine();

        System.out.println("\nIevadiet jauno vertibu: ");
        String korekcija = sc.nextLine();

        switch(izvele_2){
            case 1:
                if(isDouble(korekcija)){
                    double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(korekcija)));
                    informacija[0] = String.valueOf(temp + " €");

                }else{
                    System.out.println("\nNepareizi ievadita vertiba.");
                    return;
                }
                break;

            case 2:
                informacija[1] = korekcija;
                break;
            
            default:
                System.out.println("\nNepareiza opcija, meginiet velreiz.");
                return;
        }

        lines.set(izvele, String.join(",", informacija));

        try(PrintWriter writer = new PrintWriter(new FileWriter(fails))){
            for(String line : lines){
                writer.println(line);
            }
        }catch(IOException ex){
            System.out.println("\nParliecienieties, ka esat veikusi ierakstus.");
        }

        System.out.println("\nIeraksts veiksmigi labots.");
    }

    //----------------------------------------------------------------------------------------------------- Redigesana izdevumos

    private static void edit_expenses(){
        String fails = "Expenses_records.csv";
        List<String> lines = new ArrayList<>();

        try(Scanner scanner = new Scanner(new FileReader(fails))){
            int i = 0;
            System.out.println(scanner.nextLine() + "\n");

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.println((i + 1) + ". " + line);
                lines.add(line);
                i++;

            }

            scanner.close();

        }catch(FileNotFoundException ex){
            System.out.println("\nParliecinieties, ka esat izveidojusi ierakstus");
            return;
        }

        System.out.println("\nKuru ierakstu Jus veletos rediget: ");
        int izvele = sc.nextInt();

        if((izvele - 1) < 0 || (izvele - 1) >= lines.size()){
            System.out.println("\nNepareiza izvele");
            return;
        }

        String[] informacija = lines.get(izvele - 1).split(",");

        System.out.println("\nKo Jus veletos rediget: ");
        System.out.println("1) Summu; ");
        System.out.println("2) Kategoriju. ");

        int izvele_2 = sc.nextInt();
        sc.nextLine();

        System.out.println("\nIevadiet jauno vertibu: ");
        String korekcija = sc.next();

        switch(izvele_2){
            case 1:
                if(isDouble(korekcija)){
                    double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(korekcija)));
                    informacija[0] = String.valueOf(temp + " €");

                }else{
                    System.out.println("\nNepareizi ievadita vertiba.");
                    return;
                }
                break;
            
            case 2:
                informacija[1] = korekcija;
                break;

            default:
                System.out.println("\nNepareiza opcija, meginiet velreiz.");
                return;

        }

        lines.set(izvele, String.join(",", informacija));

        try(PrintWriter writer = new PrintWriter(new FileWriter(fails))){
            for(String line: lines){
                writer.write(line);
            }

        }catch(IOException ex){
            System.out.println("\nParliecinieties, ka esat izveidojusi ierakstus");
        }

        System.out.println("\nIeraksts veiksmigi labots.");
    }

    //----------------------------------------------------------------------------------------------------- Redigesana krajrika

    private static void edit_savings(){
        String fails = "Savings_records.csv";
        List<String> lines = new ArrayList<>();

        try(Scanner scanner = new Scanner(new FileReader(fails))){
            int i = 0;
            System.out.println(scanner.nextLine() + "\n");

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.print((i + 1) + ". " + line);
                lines.add(line);
                i++;
            }

            scanner.close();

        }catch(FileNotFoundException ex){
            System.out.println("\nParliecinieties, ka esat izveidojusi ierakstus");
        }

        System.out.println("\nKuru ierakstu Jus veletos rediget: ");
        int izvele = sc.nextInt();

        if((izvele - 1) < 0 || (izvele - 1) >= lines.size()){
            System.out.println("\nNepareiza izvele");
            return;
        }

        String[] informacija = lines.get(izvele - 1).split(",");

        System.out.println("\nKo Jus veletos rediget: ");
        System.out.println("1) Summu; ");
        System.out.println("2) Merka datumu; ");

        int izvele_2 = sc.nextInt();
        sc.nextLine();

        switch(izvele_2){
            case 1:
                System.out.println("\nIevadiet jauno vertibu: ");
                String korekcija = sc.next();

                if(isDouble(korekcija)){
                    double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(korekcija)));
                    informacija[0] = String.valueOf(temp + " €");

                }else{
                    System.out.println("\nNepareizi ievadita vertiba.");
                    return;
                }
                break;

            case 2:
                System.out.println("\nIevadiet jauno merka datumu(dd.MM.yyyy): ");
                String temp_datums = sc.next();
                if(isDate(temp_datums)){
                    informacija[2] = temp_datums;

                }else{
                    System.out.println("\nNepareizi ievadits datums, meginiet velreiz. ");
                    return;
                }

                break;

            default:
                System.out.println("\nNepareiza opcija, meginiet velreiz");
                return;
        }

        lines.set(izvele, String.join(",", informacija));

        try(PrintWriter writer = new PrintWriter(new FileWriter(fails))){
            for(String line: lines){
                writer.write(line);
            }
        }catch(IOException ex){
            System.out.println("\nParliecienieties, ka esat veikusi ierakstus.");
        }

        System.out.println("\nIeraksts veikmigi labots.");

    }

    //===================================================================================================== MEKLESANA

    private static void find(){
        System.out.println("\nKo Jus veletos atrast: ");
        System.out.println("1) Ierakstu ienakumos; ");
        System.out.println("2) Ierakstu izdevumos; ");
        System.out.println("3) Ierakstu krajrika; ");
        System.out.println("4) Doties atpakal.");

        int izvele = sc.nextInt();

        switch (izvele){
            case 1:
                find_in_Income();
                break;

            case 2:
                find_in_Expenses();
                break;

            case 3:
                find_in_Savings();
                break;

            case 4:
                System.out.println();
                break;

            default:
                System.out.println("\nNepareiza opcija, meginiet velreiz.");
        }
    }

    //----------------------------------------------------------------------------------------------------- Meklesana ienakumos

    public static void find_in_Income(){
        String fails = "Income_records.csv";
        List<String> lines = new ArrayList<>();

        System.out.println("\n Ko jus veletos atrast (datumu ievadiet ka dd.MM.yyyy): \n");

        String meklet_temp = sc.nextLine();
        sc.nextLine();
        String meklet = "";

        if(isDouble(meklet_temp)){
            double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(meklet_temp)));
            meklet = temp + " €";

        }else if(isDate(meklet_temp)){
            meklet = meklet_temp;

        }else{
            System.out.println("\nNepareizi ievadita vertiba.");
            return;
        }

        try{
            Scanner scanner = new Scanner(new FileReader(fails));

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);
            }

            scanner.close();

        }catch(Exception ex){
            System.out.println("\nJa neesat izveidojusi ierakstu ieprieks, meginiet velreiz.");
        }

        boolean found = false;
        for(int i = 0; i < lines.size(); i++){
            String[] informacija = lines.get(i).split(",");

            if(meklet.equals(informacija[0]) || meklet.equals(informacija[1]) || meklet.equals(informacija[2])){
                System.out.println(lines.get(i));
                found = true;

            }
        }

        if(!found){
            System.out.println("\nNeviens ieraksts netika atrasts.");
            return;
        
        }

        System.out.println();
    }

    //----------------------------------------------------------------------------------------------------- Meklesaa izdevumos

    public static void find_in_Expenses(){
        String fails = "Expenses_records.csv";
        List<String> lines = new ArrayList<>();

        System.out.println("\n Ko jus veletos atrast (datumu ievadiet ka dd.MM.yyyy): \n");

        String meklet_temp = sc.nextLine();
        sc.nextLine();
        String meklet = "";

        if(isDouble(meklet_temp)){
            double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(meklet_temp)));
            meklet = temp + " €";

        }else if(isDate(meklet_temp)){
            meklet = meklet_temp;

        }else{
            System.out.println("\nNepareizi ievadita vertiba.");
            return;
        }

        try{
            Scanner scanner = new Scanner(new FileReader(fails));

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);

            }

            scanner.close();

        }catch(Exception ex){
            System.out.println("\nJa neesat izveidojusi ierakstu ieprieks, meginiet velreiz.");
        }

        boolean found = false;
        for(int i = 0; i < lines.size(); i++){
            String[] informacija = lines.get(i).split(",");

            if(meklet.equals(informacija[0]) || meklet.equals(informacija[1]) || meklet.equals(informacija[2])){
                System.out.println(lines.get(i));
                found = true;

            }
        }

        if(!found){
            System.out.println("\nNeviens ieraksts netika atrasts.");
            return;
        }

        System.out.println();

    }

    //----------------------------------------------------------------------------------------------------- Meklesana krajrika

    public static void find_in_Savings(){
        String fails = "Savings_records.csv";
        List<String> lines = new ArrayList<>();

        System.out.println("\n Ko jus veletos atrast (datumu ievadiet ka dd.MM.yyyy): \n");

        String meklet_temp = sc.nextLine();
        sc.nextLine();
        String meklet = "";

        if(isDouble(meklet_temp)){
            double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(meklet_temp)));
            meklet = temp + " €";

        }else if(isDate(meklet_temp)){
            meklet = meklet_temp;

        }else{
            System.out.println("\nNepareizi ievadita vertiba.");
            return;
        }

        try{
            Scanner scanner = new Scanner(new FileReader(fails));

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);

            }

            scanner.close();

        }catch(Exception ex){
            System.out.println("\nJa neesat izveidojusi ierakstu ieprieks, meginiet velreiz.");
        }

        boolean found = false;
        for(int i = 0; i < lines.size(); i++){
            String[] informacija = lines.get(i).split(",");

            if(meklet.equals(informacija[0]) || meklet.equals(informacija[1]) || meklet.equals(informacija[2])){
                System.out.println(lines.get(i));
                found = true;

            }
        }

        if(!found){
            System.out.println("\nNeviens ieraksts netika atrasts.");
            return;
        }

        System.out.println();
    }

    //===================================================================================================== ALGAS KALKULATORS

    /*private static void algas_kalkultors(){
        System.out.println("\nIevadiet savu algu pirms nodokļiem: ");
        String alga_bruto = sc.nextLine();

        double darba_deveja_izmaksas = 23.59;
        double VSAOI = 10.50;


        if(isDouble(alga_bruto)){
            double temp = Double.parseDouble(String.format("%.2f", Double.parseDouble(alga_bruto)));
            
            double VSAOI_dala = (temp * VSAOI);

        }else{
            System.out.println("\nNepareizi ievadita vertiba.");
            return;

        }

        System.out.println();
    }*/

    //===================================================================================================== IERAKSTU GLABASANA

    private static void Income_records(String record){
        try(PrintWriter out = new PrintWriter(new FileWriter (Income_filename, true))){
                out.println(record);

        }catch(IOException e){
            System.out.println("\nNespeja saglabat ievadito iformaciju.");
        }
    }

    private static void Expenses_records(String record){
        try(PrintWriter out = new PrintWriter(new FileWriter (Expenses_filename, true))){
                out.println(record);

        }catch(IOException e){
            System.out.println("\nNespeja saglabat ievadito iformaciju.");
        }
    }
    private static void Savings_records(String record){
        try(PrintWriter out = new PrintWriter(new FileWriter (Savings_filename, true))){
                out.println(record);

        }catch(IOException e){
            System.out.println("\nNespeja saglabat ievadito iformaciju.");
        }
    }

    //===================================================================================================== PARBAUDE

    public static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;

        }catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isDate(String date_str){
        if(!Pattern.matches(string_formatter, date_str)){
            return false;

        }else{
            return true;
        }
    }
}