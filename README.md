
# SmartExpenseManager-DebtSimplifier-Java-Project

A simple Java-based command-line application that helps users track shared expenses and automatically simplifies debts between individuals using a debt-settling algorithm. Inspired by platforms like Splitwise, this tool aims to minimize redundant transactions and offer clarity in group expense sharing.

## 📌 Features

- Add shared expenses among multiple users
- Automatically calculate who owes whom and how much
- Simplify transactions using a greedy debt-settling algorithm
- CLI-based, menu-driven interface for easy use

## 🛠 Tech Stack

- Java
- Object-Oriented Programming (OOP)
- Java Collections Framework (Map, List)
- Command-line interaction


## 📁 Project Structure: Organized for Efficiency

The project is structured to promote maintainability and scalability:

SmartExpenseManager-DebtSimplifier-Java-Project/
├── src/
│   └── com/
│        └── BillsSplitMain /
│            ├── model
│            ├── service
│            ├── BillsSplitMain.java
│            └── ...
├── README.md
└── ...
* `src/com/BillsSplitMain/`:  Contains the core Java source code.
    * `model/Member.java`:  Represents a debt with its properties.
    * `model/Expense.java`: Represents an expense entry.
    * `model/Transaction.java`: Represents an expense entry.
    * `BillsSplit.java`:  The main class, orchestrating the application's flow.
    * `service/BillsSplit.java`:  Handles debt-related operations.
    * `service/BillsSplitUtil.java`:  Handles debt-related operations.
* `README.md`:  This file.## 🧪 How to Run

### Prerequisites
- Java 8 or above installed
### Installation

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/Vabna97/SmartExpenseManager-DebtSimplifier-Java-Project.git
    cd SmartExpenseManager-DebtSimplifier-Java-Project
    ```
2.  **Compile the Source Code:**
    Navigate to the `src` directory and compile the Java files:
    ```bash
    cd src
    javac com/BillsSplitMain/*.java
    ```
    ## How to Use SmartExpenseManager

1.  **Navigate to the `src` directory.**
2.  **Run the Application:**
    ```bash
    java com/BillsSplitMain/BillsSplitMain
    ```
3.  **Follow the Prompts:** The application will guide you through the menu options.  You can add record expenses, members, transactions, and more.



## 🧠 How It Works
The app tracks individual debts resulting from shared expenses, then runs a greedy debt-simplifying algorithm to reduce the number of payback transactions, making group settlements easier and more efficient.
## 🌱 Future Enhancements
Add database integration (JPA + MySQL)

Expose as RESTful APIs using Spring Boot

Implement web interface using Spring MVC / React

Add login & user authentication features


## 🙋‍♂️ Author
Vabna Pal
🔗 GitHub