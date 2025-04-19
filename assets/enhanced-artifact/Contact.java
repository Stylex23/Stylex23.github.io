package ProjectOne;

import java.util.regex.Pattern;

/**
 * Represents a contact entity with validation and fields for priority-based processing.
 */
public class Contact {
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10}");

    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private int priorityLevel;

    public Contact(String contactId, String firstName, String lastName, String phone, String address, int priorityLevel) {
        validateContactId(contactId);
        validateName(firstName, "First name");
        validateName(lastName, "Last name");
        validatePhone(phone);
        validateAddress(address);
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.priorityLevel = priorityLevel;
    }

    // Getters and Setters
    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        validateName(firstName, "First name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        validateName(lastName, "Last name");
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int level) {
        this.priorityLevel = level;
    }

    // Input validation
    private void validateContactId(String contactId) {
        if (contactId == null || contactId.length() > 10) {
            throw new IllegalArgumentException("Invalid contact ID");
        }
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.length() > 10) {
            throw new IllegalArgumentException("Invalid " + fieldName);
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    private void validateAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Invalid address");
        }
    }
}
