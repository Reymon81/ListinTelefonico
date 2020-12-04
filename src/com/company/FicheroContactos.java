package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.Vector;

public class FicheroContactos {

    private Vector<String> contactos = new Vector<>();

    public FicheroContactos() {
    }

    public void anadirContacto(String nombre, int numero){

        //Al crear el BufferedWriter entre los parentesis del try lo cierra automaticamente al acabar
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter("contactos.txt",true))){

            bw.write(nombre+";"+numero+"\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void borrarContacto(int telefono) {
        File fichero = new File("contactos.txt");
        File ficheroTemporal = new File("contactos.txt.tmp");
        int contador=0;
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea = "";
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ";");
                String nombre = st.nextToken();
                int numero = Integer.parseInt(st.nextToken());
                if (telefono != numero) {
                    contactos.add(linea);
                }
                contador++;
            }
            if(contador == contactos.size()){
                System.out.println("el numero introducido no esta en la agenda");
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroTemporal));
            for (int i = 0; i < contactos.size(); i++) {
                bw.write(contactos.elementAt(i) + "\r\n");
            }
            bw.close();

            Files.deleteIfExists(Paths.get(String.valueOf(fichero)));
            ficheroTemporal.renameTo(new File(String.valueOf(fichero)));

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarContactos(){

        try (BufferedReader br = new BufferedReader(new FileReader(("contactos.txt")))){

            String linea="";
            while ((linea = br.readLine()) != null){
                StringTokenizer st = new StringTokenizer(linea,";");
                String nombre = st.nextToken();
                int numero = Integer.parseInt( st.nextToken());
                System.out.printf("nombre: %s\t telefono: %d\n", nombre, numero);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
