package ProjectOne;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    public void setUp() {
        service = new ContactService();
    }

    @Test
    public void testAddContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St");
        assertTrue(service.addContact(contact));
        assertFalse(service.addContact(contact)); // Should not allow duplicate ID
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);
        assertTrue(service.deleteContact("1"));
        assertFalse(service.deleteContact("1")); // Contact already deleted
    }

    @Test
    public void testUpdateContact() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);
        assertTrue(service.updateContact("1", "Jane", "Smith", "0987654321", "456 Elm St"));
        assertEquals("Jane", service.getContact("1").getFirstName());
        assertEquals("Smith", service.getContact("1").getLastName());
        assertEquals("0987654321", service.getContact("1").getPhone());
        assertEquals("456 Elm St", service.getContact("1").getAddress());

        // Test invalid updates
        assertFalse(service.updateContact("2", "Jane", "Smith", "0987654321", "456 Elm St")); // Non-existent contact
    }
}