import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Section {
    private String name;

    // Constructors

    public Section(String name) {
        this.name = name;
    }

    // Getters

    public String get_name() {
        return name;
    }

    // Returns the number of apparitions in the book list

    public int get_frequency(List<Book> books, Reader user)
    {
        Set<Integer> read_books = user.get_books_id();
        int count = 0;
        for(Book book : books)
        {
            if(read_books.contains(book.get_id()))
            {
                if (book.get_section() == this)
                {
                    count++;
                }
            }
        }
        return count;
    }

    // Static methods

    // It returns a section based on its name, or creates a new one

    public static Section get_section(Library library, String section_name)
    {
        for (Section section : library.get_sections())
        {
            if(Objects.equals(section.get_name(), section_name))
            {
                return section;
            }
        }
        System.out.println("It seems that this section does not exists, let's add it!");
        return new Section(section_name);
    }

    @Override
    public String toString() {
        return "Section{" + name + "} ";
    }
}