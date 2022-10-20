# 1. RabbitMQ

## (1) What is RabbitMQ ?

- AMQP(Advanced Message Queue Protocol) 스펙을 구현한 Message Broker.
- 얼랭(Erlang) 언어를 통해 개발되었고, 상호운용성(interoperability), 성능, 안정성을 중요한 목표로 개발되었다.
  - 얼랭은 분산 처리, 장애 허용, 실시간 시스템과 같은 99.999% 가동시간을 요구하는 애플리케이션을 위해 설계됐다.

<br>

## (2) RabbitMQ 장점

1. 유연한 메시지 라우팅, 메시지 내구성 설정, 데이터센터 간 통신
2. 느슨하게 결합된 아키텍처를 구현할 수 있다.
   1. 애플리케이션의 의존성 제거 
   2. 데이터베이스 쓰기 의존성 제거
      1. RabbitMQ를 이용한 느슨한 결합은 데이터베이스 쓰기 성능에 영향을 받지 않는다. 
          (강결합일 경우, DB 서버가 트랜잭션을 완료하고 응답할 때까지 기다려야 하고 병목현상이 발생할 수 있다.)
      2. 확장성 (새로운 기능 추가)
         - 메세지 본문을 복제해 다른 목적으로 사용한다. (e.g. DB 쓰기 + 클라우드 서비스에 데이터 전달)
      3. 다중 마스터 Federation
         - Federation plugins : 인터넷으로 연결된 데이터센터 간에 애플리케이션의 데이터를 처리할 수 있는 플러그인 
         - 메세지를 복제해 다른 데이터 센터에 전달할 수 있다.
    

<br>

## (3) AMQ Model

![image](https://user-images.githubusercontent.com/48561660/196949454-8f0f06b1-2251-4fac-b6e8-cc7745ebef9a.png)

(출저 : https://www.cloudamqp.com/img/blog/exchanges-topic-fanout-direct.png)


1. `Exchange` : 메시지 브로커에서 큐에 메세지를 전달하는 컴포넌트. 메세지를 수신하고 메세지를 보낼 위치를 결정한다.
2. `Queue` : 메세지를 저장하는 디스크상이나 메모리상의 자료 구조. 메세지에 수행하는 작업을 정의하는 설정 정보가 있다.
3. `Binding` : 익스체인지에 전달된 메세지가 어떤 큐에 저장돼야 하는지 정의하는 컴포넌트.
   - `Binding key` :  익스체인지가 어떤 큐에 메세지를 전달해야 하는지를 의미한다. (단순히 **큐 이름**이다.)

<br>

## (4) RPC(Remote Procedure Call)

![image](https://user-images.githubusercontent.com/48561660/196976116-77ed2c8a-0ac0-4e32-84a5-fc913260853b.png)

- RabbitMQ 는 AMQP 메세지 브로커로 코어 서버와 통신하는 거의 모든 부분에서 RPC(Remote Procedure Call) 패턴으로 엄격하게 통신한다.
  - RPC : 한 컴퓨터에서 다른 컴퓨터의 프로그램이나 프로그램의 메서드를 원격에서 실행할 수 있게 해주는 컴퓨터 간의 통신 유형 중 하나.
- 웸서버의 경우 클라언트가 요청, 서버가 응답을 하는 구조 또한 RPC 이다. AMQP 는 클라이언트, 서버 모두 명령을 실행하는 RPC 형태이다.

- 클라이언트가 RabbitMQ 와 통신할 떄, RPC 동작하는 과정을 연결 협상이라 한다.

<br>

## (5) 프레임(Frame)

![image](https://user-images.githubusercontent.com/48561660/196981683-d840929b-02fe-464f-b398-bdc5439d5114.png)

- AMQP 프레임 컴포넌트는 RabbitMQ 에서 AMQP 명령을 전송하거나 수신 시에 전송되는 캡슐화된 프레임이다. 수신 시 필요한 모든 인자를 프레임 컴포넌트라 부른다.
- 프레임 컴포넌트는 `(1) 프레임 유형`, `(2) 채널 번호`, `(3) 프레임 크기(바이트)`, `(4) 프레임 페이로드(Payload)`, `(5) 끝 바이트 표식(ASCII 값 206)` 로 구성되어 있다.
- 프레임 유형은 `(1) 헤더 프레임`, `(2) 메서드 프레임`, `(3) 헤더 프레임`, `(4) 바디 프레임`, `(5) 하트비트 프레임` 로 정의된다.
  1. 헤더 프레임 : RabbitMQ 에 연결할 때 한 번만 사용
  2. 메서드 프레임 : RabbitMQ와 서로 주고받는 RPC 요청이나 응답을 전달
  3. 콘텐츠 헤더 프레임 : 메세지 크기와 속성을 포함
  4. 바디 프레임 : 메세지 내용을 포함
  5. 하트비트 프레임 : RabbitMQ와 연결된 클라이언트와 서버가 주고받으로 서로 사용 가능한 상태인지 확인하는 데 사용한다.
- 프로토콜과 헤더 프레임, 하트비트 프레임은 추상화돼 라이브러리의 사용자에게 보이지 않지만,
  메서드 프레임, 콘텐츠 헤더 프레임, 바디 프레임은 통신하는데 표면적으로 보인다.
  1. 메서드 프레임 : 첫 번째 전송되는 프레임. 익스체인지와 라우팅 키를 함꼐 전송한다.
  2. 콘텐츠 헤더 프레임 : 본문 크기와 메세지 속성이 포함. (본문 크기를 초과하면 콘텐츠가 여러 바디 프레임으로 분할.(하나 이상의 바디 프레임으로 전송될 수도 있다.))
  3. 바디 프레임 : 메세지 본문으로 전송된다.

<br>

### References

- RabbitMQ in depth : https://livebook.manning.com/book/rabbitmq-in-depth/chapter-2/48
- QueueAdmin : https://docs.spring.io/spring-amqp/docs/2.0.0.RELEASE/reference/html/_reference.html#broker-configuration
