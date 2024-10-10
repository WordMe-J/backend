@Configuration
public class KafkaTopicConfig {

    @Autowired
    private KafkaAdmin kafkaAdmin;

    public void createTopicIfNotExists(String category) {
        String topicName = "vocabulary-category-" + category;
        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);

        if (!kafkaAdmin.describeTopics(Collections.singleton(topicName)).containsKey(topicName)) {
            kafkaAdmin.createOrModifyTopics(newTopic);
        }
    }
}