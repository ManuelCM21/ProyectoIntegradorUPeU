package pe.edu.upeu.util;

import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class UtilsX {
    
    public final void clearConsole(){
        try{            
            final String os = System.getProperty("os.name");    
            if (os.contains("Windows")){
               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        }
        catch (final Exception e){
           System.out.println("Error: "+e.getMessage());
        }
    }

    public final void clearConsole1(){
        try{            
            final String os = System.getProperty("os.name");    
            if (os.contains("Windows")){
               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        }
        catch (final Exception e){
           System.out.println("Error: "+e.getMessage());
        }
       pintarLine('H', 42);
    }

    public void pintarLine(char horient, int sizen) {
        AnsiConsole.systemUninstall();
        for (int i = 0; i <= (sizen); i++) {
            if (horient=='H') {
                if(i==sizen){
                    System.out.print(ansi().fg(GREEN).a("-").reset());
                }else{System.out.print(ansi().fg(GREEN).a("--").reset());}
                
            } else {
                System.out.println(ansi().fg(GREEN).a("|").reset());
            }
        }
        if (horient=='H') {
            System.out.print("\n");
        }
    }

    public void pintarLine1(char horient, int sizen) {
        for (int i = 0; i <= (sizen); i++) {
            if (horient=='H') {
                if(i==sizen){
                    System.out.print(ansi().fg(GREEN).a("-").reset());
                }else{System.out.print(ansi().fg(GREEN).a("--").reset());}
            } else {
                System.out.print(ansi().fg(GREEN).a("|").reset());
            }
        }
    }

    public void pintarTextHeadBody(char type, int sizen, String content) {
        int sizeX=sizen>=2?4*sizen:4;
        System.out.print(ansi().fg(GREEN).a("| ").reset()); 
        String[] data=content.split(",");
        for (int j = 1; j <= data.length; j++) {
            data[j-1]=data[j-1].length()>sizeX?data[j-1].substring(0,sizeX):data[j-1];            
            int contentSize=data[j-1].length();
            System.out.print(data[j-1]);         
            if(sizeX-contentSize>=4 && (double)(sizeX-contentSize)/4>=1){
                int y=(int)((sizeX-contentSize)/4);
                //System.out.print("y*"+y); 
                for (int i = 0; i <y; i++) {
                    System.out.print("    ");     
                }
                int x=((sizeX-contentSize)-(y*4));                
                //System.out.print("z*"+x); 
                for (int i = 0; i < x; i++) {
                    System.out.print(" "); 
                }                 
            }else{
                int x=((sizeX-contentSize)-((int)((sizeX-contentSize)/4)*4));
                //System.out.print("x*"+x); 
                for (int i = 0; i < x; i++) {
                    System.out.print(" "); 
                }
            }           
            System.out.print(ansi().fg(GREEN).a("| ").reset());  
        }
        if(type!='H'){
            System.out.println("");
        }
    }

    public void pintarTextHeadBody1(char type, int sizen, String content) {
        AnsiConsole.systemUninstall();
        int sizeX=sizen>=2?4*sizen:4;
        System.out.print(ansi().fg(GREEN).a("| ").reset());
        String[] data=content.split(",");
        for (int j = 1; j <= data.length; j++) {
            data[j-1]=data[j-1].length()>sizeX?data[j-1].substring(0,sizeX):data[j-1];            
            int contentSize=data[j-1].length();
            System.out.print(ansi().fg(YELLOW).a(data[j-1]).reset());         
            if(sizeX-contentSize>=4 && (double)(sizeX-contentSize)/4>=1){
                int y=(int)((sizeX-contentSize)/4);
                for (int i = 0; i <y; i++) {
                    System.out.print("    ");     
                }
                int x=((sizeX-contentSize)-(y*4));                
                for (int i = 0; i < x; i++) {
                    System.out.print(" "); 
                }                 
            }else{
                int x=((sizeX-contentSize)-((int)((sizeX-contentSize)/4)*4));

                for (int i = 0; i < x; i++) {
                    System.out.print(" "); 
                }
            }           
            System.out.print(ansi().fg(GREEN).a("| ").reset()); 
        }
        if(type!='H'){
            System.out.println("");
        }
    }
}
