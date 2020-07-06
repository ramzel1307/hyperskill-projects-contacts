package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class PersonContact extends Contact {
    static final long serialVersionUID = 1L;
    private String firstName;
    private String surname;
    private String birthDate;
    private String gender;

    PersonContact(String firstName, String surname, String birthDate, String gender, String number) {
        super(firstName + " " + surname, number);
        setFirstName(firstName);
        setSurname(surname);
	    // super.isPerson = true;
	    this.birthDate = birthDate;
	    this.gender = gender;
    }

    @Override
    public String toString() { return "Name: " + firstName + "\n" +
    						   "Surname: " + surname + "\n" + 
    						   "Birth date: " + birthDate + "\n" + 
    						   "Gender: " + gender + "\n" + 
    						   super.toString(); }

//    @Override
//    String getName() {
//        return getFirstName() + " " + getSurname();
//    }
//
//    String getFirstName() { return this.firstName; }
//
//    String getSurname() { return this.surname; }

    void setFirstName(String firstName) {
        this.firstName = !firstName.isBlank() ? firstName : "[no data]";
    }

    void setSurname(String surname) {
        this.surname = !surname.isBlank() ? surname : "[no data]";
    }

////    @Override
//    void setName(String firstName, String surname) {
//        this.firstName = firstName;
//        this.surname = surname;
//        setName(firstName + " " + surname);
//    }

    void setBirthDate(String birthDate) {
        this.birthDate = checkBirthDate(birthDate);
    }

    void setGender(String gender) {
        this.gender = checkGender(gender);
    }

    static String checkBirthDate(String birthDate) {
        LocalDate parsedDate = null;
        boolean isValidBirthDate;

        try {
            parsedDate = LocalDate.parse(birthDate);
            LocalDate presentDate = LocalDate.now();
            LocalDate fromDate = presentDate.minusYears(150);
            isValidBirthDate = !(parsedDate.isBefore(fromDate) || parsedDate.isAfter(presentDate));

            if (!isValidBirthDate) {
                System.out.println("Bad birth date!");
            }
        } catch (DateTimeParseException dateException) {
            System.out.println("Bad birth date!");
            isValidBirthDate = false;
        }

        return isValidBirthDate ? parsedDate.toString() : "[no data]";
    }

    static String checkGender(String gender) {
        boolean isValidGender = gender.matches("male|female|[Mm]|[Ff]");

        if(!isValidGender) {
            System.out.println("Bad gender!");
        }

        return isValidGender ? gender : "[no data]";
    }
}
