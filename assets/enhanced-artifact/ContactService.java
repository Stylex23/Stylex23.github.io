package ProjectOne;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simulates database service operations such as indexing (priority queue),
 * data retrieval (merge sort), update, delete, and mock backup.
 */
public class ContactService {
    private static final Logger logger = LogManager.getLogger(ContactService.class);
    private final Map<String, Contact> contacts;
    private final PriorityQueue<Contact> urgentContacts;

    public ContactService() {
        contacts = new HashMap<>();
        urgentContacts = new PriorityQueue<>(Comparator.comparingInt(Contact::getPriorityLevel));
    }

    // Add new contact to "database"
    public boolean addContact(Contact contact) {
        if (contacts.containsKey(contact.getContactId())) {
            logger.warn("Duplicate contact ID: {}", contact.getContactId());
            return false;
        }
        contacts.put(contact.getContactId(), contact);

        if (contact.getPriorityLevel() <= 2) {
            urgentContacts.add(contact);  // "Indexed" for fast retrieval
        }

        logger.info("Added contact: {}", contact.getContactId());
        return true;
    }

    // Remove contact
    public boolean deleteContact(String contactId) {
        if (contacts.containsKey(contactId)) {
            Contact removed = contacts.remove(contactId);
            urgentContacts.remove(removed);
            logger.info("Deleted contact: {}", contactId);
            return true;
        }
        logger.warn("Contact ID not found for deletion: {}", contactId);
        return false;
    }

    // Update contact with validation
    public String updateContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            logger.warn("Update failed: Contact ID {} not found", contactId);
            return "Contact not found.";
        }

        StringBuilder errors = new StringBuilder();

        try { if (firstName != null) contact.setFirstName(firstName); }
        catch (IllegalArgumentException e) {
            errors.append("Invalid first name. ");
            logger.error("Invalid first name: {}", e.getMessage());
        }

        try { if (lastName != null) contact.setLastName(lastName); }
        catch (IllegalArgumentException e) {
            errors.append("Invalid last name. ");
            logger.error("Invalid last name: {}", e.getMessage());
        }

        try { if (phone != null) contact.setPhone(phone); }
        catch (IllegalArgumentException e) {
            errors.append("Invalid phone number. ");
            logger.error("Invalid phone: {}", e.getMessage());
        }

        try { if (address != null) contact.setAddress(address); }
        catch (IllegalArgumentException e) {
            errors.append("Invalid address. ");
            logger.error("Invalid address: {}", e.getMessage());
        }

        return errors.length() > 0 ? errors.toString().trim() : "Update successful.";
    }

    // Retrieve contact by ID
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    // Retrieve top-priority urgent contact (simulates priority-based data mining)
    public Contact getNextUrgentContact() {
        return urgentContacts.poll();
    }

    // Return sorted list of contacts by first name (simulates indexed search)
    public List<Contact> getSortedContactsByName() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        mergeSort(sortedList, 0, sortedList.size() - 1);
        return sortedList;
    }

    // Simulate data snapshot (like a database backup)
    public Map<String, Contact> backupDataSnapshot() {
        return new HashMap<>(contacts);
    }

    // Merge sort to simulate efficient retrieval
    private void mergeSort(List<Contact> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    private void merge(List<Contact> list, int left, int mid, int right) {
        List<Contact> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Contact> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getFirstName().compareTo(rightList.get(j).getFirstName()) <= 0) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) list.set(k++, leftList.get(i++));
        while (j < rightList.size()) list.set(k++, rightList.get(j++));
    }
}
