package com.appsforgood.isayandjack.circuitsplus;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * This class handles user data writing and reading
 */
public class DataController {
    //init vars
    public static boolean ch1Complete = false;
    public static boolean ch2Complete = false;
    public static boolean ch3Complete = false;
    public static boolean ch4Complete = false;
    public static boolean ch5Complete = false;
    public static boolean ch6Complete = false;
    public static boolean ch7Complete = false;

    public static String file = "/CircuitsPlus/.circuitsplus";
    public static String path = "/CircuitsPlus/";
    //load data from file
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(file)));
            ch1Complete = Boolean.parseBoolean(in.readLine());
            ch2Complete = Boolean.parseBoolean(in.readLine());
            ch3Complete = Boolean.parseBoolean(in.readLine());
            ch4Complete = Boolean.parseBoolean(in.readLine());
            ch5Complete = Boolean.parseBoolean(in.readLine());
            ch6Complete = Boolean.parseBoolean(in.readLine());
            ch7Complete = Boolean.parseBoolean(in.readLine());
        } catch (IOException e) {
            //defaults for the win
        } catch (NumberFormatException e) {
            // defaults for the win
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }

    //write data out to file
    public static void save(FileIO files) {
        Log.d("data","saved");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file, path)));
            out.write(Boolean.toString(ch1Complete) + "\n");
            out.write(Boolean.toString(ch2Complete) + "\n");
            out.write(Boolean.toString(ch3Complete) + "\n");
            out.write(Boolean.toString(ch4Complete) + "\n");
            out.write(Boolean.toString(ch5Complete) + "\n");
            out.write(Boolean.toString(ch6Complete) + "\n");
            out.write(Boolean.toString(ch7Complete));
        } catch (IOException e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
