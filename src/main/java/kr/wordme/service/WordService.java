package kr.wordme.service;

import kr.wordme.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordService {

//    @Autowired
//    private AmazonPolly amazonPolly;
//
//    @Autowired
//    private AmazonS3 amazonS3;

//    @Value("${aws.s3.bucketName}")
//    private String bucketName;

    private final WordRepository wordRepository;

//    public String generateAndUploadSpeech(String word) {
//        // Amazon Polly를 사용하여 음성 생성
//        SynthesizeSpeechRequest speechRequest = new SynthesizeSpeechRequest()
//                .withText(word)
//                .withVoiceId(VoiceId.Joanna)  // 사용할 목소리 설정
//                .withOutputFormat(OutputFormat.Mp3);
//
//        SynthesizeSpeechResult speechResult = amazonPolly.synthesizeSpeech(speechRequest);
//
//        // S3에 업로드
//        InputStream audioStream = speechResult.getAudioStream();
//        String s3Key = "tts/" + word + ".mp3";  // S3 내의 파일 경로 지정
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType("audio/mpeg");
//        amazonS3.putObject(bucketName, s3Key, audioStream, metadata);
//
//        return amazonS3.getUrl(bucketName, s3Key).toString();
//    }


}

