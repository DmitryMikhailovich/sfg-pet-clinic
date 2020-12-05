package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private final Long ID = 1L;
    private final String LAST_NAME = "Ivanov";

    private OwnerMapService service;

    @BeforeEach
    void setUp() {

        service = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        service.save(Owner.builder().id(ID).lastName(LAST_NAME).build());
    }

    @Test
    void findAll() {

        Set<Owner> ownerSet = service.findAll();

        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = service.findById(ID);

        assertEquals(ID, owner.getId());
    }

    @Test
    void save() {
        Long id = 2L;

        Owner owner2 = Owner.builder().id(id).build();
        Owner savedOwner2 = service.save(owner2);

        assertEquals(id, savedOwner2.getId());

        Owner owner3 = new Owner();
        Owner savedOwner3 = service.save(owner3);

        assertNotNull(savedOwner3);
        assertNotNull(savedOwner3.getId());
    }

    @Test
    void delete() {

        service.delete(service.findById(ID));

        assertEquals(0, service.findAll().size());
    }

    @Test
    void deleteById() {

        service.deleteById(ID);

        assertEquals(0, service.findAll().size());
    }

    @Test
    void findByLastName() {

        Owner owner = service.findByLastName(LAST_NAME);

        assertNotNull(owner);
        assertEquals(ID, owner.getId());
        assertEquals(LAST_NAME, owner.getLastName());

        Owner nullOwner = service.findByLastName("not exists");
        assertNull(nullOwner);
    }
}