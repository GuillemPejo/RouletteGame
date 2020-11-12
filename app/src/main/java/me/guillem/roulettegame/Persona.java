package me.guillem.roulettegame;

public class Persona {
    private String nom;
    private int puntuacio;
    private int max_aposta;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public int getMax_aposta() {
        return max_aposta;
    }

    public void setMax_aposta(int max_aposta) {
        this.max_aposta = max_aposta;
    }

    @Override
    public String toString() {
        return nom + "  -  " + puntuacio;
    }
}
