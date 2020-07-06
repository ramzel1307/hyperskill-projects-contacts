package contacts;

import java.util.Collections;
import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Scanner;

class ContactsManager implements Serializable {
    static final long serialVersionUID = 1L;
    transient String fileName;
    transient final String MENU = "[menu] Enter action (add, list, search, count, exit): ";
    private ArrayList<Contact> contacts;
    transient static final Scanner scan = new Scanner(System.in);
    transient private boolean isExit = false;

    ContactsManager() {
        contacts = new ArrayList<>();
    }

    void menu() {
        while (!isExit) {
            System.out.print("\n" + MENU);
//            String option = "exit";
            String option = scan.nextLine();
            switch (option.toLowerCase()) {
                case "add":
                    add();
                    break;
                case "search":
                    search();
                    break;
                case "count":
                    count();
                    break;
                case "list":
                    list();
                    break;
                case "exit":
                    isExit = true;
                    System.out.println("\nHave a nice day!");
                    break;
                default:
                    System.out.println("Invalid action!");
                    // throw new TypeNotPresentException(option, null);
            }
            // System.out.println();
        }
    }

    private void add() {
        System.out.print("Enter the type: (person, organization): ");
        String type = scan.nextLine();

        switch (type.toLowerCase()) {
            case "person": {
                System.out.print("Enter the name: ");
                String firstName = scan.nextLine();
                System.out.print("Enter the surname: ");
                String surname = scan.nextLine();
                System.out.print("Enter the birth date: ");
                String birthDate = PersonContact.checkBirthDate(scan.nextLine());
                System.out.print("Enter the gender (M, F): ");
                String gender = PersonContact.checkGender(scan.nextLine());
                System.out.print("Enter the number: ");
                String number = Contact.checkNumber(scan.nextLine());

                // note: refactor this with builder
                contacts.add(new PersonContact(firstName, surname, birthDate, gender, number));
                System.out.println("The record added.");
                break;
            }
            case "organization": {
                System.out.print("Enter the organization name: ");
                String name = scan.nextLine();
                System.out.print("Enter the address: ");
                String address = scan.nextLine();
                System.out.print("Enter the number: ");
                String number = Contact.checkNumber(scan.nextLine());

                contacts.add(new OrganizationContact(name, address, number));
                System.out.println("The record added.");
                break;
            }
            default:
                // throw new TypeNotPresentException(type, null);
        }

        if (contacts.size() > 1) {
            Collections.reverse(contacts);
        }
    }

    private void list(){
        contacts.forEach(contact -> System.out.println(contacts.indexOf(contact) + 1 + ". " + contact.getName()));

        if (contacts.isEmpty()) {
            System.out.println("The Phone Book is empty.");
            return;
        }

        System.out.print("\n[list] Enter action ([number], back): ");
        String action = scan.nextLine();

        try {
            int recordNum = Integer.parseInt(action);
            System.out.println(contacts.get(recordNum - 1).toString());
            record(recordNum);
        } catch (NumberFormatException nfe) {
            if (action.toLowerCase().equals("back")) {
                this.menu();
            } else {
                this.menu();
            }
        }
    }

    private void count(){
        System.out.println("The Phone Book has " + contacts.size() + " records");
    }

    private void edit(int recordNum){
        recordNum--;
        if (contacts.get(recordNum).getClass() == PersonContact.class) {
            System.out.println("Select a field (name, surname, birth, gender, number): ");
            String field = scan.nextLine();

            switch (field.toLowerCase()) {
                case "name":
                    System.out.print("Enter name: ");
                    String name = scan.nextLine();
                    ((PersonContact) contacts.get(recordNum)).setFirstName(name);
                    break;
                case "surname":
                    System.out.print("Enter surname: ");
                    String surname = scan.nextLine();
                    ((PersonContact) contacts.get(recordNum)).setSurname(surname);
                    break;
                case "birth":
                    System.out.print("Enter birth date: ");
                    String birthDate = scan.nextLine();
                    ((PersonContact) contacts.get(recordNum)).setBirthDate(birthDate);
                    break;
                case "gender":
                    System.out.print("Enter gender (M, F): ");
                    String gender = scan.nextLine();
                    ((PersonContact) contacts.get(recordNum)).setGender(gender);
                    break;
                case "number":
                    System.out.print("Enter number: ");
                    String number = scan.nextLine();
                    contacts.get(recordNum).setNumber(number);
                    break;
                default:
                    System.out.println("Bad field!");
                    return;
            }
            System.out.println("The record updated.");
            contacts.get(recordNum).setDateLastEdited();
        } else if (contacts.get(recordNum).getClass() == OrganizationContact.class) {
            System.out.print("Select a field (name, address, number): ");
            String field = scan.nextLine();

            switch (field.toLowerCase()) {
                case "name":
                    System.out.print("Enter the name: ");
                    String name = scan.nextLine();
                    contacts.get(recordNum).setName(name);
                    break;
                case "address":
                    System.out.print("Enter the address: ");
                    String address = scan.nextLine();
                    ((OrganizationContact) contacts.get(recordNum)).setAddress(address);
                    break;
                case "number":
                    System.out.print("Enter the number: ");
                    String number = scan.nextLine();
                    contacts.get(recordNum).setNumber(number);
                    break;
                default:
                    System.out.println("Bad field!");
                    return;
            }
            System.out.println("The record updated.");
            contacts.get(recordNum).setDateLastEdited();
        }
        list();
    }

    private void search() {
        System.out.print("\nEnter search query: ");
        String query = scan.nextLine().toLowerCase();
        ArrayList<Contact> queryMatches = contacts.stream()
                .filter(contact -> (contact.getName().toLowerCase() + contact.getNumber()).contains(query))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.printf("Found %d results", queryMatches.size());
        queryMatches.forEach(match -> System.out.printf("%n%d. %s", queryMatches.indexOf(match) + 1, match.getName()));

        System.out.print("\n[search] Enter action ([number], back, again): ");
        String action = scan.nextLine();

        try {
            int number = Integer.parseInt(action) - 1;
            if (number < queryMatches.size()) {
                System.out.println(queryMatches.get(number).toString());
            }
            record(number + 1);
        } catch (NumberFormatException nfe) {
            if (action.equalsIgnoreCase("back")) {
                menu();
            } else if (action.equalsIgnoreCase("again")) {
                search();
            }
        }
    }

    private void delete(int recordNum) {
        contacts.remove(recordNum - 1);
    }

    private void record(int recordNum) {
        System.out.print("\n[record] Enter action (edit, delete, menu): ");
        String action = scan.nextLine();

        switch (action.toLowerCase()) {
            case "edit":
                edit(recordNum);
                break;
            case "delete":
                delete(recordNum);
                break;
            case "menu":
                menu();
                break;
            default:
                menu();
        }
    }
}
