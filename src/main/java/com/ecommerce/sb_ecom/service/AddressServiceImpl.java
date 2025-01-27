package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Address;
import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.payload.AddressDTO;
import com.ecommerce.sb_ecom.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        List<AddressDTO> addressDTOList = addressList.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
        return addressDTOList;
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    @Override
    public List<AddressDTO> getAddressByUser(User user) {
        List<Address> addressList = user.getAddresses();
        List<AddressDTO> addressDTOList =addressList.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class)).toList();
        return addressDTOList;
    }
}
