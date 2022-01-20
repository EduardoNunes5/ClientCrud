package com.eduardo.clientcrud.service;

import com.eduardo.clientcrud.dto.ClientDTO;
import com.eduardo.clientcrud.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientDTO> findAllPaged(Pageable pageable){
        return clientRepository.findAll(pageable)
                .map(cli -> new ClientDTO(cli));

    }
}
