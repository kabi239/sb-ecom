package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAddressByUser(User user);
}
