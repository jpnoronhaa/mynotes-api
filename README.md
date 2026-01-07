# MyNotes API 📝

**MyNotes** is a RESTful API built with **Java 21** and **Spring Boot 3**. It allows users to manage notes written in **Markdown**, rendering them to HTML and providing spell-checking capabilities for both English and Portuguese.

This project was based on [markdown-note-taking-app](https://roadmap.sh/projects/markdown-note-taking-app).
## 🚀 Technologies & Tools

* **Language:** [Java 21](https://openjdk.org/projects/jdk/21/)
* **Framework:** [Spring Boot 3.4.1](https://spring.io/projects/spring-boot)
* **Database:** H2 Database (In-memory, easily switchable to PostgreSQL/MySQL)
* **ORM:** Spring Data JPA (Hibernate)
* **Documentation:** SpringDoc OpenAPI (Swagger UI)
* **Utilities:**
    * [Lombok](https://projectlombok.org/) (Boilerplate reduction)
    * [Commonmark](https://github.com/commonmark/commonmark-java) (Markdown parsing & rendering)
    * [LanguageTool](https://languagetool.org/) (Grammar & Spell checking API)

## ✨ Key Features

* **Markdown Management:**
    * Create notes sending raw text (supports `text/plain` for easy copy-pasting).
    * Full **CRUD** (Create, Read, Update, Delete) operations.
* **HTML Rendering:**
    * On-the-fly conversion of Markdown notes to clean HTML via the `/html` endpoint.
* **Spell Checker:**
    * Integrated **LanguageTool** engine.
    * Supports **English (US)** and **Portuguese (BR)**.
    * **Context-aware:** Extracts plain text from Markdown before checking (ignores symbols like `**`, `#`, links, etc).
    * Performance optimized with tool caching.
* **Documentation:**
    * Interactive API documentation via Swagger UI.

## 🏗 Architecture

The application follows a layered architecture to ensure separation of concerns:

1.  **Controller Layer:** Handles HTTP requests and responses. Uses **DTOs** (Data Transfer Objects) via Java Records to decouple the internal domain from the external API.
2.  **Service Layer:** Contains business logic (Markdown parsing, Spell check integration).
3.  **Repository Layer:** Handles database interactions using Spring Data JPA.
4.  **Domain Layer:** Represents the database entities (`Note`).

## 🛠️ How to Run

### Prerequisites
* Java 21 SDK installed.
* Maven installed.

### Steps
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/jpnoronhaa/mynotes-api.git
    cd mynotes
    ```

2.  **Build and Run:**
    ```bash
    mvn spring-boot:run
    ```

3.  **Access the Application:**
    The server will start at port `8080`.

## 📖 API Documentation (Swagger)

Once the application is running, you can access the interactive documentation at:

👉 **http://localhost:8080/swagger-ui.html**

### Main Endpoints

| Method   | Endpoint                              | Description                                                                         |
|:---------|:--------------------------------------|:------------------------------------------------------------------------------------|
| `POST`   | `/api/notes`                          | Create a new note.                                                                  |
| `GET`    | `/api/notes`                          | List all notes.                                                                     |
| `GET`    | `/api/notes/{id}`                     | Get a specific note.                                                                |
| `PUT`    | `/api/notes/{id}`                     | Update a note.                                                                      |
| `DELETE` | `/api/notes/{id}`                     | Delete a note.                                                                      |
| `GET`    | `/api/notes/{id}/html`                | Render note as HTML.                                                                |
| `GET`    | `/api/notes/{id}/spell-check`         | Check spelling (Query param: `?lang=en` or `pt`).                                   |
| `POST`   | `/api/spell-checker` | Extra functionality of spell check of plain text (Query param: `?lang=en` or `pt`). |

---
## 🧪 Usage Examples

### 1. Creating a Note (Raw Text)
**POST** `/api/notes`  
*Header:* `Content-Type: text/plain`
```markdown
# Meeting Notes
- Check the **servers**.
- Deploy the new version.
```

### 2. Checking Spelling
**GET** `/api/notes/1/spell-check?lang=en`

**Response:**
```json
[
  {
    "message": "Possible spelling mistake found.",
    "match": "servers",
    "suggestions": ["server", "serves"],
    "startPosition": 16,
    "endPosition": 23
  }
]
```

#### Developed by João Pedro Noronha.
