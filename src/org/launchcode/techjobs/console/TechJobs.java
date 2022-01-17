package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);
    //---------------------------------------------------------------------------------------------------------------------
    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {
            //passes in arguments into getUserSelection and stores returned value into actionChoice
            String actionChoice = getUserSelection("View jobs by:", actionChoices);
            //if actionChoice equals list
            if (actionChoice.equals("list")) {
                //calls getUserSelection again with choices of column choice(since its list) stores return in column choice
                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    //findAll just loads the data(returns all jobs) to printJobs
                    printJobs(JobData.findAll());
                } else {
                    //stores the arraylist of chosen value(s) into results
                    ArrayList<String> results = JobData.findAll(columnChoice);
                    //prints out header for natural values
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");
                    //iterates through the string arraylist results
                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // stores column choice in searchField
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term? store it in searchTerm
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();
                //returned value of getUserSelection
                if (searchField.equals("all")) {

                    //System.out.println("Search all fields not yet implemented.");
                    printJobs(JobData.findByValue(searchTerm.toUpperCase()));

                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm.toUpperCase()));
                }
            }
        }
    }
    //---------------------------------------------------------------------------------------------------------------------
    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }
    //---------------------------------------------------------------------------------------------------------------------
    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        //iterate through the arraylist of hashmaps someJobs
        for (HashMap<String, String> eachJob: someJobs) {
            //top line
            System.out.println("*****");
            //iterate over each key of the hashmap of a job
            for (HashMap.Entry<String, String> eachKeyValuePair: eachJob.entrySet()) {
                //prints key value pair by iterating through eachJob
                System.out.println(eachKeyValuePair.getKey() + ": " + eachKeyValuePair.getValue());
            }
            //bottom line
            System.out.println("*****");
        }

        //if there are no jobs.check case sensitivity
        if(someJobs.isEmpty()){
            System.out.println("job not found");
        }


    }
}
