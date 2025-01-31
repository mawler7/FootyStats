
FootyStars ⚽
🚀 A next-generation football statistics tracking application – inspired by Flashscore!

📌 Project Overview
FootyStars is a full-stack web application designed for tracking live football matches, team & player statistics, and match predictions.
It fetches real-time data from an external API and efficiently stores it in a PostgreSQL database.

🔹 Tech Stack
Backend: Java (Spring Boot, Spring Security, JPA, MapStruct)
Database: PostgreSQL
Authentication: Google OAuth2, JWT
✨ Key Features
✅ 📅 Live match tracking (similar to Flashscore)
✅ ⭐ Add favorite matches & leagues for quick access
✅ 📊 In-depth match & player statistics
✅ 🔢 Head-to-Head (H2H) comparison
✅ 🎲 Betting slip with a winnings calculator
✅ ⚙️ User settings customization (timezone, dark/light mode, match sorting preferences)

⚙️ Installation & Setup
1️⃣ Backend (Spring Boot)
Clone the Repository
bash
Copy
Edit
git clone https://github.com/yourusername/FootyStars.git
cd FootyStars
Configure Database Connection
Edit the application.properties file:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/footystars
spring.datasource.username=your_user
spring.datasource.password=your_password
Run the Backend
bash
Copy
Edit
mvn spring-boot:run
🛠 Technologies Used
Backend:
Java 17
Spring Boot 3.3.2 (Spring Security, OAuth2, JWT)
PostgreSQL + Hibernate
MapStruct for DTO mapping
Maven for dependency management
🚀 API Endpoints
Method	Endpoint	Description
GET	/fixture/{date}	Get matches for a specific date
GET	/fixture/id/{id}	Get match details
GET	/fixture/upcoming/{leagueId}	Get upcoming fixtures for a league
GET	/fixture/current/{leagueId}	Get completed fixtures for a league
GET	/h2h/{homeId}/{awayId}	Get Head-to-Head matches
GET	/standing/{leagueId}	Get league standings
POST	/prediction/savePrediction	Save match prediction
POST	/auth/login	User login with OAuth2
🔒 Security & Authentication
Google OAuth2 for secure user authentication
JWT (JSON Web Token) for API protection
CORS configured to allow React frontend access
👥 Contributing
Contributions are welcome! If you'd like to improve the project, follow these steps:

Fork the repository
Create a new branch (feature/my-new-feature)
Commit your changes with a clear message
Push the branch and submit a pull request 🚀
📜 License
This project is licensed under the MIT License. See the LICENSE file for details.

📧 Contact
For any questions or suggestions, feel free to reach out:
✉️ Email: mawler50@gmail.com
📌 GitHub: mawler7

🔗 Star the repo ⭐ and follow for updates!
