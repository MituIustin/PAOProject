import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Author extends Person {
    private final boolean alive;

    // Constructors

    public Author(String f_name, String l_name, int b_year, boolean alive)
    {
        super(f_name, l_name, b_year);
        this.alive = alive;
    }

    // Getters

    public String get_name()
    {
        return this.f_name + " " + this.l_name;
    }

    // Static methods

    // Returns an author based on his name, or creates new one.
    public static Author get_author(Library library, String name)
    {
        Scanner scanner = new Scanner(System.in);
        for (Author author : library.get_authors())
        {
            if(Objects.equals(author.get_name(), name))
            {
                return author;
            }
        }
        System.out.println("It seems that this author does not exist. Let's add it!");
        System.out.println("Provide us with more informations :");
        System.out.println("Firstname: ");
        String f_name = scanner.nextLine();
        System.out.println("Lastname: ");
        String l_name = scanner.nextLine();
        System.out.println("Year of birth: ");
        int b_year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Is he/she still alive ? (yes/no) ");
        String alive_input = scanner.nextLine();
        boolean alive;
        alive = Objects.equals(alive_input, "yes");
        return new Author(f_name, l_name, b_year, alive);
    }

    // A function that shows all authors thah can be found in the library.

    public static void show_authors(Library library)
    {
        List<Author> authors = library.get_authors();
        for(Author author : authors)
        {
            System.out.println(author.get_name() );
        }
    }

    @Override
    public String toString()
    {
        return "Author{" + super.toString() + "} ";
    }

}
