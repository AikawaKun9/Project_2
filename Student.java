public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private String program;
    private String yearLevel;
    private String gender;
    private String college;
    public Student() {}

    public Student(String id, String firstName, String lastName,
                   String program, String college, String yearLevel, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.college   = college;
        this.yearLevel = yearLevel;
        this.gender = gender;
    }

    public String getId()           {return id;}
    public String getFirstName()    {return firstName;}
    public String getLastName()     {return lastName;}
    public String getProgram()      {return program;}
    public String getCollege()      {return college;}
    public String getYearLevel()    {return yearLevel;}
    public String getGender()       {return gender;}

    public void setId(String id)                {this.id = id;}
    public void setFirstName(String firstName)  {this.firstName = firstName;}
    public void setLastName(String lastName)    {this.lastName = lastName;}
    public void setProgram(String program)      {this.program = program.toUpperCase();}
    public void setCollege(String college)      {this.college = college;}
    public void setYearLevel(String yearLevel)  {this.yearLevel = yearLevel;}
    public void setGender(String gender)        {this.gender = gender.toUpperCase();}

    public String[] toArray() { 
        return new String[]{ id, firstName, lastName, program, college, yearLevel, gender };
    }

    public static Student fromArray(String[] data) {
        if (data.length < 7) return null;
        return new Student(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
    }

    @Override
    public String toString() {
        return String.join(",", id, firstName, lastName, program, college, yearLevel, gender);
    }
}