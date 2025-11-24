# WomenShop

WomenShop est une application desktop JavaFX permettant de gérer un catalogue de produits, leurs catégories, ainsi que le stock.
Le projet suit une architecture claire et modulaire (MVC + Services + Repository) afin de garantir maintenabilité et évolutivité.

---

## 📌 Table des matières

1. Aperçu
2. Architecture
3. Fonctionnalités
4. Technologies
5. Structure du projet
6. Installation & Exécution
7. Configuration Base de données
8. Développement & Contribution
9. Bonnes pratiques
10. Licence

---

## 🖼️ 1. Aperçu

WomenShop est une application JavaFX permettant à un administrateur de :

* visualiser les produits,
* filtrer les produits (par nom, ID, catégorie),
* gérer le stock,
* ajouter, modifier ou supprimer des produits et catégories.

L’UI repose sur FXML + controllers JavaFX, avec navigation gérée par un `SceneManager`.

---

## 🏛️ 2. Architecture

L'application est organisée selon un modèle inspiré de **MVC**, renforcé par une séparation nette :

### 📁 Modèle (`model`)

Contient les classes métiers :
`Product`, `Category`, etc.

### 🎨 Vue (`resources/.../*.fxml`)

Interfaces JavaFX créées avec SceneBuilder.

### 🎮 Contrôleurs (`controller`)

Contrôleurs liés aux vues.
Deux sous-packages :

* `controller.base` → abstractions (`BaseController`, `ModuleController`, interfaces)
* `controller.fxml` → contrôleurs concrets liés aux FXML

### 🧠 Service layer (`service`)

Contient la logique métier :

* validation
* orchestration entre UI et repository

### 🗄️ Repository layer (`repository`)

Accès aux données (implémenté via JDBC/MySQL) :

* `ProductRepository`
* `CategoryRepository`
* Gestion des requêtes SQL + mapping ResultSet → objets

### 🗂️ Utilitaires (`util`)

Helpers :

* `UIUtils` (convertisseurs pour ComboBox/ListView, helpers JavaFX…)
* Fonctions réutilisables

### 🧭 Navigation (`SceneManager`)

Gère le chargement de scènes, centralise les FXML, permet :

* chargement unique des vues,
* réutilisation des controllers,
* navigation cohérente.

---

## ✨ 3. Fonctionnalités

* Affichage des produits
* Filtre dynamique :

    * par ID
    * par nom
    * par catégorie
* Edition d’un produit
* Gestion de stock (Spinner)
* Gestion des catégories
* Navigation entre modules
* Helpers UI (affichage d’objets dans ComboBox/ListView)

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

## 📂 5. Structure du projet

```
src/
 ├── main/
 │    ├── java/com/example/womenshop/
 │    │    ├── controller/
 │    │    │    ├── base/           // BaseController, ModuleController, interfaces
 │    │    │    └── fxml/           // Contrôleurs liés aux FXML
 │    │    ├── model/               // Product, Category...
 │    │    ├── service/             // Services métier
 │    │    ├── repository/          // DAO / JDBC MySQL
 │    │    ├── util/                // UIUtils, helpers
 │    │    └── SceneManager.java    // Navigation entre scènes
 │    └── resources/com/example/womenshop/
 │         ├── *.fxml               // Vues JavaFX
 │         ├── styles.css
 │         └── images/
 └── test/                          // Futurs tests unitaires
```

---

## ▶️ 6. Installation & Exécution

### 1. Cloner le projet

```bash
git clone https://github.com/YalphaFR/WomenShop.git
cd WomenShop
```

### 2. Builder

```bash
mvn clean install
```

### 3. Lancer

```bash
mvn javafx:run
```

---

## 🗄️ 7. Configuration Base de données

1. Créer une base **women_shop**
2. Importer le script SQL (tables `products`, `categories`, etc.)
3. Configurer la connexion dans ta classe DBManager :

```java
private static final String URL = "jdbc:mysql://localhost:3306/women_shop";
private static final String USER = "root";
private static final String PASSWORD = "password";
```

4. Les repository utilisent JDBC + requêtes préparées pour la sécurité.

---

## 👨‍💻 8. Développement & Contribution

### ➕ Ajouter une nouvelle vue (module)

1. Créer fichier `Something.fxml`
2. Ajouter controller `SomethingController` dans `controller.fxml`
3. Dans `Main`, charger la scène via `SceneManager.loadScene(...)`
4. Appeler `controller.setSceneManager(...)` et injecter les services nécessaires
5. Appeler `initData()` si ton controller implémente `ISceneAwareController`

### ➕ Ajouter une nouvelle fonctionnalité métier

* Ajouter la méthode dans `ProductService` ou `CategoryService`
* Implémenter la partie repository
* Appeler depuis le controller
* Mettre à jour la vue si nécessaire

### ✔️ Conventions

* Java standard (CamelCase)
* Architecture modulaire
* Code UI minimal dans les FXML
* Logique métier strictement dans les services

---

## 📘 9. Bonnes pratiques du projet

* Utilisation d'un **SceneManager** pour centraliser les scènes
* Utilisation d’un **BaseController** pour tout code commun
* **ModuleController** pour les scènes principales nécessitant un `initData()`
* `UIUtils` pour réduire les répétitions (ComboBox → setCellFactory, etc.)
* Repository totalement séparé de la couche vue
* Utilisation du module‐info → nécessite `opens ... to javafx.fxml`

Exemple :

```java
opens com.example.womenshop.controller.fxml to javafx.fxml;
opens com.example.womenshop.controller.base to javafx.fxml;
```

---

## 📄 10. Licence

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