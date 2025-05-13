import org.aeis.reader.dto.recording.RecordingDTO;
import org.aeis.reader.dto.userdto.*; // Assuming UserDTO and Role are in here
import org.aeis.reader.service.instructor.LectureRecordingService;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.aeis.reader.cache.summary.GeneratedSummaryCache;
import org.aeis.reader.cache.user.UserSessionCache;
import org.aeis.reader.cache.video.GeneratedVideoCache;
import org.aeis.reader.util.ValidateTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderTest {

    @Mock
    private UrlServiceLocator urlServiceLocator;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserSessionCache userSessionCache; // Corrected declaration
    @Mock
    private ValidateTokenService validateTokenService; // Corrected declaration
    @Mock
    private GeneratedSummaryCache summaryCache;
    @Mock
    private GeneratedVideoCache videoCache;

    @Mock
    private UserDTO mockUserDTO; // Using a mocked UserDTO

    @InjectMocks
    private LectureRecordingService service;

    // Course related DTOs remain as they define the data structure for courses
    private HallDto testHall;
    private CourseTimePeriodDto testTimePeriod;
    private CourseDto testCourse;
    private MultipartFile mockContextFile;

    private final String HALL_NAME = "Hall A";
    private final Long COURSE_ID = 2L;
    private final Long HALL_ID = 1L;
    private final String DUMMY_RECORDING_STARTER_URL = "http://localhost/start";

    @BeforeEach
    void setUp() {
        testHall = HallDto.builder().name(HALL_NAME).id(HALL_ID).build();

        testTimePeriod = CourseTimePeriodDto.builder()
                .startTime(LocalTime.now().minusMinutes(30))
                .endTime(LocalTime.now().plusMinutes(30))
                .days(getCurrentDayShortName())
                .build();

        testCourse = CourseDto.builder()
                .id(COURSE_ID)
                .hall(testHall)
                .courseTimePeriod(testTimePeriod)
                .name("Test Course")
                .section(1)
                .build();

        // Configure the mockUserDTO for default "happy path" scenarios
        when(mockUserDTO.getRole()).thenReturn(Role.INSTRUCTOR); // Assuming Role is an enum
        when(mockUserDTO.getCourses()).thenReturn(Set.of(testCourse));

        mockContextFile = mock(MultipartFile.class);
        when(urlServiceLocator.getRECORDING_STARTER_URL()).thenReturn(DUMMY_RECORDING_STARTER_URL);
    }

    private String getCurrentDayShortName() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();
    }

    @Test
    void userHasALecture_whenLectureIsCurrent_shouldReturnTrueAndSendStatus() throws IOException {
        byte[] fileContent = "Sample file content".getBytes();
        when(mockContextFile.getBytes()).thenReturn(fileContent);
        when(restTemplate.postForEntity(anyString(), any(RecordingDTO.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Success"));

        boolean result = service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);

        assertTrue(result, "Should return true for a current lecture.");

        ArgumentCaptor<RecordingDTO> recordingDTOCaptor = ArgumentCaptor.forClass(RecordingDTO.class);
        verify(restTemplate, times(1)).postForEntity(
                eq(DUMMY_RECORDING_STARTER_URL),
                recordingDTOCaptor.capture(),
                eq(String.class)
        );

        RecordingDTO capturedDTO = recordingDTOCaptor.getValue();
        assertEquals(HALL_ID, capturedDTO.getHallId());
        assertEquals(COURSE_ID, capturedDTO.getCourseId());
        assertArrayEquals(fileContent, capturedDTO.getContextFile());
    }

    @Test
    void userHasALecture_whenContextFileIsNull_shouldSendStatusWithNullBytes() throws IOException {
        when(restTemplate.postForEntity(anyString(), any(RecordingDTO.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Success"));

        boolean result = service.userHasALecture(HALL_NAME, mockUserDTO, null);

        assertTrue(result, "Should return true even if context file is null.");

        ArgumentCaptor<RecordingDTO> recordingDTOCaptor = ArgumentCaptor.forClass(RecordingDTO.class);
        verify(restTemplate, times(1)).postForEntity(
                eq(DUMMY_RECORDING_STARTER_URL),
                recordingDTOCaptor.capture(),
                eq(String.class)
        );
        assertNull(recordingDTOCaptor.getValue().getContextFile());
    }


    @Test
    void userHasALecture_whenTimeIsBeforeLectureWindow_shouldReturnFalse() throws IOException {
        testCourse.getCourseTimePeriod().setStartTime(LocalTime.now().plusMinutes(30));
        testCourse.getCourseTimePeriod().setEndTime(LocalTime.now().plusMinutes(90));
        // Ensure mockUserDTO returns the updated testCourse
        when(mockUserDTO.getCourses()).thenReturn(Set.of(testCourse));


        boolean result = service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);

        assertFalse(result, "Should return false if current time is before lecture.");
        verify(restTemplate, never()).postForEntity(anyString(), any(), any());
    }

    @Test
    void userHasALecture_whenTimeIsAfterLectureWindow_shouldReturnFalse() throws IOException {
        testCourse.getCourseTimePeriod().setStartTime(LocalTime.now().minusMinutes(90));
        testCourse.getCourseTimePeriod().setEndTime(LocalTime.now().minusMinutes(30));
        when(mockUserDTO.getCourses()).thenReturn(Set.of(testCourse));

        boolean result = service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);

        assertFalse(result, "Should return false if current time is after lecture.");
        verify(restTemplate, never()).postForEntity(anyString(), any(), any());
    }

    @Test
    void userHasALecture_whenHallDoesNotMatch_shouldReturnFalse() throws IOException {
        boolean result = service.userHasALecture("Wrong Hall", mockUserDTO, mockContextFile);

        assertFalse(result, "Should return false if hall name does not match.");
        verify(restTemplate, never()).postForEntity(anyString(), any(), any());
    }

    @Test
    void userHasALecture_whenDayDoesNotMatch_shouldReturnFalse() throws IOException {
        String notToday = LocalDate.now().plusDays(1).getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();
        testCourse.getCourseTimePeriod().setDays(notToday);
        when(mockUserDTO.getCourses()).thenReturn(Set.of(testCourse));

        boolean result = service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);

        assertFalse(result, "Should return false if day does not match.");
        verify(restTemplate, never()).postForEntity(anyString(), any(), any());
    }

    @Test
    void userHasALecture_whenUserHasNoCourses_shouldReturnFalse() throws IOException {
        when(mockUserDTO.getCourses()).thenReturn(Collections.emptySet()); // Override default mock

        boolean result = service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);

        assertFalse(result, "Should return false if user has no courses.");
        verify(restTemplate, never()).postForEntity(anyString(), any(), any());
    }

    @Test
    void userHasALecture_whenContextFileReadThrowsIOException_shouldPropagateException() throws IOException {
        when(mockContextFile.getBytes()).thenThrow(new IOException("Disk read error"));

        assertThrows(IOException.class, () -> {
            service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);
        }, "IOException from contextFile.getBytes() should be propagated.");

        verify(restTemplate, never()).postForEntity(anyString(), any(), any());
    }

    @Test
    void userHasALecture_whenSendStatusToStartRecordingFails_shouldThrowRuntimeException() throws IOException {
        when(mockContextFile.getBytes()).thenReturn("content".getBytes());
        when(restTemplate.postForEntity(anyString(), any(RecordingDTO.class), eq(String.class)))
                .thenThrow(new RestClientException("Network error"));

        assertThrows(RuntimeException.class, () -> {
            service.userHasALecture(HALL_NAME, mockUserDTO, mockContextFile);
        }, "RuntimeException from sendStatusToStartRecording should be propagated.");
    }
}
