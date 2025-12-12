package com.cinema_system.config;

import com.cinema_system.model.*;
import com.cinema_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final HallRepository hallRepository;
    private final MovieRepository movieRepository;
    private final NewsRepository newsRepository;
    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Создание администратора
        if (userRepository.findByEmail("admin@cinemasystem.com").isEmpty()) {
            User admin = User.builder()
                    .fullName("Администратор")
                    .email("admin@cinemasystem.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Администратор создан: admin@cinemasystem.com / admin");
        }

        // Создание менеджера
        if (userRepository.findByEmail("manager@cinemasystem.com").isEmpty()) {
            User manager = User.builder()
                    .fullName("Менеджер Алексей Алексеев")
                    .email("manager@cinemasystem.com")
                    .password(passwordEncoder.encode("manager"))
                    .role(Role.MANAGER)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(manager);
            System.out.println("✅ Менеджер создан: manager@cinemasystem.com / manager");
        }

        // Создание обычного пользователя
        if (userRepository.findByEmail("user@cinemasystem.com").isEmpty()) {
            User user = User.builder()
                    .fullName("Пользователь Петр Петров")
                    .email("user@cinemasystem.com")
                    .password(passwordEncoder.encode("user"))
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
            System.out.println("✅ Пользователь создан: user@cinemasystem.com / user");
        }

        // Создание залов
        if (hallRepository.count() == 0) {
            Hall hall1 = new Hall(null, "Зал 1", 100, "Основной зал с современным оборудованием");
            hallRepository.save(hall1);

            Hall hall2 = new Hall(null, "Зал 2", 50, "Малый зал для камерных показов");
            hallRepository.save(hall2);

            System.out.println("✅ Залы созданы");
        }

        // Создание фильмов
        if (movieRepository.count() == 0) {
            Movie movie1 = new Movie(null, "Интерстеллар", "Эпическая история о путешествиях в космос", "Sci-Fi", 169, "/static/images/interstellar.jpg");
            movieRepository.save(movie1);

            Movie movie2 = new Movie(null, "Побег из Шоушенка", "История надежды и дружбы в тюрьме", "Drama", 142, "/static/images/shawshank.jpg");
            movieRepository.save(movie2);

            System.out.println("✅ Фильмы созданы");
        }

        // Создание сеансов
        if (sessionRepository.count() == 0) {
            User manager = userRepository.findByEmail("manager@cinemasystem.com").orElseThrow();
            Hall hall1 = hallRepository.findAll().get(0);
            Movie movie1 = movieRepository.findAll().get(0);

            Session session1 = new Session(null, movie1, hall1, LocalDateTime.now().plusDays(1).withHour(18).withMinute(0), LocalDateTime.now().plusDays(1).withHour(21).withMinute(0), manager);
            sessionRepository.save(session1);

            System.out.println("✅ Сеансы созданы");
        }

        // Создание новостей
        if (newsRepository.count() == 0) {
            News news1 = News.builder()
                    .title("Открытие нового зала")
                    .content("Мы рады объявить об открытии нового зала в нашем кинотеатре. Теперь у вас есть возможность наслаждаться фильмами в комфортной обстановке.")
                    .createdAt(LocalDateTime.now().minusDays(5))
                    .build();
            newsRepository.save(news1);

            News news2 = News.builder()
                    .title("Показ фильма 'Интерстеллар'")
                    .content("В ближайшие дни состоится показ научно-фантастического фильма 'Интерстеллар'. Не пропустите возможность увидеть шедевр Кристофера Нолана!")
                    .createdAt(LocalDateTime.now().minusDays(2))
                    .build();
            newsRepository.save(news2);

            System.out.println("✅ Новости созданы");
        }
    }
}
