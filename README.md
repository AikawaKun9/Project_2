# 🎓 Student Information System

A desktop application built with **Java Swing** and **SQLite** for managing student records. Features a yellowgreen-themed GUI with full CRUDL operations, live search, column sorting, and pagination.

---

## 📁 What's in This Repository

```
├── Studentinfosystem.java   ← Main application (UI + all features)
├── DatabaseManager.java     ← Database setup and auto-seeding
├── Student.java             ← Student model
├── Program.java             ← Program model
├── College.java             ← College model
├── .gitignore               ← Tells Git to ignore compiled files
└── README.md                ← This file
```

> The database (`database.db`) and compiled files (`*.class`) are **not** included — they are generated automatically when you run the app.

---

## ✅ What You Need to Install Before Running

### 1. Java JDK
Check if you already have Java by opening a terminal and typing:
```
java -version
```
If it shows a version number, you're good. If not, download and install it from:
👉 https://www.oracle.com/java/technologies/downloads/

Install the **JDK** (not just JRE), version 8 or higher.

### 2. SQLite JDBC Driver (JAR file)
This is a single file the app needs to talk to the database.

1. Go to: 👉 https://github.com/xerial/sqlite-jdbc/releases
2. Under the latest release, download the file named **`sqlite-jdbc-x.x.x.jar`** (e.g. `sqlite-jdbc-3.51.3.0.jar`)
3. Place that `.jar` file into the **same folder** as the `.java` files

---

## 🚀 Step-by-Step: How to Run

### Step 1 — Get the project files

**Option A — Clone with Git:**
```
git clone https://github.com/your-username/your-repo-name.git
```

**Option B — Download ZIP:**
On the GitHub page click the green **Code** button → **Download ZIP** → extract it.

---

### Step 2 — Add the SQLite JAR to the folder

After downloading the JAR from the link above, your project folder should look like this:
```
your-project-folder/
├── Studentinfosystem.java
├── DatabaseManager.java
├── Student.java
├── Program.java
├── College.java
├── sqlite-jdbc-3.51.3.0.jar   ← place it here
└── README.md
```

---

### Step 3 — Open a terminal inside the project folder

**On Windows:**
- Open the project folder in File Explorer
- Click the address bar at the top, type `cmd`, press Enter

**On Mac:**
- Right-click the project folder → **New Terminal at Folder**

**On Linux:**
- Right-click inside the folder → **Open Terminal**

---

### Step 4 — Compile the code

**On Windows:**
```bash
javac -cp ".;sqlite-jdbc-3.51.3.0.jar" *.java
```

**On Mac / Linux:**
```bash
javac -cp ".:sqlite-jdbc-3.51.3.0.jar" *.java
```

> ⚠️ Replace `sqlite-jdbc-3.51.3.0.jar` with the **exact filename** of the JAR you downloaded — check the filename carefully.

If successful, you will see no error messages and several `.class` files will appear in the folder.

---

### Step 5 — Run the application

**On Windows:**
```bash
java -cp ".;sqlite-jdbc-3.51.3.0.jar" Studentinfosystem
```

**On Mac / Linux:**
```bash
java -cp ".:sqlite-jdbc-3.51.3.0.jar" Studentinfosystem
```

The app window will open. On the **very first run**, it will automatically:
- Create `database.db` in the same folder
- Set up the `college`, `program`, and `student` tables
- Pre-populate **7 colleges**, **30 programs**, and **5,000 students**

This takes a few seconds on first launch — subsequent launches are instant.

---

## 🗄️ Database Schema

```sql
college (
  code TEXT PRIMARY KEY,        -- e.g. CCS
  name TEXT NOT NULL            -- e.g. College of Computer Studies
)

program (
  code    TEXT PRIMARY KEY,     -- e.g. BSCS
  name    TEXT NOT NULL,        -- e.g. Bachelor of Science in Computer Science
  college TEXT NOT NULL         -- refers to college.code
)

student (
  id        TEXT PRIMARY KEY,   -- format: YYYY-NNNN  e.g. 2024-0001
  firstname TEXT NOT NULL,
  lastname  TEXT NOT NULL,
  program   TEXT,               -- refers to program.code
  college   TEXT,               -- auto-resolved from the selected program
  year      TEXT NOT NULL,      -- 1 to 5
  gender    TEXT NOT NULL       -- M or F
)
```

---

## 🖥️ Features

### ✔️ CRUDL
| Entity | Create | Read | Update | Delete | List |
|--------|--------|------|--------|--------|------|
| Student | ✅ | ✅ | ✅ | ✅ | ✅ |
| Program | ✅ | ✅ | — | ✅ | ✅ |
| College | ✅ | ✅ | — | ✅ | ✅ |

### 🔍 Search
- Live search with a 300ms debounce (waits until you stop typing)
- Searches across ID, first name, last name, program, college, year, and gender
- Automatically resets to page 1 on every new search

### 🔃 Sorting
- Click any **column header** to sort ascending or descending

### 📄 Pagination
- **8 rows per page** — only fetches 8 rows from the database at a time
- Shows **"Showing X–Y of Z students"** and **"Page X of Y"**
- Prev / Next buttons disable at the first and last page

### 🏫 Manage Colleges
- Add or delete colleges
- Cannot delete a college that still has programs assigned to it

### 📚 Manage Programs
- Add or delete programs
- College is selected from a dropdown (add colleges first)
- College is auto-filled on student records when a program is selected

---

## ✔️ Validation Rules

### Student Form
| Field | Rule |
|-------|------|
| ID | Format `YYYY-NNNN` (e.g. `2024-0001`), year 2000–2099, must be unique |
| First / Last Name | Letters and spaces only, min 2 characters, auto-capitalized |
| Program | Must be selected from the dropdown |
| Year Level | 1 to 5 only |
| Gender | `M` or `F` only |

### College Manager
| Field | Rule |
|-------|------|
| Code | Letters only, 2–10 characters, must be unique |
| Name | Letters and spaces only, min 5 characters |

### Program Manager
| Field | Rule |
|-------|------|
| Code | Letters only, 2–10 characters, must be unique |
| Name | Letters and spaces only, min 5 characters |
| College | Must be selected from the dropdown |

---

## 👥 Authors

| Name | Student ID |
|------|-----------|
| Your Name Here | 0000-0000 |

---

## 📄 License

This project was made for academic purposes.
