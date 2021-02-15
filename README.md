# Springboot 에서 AWS SQS Sender & Receiver 구현

: AWS SQS Standard Queue를 생성하고, Spring Application에서 Spring Cloud를 셋팅하고, Queue Message를 보내고, 받는 기능 구현

## 목차

1. AWS SQS Standard Queue 생성
2. SQS Gradle 셋팅
3. SQS에서 사용할 .yml 설정
4. SQS Message Sender 구현
5. SQS Message Receiver 구현

---

## 1. AWS SQS Standard Queue 생성

* AWS Daschboard -> AWS SQS -> "Create queue"
  ![1](https://user-images.githubusercontent.com/47520613/106736811-4626c300-6659-11eb-9d33-8e4a01ff73af.png)

* Test 용도로 Standard Queue의 Name 입력 -> "Create queue"
  ![2](https://user-images.githubusercontent.com/47520613/106736815-47f08680-6659-11eb-939b-35777042191f.png)
  ![3](https://user-images.githubusercontent.com/47520613/106736818-4921b380-6659-11eb-955b-a2985dc83f93.png)

## 2. SQS Gradle 셋팅

``` java
ext {
    // spring-cloud에서 사용할 버전 상수 셋팅
    set('springCloudVersion', "Hoxton.SR6")
}

dependencies {
    // spring-cloud에서 필요한 의존성을 web이 가지고 있어서 필요
    implementation 'org.springframework.boot:spring-boot-starter-web'
    // spring-cloud-message 추가
    implementation 'org.springframework.cloud:spring-cloud-starter-aws-messaging'
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
    }
}
```

## 3. SQS에서 사용할 .yml 설정

* .yml에서 사용할 SQS Queue의 URL 정보를 가져온다.
* Amazon SQS -> 해당 Queue 선택 -> URL 정보 복사
  ![4](https://user-images.githubusercontent.com/47520613/106737280-d6fd9e80-6659-11eb-8c55-ce03e1cb7ea6.png)
  ![5](https://user-images.githubusercontent.com/47520613/106737287-d8c76200-6659-11eb-8a15-94d1674e45eb.png)

## 4. SQS Message Sender 구현

* SQS message를 보내기 위한 래핑 클래스인 'QueueMessagingTemplate' 를 빈 설정
* 'QueueMessagingTemplate'는 Spring 프레임워크의 Cloud 라이브러리에서 제공

``` java
@Configuration
@EnableSqs
@EnableConfigurationProperties(AmazonSqsProperties.class)
public class AwsConfig {
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
}
```

* SQS message를 보내기 위한 send 메서드 구현

``` java
@Component
public class SqsMessageSender {
    private final AmazonSqsProperties amazonSqsProperties;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public SqsMessageSender(AmazonSqsProperties amazonSqsProperties, QueueMessagingTemplate queueMessagingTemplate) {
        this.amazonSqsProperties = amazonSqsProperties;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    public void send(String message) {
        queueMessagingTemplate.send(amazonSqsProperties.getUrl(), MessageBuilder.withPayload(message).build());
    }
}
```

## 5. SQS Message Receiver 구현

* 보냈던 SQS message를 polling하는 Listener 셋팅

``` java
public class SqsMessageReceiver {
    @SqsListener(value = "${cloud.aws.sqs.queue.url}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, @Header("SenderId") String senderId) {
        System.out.println(String.format("%s %s", message, senderId));
    }
}
```
