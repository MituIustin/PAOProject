
public class Person {
    private static int last_id = 0;
    protected int id;
    protected String f_name;
    protected String l_name;
    private final int birth_year;

    // Constructors

    public Person(String f_name, String l_name, int birth_year) {
        this.id = ++last_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.birth_year = birth_year;
    }

    // Getters

    public int get_id()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return f_name + ", " + l_name + ", " + birth_year;
    }
}
