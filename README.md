# FootyStars ⚽  
🚀 An advanced football statistics tracking application

## 📌 Project Description  
FootyStars is a web application for tracking football match results, team and player statistics, and making match predictions. The data is fetched from an external API and stored in a PostgreSQL database.  

### 🔹 Tech Stack  
- **Backend:** Java (Spring Boot, Spring Security, JPA, MapStruct)  
- **Database:** PostgreSQL  
- **Authentication:** Google OAuth2, JWT  

## ✨ Features  
✅ **Live match tracking** 📅  
✅ **Add matches and leagues to favorites** ⭐  
✅ **Match & player statistics** 📊  
✅ **Head-to-Head (H2H) comparison** 🔢  
✅ **Betting slip with winnings calculator** 🎲  
✅ **User settings customization (timezone, dark/light mode, match sorting style)** ⚙️  

## ⚙️ Installation & Setup  

### 1️⃣ Backend (Spring Boot)  

#### Clone the repository  
```bash
git clone https://github.com/yourusername/FootyStars.git
cd FootyStars
```

#### Set up the database in `application.properties`  
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/footystars
spring.datasource.username=your_user
spring.datasource.password=your_password
```

#### Run the backend  
```bash
mvn spring-boot:run
```

## 🛠 Technologies Used  
**Backend:**  
- Java 17  
- Spring Boot 3.3.2 (Spring Security, OAuth2, JWT)  
- PostgreSQL + Hibernate  
- MapStruct for DTO mapping  
- Maven for dependency management  

## 🚀 API Endpoints  

| Method  | Endpoint                          | Description |
|---------|----------------------------------|-------------|
| **GET** | `/fixture/{date}`               | Get matches for a specific date |
| **GET** | `/fixture/id/{id}`               | Get match details |
| **GET** | `/fixture/upcoming/{leagueId}`   | Get upcoming fixtures for a league |
| **GET** | `/fixture/current/{leagueId}`    | Get completed fixtures for a league |
| **GET** | `/h2h/{homeId}/{awayId}`         | Get Head-to-Head matches |
| **GET** | `/standing/{leagueId}`           | Get league standings |
| **POST**| `/prediction/savePrediction`     | Save match prediction |
| **POST**| `/auth/login`                    | User login with OAuth2 |

## 🔒 Security & Authentication  
- **Google OAuth2** for user authentication  
- **JWT (JSON Web Token)** for securing API endpoints  
- **CORS** configured for React frontend  

## 👥 Contributing  
Contributions are welcome! If you'd like to contribute, please follow these steps:  

1. **Fork the repository**  
2. **Create a new branch** (`feature/my-new-feature`)  
3. **Commit your changes**  
4. **Push the branch and submit a pull request** 🚀  

## 📜 License  
This project is licensed under the **MIT License**. See the `LICENSE` file for details.  

## 📧 Contact  
For any questions or suggestions, feel free to contact me:  

✉️ **Email:** mawler50@gmail.com  
📌 **GitHub:** [mawler7](https://github.com/mawler7)  
