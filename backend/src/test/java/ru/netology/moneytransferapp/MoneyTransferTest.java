package ru.netology.moneytransferapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.moneytransferapp.controller.TransferController;
import ru.netology.moneytransferapp.dto.ConfirmOperation;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.service.TransferService;

import java.util.Map;

class MoneyTransferTest {
    @Test
    void test_add_transfer() {
        Transfer transfer = new Transfer();

        TransferService service = Mockito.mock(TransferService.class);
        Mockito.when(service.addTransfer(Mockito.any(Transfer.class)))
                .thenReturn("1111");

        TransferController controller = new TransferController(service);
        Map<String, String> response = controller.addTransfer(transfer);

        Assertions.assertEquals("1111", response.get("operationId"));
        Mockito.verify(service).addTransfer(Mockito.any(Transfer.class));
    }

    @Test
    void test_confirm_operation() {
        ConfirmOperation confirmOperation = new ConfirmOperation();

        TransferService service = Mockito.mock(TransferService.class);
        Mockito.when(service.confirmOperation(Mockito.any(ConfirmOperation.class)))
                .thenReturn("1111");

        TransferController controller = new TransferController(service);
        Map<String, String> response = controller.confirmOperation(confirmOperation);

        Assertions.assertEquals("1111", response.get("operationId"));
        Mockito.verify(service).confirmOperation(Mockito.any(ConfirmOperation.class));
    }
}
