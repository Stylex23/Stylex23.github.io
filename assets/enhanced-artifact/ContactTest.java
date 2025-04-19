package ProjectOne;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    public void testContactCreation() {
        Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Main St", 3);
        assertEquals("1", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
        assertEquals(3, contact.getPriorityLevel());
    }

    @Test
    public void testContactInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "John", "Doe", "1234567890", "123 Main St", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St", 3);
        });
    }

    @Test
    public void testContactInvalidFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", null, "Doe", "1234567890", "123 Main St", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "ThisIsAVeryLongFirstName", "Doe", "1234567890", "123 Main St", 3);
        });
    }

    @Test
    public void testContactInvalidLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "John", null, "1234567890", "123 Main St", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "John", "ThisIsAVeryLongLastName", "1234567890", "123 Main St", 3);
        });
    }

    @Test
    public void testContactInvalidPhone() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "John", "Doe", null, "123 Main St", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "John", "Doe", "12345", "123 Main St", 3);
        });
    }

    @Test
    public void testContactInvalidAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "John", "Doe", "1234567890", null, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1", "John", "Doe", "1234567890", "This is a very long address that exceeds the limit", 3);
        });
    }
}
