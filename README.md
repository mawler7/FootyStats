FootyStars ⚽
🚀 An advanced football statistics tracking application – inspired by Flashscore!

📌 Project Description
FootyStars is a web application for tracking football match results, team and player statistics, and making match predictions. The data is fetched from external API and stored in a PostgreSQL database.

🔹 Backend: Java (Spring Boot, Spring Security, JPA, MapStruct)
🔹 Database: PostgreSQL
🔹 Authentication: Google OAuth2, JWT

✨ Features:
📅 Live match tracking 
⭐ Add matches and leagues to favorites
📊 Match & player statistics
🔢 Head-to-Head (H2H) comparison
🎲 Betting slip with winnings calculator
⚙️ User settings customization (timezone, dark/light mode, match sorting style)

⚙️ Installation & Setup
1️⃣ Backend (Spring Boot)
Clone the repository

bash
Copy
Edit
git clone https://github.com/yourusername/FootyStars.git
cd FootyStars
Set up the database in application.properties

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/footystars
spring.datasource.username=your_user
spring.datasource.password=your_password
Run the backend

bash
Copy
Edit
mvn spring-boot:run
