# MyApp

MyApp is a modern productivity tool designed to help users organize book list, track progress, create category, write notes on books and seting reading goals.

## Core Features

### 1. User Authentication

Secure user registration and login using email and password.

**Implementation details:**
- Kotlin
- Database: Room Database

### 2. Book list

Create, edit, delete, and read a book list.

**Implementation details:**
- RESTful API for CRUD operations
- UI built with React, using Redux for state management
- Tasks stored in MongoDB, associated with specific users

### 3. Project Collaboration

Collaborate with team members on shared projects.

**Implementation details:**
- Invitation system to add users to projects
- Real-time updates via WebSockets (Socket.io)
- Project-level permissions enforced server-side

### 4. Due Date Reminders

Get notified when tasks are nearing their deadlines.

**Implementation details:**
- Server-side job scheduler (node-cron) checks due dates
- Email notifications via Nodemailer
- In-app pop-up reminders using React Toasts

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
