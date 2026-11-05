package com.example.sgm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "veiculos")
public class Veiculo {
    @PrimaryKey(autoGenerate = true) public int id;
    public String marca;
    public String modelo;
    public String valor;
    public Veiculo(String marca, String modelo, String valor) { this.marca = marca; this.modelo = modelo; this.valor = valor; }
}
