
import org.aeis.devicelogs.entitiy.Device;
import org.aeis.devicelogs.entitiy.DeviceStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

 class DeviceEntityTest {

    @Test
    void testDeviceCreationAndProperties() {
        Long deviceId = 1L;
        String hallName = "Conference Room A";

        Device device = Device.builder()
                .id(deviceId)
                .hall(hallName)
                .statuses(new ArrayList<>())
                .build();

        assertNotNull(device);
        assertEquals(deviceId, device.getId());
        assertEquals(hallName, device.getHall());
        assertNotNull(device.getStatuses());
        assertTrue(device.getStatuses().isEmpty());
    }

    @Test
    void testDeviceSettersAndGetters() {
        Device device = new Device();

        Long deviceId = 2L;
        String hallName = "Lecture Hall B";
        List<DeviceStatus> statuses = new ArrayList<>();
        DeviceStatus status1 = DeviceStatus.builder().status("ONLINE").build(); // Assuming DeviceStatus has a builder
        statuses.add(status1);

        device.setId(deviceId);
        device.setHall(hallName);
        device.setStatuses(statuses);

        assertEquals(deviceId, device.getId());
        assertEquals(hallName, device.getHall());
        assertEquals(1, device.getStatuses().size());
        assertEquals("ONLINE", device.getStatuses().get(0).getStatus());
    }

    @Test
    void testDeviceAllArgsConstructor() {
        Long deviceId = 3L;
        String hallName = "Lab C";
        List<DeviceStatus> statuses = new ArrayList<>();
        DeviceStatus status1 = DeviceStatus.builder().status("OFFLINE").build();
        statuses.add(status1);

        Device device = new Device(deviceId, hallName, statuses);

        assertEquals(deviceId, device.getId());
        assertEquals(hallName, device.getHall());
        assertEquals(1, device.getStatuses().size());
        assertEquals("OFFLINE", device.getStatuses().get(0).getStatus());
    }

    @Test
    void testDeviceNoArgsConstructor() {
        Device device = new Device();
        assertNull(device.getId());
        assertNull(device.getHall());
        assertNull(device.getStatuses()); // Or check if it's initialized to an empty list by Lombok if @Builder.Default is used
    }
}
