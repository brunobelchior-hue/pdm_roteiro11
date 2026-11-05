package com.example.sgm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class Cliente {
    @PrimaryKey(autoGenerate = true) public int id;
    public String nome;
    public String telefone;
    public Cliente(String nome, String telefone) { this.nome = nome; this.telefone = telefone; }
}
