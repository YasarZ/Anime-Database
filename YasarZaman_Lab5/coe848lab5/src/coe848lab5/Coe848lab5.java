/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe848lab5;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author yasar
 */
public class Coe848lab5 {

    Connection c = null;
    Statement message;
    Scanner input;
    int test = 0;
    
    public Coe848lab5() throws SQLException {
        c = DriverManager.getConnection("jdbc:sqlite:F:\\Final Semester\\COE 848\\animeList.db");
        message = c.createStatement();
    }
    
    
    
    void answer (ResultSet I) throws SQLException {
        ResultSetMetaData random = I.getMetaData();
        int cNumber = random.getColumnCount();
        for (int i = 1; i <= cNumber; i++) {
            if (i > 1) {
                System.out.println (", ");
            }
            System.out.print (random.getColumnName(i));
        }
        System.out.println();
        while (I.next()) {
            for (int i = 1; i <= cNumber; i++) {
                if (i > 1) {
                    System.out.println (", ");
                }
                String cValue = I.getString(i);
                System.out.println (cValue + " ");
            }
            System.out.println ();
        }
        input.nextLine();
    }
    //1
    ResultSet getTotal() throws SQLException {
        String queryCode = "SELECT animeGenre, COUNT(animeGenre) AS cnt FROM anime " +
                           "GROUP BY animeGenre " +
                           "ORDER BY cnt DESC;";
        return message.executeQuery(queryCode);
    }
    //2
    ResultSet appearMost() throws SQLException {
        String queryCode = "SELECT vaName, COUNT(vaName) AS cnt FROM voiceActor " +
                           "GROUP BY vaName " +
                           "ORDER BY cnt DESC;";
        return message.executeQuery(queryCode);
    }
    //3
    ResultSet listRating() throws SQLException {
        String queryCode = "SELECT animeName FROM anime WHERE animeRating >= 7.00 " +
                           "ORDER BY animeRating;";
        return message.executeQuery(queryCode);
    }
    //4
    ResultSet mostAnime() throws SQLException {
        String queryCode = "SELECT animeRD, COUNT(strftime('%Y', animeRD)) AS cnt FROM anime " + 
                           "GROUP BY animeRD " +
                           "ORDER BY cnt DESC;";
        return message.executeQuery(queryCode);
    }
    //5
    ResultSet lowestRated() throws SQLException {
        String queryCode = "SELECT min(animeRating) FROM anime;";
        return message.executeQuery(queryCode);
    }
    //6
    ResultSet highestRated() throws SQLException {
        String queryCode = "SELECT max(animeRating) FROM anime;";
        return message.executeQuery(queryCode);
    }
    //7
    ResultSet voiceActor(String va) throws SQLException {
        String queryCode = "SELECT vaAge FROM voiceActor WHERE vaName = '" + va + "';" ;
        return message.executeQuery(queryCode);
    }
    //8
    ResultSet averageLength(String genre)throws SQLException {
        String queryCode = "SELECT avg(animeNOE) FROM anime WHERE animeGenre = '" + genre + "';";
        return message.executeQuery(queryCode);
    }
    //9
    ResultSet oldestStudio() throws SQLException {
        String queryCode = "SELECT studioDOB, COUNT(strftime('%Y', studioDOB)) AS cnt FROM STUDIO " +
                           "GROUP BY studioDOB " +
                           "ORDER BY cnt ASC;";
        return message.executeQuery(queryCode);
    }
    //10
    ResultSet averageRating(String genre) throws SQLException {
        String queryCode = "SELECT avg(animeRating) FROM anime WHERE animeGenre = '" + genre + "';";
        return message.executeQuery(queryCode);
    }
    
    public void options() throws SQLException {
        String questions = "1. Most watched genre of anime\n" +
                           "2. Which voice actor appears the most\n" +
                           "3. Which anime has at least a 7.00 rating\n" +
                           "4. Which year of anime did you watch the most\n" +
                           "5. What is your lowest rated anime\n" +
                           "6. What is your highest rated anime\n" +
                           "7. How old is a certain actor\n" +
                           "8. What is the average length in episodes of a particular genre\n" +
                           "9. Which studio is the oldest established studio\n" +
                           "10. What is the average rating for slice of life animes";
        
        input = new Scanner(System.in);
        System.out.println ("Please select your question from the anime database");
        System.out.println ("If you would like to quit, type f");
        System.out.println (questions);
        
        System.out.println ();
        
        while (test == 0) {
            switch (input.nextLine()) {
                case "1":
                    answer(getTotal());
                    break;
                case "2":
                    answer(appearMost());
                    break;
                case "3":
                    answer(listRating());
                    break;
                case "4":
                    answer(mostAnime());
                    break;
                case "5":
                    answer(lowestRated());
                    break;
                case "6":
                    answer(highestRated());
                    break;
                case "7":
                    System.out.println ("What is the voice actor's name: ");
                    answer(voiceActor(input.nextLine()));
                    break;
                case "8":
                    System.out.println ("What is the name of the genre: ");
                    answer(averageLength(input.nextLine()));
                    break;
                case "9":
                    answer(oldestStudio());
                    break;
                case "10":
                    System.out.println ("What is the name of the genre: ");
                    answer(averageRating(input.nextLine()));
                    break;
                case "f":
                    test = 1;
                    break;
                default:
                    System.out.println("Try Again");
            }
            if (test != 0) {
                System.out.println ("Please select your question from the anime database");
                System.out.println ("If you would like to quit, type f");
                System.out.println (questions);
            }
        }
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Coe848lab5 db = new Coe848lab5();
            db.options();
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
    
}
