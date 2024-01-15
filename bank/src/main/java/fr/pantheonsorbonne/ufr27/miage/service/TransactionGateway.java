package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;



public interface TransactionGateway {

    String makeTransaction(TransactionDTO transaction);

    public boolean isSendingBank(TransactionDTO transaction);

    public boolean isReceivingBank(TransactionDTO transaction);

    public boolean isSendingAndReceiving(TransactionDTO transaction);



}
