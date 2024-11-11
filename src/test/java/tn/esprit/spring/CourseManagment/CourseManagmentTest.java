package tn.esprit.spring.CourseManagment;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.GestionStationSkiApplication;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;
import tn.esprit.spring.services.ICourseServices;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // For unit tests with mocks
public class CourseManagmentTest {

    @Mock
    ICourseRepository courseRepository;

    @InjectMocks
    CourseServicesImpl courseServices;

    Course course1 = new Course(12344555L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 2, new HashSet<>());

    @Test
    void updateCourse() {
        // Test updating course price
        Course course = new Course(12L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 2, new HashSet<>());
        course.setPrice(20.0f);
        when(courseRepository.save(Mockito.any())).thenReturn(course);

        Course updatedCourse = courseServices.updateCourse(course);
        assertEquals(20.0f, updatedCourse.getPrice());
    }

    @Test
    void addCourse() {
        // Test adding course
        when(courseRepository.save(course1)).thenReturn(course1);
        Course course = courseServices.addCourse(course1);
        assertNotNull(course);
    }

    @Test
    void retrieveCourse() {
        // Test retrieving a course
        when(courseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(course1));

        Course retrievedCourse = courseServices.retrieveCourse(5L);
        assertNotNull(retrievedCourse);
        assertEquals(course1.getNumCourse(), retrievedCourse.getNumCourse());
    }
}

@SpringBootTest(classes = {GestionStationSkiApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class CourseServicesImplJunitTest {

    @Autowired
    ICourseServices cr;

    @Test
    @Order(1)
    void testAddCourse() {
        Course course = cr.addCourse(new Course(12344555L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 2, new HashSet<>()));
        assertNotNull(course);
    }

    @Test
    @Order(2)
    void testRetrieveAllCourses() {
        // Test retrieving all courses
        List<Course> courseList = cr.retrieveAllCourses();
        assertNotNull(courseList);
    }

    @Test
    @Order(3)
    void testRetrieveCourse() {
        // Test retrieving a course by ID
        Course course = cr.retrieveCourse(1L);
        assertNotNull(course);
    }
}
