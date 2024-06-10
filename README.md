# sveDB

sveDB is an Android database application designed for the trading card game Shadowverse Evolve (SVE). This application allows users to store and display data related to the cards in the game, providing a convenient and user-friendly way to manage and browse through card information.

## Features

- **Cards**: Store and retrieve detailed information about SVE cards.
- **Decks**: Create and manage decks, view all decks, and retrieve specific decks by name.
- **Lists**: Create and manage lists, view all lists, and retrieve specific lists by name.
- **User Management**: Register and login users to personalize their experience.

## API Endpoints

### Cards

- `GET /cards/`: Retrieve all cards.
- `POST /cards/filtered`: Retrieve cards based on filter criteria.

### Decks

- `POST /decks/create`: Create a new deck.
- `GET /decks/`: Retrieve all decks.
- `GET /decks/cards`: Retrieve a specific deck by name.

### Lists

- `POST /list/create`: Create a new list.
- `GET /list/`: Retrieve all lists.
- `GET /list/cards`: Retrieve a specific list by name.

### Users

- `POST /user/register`: Register a new user.
- `POST /user/login`: Login an existing user.

## Android Frontend

The Android frontend for the application is provided in the GitHub repository. It includes the following activities:

- **LoginActivity**: For logging in. Includes a button to navigate to RegisterActivity.
- **RegisterActivity**: For registering a new account.
- **MainActivity**: Contains buttons to navigate to AllCardsActivity, AllDeckActivity, and AllListActivity.
- **AllCardsActivity**: Accesses `/cards` in default state and `/cards/filtered` when filtered, displaying the cards.
- **FilterCardsActivity**: Allows users to input filters and then returns to AllCardsActivity to view the filtered cards.
- **AllDeckActivity**: Accesses `/decks` and displays the decks. Includes a button for users to create a deck.
- **AllListActivity**: Accesses `/list` and displays the lists. Includes a button for users to create a list.

## Installation

To run this project, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/sveDB.git
   ```

2. **Navigate to the project directory:**
  ```bash
   cd sveDB
  ```
3. **Install dependencies:**
  ```bash
   npm install
  ```
4. **Start the application:**
  ```bash
   npm start
  ```

## Usage

Once the application is running, you can access the different endpoints through your Android application.
