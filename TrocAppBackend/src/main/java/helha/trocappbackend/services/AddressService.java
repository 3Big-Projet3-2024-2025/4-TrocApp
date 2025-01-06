package helha.trocappbackend.services;

import helha.trocappbackend.models.Address;
import helha.trocappbackend.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class handling business logic for Address entities.
 * This service provides CRUD operations for managing addresses in the system.
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository; // Repository for Address

    /**
     * Adds a new address
     *
     * @param address The Address object to be saved
     * @return The saved Address object with its generated ID
     * @throws RuntimeException if there's an error during the save operation
     */
    public Address addAddress(Address address) {
        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to add the address " + e.getMessage());
        }
    }

    /**
     * Retrieves an address by its ID
     *
     * @param id The ID of the address to retrieve
     * @return The Address object if found, null otherwise
     * @throws RuntimeException if there's an error during the retrieval
     */
    public Address getAddress(int id) {
        try {
            Optional<Address> address = addressRepository.findById(id);
            return address.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to get the address " + e.getMessage());
        }
    }

    /**
     * Retrieves all addresses
     *
     * @return A List of all Address objects in the system
     * @throws RuntimeException if there's an error during the retrieval
     */
    public List<Address> getAllAddresses() {
        try {
            return addressRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to get the addresses " + e.getMessage());
        }
    }

    /**
     * Deletes an address by its ID
     *
     * @param id The ID of the address to delete
     * @throws RuntimeException if there's an error during the deletion
     */
    public void deleteAddress(int id) {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to delete the address " + e.getMessage());
        }
    }

    /**
     * Updates an existing address in the system.
     * If the address doesn't exist, it will be created.
     *
     * @param address The Address object with updated information
     * @return The updated Address object
     * @throws RuntimeException if there's an error during the update operation
     */
    public Address updateAddress(Address address) {
        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to update the address " + e.getMessage());
        }
    }
}