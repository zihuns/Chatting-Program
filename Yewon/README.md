## 채팅 프로그램

### 소개
Java를 기반으로 Socket, Thread를 사용하여 채팅방을 구현했습니다.<br>
socket을 통해서 메세지를 주고 받으며, thread를 통해 메세지가 들어올 때마다 처리하도록 하였습니다.<br>
[서버](https://github.com/jikimomo/Bit-ChattingProgram/tree/main/Yewon/Server)와 [클라이언트](https://github.com/jikimomo/Bit-ChattingProgram/tree/main/Yewon/Server)로 나눠져 있으며, 클라이언트에서 메세지를 보내면 서버에서 다른 클라이언트에게 메세지를 전달해줍니다.

### 주요 기능
1. 채팅방을 생성하거나 존재하는 채팅방에 입장<br>
2. 채팅방마다 현재 입장해있는 사람들 리스트 확인<br>
3. 채팅방에 있는 사람들과 채팅

### 화면 구성
(1) 입장하기 전에 아이디를 입력합니다.<br>
![1](https://user-images.githubusercontent.com/51360315/174593773-d5ae9070-c6bd-46a1-a293-f13a6bd4b6a1.PNG)<br><br>
(2) 왼쪽 상단이 로비 채팅 화면이고, 오른쪽 상단이 현재 로비에 접속한 사람들입니다. 하단에는 채팅방 리스트가 적혀있습니다.
왼쪽 입력창을 통해 로비에 있는 사람들과 채팅을 할 수 있으며, 오른쪽 입력창을 통해 채팅방 이름을 치면 해당 채팅방에 입장할 수 있습니다. 존재하지 않는 채팅방 이름을 입력하면 새로운 채팅방이 생성됩니다.<br>
<img src="https://user-images.githubusercontent.com/51360315/174593813-13b822dd-59d4-41e0-be7e-b08d68abc984.PNG" width="700px"/><br>
<img src="https://user-images.githubusercontent.com/51360315/174593844-9d21d31d-a3c6-4b00-8a7c-fd41ced1212a.PNG" width="700px"/><br><br>
(3) 채팅방에 입장하면 해당 채팅방에 있는 사람들을 알 수 있습니다. 왼쪽 입력창을 통해 해당 채팅방에 있는 사람들과 채팅을 할 수 있습니다.<br>
<img src="https://user-images.githubusercontent.com/51360315/174593878-8fcb10d3-7ef9-4387-bc2e-105ff4684f51.PNG" width="700px"/><br>
<img src="https://user-images.githubusercontent.com/51360315/174593903-ab82c191-a092-4c6f-b9e7-7e835b5fb534.PNG" width="700px"/><br><br>

### 아쉬운 부분
현재는 채팅방 이름을 입력해야 입장할 수 있도록 구현되어 있는데, 직접 입력이 아닌 채팅방을 클릭하면 입장할 수 있도록 수정하면 훨씬 사용하기 편리할 것 같습니다. 
