

# Chatting Program 💻

Java와 Eclipse, TCP Socket 통신을 이용한 간단한 채팅 프로그램 구현 <br>

id를 등록하고 전체 채팅방 로비에 입장할 수 있다. <br>

사용자의 새로운 방 생성이 가능하며, 존재하는 채팅방에 입장이 가능하다. <br>

같은 채팅방의 입장한 사용자간의 채팅이 가능하다. <br>

exit을 눌러서 퇴장할 경우 접속중인 id에서 제거된다. <br>

<br>

## 개발 기간
2022.06.14 ~ 2022.06.15

<br>

## 주요 기능

- id 등록
- 채팅방 생성
- 채팅방 입장
- 같은 채팅방 내 사용자끼리의 채팅 기능

## 구현 방식

수업 시간에 진행한 TCP Server와 GUIClient를 토대로 수정하며 제작했다. <br>

### 서버와 클라이언트간의 프로토콜

Code는 다음과 같이 통일시켜 사용했다.

<br>

| CodeName | Code |
| :---: | :---: |
| LOGIN | 1010 |
| MSG | 2020 |
| CHATROOM | 3030 |

<br>

`Code | roomName | id | msg` 의 형태로 통신하며, <br>
Server(ServerThread - readRecieve())와 Client(ReadThread - readRecieve())에서 
각각 코드에 따른 처리를 진행한다.


<br>

## 개선이 필요한 점

과제에서 요구하는 간단한 메인 기능을 위주로 구현한 프로그램이라 전체적으로 다양한 문제가 존재하고 있다.. <br>
*시간이 생기면 전체적으로 이 이상한 문제들을 개선하고 싶다..* <br>

- 다른 사용자가 만들고 대화를 시작한 경우에만 다른 사용자의 로비 채팅방 목록에 채팅방이 보인다. <br>
내가 만들자마자 아무 대화도 시작하지 않는다면, 해당 방의 유무를 다른 유저가 알 수 없다. <br>
~*(근데 이건 카톡도 비슷한 것 같다.)*~

- 중복 비교 기능 <br>
(이미 존재하는 id와 채팅방 이름 비교 기능)
이미 존재하는 id일 경우 처리하는 기능이 존재하지 않는다.
존재하는 이름의 채팅방으로 방만들기를 할 경우 같은 이름의 채팅방으로 입장 처리가 된다.

- 채팅방 퇴장 <br>
가장 기본적인 방의 생성과 입장 기능만 가능한 채팅 프로그램이기 때문에
채팅방 나가기를 할 경우 퇴장을 하며 방 목록에서 사용자의 이름이 삭제 될 필요가 있다.

- 방이름 수정 <br>
채팅방의 이름을 수정할 수 없다.

- 나갈 경우 채팅방 비우는 기능을 추가했으나, 빠르게 진행할 경우 즉각적으로 반영되지 않는다.


<br>

## UI


![image](https://user-images.githubusercontent.com/49184115/174019139-b9478965-a131-4b79-a88e-69193c096111.png)
채팅방에서 이용할 ID 등록 화면

<br>

![image](https://user-images.githubusercontent.com/49184115/174216026-6bc5ce25-def3-4c0f-8147-2bc6bc48de26.png)
전체 채팅방 목록을 확인할 수 있는 전체 채팅방 로비

<br>

![image](https://user-images.githubusercontent.com/49184115/174216052-f5ae795f-ac96-4a91-af3f-920318f5a793.png)
![image](https://user-images.githubusercontent.com/49184115/174216099-2f3e13e4-9a34-40df-8c44-9ea6dfc604b6.png)
방만들기가 가능하고 만들어진 방에 입장이 가능하다.

<br>

![image](https://user-images.githubusercontent.com/49184115/174216075-9089c6ac-0cb8-4460-9f03-985e2a106d89.png)
개인 채팅방의 모습이다.

<br>

![image](https://user-images.githubusercontent.com/49184115/174216202-2436844d-817b-4706-912a-67d72c10c1e1.png)
같은 채팅방 내의 유저끼리 채팅을 진행할 수 있다.

<br>

끝 🐧

<br>
