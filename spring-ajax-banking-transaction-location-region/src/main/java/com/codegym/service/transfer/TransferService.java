package com.codegym.service.transfer;

import com.codegym.model.Transfer;
import com.codegym.model.dto.TransferHistoryDTO;
import com.codegym.service.IGeneralService;

import java.util.List;

public interface TransferService extends IGeneralService<Transfer> {

    List<Transfer> findAll();

    List<TransferHistoryDTO> findAllHistories();

    void doTransfer(Transfer transfer);
}
