package com.company;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    /**
     * Procedimiento para imprimir el menú que vamos a mostrar en el main
     */
    public static void printMenu() {
        System.out.println("\n******* Menú Contactos *******\n");
        System.out.println("1. Añadir Contacto\n" + "2. Borrar Contacto\n" + "3. Mostrar Contactos\n" + "4. Salir\n");
    }

    /**
     * Funcion que comprueba si es un numero lo que hemos introducido por teclado
     *
     * @param cadena es un String que obtenemos al escribir por teclado
     * @return <ul>
     *         <li>true: nos devuelve este valor cuando hemos introducido un numero
     *         <li>false: nos devuelve este valor cuando no hemos introducido un
     *         numero
     *         <ul>
     */
    public static boolean esNumero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static void annadirAgenda(){
        Scanner sc = new Scanner(System.in);
        System.out.println("introduce el nombre del contacto: ");
        String nombre = sc.nextLine();
        System.out.println("introduce el numero de telefono: ");
        String cadena = sc.nextLine();
        while(!esNumero(cadena)){
            System.out.println("no has introducido un numero, prueba de nuevo: ");
        }
        int numero=Integer.parseInt(cadena);
        Contacto persona = new Contacto(nombre, numero);
        FicheroContactos contactos= new FicheroContactos();
        contactos.anadirContacto(persona.getNombre(), persona.getNumero());
    }

    public static void eliminarAgenda(){
        Scanner sc = new Scanner(System.in);
        System.out.println("introduce el numero de telefono del contacto: ");
        String cadena=sc.nextLine();
        while (!esNumero(cadena)){
            System.out.println("no has introducido un numero de telefono, prueba de nuevo: ");
            cadena=sc.nextLine();
        }
        int numero=Integer.parseInt(cadena);
        FicheroContactos fc = new FicheroContactos();
        fc.borrarContacto(numero);
    }

    //metodo para comprobar si existe el fichero, si no lo encuentra lo crea
    public static void existeFichero(){
        File fichero = new File("config.ser");

        try{

            if (fichero.exists()) {
                recuperarFecha();
            }
            else {
                fichero.createNewFile();
            }
        }catch(IOException ex){
            System.out.println("Excepcion al crear el fichero: " + ex);
        }

    }

   //metodo para almacenar la configuracion
    public static void almacenarFecha() throws IOException{

        FileOutputStream fs;
        ObjectOutputStream os = null;

        try{
            Configuracion a1 = new Configuracion();
            fs = new FileOutputStream("config.ser");//Creamos el archivo
            os = new ObjectOutputStream(fs);//Esta clase tiene el método
            os.writeObject(a1);//El método writeObject() serializa el objeto y lo escribe en el archivo
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            os.close();//Hay que cerrar siempre el archivo
        }

    }

    //metodo para recuperar la configuracion
    public static void recuperarFecha() {

        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("config.ser"))){
            //Cuando no haya mas objetos saltara la excepcion EOFException
            while(true){
                Configuracion aux=(Configuracion)ois.readObject();
                System.out.println("Fecha:" + aux.getFecha() + " " + "Hora: " + aux.getHora()+"\n");
            }
        }catch(ClassNotFoundException e){
        }catch(EOFException e){
        }catch(IOException e){
        }
    }

    public static void main(String[] args) {
        Scanner sl = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        String apagar = "";
        boolean salir = false;
        //Verificamos si existe o no el fichero config.ser
        existeFichero();
        int opcion;

        while (!salir) {

            printMenu();
            try {
                System.out.print("Elije una opcion: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1 -> annadirAgenda();
                    case 2 -> eliminarAgenda();
                    case 3 -> {
                        FicheroContactos fc = new FicheroContactos();
                        fc.listarContactos();
                    }
                    case 4 -> {
                        System.out.println("¿Estas seguro de querer salir? s/n");
                        apagar = sl.nextLine();
                        while (!apagar.equals("s") && !apagar.equals("n")) {
                            System.out.println("la respuesta es incorrecta, prueba de nuevo s/n");
                            apagar = sl.nextLine();
                        }
                        if (apagar.equals("s")) {
                            almacenarFecha();
                            salir = true;
                        }
                    }
                }
            } catch (InputMismatchException | IOException e) {
                System.err.println("Error, elije una opción del 1 al 4");
                sc.next();
            }
        }
    }
}
