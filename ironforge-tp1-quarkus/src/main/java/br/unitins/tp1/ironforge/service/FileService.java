package br.unitins.tp1.ironforge.service;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String save(String nomeArquivo, byte[] arquivo) throws IOException; // Nome do arquivo para indicar a extensão, pois bytes podem representar qqlr coisa (pdf, gif)

    File find(String nomeArquivo); //Representa o dowload do arquivo
}
