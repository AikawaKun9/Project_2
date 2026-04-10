public class Program {
    private String college;
    private String code;
    private String name;

    public Program() {}

    public Program(String college, String code, String name) {
        this.college = college.toUpperCase();
        this.code = code.toUpperCase();
        this.name = name;
        
    }

    public String getCollege()            {return college;}
    public String getCode()               {return code.toUpperCase();}
    public String getName()               {return name;}
    public void setCollege(String college){this.college = college.toUpperCase();}
    public void setCode(String code)      {this.code = code.toUpperCase();}
    public void setName(String name)      {this.name = name;}

    public String[] toArray()             {return new String[]{college, code, name };}

    public static Program fromArray(String[] data) {
        if (data.length < 3) return null;
        return new Program(data[0], data[1], data[2]);
    }

    @Override
    public String toString() {return college + "," + code + "," + name;}
}