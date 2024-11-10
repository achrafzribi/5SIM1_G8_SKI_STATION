package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PisteServicesImplTest {

	@InjectMocks
	private PisteServicesImpl pisteServices; // The service being tested

	@Mock
	private IPisteRepository pisteRepository; // Mocked repository dependency

	@BeforeEach
	void setUp() {
		// No need to initialize mocks manually; handled by @ExtendWith(MockitoExtension.class)
	}

	@Test
	void retrieveAllPistes() {
		// Arrange: Mock some test data
		List<Piste> pistes = Arrays.asList(
				new Piste(1L, "Piste 1", Color.BLUE, 500, 15, null),
				new Piste(2L, "Piste 2", Color.RED, 800, 20, null)
		);
		when(pisteRepository.findAll()).thenReturn(pistes);

		// Act: Call the service method
		List<Piste> result = pisteServices.retrieveAllPistes();

		// Assert: Check that the service returns the expected data
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Piste 1", result.get(0).getNamePiste());
		verify(pisteRepository, times(1)).findAll();
	}

	@Test
	void addPiste() {
		// Arrange: Mock a piste to add
		Piste piste = new Piste(1L, "Piste 1", Color.GREEN, 600, 10, null);
		when(pisteRepository.save(piste)).thenReturn(piste);

		// Act: Call the service method
		Piste result = pisteServices.addPiste(piste);

		// Assert: Check that the piste was added as expected
		assertNotNull(result);
		assertEquals("Piste 1", result.getNamePiste());
		verify(pisteRepository, times(1)).save(piste);
	}

	@Test
	void removePiste() {
		// Arrange: Define piste ID to delete
		Long pisteId = 1L;
		doNothing().when(pisteRepository).deleteById(pisteId);

		// Act: Call the service method
		pisteServices.removePiste(pisteId);

		// Assert: Verify the deletion was triggered in the repository
		verify(pisteRepository, times(1)).deleteById(pisteId);
	}

	@Test
	void retrievePiste_existingPiste() {
		// Arrange: Mock a piste to be retrieved
		Long pisteId = 1L;
		Piste piste = new Piste(1L, "Piste 1", Color.BLACK, 700, 24, null);
		when(pisteRepository.findById(pisteId)).thenReturn(Optional.of(piste));

		// Act: Call the service method
		Piste result = pisteServices.retrievePiste(pisteId);

		// Assert: Check that the correct piste was returned
		assertNotNull(result);
		assertEquals("Piste 1", result.getNamePiste());
		verify(pisteRepository, times(1)).findById(pisteId);
	}

	@Test
	void retrievePiste_nonExistingPiste() {
		// Arrange: Define a piste ID that doesn't exist
		Long pisteId = 1L;
		when(pisteRepository.findById(pisteId)).thenReturn(Optional.empty());

		// Act: Call the service method
		Piste result = pisteServices.retrievePiste(pisteId);

		// Assert: Check that the result is null for a non-existing piste
		assertNull(result);
		verify(pisteRepository, times(1)).findById(pisteId);
	}
}
