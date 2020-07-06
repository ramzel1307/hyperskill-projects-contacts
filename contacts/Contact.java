package contacts;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDateTime;
import java.io.Serializable;

class Contact implements Serializable {
    static final long serialVersionUID = 1L;
    private String number;
    // private boolean isPerson;
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastEdited;

    Contact(String name, String number) {
	    this.name = !name.isBlank() ? name : "[no data]";
        this.number = number;
        this.dateCreated = LocalDateTime.now().withSecond(0).withNano(0);
        setDateLastEdited();
    }

    public String toString() { return "Number: " + number + "\n" +
    						   "Time created: " + dateCreated + "\n" + 
    						   "Time last edit: " + dateLastEdited; }
    						   
    // boolean isPerson() {
    // 	return isPerson;
    // }

    String getNumber() {
        return number;
    }

    String getName() {
    	return name;
    }
    
    void setName(String name) {
        this.name = name;
    }

    void setNumber(String number) { this.number = checkNumber(number); }

    static String checkNumber(String number) {
        Pattern pattern = Pattern.compile("\\+?\\(?[^\\W_]+\\)?|((\\+?\\(?[^\\W_]+\\)?[ -][^\\W_]{2,}|\\+?[^\\W_]+[ -]\\(?[^\\W_]{2,}\\)?)([ -]\\(?[^\\W_]{2,})*)");
        Matcher matcher = pattern.matcher(number);

        if(matcher.matches()) {
            return number;
        } else {
            System.out.println("Wrong number format!");
            return "[no number]";
        }
    }
    
    void setDateLastEdited() {
    	this.dateLastEdited = LocalDateTime.now().withSecond(0).withNano(0);
    }
}
