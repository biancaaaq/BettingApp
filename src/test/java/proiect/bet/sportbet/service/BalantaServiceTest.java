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

import proiect.bet.sportbet.models.Balanta;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.BalantaRepository;

@SpringBootTest
public class BalantaServiceTest {

    @Autowired
    private BalantaService balantaService;

    @MockBean
    private BalantaRepository balantaRepository;

    @Test
    void testCreateBalanta() {
        Utilizator user = new Utilizator();
        Balanta balanta = new Balanta(user, 100.0);

        when(balantaRepository.save(any(Balanta.class))).thenReturn(balanta);

        Balanta result = balantaService.createBalanta(balanta);

        assertNotNull(result);
        assertEquals(100.0, result.getSuma());
        verify(balantaRepository, times(1)).save(balanta);
    }

    @Test
    void testGetAllBalante() {
        Balanta b1 = new Balanta(new Utilizator(), 50.0);
        Balanta b2 = new Balanta(new Utilizator(), 75.0);

        when(balantaRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Balanta> result = balantaService.getAllBalante();

        assertEquals(2, result.size());
    }

    @Test
    void testGetBalantaById() {
        Balanta balanta = new Balanta(new Utilizator(), 120.0);

        when(balantaRepository.findById(1L)).thenReturn(Optional.of(balanta));

        Optional<Balanta> result = balantaService.getBalantaById(1L);

        assertTrue(result.isPresent());
        assertEquals(120.0, result.get().getSuma());
    }

    @Test
    void testUpdateBalantaSuccess() {
        Utilizator user = new Utilizator();
        Balanta existing = new Balanta(user, 100.0);
        Balanta update = new Balanta(user, 200.0);

        when(balantaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(balantaRepository.save(any(Balanta.class))).thenReturn(update);

        Balanta result = balantaService.updateBalanta(1L, update);

        assertEquals(200.0, result.getSuma());
    }

    @Test
    void testUpdateBalantaNotFound() {
        Balanta update = new Balanta(new Utilizator(), 150.0);

        when(balantaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            balantaService.updateBalanta(1L, update);
        });

        assertTrue(ex.getMessage().contains("nu a fost găsită"));
    }

    @Test
    void testDeleteBalantaSuccess() {
        when(balantaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(balantaRepository).deleteById(1L);

        assertDoesNotThrow(() -> balantaService.deleteBalanta(1L));
        verify(balantaRepository).deleteById(1L);
    }

    @Test
    void testDeleteBalantaNotFound() {
        when(balantaRepository.existsById(2L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            balantaService.deleteBalanta(2L);
        });

        assertTrue(ex.getMessage().contains("nu a fost găsită"));
    }
}
