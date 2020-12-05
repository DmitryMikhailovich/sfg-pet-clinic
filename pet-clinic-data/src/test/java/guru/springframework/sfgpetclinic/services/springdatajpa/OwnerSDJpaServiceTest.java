package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final String LAST_NAME = "Ivanov";
    private static final Long ID = 1L;

    @Mock
    private OwnerRepository repository;

    @InjectMocks
    private OwnerSDJpaService service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findByLastName() {

        when(repository.findByLastName(LAST_NAME)).thenReturn(Owner.builder().lastName(LAST_NAME).build());

        Owner owner = service.findByLastName(LAST_NAME);

        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
        verify(repository).findByLastName(LAST_NAME);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {

        when(repository.findAll()).thenReturn(Collections.singletonList(new Owner()));

        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(1, owners.size());
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById() {
        Owner returnedOwner = Owner.builder().id(ID).build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(returnedOwner));

        Owner owner = service.findById(ID);
        assertNotNull(owner);
        assertEquals(ID, owner.getId());
        verify(repository).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findByIdNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = service.findById(ID);
        assertNull(owner);
        verify(repository).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void save() {

        Owner owner = Owner.builder().id(ID).build();

        when(repository.save(any())).thenReturn(Owner.builder().id(ID).build());

        Owner savedOwner = service.save(owner);

        assertNotNull(savedOwner);
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void delete() {
        Owner owner = Owner.builder().id(ID).build();

        service.delete(owner);

        verify(repository).delete(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteById() {

        service.deleteById(ID);

        verify(repository).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}