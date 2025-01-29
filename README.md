# Intern Directory

## Overview
The **Intern Directory** is a Java-based application designed to modernize the management of interns within a training center. Previously, the center relied on a simple text file to store and manage its list of interns, which posed significant usability and efficiency limitations. To address this issue, we developed an intuitive, feature-rich directory system with a user-friendly graphical interface.

## Features
- **Data Import**: Load intern data from an existing text file.
- **Alphabetical Sorting**: Interns are displayed in alphabetical order for easier navigation.
- **CRUD Operations**: Authorized users can **add, update, and delete** intern records.
- **Advanced Search**: Multi-criteria search functionality to quickly find specific interns.
- **Printable Directory**: Generate a printable version of the intern list using PDF export.
- **User Guide**: A comprehensive manual to assist users in getting started with the software.

## Technical Details
### Architecture & Technologies
- **Programming Language**: Java (96.3%) and CSS (3.7%)
- **GUI Framework**: JavaFX
- **Data Storage**: Binary file for persistent intern records
- **Algorithmic Implementation**: Binary Search Tree (BST) for efficient intern management
- **Styling**: CSS for UI consistency and improved user experience

## Algorithmic Challenge
One of the most significant challenges was implementing an **efficient search and management system** using a **Binary Search Tree (BST)**. This approach allows for optimized performance in searching, inserting, and deleting records compared to a linear data structure.

### BST Operations:
- **Insertion**: New intern records are inserted while maintaining the BST properties.
- **Deletion**: Handled through four distinct cases to ensure structural integrity.
- **Search**: Optimized lookup with logarithmic complexity.
- **Persistence**: BST data is stored and retrieved from a binary file for long-term storage.

---
This project showcases the power of **algorithmic efficiency, JavaFX UI design, and binary data management** while providing a practical solution to an everyday administrative challenge.

