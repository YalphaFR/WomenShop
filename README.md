# My Project

This project is documented in [French](README.fr.md)

# WomenShop

WomenShop is a JavaFX desktop application that allows managing a product catalog, their categories, and stock.
The project follows a clear and modular architecture (MVC + Services + Repository) to ensure maintainability and scalability.

---

## 📌 Table of Contents

1. Overview
2. Architecture
3. Features
4. Technologies
5. Project Structure
6. Installation & Execution
7. Database Configuration
8. Development & Contribution
9. Best Practices
10. License

---

## 🖼️ 1. Overview

WomenShop is a JavaFX application that allows an administrator to:

* view products,
* filter products (by name, ID, category),
* manage stock,
* add, edit, or delete products and categories.

The UI is based on FXML + JavaFX controllers, with navigation managed by a `SceneManager`.

---

## 🏛️ 2. Architecture

The application is organized following an **MVC-inspired model**, reinforced by a clear separation of concerns:

### 📁 Model (`model`)

Contains business classes:
`Product`, `Category`, etc.

### 🎨 View (`resources/.../*.fxml`)

JavaFX interfaces created with SceneBuilder.

### 🎮 Controllers (`controller`)

Controllers linked to views.
Two sub-packages:

* `controller.base` → abstractions (`BaseController`, `ModuleController`, interfaces)
* `controller.fxml` → concrete controllers linked to FXML

### 🧠 Service Layer (`service`)

Contains business logic:

* validation
* orchestration between UI and repository

### 🗄️ Repository Layer (`repository`)

Data access (implemented via JDBC/MySQL):

* `ProductRepository`
* `CategoryRepository`
* Handles SQL queries + mapping ResultSet → objects

### 🗂️ Utilities (`util`)

Helpers:

* `UIUtils` (converters for ComboBox/ListView, JavaFX helpers…)
* Reusable functions

### 🧭 Navigation (`SceneManager`)

Manages scene loading, centralizes FXML, allows:

* single view loading,
* controller reuse,
* consistent navigation.

---

## ✨ 3. Features

* Product display
* Dynamic filtering:

    * by ID
    * by name
    * by category
* Product editing
* Stock management (Spinner)
* Category management
* Module navigation
* UI helpers (display objects in ComboBox/ListView)

---

## 🛠️ 4. Technologies

* **Java 21**
* **JavaFX 21** (via Maven)
* **Maven**
* **MySQL 8+**
* **JDBC**
* **FXML + SceneBuilder**
* **JPMS (module-info.java)**

---

## 📂 5. Project Structure

````
src/
├── main/
│    ├── java/com/example/womenshop/
│    │    ├── controller/
│    │    │    ├── base/           // BaseController, ModuleController, interfaces
│    │    │    └── fxml/           // Controllers linked to FXML
│    │    ├── model/               // Product, Category...
│    │    ├── service/             // Business services
│    │    ├── repository/          // DAO / JDBC MySQL
│    │    ├── util/                // UIUtils, helpers
│    │    └── SceneManager.java    // Scene navigation
│    └── resources/com/example/womenshop/
│         ├── *.fxml               // JavaFX views
│         ├── styles.css
│         └── images/
└── test/                          // Future unit tests

````

---

## ▶️ 6. Installation & Execution

### 1. Clone the project

```bash
git clone https://github.com/YalphaFR/WomenShop.git
cd WomenShop
````

### 2. Build

```bash
mvn clean install
```

### 3. Run

```bash
mvn javafx:run
```

---

## 🗄️ 7. Database Configuration

1. Create a database **women_shop**
2. Import the SQL script (tables `products`, `categories`, etc.)
3. Configure the connection in your DBManager class:

```java
private static final String URL = "jdbc:mysql://localhost:3306/women_shop";
private static final String USER = "root";
private static final String PASSWORD = "password";
```

4. The repositories use JDBC + prepared statements for security.

---

## 👨‍💻 8. Development & Contribution

### ➕ Add a new view (module)

1. Create `Something.fxml`
2. Add controller `SomethingController` in `controller.fxml`
3. In `Main`, load the scene via `SceneManager.loadScene(...)`
4. Call `controller.setSceneManager(...)` and inject the required services
5. Call `initData()` if your controller implements `ISceneAwareController`

### ➕ Add a new business feature

* Add the method in `ProductService` or `CategoryService`
* Implement the repository part
* Call from the controller
* Update the view if necessary

### ✔️ Conventions

* Standard Java (CamelCase)
* Modular architecture
* Minimal UI code in FXML
* Business logic strictly in services

---

## 📘 9. Project Best Practices

* Use a **SceneManager** to centralize scenes
* Use a **BaseController** for common code
* **ModuleController** for main scenes requiring `initData()`
* `UIUtils` to reduce repetition (ComboBox → setCellFactory, etc.)
* Repository fully separated from the view layer
* Use `module-info.java` → requires `opens ... to javafx.fxml`

Example:

```java
opens com.example.womenshop.controller.fxml to javafx.fxml;
opens com.example.womenshop.controller.base to javafx.fxml;
```

---

## 📄 10. License

MIT License

Copyright (c) 2025 [YalphaFR, Anis-Ghom]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.