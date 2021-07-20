package pe.edu.upeu.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import pe.edu.upeu.util.LeerArchivo;

public class AppCrud {
    
    public String generarId(LeerArchivo la, int numColum, String prefijo, int iniPrefNex){
        int idX=1;
        Object[][] data=listarContenido(la);
        if(data!=null){        
            idX=Integer.parseInt(data[data.length-1][numColum].toString()
            .substring(iniPrefNex))+1;
            return prefijo+""+idX;  
        }else{
            return prefijo+""+idX;
        }
    }

    public int numColumna(LeerArchivo aq, Object modelo){
        Field[] fields =(modelo).getClass().getDeclaredFields();
        int numColum=0;
        for(Field field : fields) {
            numColum++;
        }
        return numColum;
    }

    public Object[][] agregarContenido(LeerArchivo aq, Object modelo){
        Object[][] datosAnt=listarContenido(aq);
        int tamaño=(datosAnt==null?0:datosAnt.length);
        int numColum=numColumna(aq, modelo);
        Object[][] datosNew=new Object[tamaño+1][numColum];
        for (int i = 0; i < tamaño; i++) {
            for(int j = 0;j<datosAnt[0].length;j++){
                datosNew[i][j]=datosAnt[i][j];
            }
        }
        List<String> lista = new ArrayList<String>();		
        Field[] fields = (modelo).getClass().getDeclaredFields();
        int primero=0;
        for(Field field : fields) {
            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(modelo);                
                datosNew[datosNew.length-1][primero]=fieldValue;
                System.out.println((fieldName).toUpperCase()+": "+ fieldValue);
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }
            primero++;
        }
        String contenido="";
        for (int j = 0; j < datosNew.length; j++) {                                               
            for(int k=0; k<datosNew[0].length;k++){
                if(k==0){
                    contenido+=datosNew[j][k];
                }else{
                    contenido+="\t"+datosNew[j][k];
                }
            }
            lista.add(contenido);
            contenido="";
        }    
        try {
                aq.escribir(lista);
        } catch (Exception e) { e.getMessage();
        } 
        return datosNew;    
    }

    public Object[][] editarRegistro(LeerArchivo aq, int numIdColumModelo, String dato, Object modelo){
        Object[][] datosAnt=listarContenido(aq);
        Field[] fields =(modelo).getClass().getDeclaredFields();
        List<String> lista2 = new ArrayList<String>();
        int numFilasX=-1;  
        try {
            lista2=aq.leer();           
            for (int i = 0; i < lista2.size(); i++) {
                String[] column=(lista2.get(i).toString()).split("\t");
                if(column[numIdColumModelo].equals(dato)){ numFilasX=i; break; }
            }
            if(numFilasX>=0){
            int primero=0;
            for(Field field : fields) {
                String fieldName = field.getName();
                Object fieldValue = field.get(modelo);                
                if(fieldValue!=null && !fieldValue.equals("") && numIdColumModelo!=primero && !fieldValue.equals(0) && !fieldValue.equals(0.0)){
                    datosAnt[numFilasX][primero]=fieldValue;
                }                
                System.out.println((fieldName).toUpperCase() + ": "+ fieldValue);
                primero++;
            }            
            }
        } catch (Exception e) {
            e.getMessage();
        }
        lista2 = new ArrayList<String>();	
        String contenido="";
        for (int j = 0; j < datosAnt.length; j++) {                                               
            for(int k=0; k<datosAnt[0].length;k++){
                if(k==0){
                    contenido+=datosAnt[j][k];
                }else{
                    contenido+="\t"+datosAnt[j][k];
                }
            }
            lista2.add(contenido);
            contenido="";
        }    
        try {
                aq.escribir(lista2);
        } catch (Exception e) { e.getMessage();
        }         
        return datosAnt;
    }

    public Object[][] listarContenido(LeerArchivo aq){
        Object[][] datos=null;		
        List<String> lista2 = new ArrayList<String>();                
        try {                  
            lista2=aq.leer();
            String[] column=(lista2.get(0).toString()).split("\t");
            datos=new Object[lista2.size()][column.length];
            for (int i = 0; i < lista2.size(); i++) {
                String[] extractData=(lista2.get(i).toString()).split("\t");
                for(int j=0; j<extractData.length;j++){
                    datos[i][j]=extractData[j]; 
                }                                            
            }
        } catch (Exception e) {
            System.err.println("Error:"+e.getMessage());
        } 
        return datos;
    }

    public Object[][] buscarContenido(LeerArchivo aq, int numColumna, String dato){
        Object[][] datos=null;	
        List<String> lista2 = new ArrayList<String>();                
        try {                  
            lista2=aq.leer();
            String[] column=(lista2.get(0).toString()).split("\t");
            int numFilasX=0;
            for (int i = 0; i < lista2.size(); i++) {
                column=(lista2.get(i).toString()).split("\t");
                if(column[numColumna].equals(dato)){ numFilasX++; }
            }

            datos=new Object[numFilasX][column.length];
            int cantNewFila=0;
            for (int i = 0; i < lista2.size(); i++) {
                String[] extractData=(lista2.get(i).toString()).split("\t");
                if(extractData[numColumna].equals(dato)){
                    for(int j=0; j<extractData.length;j++){                                
                        datos[cantNewFila][j]=extractData[j]; 
                    }
                    cantNewFila++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error:"+e.getMessage());
            } 
        return datos;
    }

}