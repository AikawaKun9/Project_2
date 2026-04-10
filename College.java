public class College {

    // =================== FIELDS ===================
    private String code;
    private String name;

    // =================== CONSTRUCTORS ===================
    public College() {}

    public College(String code, String name) {
        this.code = code.toUpperCase();
        this.name = name;
    }

    // =================== GETTERS ===================
    public String getCode() { return code; }
    public String getName() { return name; }

    // =================== SETTERS ===================
    public void setCode(String code) { this.code = code.toUpperCase(); }
    public void setName(String name) { this.name = name; }

    // =================== UTILITIES ===================

    /** Converts this College to a String array for JTable rows */
    public String[] toArray() {
        return new String[]{ code, name };
    }

    /** Creates a College from a CSV line — requires exactly 2 fields */
    public static College fromArray(String[] data) {
        if (data.length < 2) return null;
        return new College(data[0], data[1]);
    }

    /** Formats this College as a CSV line */
    @Override
    public String toString() {
        return code + "," + name;
    }
}