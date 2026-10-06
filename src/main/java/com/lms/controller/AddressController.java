package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.AddressDto;
import com.lms.entity.Address;
import com.lms.service.IAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
@Tag(
    name = "Address APIs",
    description = "APIs related to Address Management"
)
public class AddressController {

    @Autowired
    IAddressService addressService;


    // SAVE ADDRESS

    @Operation(
        operationId = "CreateAddress",
        summary = "Adding Address",
        description = "This REST endpoint is used to create a new address"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Address saved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid address details"
        )
    })
    @PostMapping
    public String saveAddress(@Valid @RequestBody AddressDto addressDto) {

        addressService.saveAddress(addressDto);

        return "Address Saved Successfully";

    }


    // UPDATE ADDRESS

    @Operation(
        operationId = "UpdateAddress",
        summary = "Update Address",
        description = "This REST endpoint is used to update an existing address"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Address updated successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Address not found"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid address details"
        )
    })
    @PutMapping
    public String updateAddress(@RequestBody AddressDto addressDto) {

        addressService.updateAddress(addressDto);

        return "Address Updated Successfully";

    }


    // DELETE ADDRESS

    @Operation(
        operationId = "DeleteAddress",
        summary = "Delete Address",
        description = "This REST endpoint is used to delete an address using address ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Address deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Address not found"
        )
    })
    @DeleteMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") int addressId) {

        addressService.deleteAddress(addressId);

        return "Address Deleted Successfully";

    }


    // GET ADDRESS BY ID

    @Operation(
        operationId = "FetchAddress",
        summary = "Fetch One Address",
        description = "This REST endpoint is used to fetch one address based on address ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Address fetched successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Address for this ID not found"
        )
    })
    @GetMapping("/get/{id}")
    public Address getAddress(@PathVariable("id") int addressId) {

        return addressService.findAddressById(addressId);

    }


    // GET ALL ADDRESSES

    @Operation(
        operationId = "FetchAllAddresses",
        summary = "Fetch All Addresses",
        description = "This REST endpoint is used to fetch all addresses"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "All addresses fetched successfully"
        )
    })
    @GetMapping
    public List<Address> getAllAddress() {

        return addressService.findAllAddress();

    }

}