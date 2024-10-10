package kr.wordme.batch;

import org.springframework.stereotype.Component;

@Component
public class UserCardSetProcessor implements ItemProcessor<User, List<CardSet>> {

    @Autowired
    private CardSetRepository cardSetRepository;

    @Override
    public List<CardSet> process(User user) throws Exception {
        LocalDate subscriptionDate = user.getSubscriptionDate();
        LocalDate today = LocalDate.now();

        long daysElapsed = ChronoUnit.DAYS.between(subscriptionDate, today);

        int startIndex = (int) (daysElapsed * 10);
        int endIndex = startIndex + 10;

        return cardSetRepository.findByCategoryWithPagination(
                user.getSubscribedCategory(), startIndex, endIndex);
    }
}
