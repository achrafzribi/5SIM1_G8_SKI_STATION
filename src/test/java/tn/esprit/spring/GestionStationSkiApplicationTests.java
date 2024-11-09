package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PisteServicesImplTest {

	@InjectMocks
	private PisteServicesImpl pisteServices;  // On injecte le service à tester

	@Mock
	private IPisteRepository pisteRepository;  // On mock le dépôt

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  // Initialisation des mocks
	}

	@Test
	void retrieveAllPistes() {
		// Arrange: On simule des données de test
		List<Piste> pistes = Arrays.asList(
				new Piste(1L, "Piste 1", Color.BLUE, 500, 15, null),
				new Piste(2L, "Piste 2", Color.RED, 800, 20, null)
		);

		// On définit le comportement du mock
		when(pisteRepository.findAll()).thenReturn(pistes);

		// Act: On appelle la méthode du service
		List<Piste> result = pisteServices.retrieveAllPistes();

		// Assert: Vérification que le service renvoie bien les pistes simulées
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Piste 1", result.get(0).getNamePiste());
		verify(pisteRepository, times(1)).findAll();  // On vérifie que le dépôt a été appelé une seule fois
	}

	@Test
	void addPiste() {
		// Arrange
		Piste piste = new Piste(1L, "Piste 1", Color.GREEN, 600, 10, null);
		when(pisteRepository.save(piste)).thenReturn(piste);

		// Act
		Piste result = pisteServices.addPiste(piste);

		// Assert
		assertNotNull(result);
		assertEquals("Piste 1", result.getNamePiste());
		verify(pisteRepository, times(1)).save(piste);
	}

	@Test
	void removePiste() {
		// Arrange
		Long pisteId = 1L;
		doNothing().when(pisteRepository).deleteById(pisteId);

		// Act
		pisteServices.removePiste(pisteId);

		// Assert
		verify(pisteRepository, times(1)).deleteById(pisteId);  // Vérification que le dépôt a été appelé pour la suppression
	}

	@Test
	void retrievePiste() {
		// Arrange
		Long pisteId = 1L;
		Piste piste = new Piste(1L, "Piste 1", Color.BLACK, 700, 24, null);
		when(pisteRepository.findById(pisteId)).thenReturn(Optional.of(piste));

		// Act
		Piste result = pisteServices.retrievePiste(pisteId);

		// Assert
		assertNotNull(result);
		assertEquals("Piste 1", result.getNamePiste());
		verify(pisteRepository, times(1)).findById(pisteId);
	}

	@Test
	void retrievePiste_notFound() {
		// Arrange
		Long pisteId = 1L;
		when(pisteRepository.findById(pisteId)).thenReturn(Optional.empty());

		// Act & Assert
		Exception exception = assertThrows(RuntimeException.class, () -> {
			pisteServices.retrievePiste(pisteId);
		});

		assertEquals("Piste not found", exception.getMessage());
	}
}