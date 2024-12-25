package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Address;
import helha.trocappbackend.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Endpoint to add a new address
    @PostMapping
    public ResponseEntity<Object> addAddress(@RequestBody Address address) {
        try {
            Address savedAddress = addressService.addAddress(address);
            return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while adding the address: " + e.getMessage());
        }
    }

    // Endpoint to get an address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAddress(@PathVariable int id) {
        try {
            Address address = addressService.getAddress(id);
            if (address != null) {
                return new ResponseEntity<>(address, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Address not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while retrieving the address: " + e.getMessage());
        }
    }

    // Endpoint to get all addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        try {
            List<Address> addresses = addressService.getAllAddresses();
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Endpoint to delete an address by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable int id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while deleting the address: " + e.getMessage());
        }
    }

    // Endpoint to update an address
    @PutMapping
    public ResponseEntity<Object> updateAddress(@RequestBody Address address) {
        try {
            Address updatedAddress = addressService.updateAddress(address);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating the address: " + e.getMessage());
        }
    }
}
