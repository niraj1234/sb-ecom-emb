package com.ecommerce.project.service;


import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public AddressDTO createAddress(AddressDTO addressDto, User loggedInUser) {

        Address address = modelMapper.map(addressDto , Address.class);

        List<Address> addressList = loggedInUser.getAddresses();
        addressList.add(address);
        loggedInUser.setAddresses(addressList);
        address.setUser(loggedInUser);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress , AddressDTO.class);
    }


}
