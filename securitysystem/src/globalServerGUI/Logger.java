package globalServerGUI;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;

public class Logger {




        private ArrayList<Long> timestamps = new ArrayList(100);
        private ArrayList<String> infoStrings = new ArrayList(100);
        private File infoLog = new File("files/infoLog");
        private int counter;

        /**
         * The constructor writes the first line to the file which is the only line that is not timestamped
         * and instantiates the counter variable to 0.
         *
         */

        public Logger() {

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(infoLog)))) {
                bw.write("Time:\t\t\t\t\tEvent:");
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter = 0;
        }

        /**
         * Receives a string from ServerController, puts a timestamp on it and appends it to the file infoLog.
         * @param info Information about events on the server
         *
         *
         */

        public void addToLog(String info) {
            String res = "";
            Date date = new Date();
            java.sql.Timestamp stamp = new Timestamp(date.getTime());
            long temp = stamp.getTime(); //Konverterar till long.

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("files/infoLog", true)))) {
                {
                    res += temp + "_" + info;
                    bw.write(res);
                    bw.newLine();

                }
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Reads from the file infoLog and divides the line into two parts, one for the time and one for the logged
         * event and adds them to arraylists at the same index (counter). The counter variable is increased for each line.
         */
        public void createArrayLists() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/infoLog")))) {
                String line = br.readLine();
                while (line != null) {
                    line = br.readLine();
                    if (line != null) {
                        if (line.contains("_")) {
                            String[] temp = line.split("_");

                            String time = temp[0]; //tiden.
                            String info = temp[1]; //Själva infon
                            infoStrings.add(counter,info); //infon lagras i infostrings
                            timestamps.add(counter, Long.parseLong(time));
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * Compares timestamps with logged, timestamped events and returns a string consisting of all the relevant info.
         * @param startHour     Hour of the time to start filter events from the server.
         * @param startMinute   Minute of the time to start filter events from the server.
         * @param endHour       Hour of the time to stop filter events from the server.
         * @param endMinute     Minute of the time to stop filter events from the server.
         * @return              Info about logged events on the server between the two chosen instances in time.
         */
        public String getLog(long startHour, long startMinute, long endHour, long endMinute) {

            String res = "";
            Timestamp tempStart;
            Timestamp tempEnd;

            LocalDateTime dateTimeStart = LocalDateTime.of(2020, 3, 11, (int) startHour, (int) startMinute);
            tempStart = new Timestamp(1);
            tempStart = tempStart.valueOf(dateTimeStart);

            LocalDateTime dateTimeEnd = LocalDateTime.of(2020, 3, 11, (int) endHour, (int) endMinute);

            tempEnd = new Timestamp(1);
            tempEnd = tempEnd.valueOf(dateTimeEnd);

            createArrayLists();
            Timestamp temp = null;
            for (int i = 0; i < timestamps.size(); i++) {
                temp = new Timestamp(timestamps.get(i));
                if (temp.equals(tempEnd) || temp.before(tempEnd) || temp.equals(tempStart) || temp.after(tempStart)) {
                    res += temp.toLocalDateTime() + ": " + infoStrings.get(i) + "\n";
                }


            }return res;
        }


    }

