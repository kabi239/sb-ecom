package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.model.Address;
import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.payload.AddressDTO;
import com.ecommerce.sb_ecom.service.AddressService;
import com.ecommerce.sb_ecom.util.AuthUtil;
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
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO,user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> allSavedAddresses = addressService.getAllAddresses();
        return new ResponseEntity<>(allSavedAddresses, HttpStatus.OK);
    }
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressByUser(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> userAddress = addressService.getAddressByUser(user);
        return new ResponseEntity<>(userAddress,HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress = addressService.updateAddress(addressId,addressDTO);
        return new ResponseEntity<>(updatedAddress,HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        String status= addressService.deleteAddress(addressId);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }
}
