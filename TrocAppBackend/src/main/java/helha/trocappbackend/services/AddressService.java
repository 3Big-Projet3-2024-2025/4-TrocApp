package helha.trocappbackend.services;

import helha.trocappbackend.models.Address;
import helha.trocappbackend.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository; // Repository for Address

    // Function adding an adress
    public Address addAddress(Address address) {
        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new RuntimeException("Add of address failed " + e.getMessage());
        }
    }

    // Function getting an address by ID
    public Address getAddress(int id) {
        try {
            Optional<Address> address = addressRepository.findById(id);
            return address.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Get of address failed " + e.getMessage());
        }
    }

    // Function getting all the adresses
    public List<Address> getAllAddresses() {
        try {
            return addressRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get of the addresses failed " + e.getMessage());
        }
    }

    // Function deleting an address by ID
    public void deleteAddress(int id) {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Deletion of address failed " + e.getMessage());
        }
    }
// Function updating an address
    public Address updateAddress(Address address) {
        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new RuntimeException("Update of address failed " + e.getMessage());
        }
    }

}
