package com.ecommerce.project.service;


import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService{


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


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


    @Override
    public List<AddressDTO> getAddress() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDTO> addressDTOList = addresses.stream()
                .map(addr -> modelMapper.map(addr , AddressDTO.class) )
                .collect(Collectors.toList());
        return addressDTOList;
    }


    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId));
        return modelMapper.map( address , AddressDTO.class );
    }

    @Override
    public List<AddressDTO> getAddressOfUser(User loggedInUser) {
        List<Address> addresses = loggedInUser.getAddresses();
        List<AddressDTO> addressDTOList = addresses.stream()
                .map(addr -> modelMapper.map(addr , AddressDTO.class) )
                .collect(Collectors.toList());
        return addressDTOList;

    }


    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {
        Address addressFromDB = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId));

        addressFromDB.setCity(addressDTO.getCity());
        addressFromDB.setPincode(addressDTO.getPincode());
        addressFromDB.setState(addressDTO.getState());
        addressFromDB.setCountry(addressDTO.getCountry());
        addressFromDB.setStreet(addressDTO.getStreet());
        addressFromDB.setBuildingName(addressDTO.getBuildingName());

        Address updatedAddress = addressRepository.save(addressFromDB);

        User user = addressFromDB.getUser();
        user.getAddresses().removeIf( a -> a.getAddressId().equals(addressId) );
        user.getAddresses().add(updatedAddress);

        userRepository.save(user);

        return modelMapper.map(updatedAddress , AddressDTO.class);
    }


    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId) );

        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf( a -> a.getAddressId().equals(addressId) );
        userRepository.save(user);
        addressRepository.delete(addressFromDatabase);

        return "Address deleted with id -> " + addressId;
    }



}

