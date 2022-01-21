package com.eduardo.clientcrud.service;

import com.eduardo.clientcrud.dto.ClientDTO;
import com.eduardo.clientcrud.entity.Client;
import com.eduardo.clientcrud.repository.ClientRepository;
import com.eduardo.clientcrud.service.exceptions.ResourceAlreadyExistsException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(Pageable pageable){
        return clientRepository.findAll(pageable)
                .map(cli -> new ClientDTO(cli));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto){
        try {
            Client entity = new Client();
            copyDtoToEntity(dto, entity);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        }
        catch(DataIntegrityViolationException e){
            throw new ResourceAlreadyExistsException("Resource with cpf: " + dto.getCpf() + " already exists");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setBirthDate(dto.getBirthDate());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setName(dto.getName());
        entity.setChildren(dto.getChildren());
    }

}
