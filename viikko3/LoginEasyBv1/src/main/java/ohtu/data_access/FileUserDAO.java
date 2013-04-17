package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

public class FileUserDAO implements UserDao {

    private List<User> users;
    private String file;

    public FileUserDAO(String file) {
        this.file = file;
        this.users = new ArrayList<User>();
        try {
            Scanner tiedot = new Scanner(new File(file));
            while (tiedot.hasNextLine()) {
                User user = new User(tiedot.nextLine(), tiedot.nextLine());
                users.add(user);
            }
        } catch (FileNotFoundException ex) {
            File tiedosto = new File(file);
            try {
                tiedosto.createNewFile();
            } catch (IOException ex1) {
                System.out.println("Tiedoston luonti epäonnistui.");
            }
        }

    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(user.getUsername()+"\n");
            writer.write(user.getPassword()+"\n");
            writer.close();
            users.add(user);
        } catch (IOException ex) {
            System.out.println("Tiedoston kirjoitus epäonnistui.");
        }
    }
}
