package ProjectOne;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    public void setUp() {
        service = new ContactService();
    }

    @Test
    public void testAddContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St", 3);
        assertTrue(service.addContact(contact));
        assertFalse(service.addContact(contact)); // Should not allow duplicate ID
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St", 3);
        service.addContact(contact);
        assertTrue(service.deleteContact("1"));
        assertFalse(service.deleteContact("1")); // Already deleted
    }

    @Test
    public void testUpdateContactSuccess() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St", 3);
        service.addContact(contact);
        String result = service.updateContact("1", "Jane", "Smith", "0987654321", "456 Elm St");
        assertEquals("Update successful.", result);
        assertEquals("Jane", service.getContact("1").getFirstName());
    }

    @Test
    public void testUpdateContactInvalidData() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St", 3);
        service.addContact(contact);
        String result = service.updateContact("1", "VeryLongFirstName", null, "abc", null);
        assertTrue(result.contains("Invalid first name"));
        assertTrue(result.contains("Invalid phone number"));
    }

    @Test
    public void testUpdateNonExistentContact() {
        String result = service.updateContact("2", "Jane", "Smith", "0987654321", "456 Elm St");
        assertEquals("Contact not found.", result);
    }

    @Test
    public void testGetNextUrgentContact() {
        Contact urgent = new Contact("2", "Alice", "Zane", "1112223333", "Urgent St", 1);
        Contact normal = new Contact("3", "Bob", "Yale", "9998887777", "Normal St", 5);
        service.addContact(urgent);
        service.addContact(normal);
        assertEquals("Alice", service.getNextUrgentContact().getFirstName());
    }

    @Test
    public void testGetSortedContactsByName() {
        service.addContact(new Contact("1", "Zoe", "Smith", "1112223333", "123 St", 3));
        service.addContact(new Contact("2", "Anna", "Lee", "4445556666", "456 St", 4));
        service.addContact(new Contact("3", "Mike", "King", "7778889999", "789 St", 2));
        List<Contact> sorted = service.getSortedContactsByName();
        assertEquals("Anna", sorted.get(0).getFirstName());
        assertEquals("Mike", sorted.get(1).getFirstName());
        assertEquals("Zoe", sorted.get(2).getFirstName());
    }

    @Test
    public void testBackupDataSnapshot() {
        Contact c1 = new Contact("1", "John", "Doe", "1234567890", "123 Main St", 2);
        Contact c2 = new Contact("2", "Jane", "Smith", "0987654321", "456 Elm St", 1);
        service.addContact(c1);
        service.addContact(c2);

        Map<String, Contact> snapshot = service.backupDataSnapshot();

        assertEquals(2, snapshot.size());
        assertTrue(snapshot.containsKey("1"));
        assertTrue(snapshot.containsKey("2"));

        // Ensure snapshot is a copy, not the same object
        assertNotSame(snapshot, service.backupDataSnapshot());
    }

    @Test
    public void testUrgentPriorityOrdering() {
        Contact low = new Contact("1", "Zack", "Low", "2223334444", "Low St", 3);
        Contact med = new Contact("2", "Maya", "Med", "1112223333", "Med St", 2);
        Contact high = new Contact("3", "Eva", "High", "4445556666", "High St", 1);
        service.addContact(low);
        service.addContact(med);
        service.addContact(high);

        // Only two urgent contacts (priority ≤ 2) should be added to queue
        assertEquals("Eva", service.getNextUrgentContact().getFirstName());
        assertEquals("Maya", service.getNextUrgentContact().getFirstName());
        assertNull(service.getNextUrgentContact()); // Zack was not urgent
    }
}
