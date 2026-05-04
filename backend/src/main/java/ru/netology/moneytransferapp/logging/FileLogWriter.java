package ru.netology.moneytransferapp.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.netology.moneytransferapp.entity.TransferLogEntry;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class FileLogWriter {
    private final Path path;
    private final ObjectMapper objectMapper;

    private static final String LOG_PATH = "./logs/transfer-log.jsonl";

    public FileLogWriter(ObjectMapper objectMapper) {
        this.path = Paths.get(LOG_PATH);
        this.objectMapper = objectMapper;

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void writeLog(TransferLogEntry entry) throws IOException {
        try {
            String json = objectMapper.writeValueAsString(entry);
            Files.writeString(
                    path,
                    json + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
