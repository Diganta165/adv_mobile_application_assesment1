# MyApp

MyApp is a modern productivity tool designed to help users organize book list, track progress, create category, write notes on books and seting reading goals.

## Core Features

**Technologies:**
- Kotlin
- Database: Room Database
  
### 1. User Authentication
- Secure user registration and login using email and password.


### 2. Book list

- Create, edit, delete, and read a book list.
- User can add book with title, writer, photo, pages and category
- User can track how many pages he has read in a book. 

### 3. Category
- Category has a separate screen
- User can create and delete category 


### 4. Notes
- User can write notes on book list
- User can see all the notes in the note screen


### 5. Analytics Dashboard

Visualize task completion statistics and user activity.

**Implementation details:**
- Charts rendered using Chart.js in React
- Analytics data aggregated by backend API
- Responsive dashboard UI for desktop and mobile

## Getting Started

1. **Clone this repository**
2. **Install dependencies:**  
   - Run `npm install` in the root directory (for the server)  
   - Run `npm install` in the `client` directory (for the client)
3. **Set environment variables** in
