package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@PropertySource("springdatajpa")
public class OwnerSDJpa implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerSDJpa(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner findByLastName(String lastName) {

        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Set<Owner> findAll() {

        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owners::add);
        return owners;
    }

    @Override
    public Owner findById(Long id) {

        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        return ownerOptional.orElse(null);
    }

    @Override
    public Owner save(Owner owner) {

        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {

        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long id) {

        ownerRepository.deleteById(id);
    }
}
