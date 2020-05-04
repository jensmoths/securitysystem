package localserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CommandLine implements Runnable {

    String[] commandTakePic = {"ping", "google.se"};        //skriv in kommandot "raspistill","-o", "/home/pi/pic/cam"+number+".jpg"
    static int number = 0;


    @Override
    public void run() {



        ProcessBuilder processBuilder = new ProcessBuilder(commandTakePic);
        processBuilder.directory(new File(System.getProperty("user.home")));

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

           while ((line = reader.readLine()) != null) {

                System.out.println(line);
            }

            int exitCode = process.waitFor();

            System.out.println("\nExited with error code: " + exitCode);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }

    }


}