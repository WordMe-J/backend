package kr.wordme.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardSetWriter implements ItemWriter<List<CardSet>> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    @Override
    public void write(List<? extends List<CardSet>> items) throws Exception {
        for (List<CardSet> cardSets : items) {
            String message = cardSets.stream()
                    .map(card -> card.getWord() + ": " + card.getMeaning())
                    .collect(Collectors.joining("\n"));

            String category = cardSets.get(0).getCategory();
            String topicName = "vocabulary-category-" + category;

            // 동적으로 토픽 생성
            kafkaTopicConfig.createTopicIfNotExists(category);

            // 메시지를 해당 카테고리 토픽으로 발행
            kafkaTemplate.send(topicName, message);
        }
    }
}