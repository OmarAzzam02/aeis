
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aeis.summary.cache.SummariesCache;
import org.aeis.summary.controller.SummaryController;
import org.aeis.summary.dao.SummaryDAO;
import org.aeis.summary.dto.GeneratedSummaryDTO;
import org.aeis.summary.dto.SummaryDTO;
import org.aeis.summary.dto.SummaryRequestDTO;
import org.aeis.summary.entity.Summary;
import org.aeis.summary.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Summary Module Tests")
class SummaryTest {

    @Nested
    @WebMvcTest(SummaryController.class)
    @DisplayName("SummaryController Tests")
    class SummaryControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private HandleSummaryServices handleSummaryServices;

        @Autowired
        private ObjectMapper objectMapper;

        private SummaryDTO summaryDTO;

        @BeforeEach
        void setUp() {
            summaryDTO = SummaryDTO.builder()
                    .title("Test Title")
                    .content("Test Content".getBytes())
                    .courseId(1L)
                    .build();
        }

        @Test
        @DisplayName("POST /save-summary should call service and return success message")
        void saveSummary_shouldCallServiceAndReturnSuccess() throws Exception {
            doNothing().when(handleSummaryServices).saveSummary(any(SummaryDTO.class));

            mockMvc.perform(post("/save-summary")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(summaryDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Summary saved successfully"));

            verify(handleSummaryServices, times(1)).saveSummary(any(SummaryDTO.class));
        }

        @Test
        @DisplayName("PUT /share-summary/{id} should call service and return success message")
        void shareSummary_shouldCallServiceAndReturnSuccess() throws Exception {
            Long summaryId = 1L;
            doNothing().when(handleSummaryServices).shareSummary(summaryId);

            mockMvc.perform(put("/share-summary/{id}", summaryId))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Summary shared successfully"));

            verify(handleSummaryServices, times(1)).shareSummary(summaryId);
        }


    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    @DisplayName("HandleSummaryServices Tests")
    class HandleSummaryServicesTest {

        @Mock
        private SaveSummaryService saveSummaryService;
        @Mock
        private ShareSummaryService shareSummaryService;
        @Mock
        private RetrieveSharedSummaryService retrieveSharedSummaryService;
        @Mock
        private SummaryMapperService mapperService;
        @Mock
        private SummariesCache summariesCache;
        @Mock
        private RedirectToReaderService redirectService;

        @InjectMocks
        private HandleSummaryServices handleSummaryServices;

        private SummaryDTO summaryDTO;
        private Summary summaryEntity;

        @BeforeEach
        void setUp() {
            summaryDTO = SummaryDTO.builder().title("Test").content("Content".getBytes()).courseId(1L).build();
            summaryEntity = Summary.builder().id(1L).title("Test").content("Content".getBytes()).courseId(1L).build();
        }

        @Test
        @DisplayName("saveSummary should map, save, cache, and redirect")
        void saveSummary_shouldMapSaveCacheAndRedirect() {
            when(mapperService.mapToEntity(summaryDTO)).thenReturn(summaryEntity);
            when(saveSummaryService.saveSummary(summaryEntity)).thenReturn(summaryEntity); // Assuming save returns the saved entity
            doNothing().when(summariesCache).addSummary(summaryEntity);
            doNothing().when(redirectService).redirectToReader();

            handleSummaryServices.saveSummary(summaryDTO);

            verify(mapperService, times(1)).mapToEntity(summaryDTO);
            verify(saveSummaryService, times(1)).saveSummary(summaryEntity);
            verify(summariesCache, times(1)).addSummary(summaryEntity);
            verify(redirectService, times(1)).redirectToReader();
        }

        @Test
        @DisplayName("shareSummary should delegate to ShareSummaryService")
        void shareSummary_shouldDelegate() {
            Long summaryId = 1L;
            doNothing().when(shareSummaryService).shareSummary(summaryId);

            handleSummaryServices.shareSummary(summaryId);

            verify(shareSummaryService, times(1)).shareSummary(summaryId);
        }

        @Test
        @DisplayName("retrieveSummaries should retrieve, map, and return DTOs")
        void retrieveSummaries_shouldRetrieveMapAndReturn() {
            SummaryRequestDTO requestDTO = new SummaryRequestDTO();
            List<Long> ids = Collections.singletonList(1L);
            requestDTO.setCourseIds(ids);

            List<Summary> entities = Collections.singletonList(summaryEntity);
            when(retrieveSharedSummaryService.retrieveSharedSummaries(ids)).thenReturn(entities);
            when(mapperService.mapToDTO(summaryEntity)).thenReturn(summaryDTO); // For the stream().map()

            List<SummaryDTO> result = handleSummaryServices.retrieveSummaries(requestDTO);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(summaryDTO.getTitle(), result.get(0).getTitle());
            verify(retrieveSharedSummaryService, times(1)).retrieveSharedSummaries(ids);
            verify(mapperService, times(1)).mapToDTO(summaryEntity);
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    @DisplayName("SaveSummaryService Tests")
    class SaveSummaryServiceTest {
        @Mock
        private SummaryDAO summaryDAO;
        @InjectMocks
        private SaveSummaryService saveSummaryService;

        @Test
        @DisplayName("saveSummary should call DAO save and return saved entity")
        void saveSummary_shouldCallDaoAndReturnEntity() {
            Summary summaryToSave = Summary.builder().title("New Summary").build();
            Summary savedSummary = Summary.builder().id(1L).title("New Summary").build();
            when(summaryDAO.save(summaryToSave)).thenReturn(savedSummary);

            Summary result = saveSummaryService.saveSummary(summaryToSave);

            assertNotNull(result);
            assertEquals(savedSummary.getId(), result.getId());
            assertEquals(savedSummary.getTitle(), result.getTitle());
            verify(summaryDAO, times(1)).save(summaryToSave);
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    @DisplayName("ShareSummaryService Tests")
    class ShareSummaryServiceTest {
        @Mock
        private SummaryDAO summaryDAO;
        @InjectMocks
        private ShareSummaryService shareSummaryService;

        @Test
        @DisplayName("shareSummary should update status if summary exists")
        void shareSummary_shouldUpdateStatus_whenSummaryExists() {
            Long summaryId = 1L;
            Summary summary = Summary.builder().id(summaryId).build();
            when(summaryDAO.findById(summaryId)).thenReturn(Optional.of(summary));
            // Assuming updateSharedStatusById returns void or some status
            // doNothing().when(summaryDAO).updateSharedStatusById(summaryId);

            shareSummaryService.shareSummary(summaryId);

            verify(summaryDAO, times(1)).findById(summaryId);
            verify(summaryDAO, times(1)).updateSharedStatusById(summaryId);
        }

        @Test
        @DisplayName("shareSummary should throw exception if summary does not exist")
        void shareSummary_shouldThrowException_whenSummaryNotFound() {
            Long summaryId = 1L;
            when(summaryDAO.findById(summaryId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                shareSummaryService.shareSummary(summaryId);
            });

            assertEquals("Summary not found with id: " + summaryId, exception.getMessage());
            verify(summaryDAO, times(1)).findById(summaryId);
            verify(summaryDAO, never()).updateSharedStatusById(anyLong());
        }
    }

    @Nested
    @DisplayName("SummaryMapperService Tests")
    class SummaryMapperServiceTest {
        private SummaryMapperService mapperService = new SummaryMapperService(); // No dependencies, can instantiate directly

        @Test
        @DisplayName("mapToEntity should correctly map SummaryDTO to Summary")
        void mapToEntity_shouldMapCorrectly() {
            SummaryDTO dto = SummaryDTO.builder()
                    .title("DTO Title")
                    .content("DTO Content".getBytes())
                    .courseId(10L)
                    .build();

            Summary entity = mapperService.mapToEntity(dto);

            assertNotNull(entity);
            assertNull(entity.getId()); // ID is not part of DTO for new entity mapping
            assertEquals(dto.getTitle(), entity.getTitle());
            assertEquals(dto.getContent(), entity.getContent());
            assertEquals(dto.getCourseId(), entity.getCourseId());
        }

        @Test
        @DisplayName("mapToDTO should correctly map Summary to SummaryDTO")
        void mapToDTO_shouldMapCorrectly() {
            Summary entity = Summary.builder()
                    .id(1L) // ID is present in entity
                    .title("Entity Title")
                    .content("Entity Content".getBytes())
                    .courseId(20L)
                    .build();

            SummaryDTO dto = mapperService.mapToDTO(entity);

            assertNotNull(dto);
            // SummaryDTO builder in snippet doesn't include id or shared status,
            // so these won't be mapped by the current mapperService.mapToDTO
            // If they should be, the mapper needs an update.
            // For now, testing what IS mapped:
            assertEquals(entity.getTitle(), dto.getTitle());
            assertEquals(entity.getContent(), dto.getContent());
            assertEquals(entity.getCourseId(), dto.getCourseId());
        }

        @Test
        @DisplayName("mapToGeneratedDTO should correctly map Summary to GeneratedSummaryDTO")
        void mapToGeneratedDTO_shouldMapCorrectly() {
            Summary entity = Summary.builder()
                    .id(5L)
                    .title("Generated Title")
                    .content("Generated Content".getBytes())
                    .courseId(30L)
                    .build();

            GeneratedSummaryDTO generatedDTO = mapperService.mapToGeneratedDTO(entity);

            assertNotNull(generatedDTO);
            assertEquals(entity.getId(), generatedDTO.getId());
            assertEquals(entity.getTitle(), generatedDTO.getTitle());
            assertEquals(entity.getContent(), generatedDTO.getContent());
            assertEquals(entity.getCourseId(), generatedDTO.getCourseId());
        }
    }
}