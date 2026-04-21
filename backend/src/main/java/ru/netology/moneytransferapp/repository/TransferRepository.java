package ru.netology.moneytransferapp.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferapp.entity.TransferLogEntry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepository {
    private Map<String, String> codes = new ConcurrentHashMap<>();
    private Map<String, TransferLogEntry> logs = new ConcurrentHashMap<>();

    public void saveCode(String id, String code) {
        codes.put(id, code);
    }

    public void removeCode(String id) {
        codes.remove(id);
    }

    public String getCode(String id) {
        return codes.get(id);
    }

    public void saveLog(String id, TransferLogEntry transferLogEntry) {
        logs.put(id, transferLogEntry);
    }

    public void removeLog(String id) {
        logs.remove(id);
    }

    public TransferLogEntry getLog(String id) {
        return logs.get(id);
    }
}
