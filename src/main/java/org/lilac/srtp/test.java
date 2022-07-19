package org.lilac.srtp;


import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class test {
    public static double l = 118.807706;
    public static double r = 118.824235;
    public static double t = 32.054943;
    public static double b = 32.049128;
//    String.valueOf(l),String.valueOf(r),String.valueOf(t),String.valueOf(b)
    public static void main(String[] args) {
        try {
            String[] args1= new String[]{"python","D:\\srtp\\ExactSFRS\\demo\\test.py",
            "118.807706","118.824235", "32.054943","32.049128"};
            Process proc = Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            String res ="";
            while ((line = in.readLine()) != null){
                res+=line;
//                System.out.println(line);
            }
//            String res[] = line.split(" ");
//            System.out.println(res);
            in.close();
            int state = proc.waitFor();
            System.out.println(state);
            System.out.println(res);
            String p[] = res.split(" ");


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
