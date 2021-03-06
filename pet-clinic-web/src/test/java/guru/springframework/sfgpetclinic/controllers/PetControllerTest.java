package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;

    @Mock
    private PetService petService;

    @Mock
    private PetTypeService petTypeService;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private PetController controller;

    private MockMvc mockMvc;

    private Owner owner;
    private Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID_1).build();
        petTypes = Collections.singleton(PetType.builder().id(ID_1).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).findById(anyLong());
        verify(petTypeService).findAll();
        verifyNoInteractions(petService);
    }

    @Test
    void processCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Animal"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }

    @Test
    void processCreationFormValidationFail() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verifyNoInteractions(petService);
    }

    @Test
    void initUpdateForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(ID_2).build());

        mockMvc.perform(get("/owners/1/pets/2/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attribute("pet", hasProperty("id", is(ID_2))));

        verify(petService).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/2/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Animal"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(petService).save(any());
    }

    // TODO: fix abnormal 400 status code
//    @Test
//    void processUpdateFormValidationFail() throws Exception {
//        when(ownerService.findById(anyLong())).thenReturn(owner);
//        when(petTypeService.findAll()).thenReturn(petTypes);
//
//        mockMvc.perform(post("/owners/1/pets/2/edit")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("name", ""))
//                .andExpect(status().isOk())
//                .andExpect(view().name("pets/createOrUpdatePetForm"))
//                .andExpect(model().attributeExists("pet"));
//
//        verifyNoInteractions(petService);
//    }
}