import java.sql.*;
import java.util.Random;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
            st.execute("PRAGMA journal_mode = WAL");
            st.execute("PRAGMA cache_size = -8000");
            st.execute("PRAGMA synchronous = NORMAL");
        }
        return conn;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS college (" +
                "  code TEXT PRIMARY KEY," +
                "  name TEXT NOT NULL" +
                ")"
            );
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS program (" +
                "  code    TEXT PRIMARY KEY," +
                "  name    TEXT NOT NULL," +
                "  college TEXT NOT NULL," +
                "  FOREIGN KEY (college) REFERENCES college(code)" +
                ")"
            );
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS student (" +
                "  id        TEXT PRIMARY KEY," +
                "  firstname TEXT NOT NULL," +
                "  lastname  TEXT NOT NULL," +
                "  program   TEXT," +
                "  college   TEXT," +
                "  year      TEXT NOT NULL," +
                "  gender    TEXT NOT NULL," +
                "  FOREIGN KEY (program) REFERENCES program(code)" +
                ")"
            );

            // Indexes for faster search and sort on 5000 rows
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_student_lastname  ON student(lastname)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_student_firstname ON student(firstname)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_student_program   ON student(program)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_student_college   ON student(college)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_program_college   ON program(college)");

            // Seed only if empty
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM college");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Seeding database...");
                seedData(conn);
                System.out.println("Done.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database init failed: " + e.getMessage(), e);
        }
    }

    private static void seedData(Connection conn) throws SQLException {
        conn.setAutoCommit(false);

        // 7 colleges
        String[][] colleges = {
            {"CCS",  "College of Computer Studies"},
            {"COE",  "College of Engineering"},
            {"CBA",  "College of Business Administration"},
            {"CED",  "College of Education"},
            {"CAS",  "College of Arts and Sciences"},
            {"CNS",  "College of Nursing and Allied Health"},
            {"CAFA", "College of Architecture and Fine Arts"},
        };
        PreparedStatement ic = conn.prepareStatement(
            "INSERT OR IGNORE INTO college (code, name) VALUES (?,?)");
        for (String[] c : colleges) {
            ic.setString(1, c[0]); ic.setString(2, c[1]); ic.addBatch();
        }
        ic.executeBatch();

        // 30 programs
        String[][] programs = {
            {"BSCS",    "Bachelor of Science in Computer Science",                      "CCS"},
            {"BSIT",    "Bachelor of Science in Information Technology",                 "CCS"},
            {"BSIS",    "Bachelor of Science in Information Systems",                    "CCS"},
            {"BSDA",    "Bachelor of Science in Data Analytics",                        "CCS"},
            {"BSEMC",   "Bachelor of Science in Entertainment and Multimedia Computing", "CCS"},
            {"BSCE",    "Bachelor of Science in Civil Engineering",                      "COE"},
            {"BSEE",    "Bachelor of Science in Electrical Engineering",                 "COE"},
            {"BSME",    "Bachelor of Science in Mechanical Engineering",                 "COE"},
            {"BSECE",   "Bachelor of Science in Electronics Engineering",                "COE"},
            {"BSIE",    "Bachelor of Science in Industrial Engineering",                 "COE"},
            {"BSBA",    "Bachelor of Science in Business Administration",                "CBA"},
            {"BSACT",   "Bachelor of Science in Accountancy",                           "CBA"},
            {"BSMGT",   "Bachelor of Science in Management",                            "CBA"},
            {"BSMKT",   "Bachelor of Science in Marketing",                             "CBA"},
            {"BSFIN",   "Bachelor of Science in Finance",                               "CBA"},
            {"BSED",    "Bachelor of Secondary Education",                              "CED"},
            {"BEED",    "Bachelor of Elementary Education",                             "CED"},
            {"BSPED",   "Bachelor of Special Education",                                "CED"},
            {"BSPE",    "Bachelor of Physical Education",                               "CED"},
            {"BSMATH",  "Bachelor of Science in Mathematics",                           "CAS"},
            {"BSBIO",   "Bachelor of Science in Biology",                               "CAS"},
            {"BSCHEM",  "Bachelor of Science in Chemistry",                             "CAS"},
            {"BSPSYCH", "Bachelor of Science in Psychology",                            "CAS"},
            {"BSSOC",   "Bachelor of Science in Sociology",                             "CAS"},
            {"BSN",     "Bachelor of Science in Nursing",                               "CNS"},
            {"BSMT",    "Bachelor of Science in Medical Technology",                    "CNS"},
            {"BSPT",    "Bachelor of Science in Physical Therapy",                      "CNS"},
            {"BSARCH",  "Bachelor of Science in Architecture",                          "CAFA"},
            {"BSID",    "Bachelor of Science in Interior Design",                       "CAFA"},
            {"BSFA",    "Bachelor of Fine Arts",                                        "CAFA"},
        };
        PreparedStatement ip = conn.prepareStatement(
            "INSERT OR IGNORE INTO program (code, name, college) VALUES (?,?,?)");
        for (String[] p : programs) {
            ip.setString(1, p[0]); ip.setString(2, p[1]); ip.setString(3, p[2]); ip.addBatch();
        }
        ip.executeBatch();

        // 5000 students
        String[] firstNames = {
            "James","Maria","John","Ana","Jose","Sarah","Mark","Elena","Luis","Grace",
            "Carlos","Sofia","Miguel","Isabella","Antonio","Camille","Rafael","Diana",
            "Andres","Patricia","Gabriel","Christine","Eduardo","Melissa","Fernando",
            "Angela","Ricardo","Joanna","Roberto","Kristine","Manuel","Lorraine",
            "Daniel","Vivian","Aaron","Bianca","Kevin","Sheila","Ryan","Leah",
            "Nathan","Alicia","Adrian","Jasmine","Jerome","Vanessa","Kenneth","Ruby",
            "Patrick","Hannah"
        };
        String[] lastNames = {
            "Santos","Reyes","Cruz","Bautista","Ocampo","Garcia","Torres","Flores",
            "Ramos","Mendoza","Castro","Jimenez","Morales","Alvarez","Romero",
            "Guerrero","Navarro","Delgado","Ortega","Vargas","Molina","Herrera",
            "Medina","Aguilar","Vega","Castillo","Diaz","Fernandez","Lopez","Perez",
            "Aquino","Dela Cruz","Dela Vega","San Juan","De Leon","Santiago","Villanueva",
            "Pascual","Mercado","Tolentino","Magpayo","Resurreccion","Macaraeg",
            "Delos Santos","De Guzman","Manalo","Evangelista","Buenaventura","Lacson","Abad"
        };

        String[] progCodes    = new String[programs.length];
        String[] progColleges = new String[programs.length];
        for (int i = 0; i < programs.length; i++) {
            progCodes[i]    = programs[i][0];
            progColleges[i] = programs[i][2];
        }

        Random rand = new Random(42);
        PreparedStatement is = conn.prepareStatement(
            "INSERT OR IGNORE INTO student (id,firstname,lastname,program,college,year,gender) VALUES (?,?,?,?,?,?,?)");

        int[] buckets = {800, 900, 1000, 1100, 1200}; // years 2020-2024, total = 5000
        int seq = 1, batch = 0;
        for (int yi = 0; yi < 5; yi++) {
            int enrollYear = 2020 + yi;
            for (int n = 0; n < buckets[yi]; n++) {
                int pi = rand.nextInt(progCodes.length);
                is.setString(1, String.format("%d-%04d", enrollYear, seq++));
                is.setString(2, firstNames[rand.nextInt(firstNames.length)]);
                is.setString(3, lastNames[rand.nextInt(lastNames.length)]);
                is.setString(4, progCodes[pi]);
                is.setString(5, progColleges[pi]);
                is.setString(6, String.valueOf(rand.nextInt(5) + 1));
                is.setString(7, rand.nextBoolean() ? "M" : "F");
                is.addBatch();
                if (++batch % 500 == 0) is.executeBatch();
            }
        }
        is.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }
}