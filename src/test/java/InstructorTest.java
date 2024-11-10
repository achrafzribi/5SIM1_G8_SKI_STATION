import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstructorTest {

    @InjectMocks
    private InstructorServicesImpl instructorService;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInstructor() {
        // Données de test
        Instructor instructor = new Instructor();
        instructor.setName("John Doe");

        // Comportement simulé du repository
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Appel de la méthode du service
        Instructor addedInstructor = instructorService.addInstructor(instructor);

        // Assertions
        assertNotNull(addedInstructor);
        assertEquals("John Doe", addedInstructor.getName());
    }

    @Test
    public void testRetrieveAllInstructors() {
        // Données de test
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor());
        instructors.add(new Instructor());

        // Comportement simulé du repository
        when(instructorRepository.findAll()).thenReturn(instructors);

        // Appel de la méthode du service
        List<Instructor> retrievedInstructors = instructorService.retrieveAllInstructors();

        // Vérification que le repository a été appelé
        verify(instructorRepository).findAll();

        // Assertions
        assertNotNull(retrievedInstructors);
        assertEquals(instructors.size(), retrievedInstructors.size());
    }

    @Test
    public void testUpdateInstructor() {
        // Données de test
        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("Updated Name");

        // Comportement simulé du repository
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Appel de la méthode du service
        Instructor updatedInstructor = instructorService.updateInstructor(instructor);

        // Vérification que le repository a été appelé
        verify(instructorRepository).save(instructor);

        // Assertions
        assertNotNull(updatedInstructor);
        assertEquals(instructor.getId(), updatedInstructor.getId());
    }

    @Test
    public void testRetrieveInstructor() {
        // Données de test
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        instructor.setId(instructorId);

        // Comportement simulé du repository
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        // Appel de la méthode du service
        Instructor retrievedInstructor = instructorService.retrieveInstructor(instructorId);

        // Vérification que le repository a été appelé avec le bon ID
        verify(instructorRepository).findById(instructorId);

        // Assertions
        assertNotNull(retrievedInstructor);
        assertEquals(instructorId, retrievedInstructor.getId());
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Données de test
        Instructor instructor = new Instructor();
        Course course = new Course();
        course.setId(1L);

        // Comportement simulé des repositories
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Appel de la méthode du service
        Instructor assignedInstructor = instructorService.addInstructorAndAssignToCourse(instructor, 1L);

        // Vérification que le repository a été appelé
        verify(courseRepository).findById(1L);
        verify(instructorRepository).save(instructor);

        // Assertions
        assertNotNull(assignedInstructor);
        Set<Course> assignedCourses = assignedInstructor.getCourses();
        assertNotNull(assignedCourses);
        assertEquals(1, assignedCourses.size());
        assertEquals(course.getId(), assignedCourses.iterator().next().getId());
    }
}

