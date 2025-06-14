package proiect.bet.sportbet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import proiect.bet.sportbet.models.Bilet;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.BiletRepository;

@SpringBootTest
public class BiletServiceTest {

    @Autowired
    private BiletService biletService;

    @MockBean
    private BiletRepository biletRepository;

    @Test
    void testCreateBilet() {
        Bilet bilet = new Bilet();
        bilet.setMiza(100.0);

        when(biletRepository.save(any(Bilet.class))).thenReturn(bilet);

        Bilet result = biletService.createBilet(bilet);

        assertNotNull(result);
        assertEquals(100.0, result.getMiza());
    }

    @Test
    void testGetAllBilete() {
        Bilet b1 = new Bilet();
        Bilet b2 = new Bilet();

        when(biletRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Bilet> result = biletService.getAllBilete();

        assertEquals(2, result.size());
    }

    @Test
    void testGetBiletById() {
        Bilet bilet = new Bilet();
        bilet.setId(1L);

        when(biletRepository.findById(1L)).thenReturn(Optional.of(bilet));

        Optional<Bilet> result = biletService.getBiletById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testUpdateBiletSuccess() {
        Bilet bilet = new Bilet();
        bilet.setId(1L);
        bilet.setMiza(50.0);

        when(biletRepository.existsById(1L)).thenReturn(true);
        when(biletRepository.save(any(Bilet.class))).thenReturn(bilet);

        Bilet result = biletService.updateBilet(1L, bilet);

        assertEquals(50.0, result.getMiza());
    }

    @Test
    void testUpdateBiletNotFound() {
        Bilet bilet = new Bilet();
        bilet.setMiza(60.0);

        when(biletRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            biletService.updateBilet(1L, bilet);
        });

        assertTrue(ex.getMessage().contains("nu a fost găsit"));
    }

    @Test
    void testDeleteBiletSuccess() {
        when(biletRepository.existsById(1L)).thenReturn(true);
        doNothing().when(biletRepository).deleteById(1L);

        assertDoesNotThrow(() -> biletService.deleteBilet(1L));
        verify(biletRepository).deleteById(1L);
    }

    @Test
    void testDeleteBiletNotFound() {
        when(biletRepository.existsById(2L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            biletService.deleteBilet(2L);
        });

        assertTrue(ex.getMessage().contains("nu a fost găsit"));
    }
}
