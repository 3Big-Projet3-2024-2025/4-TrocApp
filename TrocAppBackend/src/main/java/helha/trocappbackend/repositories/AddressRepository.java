package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Override
    <S extends Address> S save(S entity);

    @Override
    List<Address> findAll();

    @Override
    Optional<Address> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}
