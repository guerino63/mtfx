
package it.ma.mototrainerp;

public class Launcher {
    
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.vm.version"));
//        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("java.library.path"));
        Mototrainer.main(args);
    }
}