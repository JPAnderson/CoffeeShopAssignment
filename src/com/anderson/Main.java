package com.anderson;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws IOException {

        //Used to locate files
        File coffeeFile = new File("C:\\Users\\Jack\\IdeaProjects\\Assignment 5 - Coffee Shop\\src\\com\\anderson\\coffee.txt");
        File salesReport = new File("C:\\Users\\Jack\\IdeaProjects\\Assignment 5 - Coffee Shop\\src\\com\\anderson\\sales-report.txt");

        /*
         The index of each list is relative to the drink. E.g., Index 0 of every list is information on the cappuccino,
         index 1 is information on the espresso, etc...
         */
        ArrayList<String> fromFile = new ArrayList<>(); //Takes all from the coffee.txt file
        ArrayList<Double> drinkCost = new ArrayList<>(); //List of the cost to make of all drinks
        ArrayList<Double> drinkPrice = new ArrayList<>(); //List of each drink's price
        ArrayList<String> justDrinks = new ArrayList<>(); //List of just the drink titles
        ArrayList<Integer> drinksSold = new ArrayList<>(); //List of all the drinks sold
        ArrayList<Double> revenue = new ArrayList<>(); //List of each drink's revenue
        ArrayList<Double> expense = new ArrayList<>(); //List of each drink's expense
        ArrayList<Double> profit = new ArrayList<>(); //List of each drink's profit
        double totalExpenses = 0;
        double totalRevenue = 0;
        double totalProfit = 0;

        FileReader reader = new FileReader(coffeeFile);
        BufferedReader bufReader = new BufferedReader(reader);
        Scanner scanner = new Scanner(bufReader);

        scanner.useDelimiter(";|\n"); //When you hit a delimiter, add what's before it to the list

        while(scanner.hasNext()){
            fromFile.add(scanner.next());
        }
        scanner.close();
        bufReader.close();


        //This loop adds the cost to make, price, and the drink names to files.
        /*
         * Each row in coffee.txt is information about one cup of coffee; it's name, cost, and price.
         * The loop starts in the middle of the row, reads it, and reads the information on both sides, then
         * moves to the next row. That works by using i+3;
         */
        for(int i = 1; i <= 36; i = i+3){
            drinkCost.add(Double.parseDouble(fromFile.get(i)));
            drinkPrice.add(Double.parseDouble(fromFile.get(i+1)));
            justDrinks.add(fromFile.get(i-1));
        }

        Scanner s = new Scanner(System.in);
        boolean goodInput = false;
        while(!goodInput) //Break when all the information is verified legal
            try {
                //For every drink in just drinks, ask the user how many of them were sold...
                for (int j = 0; j <= 11; j++) {
                    System.out.println("How many " + justDrinks.get(j) + "(s) did you sell today?");
                    drinksSold.add(s.nextInt());//...and store it in the drinks sold list.
                }
                goodInput = true;
            }
            catch(InputMismatchException ime){
                System.out.println("Integers only please!");
                s.next();
            }

        //Math for finding the cost and price of each drink
        for(int k = 0; k <= 11; k ++){
            expense.add(drinksSold.get(k) * drinkCost.get(k));
            revenue.add(drinksSold.get(k) * drinkPrice.get(k));
        }

        //Math for finding the expenses and the revenue of each drink
        for(int l = 0; l <= 11; l++){
            profit.add(revenue.get(l) - expense.get(l));
            totalExpenses = totalExpenses + expense.get(l);
            totalRevenue = totalRevenue + revenue.get(l);
        }

        //Math for finding the profit of each drink
        for(int m = 0; m <= 11; m++){
            totalProfit = totalProfit + profit.get(m);
        }

        FileWriter writer = new FileWriter(salesReport);
        BufferedWriter bufWriter = new BufferedWriter(writer);

        //This line prints each drink's info to a row in sales-report.txt
        for(int n = 0; n <= 11; n++){
            bufWriter.write(justDrinks.get(n) + ": Sold " + drinksSold.get(n) + ", Expenses " + expense.get(n) + ", Revenue " + revenue.get(n) + ", Profit " + profit.get(n) + ".\n");
        }

        //Write totals at the end of the report
        bufWriter.write("Total expenses: " + totalExpenses + " Total Revenue: " + totalRevenue + " Total profit: " + totalProfit);
        bufWriter.close();
        System.out.println("Contents written to sales-report.txt");
    }
}