package com.codegym.service;

import com.codegym.model.Transfer;
import com.codegym.model.dto.TransferHistoryDTO;

import java.util.List;

public interface TransferService extends IGeneralService{

    List<Transfer> findAll();

    Object save(Transfer transfer);

    List<TransferHistoryDTO> findAllHistories();

    void doTransfer(Transfer transfer);
}
