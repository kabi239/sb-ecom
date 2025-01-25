package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Address;
import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.payload.AddressDTO;
import com.ecommerce.sb_ecom.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addressList =user.getAddresses(); // fetching the current list
        addressList.add(address); // this will add new address to the list that exists within user
        user.setAddresses(addressList); // updating user object with new list
        address.setUser(user); //linking the address entity with user

        Address savedAddress = addressRepository.save(address); // the saved address is returned

        return new ModelMapper().map(savedAddress, AddressDTO.class);
    }
}
