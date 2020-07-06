package contacts;

//import java.util.regex.Pattern;
//import java.util.regex.Matcher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        ContactsManager contactsManager = null;

        // get filename of previously saved phone book
        String fileName = null;
		    if (args.length == 1) {
			    fileName = args[0];
		    } else {
          String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                          + "0123456789"
                                          + "abcdefghijklmnopqrstuvxyz";
          StringBuilder sb = new StringBuilder(6);

          for (int ctr = 0; ctr < 6; ctr++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
          }

        	fileName = "phonebook" + sb.toString() + ".db";
        }

        // deserialize saved phone book
        try (FileInputStream file = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(file);
        ) {
            contactsManager = (ContactsManager) in.readObject();
            contactsManager.menu();
        } catch (FileNotFoundException e) {
            contactsManager = new ContactsManager();
            contactsManager.menu();
        }

        // serialize current phone book
        try (FileOutputStream file = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(file);
        ) {
            out.writeObject(contactsManager);
        }

        scan.close();
    }
}
