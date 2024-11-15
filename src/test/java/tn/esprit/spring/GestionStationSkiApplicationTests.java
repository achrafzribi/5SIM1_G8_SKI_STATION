package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GestionStationSkiApplicationTests {

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices; // Service being tested

    @Mock
    private ISubscriptionRepository subscriptionRepository; // Mocked repository

    @BeforeEach
    void setUp() {
        // No manual initialization required; handled by Spring Boot Test
    }

    @Test
    void contextLoads() {
    }

    @Test
    void retrieveSubscriptions() {
        // Arrange
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, LocalDate.now(), LocalDate.now().plusMonths(1), 100f, TypeSubscription.MONTHLY),
                new Subscription(2L, LocalDate.now(), LocalDate.now().plusYears(1), 1200f, TypeSubscription.ANNUAL)
        );
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        // Act
        subscriptionServices.retrieveSubscriptions();

        // Assert
        verify(subscriptionRepository, times(1)).findAll();
    }

    @Test
    void addSubscription() {
        // Arrange
        Subscription subscription = new Subscription(1L, LocalDate.now(), LocalDate.now().plusMonths(6), 600f, TypeSubscription.SEMESTRIEL);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Act
        Subscription result = subscriptionServices.addSubscription(subscription);

        // Assert
        assertNotNull(result);
        assertEquals(TypeSubscription.SEMESTRIEL, result.getTypeSub());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void retrieveSubscriptionById_existingSubscription() {
        // Arrange
        Long subId = 1L;
        Subscription subscription = new Subscription(subId, LocalDate.now(), LocalDate.now().plusMonths(6), 600f, TypeSubscription.SEMESTRIEL);
        when(subscriptionRepository.findById(subId)).thenReturn(Optional.of(subscription));

        // Act
        Subscription result = subscriptionServices.retrieveSubscriptionById(subId);

        // Assert
        assertNotNull(result);
        assertEquals(subId, result.getNumSub());
        verify(subscriptionRepository, times(1)).findById(subId);
    }

    @Test
    void retrieveSubscriptionById_nonExistingSubscription() {
        // Arrange
        Long subId = 1L;
        when(subscriptionRepository.findById(subId)).thenReturn(Optional.empty());

        // Act
        Subscription result = subscriptionServices.retrieveSubscriptionById(subId);

        // Assert
        assertNull(result);
        verify(subscriptionRepository, times(1)).findById(subId);
    }

    @Test
    void getSubscriptionsByType() {
        // Arrange
        TypeSubscription type = TypeSubscription.MONTHLY;
        Set<Subscription> subscriptions = Set.of(
                new Subscription(1L, LocalDate.now(), LocalDate.now().plusMonths(1), 100f, TypeSubscription.MONTHLY),
                new Subscription(2L, LocalDate.now(), LocalDate.now().plusMonths(1), 120f, TypeSubscription.MONTHLY)
        );
        when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(type)).thenReturn(subscriptions);

        // Act
        Set<Subscription> result = subscriptionServices.getSubscriptionByType(type);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(subscriptionRepository, times(1)).findByTypeSubOrderByStartDateAsc(type);
    }

    @Test
    void retrieveSubscriptionsByDates() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);
        List<Subscription> subscriptions = List.of(
                new Subscription(1L, startDate, endDate, 200f, TypeSubscription.MONTHLY)
        );
        when(subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate)).thenReturn(subscriptions);

        // Act
        List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(startDate, endDate);
    }
}
