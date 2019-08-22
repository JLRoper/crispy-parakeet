/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

/**
 *
 * @author Jacob
 */
public class RunFromConsole {

    public static void main(String[] argv) {
//        System.out.println(argv[0]);
    countHi("hi");
    }

    public static int countHi(String str) {
        if (str.indexOf("hi") > 0) {
            return countHi(str.substring(str.indexOf("hi") + 2)) + 1;
        }
        return 0;
    }

}
