package br.unitins.tp1.ironforge.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteFileServiceImpl implements FileService {

    private final String PATH_CLIENTE = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "ironforge"
            + File.separator + "images"
            + File.separator + "cliente"
            + File.separator;

    private static final List<String> SUPPORTED_MIME_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png",
            "image/gif");

    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // Até 10MB

    @Override
    public String save(String nomeArquivo, byte[] arquivo) throws IOException {

        // verificarTipoArquivo(nomeArquivo);
        // verificarTamanhoArquivo(arquivo);
        Path diretorio = Paths.get(PATH_CLIENTE);
        if (!new File(PATH_CLIENTE).exists())
            Files.createDirectory(diretorio);

        String mimeType = Files.probeContentType(Paths.get(nomeArquivo));
        String extensao = mimeType.substring(mimeType.lastIndexOf("/") + 1);
        String novoNomeArquivo = UUID.randomUUID() + "." + extensao;

        Path filePath = diretorio.resolve(novoNomeArquivo); // caminho final do arquivo

        // Funçao recursiva ??

        if (filePath.toFile().exists()) {
            // Adicionar uma verificacao casa haja o mesmo UUID , criar um novo novamemente
            // Gerar um novo nome
            throw new IOException("O nome do arquivo ja existe " + nomeArquivo);
        }

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(arquivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return novoNomeArquivo;
    }

    @Override
    public File find(String nomeArquivo) {
        // Adicionar validações: não existe...
        return new File(PATH_CLIENTE + nomeArquivo);
    }
}
