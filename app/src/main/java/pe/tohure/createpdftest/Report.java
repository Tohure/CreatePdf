package pe.tohure.createpdftest;

import java.io.Serializable;

/**
 * Created by tohure on 17/10/17.
 */

class Report implements Serializable {

    private String hemoglobina = "0";
    private String colesterol = "0";
    private String glucosa = "0";
    private String trigliceridos = "0";
    private String edad = "0";
    private String peso = "0";

    public String getHemoglobina() {
        return hemoglobina + " g/dl";
    }

    public void setHemoglobina(String hemoglobina) {
        this.hemoglobina = hemoglobina;
    }

    public String getColesterol() {
        return colesterol + " mg/dl";
    }

    public void setColesterol(String colesterol) {
        this.colesterol = colesterol;
    }

    public String getGlucosa() {
        return glucosa + " mg/dl";
    }

    public void setGlucosa(String glucosa) {
        this.glucosa = glucosa;
    }

    public String getTrigliceridos() {
        return trigliceridos + " mg/dL";
    }

    public void setTrigliceridos(String trigliceridos) {
        this.trigliceridos = trigliceridos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPeso() {
        return peso + " kg";
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}
