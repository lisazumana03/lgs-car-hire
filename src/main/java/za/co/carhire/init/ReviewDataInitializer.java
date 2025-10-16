package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.factory.feedback.ReviewFactory;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.feedback.IReviewRepository;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.service.vehicle.ICarService;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@Order(5)  // Run after Users, Cars, Locations, Bookings
public class ReviewDataInitializer implements CommandLineRunner {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private ICarService carService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Value("${app.database.init.reviews.enabled:true}")
    private boolean initReviewsEnabled;

    @Value("${app.database.init.reviews.clear-existing:false}")
    private boolean clearExistingReviews;

    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initReviewsEnabled) {
            System.out.println("Review data initialization is disabled. Set app.database.init.reviews.enabled=true to enable.");
            return;
        }

        List<Review> existingReviews = reviewRepository.findAll();

        if (!existingReviews.isEmpty() && !clearExistingReviews) {
            System.out.println("Database already contains " + existingReviews.size() + " reviews.");
            System.out.println("Skipping review initialization. Set app.database.init.reviews.clear-existing=true to override.");
            return;
        }

        if (clearExistingReviews && !existingReviews.isEmpty()) {
            System.out.println("Clearing existing reviews as requested...");
            reviewRepository.deleteAll();
            System.out.println("Existing reviews cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing review data...");
        System.out.println("============================================");

        createSampleReviews();

        System.out.println("============================================");
        System.out.println("Review initialization completed successfully!");
        System.out.println("--------------------------------------------");
        displayReviewSummary();
        System.out.println("============================================");
    }

    private void createSampleReviews() {
        Set<Car> cars = carService.getCars();
        List<User> users = userRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        if (cars.isEmpty()) {
            System.out.println("No cars found. Skipping review creation.");
            return;
        }

        if (users.isEmpty()) {
            System.out.println("No users found. Skipping review creation.");
            return;
        }

        System.out.println("Creating sample reviews for cars...");

        int reviewsCreated = 0;
        List<Car> carList = cars.stream().limit(15).toList(); // Review first 15 cars

        for (Car car : carList) {
            // Create 1-3 reviews per car
            int reviewCount = RANDOM.nextInt(3) + 1;
            for (int i = 0; i < reviewCount; i++) {
                User randomUser = users.get(RANDOM.nextInt(users.size()));

                // Find a booking for this car and user if exists
                Booking booking = bookings.stream()
                        .filter(b -> b.getCar() != null && b.getCar().getCarID() == car.getCarID()
                                && b.getUser() != null && b.getUser().getUserId().equals(randomUser.getUserId()))
                        .findFirst()
                        .orElse(null);

                Review review = createRandomReview(car, randomUser, booking);
                if (review != null) {
                    reviewRepository.save(review);
                    reviewsCreated++;
                    System.out.println("✓ Created review for " + car.getBrand() + " " + car.getModel() +
                                     " by " + randomUser.getName() + " (Rating: " + review.getRating() + "/5)");
                }
            }
        }

        System.out.println("\n" + reviewsCreated + " sample reviews created.");
    }

    private Review createRandomReview(Car car, User user, Booking booking) {
        int rating = RANDOM.nextInt(3) + 3; // Ratings between 3-5 (mostly positive)

        String[] titles = {
            "Great car!", "Excellent experience", "Smooth ride", "Very comfortable",
            "Highly recommend", "Amazing car", "Perfect for my trip", "Good value",
            "Clean and reliable", "Fantastic vehicle", "Will rent again",
            "Exceeded expectations", "Loved it!", "Awesome car"
        };

        String[][] comments = {
            // 5-star comments
            {
                "This car exceeded all my expectations! It was clean, comfortable, and drove like a dream. The pick-up process was smooth and the staff was incredibly helpful.",
                "Absolutely loved this vehicle! Perfect for our family road trip. The car was in excellent condition and had all the features we needed.",
                "Outstanding experience! The car was spotless, fuel-efficient, and a pleasure to drive. Would definitely rent again.",
                "This is hands down the best rental car I've ever had. Smooth ride, great features, and perfect for highway driving.",
                "Phenomenal car! Everything worked perfectly and it was very comfortable for long drives. Highly recommend!"
            },
            // 4-star comments
            {
                "Great car overall. Very comfortable and reliable. Only minor issue was the Bluetooth connection, but everything else was perfect.",
                "Really enjoyed driving this car. Smooth handling and good fuel economy. Would rent again!",
                "Solid vehicle with no major complaints. Clean interior and drove well throughout my rental period.",
                "Good experience overall. The car was in great condition and met all our needs for the trip.",
                "Nice car with comfortable seats. A few minor scratches but nothing that affected the drive."
            },
            // 3-star comments
            {
                "Decent car for the price. Got us where we needed to go. Could have been cleaner but overall acceptable.",
                "Average experience. The car worked fine but had some wear and tear. Would consider renting again if needed.",
                "Good enough for a short trip. No major issues but nothing special either.",
                "Met our basic needs. The car was functional but could use some updates.",
                "Fair rental. The car did the job but there's room for improvement in cleanliness and maintenance."
            }
        };

        String title = titles[RANDOM.nextInt(titles.length)];
        String comment = comments[rating - 3][RANDOM.nextInt(comments[rating - 3].length)];

        boolean isVerified = booking != null;

        return ReviewFactory.createReview(car, user, booking, title, comment, rating);
    }

    private void displayReviewSummary() {
        List<Review> allReviews = reviewRepository.findAll();
        long verifiedReviews = allReviews.stream().filter(Review::isVerified).count();

        double averageRating = allReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        long fiveStars = allReviews.stream().filter(r -> r.getRating() == 5).count();
        long fourStars = allReviews.stream().filter(r -> r.getRating() == 4).count();
        long threeStars = allReviews.stream().filter(r -> r.getRating() == 3).count();
        long twoStars = allReviews.stream().filter(r -> r.getRating() == 2).count();
        long oneStar = allReviews.stream().filter(r -> r.getRating() == 1).count();

        System.out.println("Review Summary:");
        System.out.println("- Total Reviews: " + allReviews.size());
        System.out.println("- Verified Reviews: " + verifiedReviews);
        System.out.println("- Average Rating: " + String.format("%.2f", averageRating) + "/5.0");
        System.out.println("\nRating Distribution:");
        System.out.println("- 5 Stars: " + fiveStars + " reviews");
        System.out.println("- 4 Stars: " + fourStars + " reviews");
        System.out.println("- 3 Stars: " + threeStars + " reviews");
        System.out.println("- 2 Stars: " + twoStars + " reviews");
        System.out.println("- 1 Star: " + oneStar + " reviews");
    }
}
