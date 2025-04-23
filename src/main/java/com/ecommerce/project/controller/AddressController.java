package com.ecommerce.project.controller;


import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDto){

        User loggedInUser = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDto, loggedInUser);
        return new ResponseEntity<AddressDTO>(savedAddressDTO , HttpStatus.CREATED);
    }


    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressList = addressService.getAddress();
        return new ResponseEntity<>(addressList , HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressesById( @PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO , HttpStatus.OK);
    }

    @GetMapping("/user/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressesOfUser(){
        User loggedInUser = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getAddressOfUser(loggedInUser);
        return new ResponseEntity<>(addressList , HttpStatus.OK);
    }


    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressById( @PathVariable Long addressId ,
                                                         @RequestBody AddressDTO addressDTO){

        AddressDTO updatedAddressDTO = addressService.updateAddressById(addressId , addressDTO);
        return new ResponseEntity<AddressDTO>(updatedAddressDTO , HttpStatus.OK);
    }


}
