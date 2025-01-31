package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.enums.ServiceType;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.requests.TenderEditRequest;
import com.tendering_service.services.tender.implementations.TenderCreatorServiceImpl;
import com.tendering_service.services.tender.implementations.TenderFacadeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TenderFacadeServiceTests {

    @Mock
    private TenderDatabaseService tenderDatabaseService;

    @Mock
    private TenderCreatorServiceImpl tenderCreatorServiceImpl;

    @Mock
    private TenderStatusManagerService tenderStatusManagerService;

    @Mock
    private TenderDataManagerService tenderDataManagerService;

    @InjectMocks
    private TenderFacadeServiceImpl tenderFacadeService;

    private TenderDto tenderDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tenderDto = new TenderDto();
        tenderDto.setId(UUID.randomUUID());
        tenderDto.setName("Tender Name");
        tenderDto.setDescription("Tender Description");
        tenderDto.setServiceType(ServiceType.Construction);
        tenderDto.setStatus(TenderStatus.CREATED);

        TenderEditRequest tenderEditRequest = new TenderEditRequest();
        tenderEditRequest.setName("Updated Name");
        tenderEditRequest.setDescription("Updated Description");
        tenderEditRequest.setServiceType("Consulting");
    }

    @Test
    void createTenderSuccessTest() {
        when(tenderCreatorServiceImpl.create(any(TenderDto.class))).thenReturn(tenderDto);

        TenderDto createdTender = tenderFacadeService.createTender(tenderDto);

        assertNotNull(createdTender);
        assertEquals(tenderDto.getName(), createdTender.getName());
        verify(tenderCreatorServiceImpl, times(1)).create(any(TenderDto.class));
    }

    @Test
    void rollbackTenderSuccessTest() {
        when(tenderDataManagerService.rollback(any(UUID.class), anyInt(), anyString())).thenReturn(tenderDto);

        TenderDto rolledBackTender = tenderFacadeService.rollbackTender(UUID.randomUUID(), 1, "username");

        assertNotNull(rolledBackTender);
        verify(tenderDataManagerService, times(1)).rollback(any(UUID.class), anyInt(), anyString());
    }

    @Test
    void getTenderStatusSuccessTest() {
        when(tenderStatusManagerService.getStatus(any(UUID.class), anyString())).thenReturn(TenderStatus.PUBLISHED.name());

        String status = tenderFacadeService.getTenderStatus(UUID.randomUUID(), "username");

        assertEquals(TenderStatus.PUBLISHED.name(), status);
        verify(tenderStatusManagerService, times(1)).getStatus(any(UUID.class), anyString());
    }

    @Test
    void incrementTenderVersionTest() {
        doNothing().when(tenderDataManagerService).incrementVersion(any(UUID.class));

        tenderFacadeService.incrementTenderVersion(UUID.randomUUID());

        verify(tenderDataManagerService, times(1)).incrementVersion(any(UUID.class));
    }

    @Test
    void getTendersByUsernameSuccessTest() {
        List<TenderDto> tenders = new ArrayList<>();
        tenders.add(tenderDto);
        when(tenderDatabaseService.getItemsByUsername(anyString())).thenReturn(tenders);

        List<TenderDto> result = tenderFacadeService.getTendersByUsername("username");

        assertFalse(result.isEmpty());
        assertEquals(tenderDto.getName(), result.get(0).getName());
        verify(tenderDatabaseService, times(1)).getItemsByUsername(anyString());
    }

    @Test
    void getAllTendersSuccessTest() {
        List<TenderDto> tenders = new ArrayList<>();
        tenders.add(tenderDto);
        when(tenderDatabaseService.getAllItems()).thenReturn(tenders);

        List<TenderDto> result = tenderFacadeService.getAllTenders();

        assertFalse(result.isEmpty());
        assertEquals(tenderDto.getName(), result.get(0).getName());
        verify(tenderDatabaseService, times(1)).getAllItems();
    }

    @Test
    void editTenderSuccessTest() {
        TenderDto editedTenderDto = new TenderDto();
        editedTenderDto.setId(tenderDto.getId());
        editedTenderDto.setName("Updated Name");
        editedTenderDto.setDescription("Updated Description");
        editedTenderDto.setServiceType(ServiceType.Construction);
        editedTenderDto.setStatus(TenderStatus.CREATED);

        when(tenderDataManagerService.edit(any(UUID.class), anyString(), any(TenderEditRequest.class)))
                .thenReturn(editedTenderDto);

        TenderEditRequest tenderEditRequest = new TenderEditRequest();
        tenderEditRequest.setName("Updated Name");
        tenderEditRequest.setDescription("Updated Description");
        tenderEditRequest.setServiceType("Consulting");

        TenderDto editedTender = tenderFacadeService.editTender(UUID.randomUUID(), "username", tenderEditRequest);

        assertNotNull(editedTender);
        assertEquals("Updated Name", editedTender.getName());
        assertEquals("Updated Description", editedTender.getDescription());
        assertEquals(ServiceType.Construction, editedTender.getServiceType());
        verify(tenderDataManagerService, times(1)).edit(any(UUID.class), anyString(), any(TenderEditRequest.class));
    }

    @Test
    void editTenderFailureTest() {
        when(tenderDataManagerService.edit(any(UUID.class), anyString(), any(TenderEditRequest.class)))
                .thenThrow(new RuntimeException("Failed to edit tender"));

        TenderEditRequest tenderEditRequest = new TenderEditRequest();
        tenderEditRequest.setName("Updated Name");
        tenderEditRequest.setDescription("Updated Description");
        tenderEditRequest.setServiceType("Consulting");

        assertThrows(RuntimeException.class, () -> tenderFacadeService.editTender(UUID.randomUUID(), "username", tenderEditRequest));
        verify(tenderDataManagerService, times(1)).edit(any(UUID.class), anyString(), any(TenderEditRequest.class));
    }
}
