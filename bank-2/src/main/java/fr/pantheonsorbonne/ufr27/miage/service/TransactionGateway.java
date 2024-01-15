package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;



public interface TransactionGateway {

    void sendTransaction(TransactionDTO transaction);

    public boolean shouldProcess(TransactionDTO transaction);
}
