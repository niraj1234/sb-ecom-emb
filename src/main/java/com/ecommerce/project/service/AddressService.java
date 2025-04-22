package com.ecommerce.project.service;


import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDto, User loggedInUser);

    List<AddressDTO> getAddress();
}
