package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;

    @Mock
    private PetService petService;

    @Mock
    private VisitService visitService;

    @InjectMocks
    private VisitController controller;

    private MockMvc mockMvc;

    private Owner owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID_1).build();
        pet = Pet.builder().id(ID_2).build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initCreationForm() throws Exception { ;
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/owners/1/pets/2/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"));

        verify(petService).findById(anyLong());
        verifyNoInteractions(visitService);
    }

    @Test
    void processCreationForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/2/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(visitService).save(any());
    }

//    @Test
//    void initUpdateForm() throws Exception {
//        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(ID_2).build());
//
//        mockMvc.perform(get("/owners/1/pets/2/edit"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("pets/createOrUpdatePetForm"))
//                .andExpect(model().attribute("pet", hasProperty("id", is(ID_2))));
//
//        verify(petService).findById(anyLong());
//    }
//
//    @Test
//    void processUpdateForm() throws Exception {
//        when(ownerService.findById(anyLong())).thenReturn(owner);
//        when(petTypeService.findAll()).thenReturn(petTypes);
//
//        mockMvc.perform(post("/owners/1/pets/2/edit"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/owners/1"))
//                .andExpect(model().attributeExists("owner"));
//
//        verify(petService).save(any());
//    }
}