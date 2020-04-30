import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CommandLine {

    String[] commandTakePic = {"raspistill","-o", "/home/pi/pic/cam"+number+".jpg"};        //skriv in kommandot
    static int number = 0;


    public CommandLine(){
        
        ProcessBuilder processBuilder = new ProcessBuilder(commandTakePic);
        processBuilder.directory(new File(System.getProperty("user.home")));

        try{
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while((line = reader.readLine()) != null){

                System.out.println(line);
            }

            int exitCode = process.waitFor();

            System.out.println("\nExited with error code: "+ exitCode);


        }catch (IOException | InterruptedException e){
            e.printStackTrace();

        }

    }

    public static void main(String[] args) {
        CommandLine CL = new CommandLine();

    }
}
