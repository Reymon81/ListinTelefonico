package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Configuracion implements Serializable {

    //ATRIBUTOS
    private LocalDate fecha; //fecha actual
    private LocalTime hora; //hora actual

    public Configuracion() {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraActual(LocalTime hora) {
        this.hora = hora;
    }
}
