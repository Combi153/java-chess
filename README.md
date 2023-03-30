### 기능목록

#### 도메인 기능 목록

- 체스말 (Piece)
    - [x] 위치를 갖는다.
        - [x] 위치는 File, Rank로 구성된다.
    - [x] 총 6가지의 종류가 있다
        - King, Queen, Knight, Bishop, Rook, Pawn
    - [x] 흑 혹은 백의 색깔을 갖는다.
    - [x] 각 색깔 당 16개의 체스말을 갖는다.
        - King(1개), Queen(1개), Knight(2개), Bishop(2개), Rook(2개), Pawn(8개)
    - [x] 각 체스말은 점수를 갖는다.
        - King(0), Queen(9), Knight(2.5), Bishop(3), Rook(5), Pawn(1)
        - 단, 같은 세로줄에 있는 같은 색 폰의 경우 0.5점이다.

- 체스보드 (Board)
    - [x] 초기에 체스말을 갖는다.
    - [x] 체스보드의 말이 이동한다.
        - [x] 입력 받은 현재 위치에 체스말이 있는지 확인한다.
            - [x] 말이 없으면 에외가 발생한다.
        - [x] 목표 위치까지 경로에 다른 체스말이 있는지 확인한다.
            - [x] 말이 있으면 예외가 발생한다.
        - [x] 체스말을 이동시킨다.
            - [x] 잡을 수 있는 말이 있으면 잡는다.
            - [x] 잡을 수 없는 말이 있으면 예외가 발생한다.
    - [x] 8 x 8 의 칸을 갖는다.
        - 가로 칸은 8개의 Rank로 구성된다. (1~8)
        - 세로 칸은 8개의 File로 구성된다. (a~h)
    - [x] 체스보드의 말을 확인한다.

- 말 이동
- [x] Knight 을 제외한 모든 말은 다른 기물을 뛰어넘을 수 없다.
    - King
        - [x] 전후좌우, 대각선 방향으로 한 칸 움직일 수 있다.
    - Queen
        - [x] 전후좌우, 대각선 방향으로 여러 칸 움직일 수 있다.
    - Bishop
        - [x] 대각선 방향으로 여러 칸 움직일 수 있다.
    - Knight
        - [x] 두 칸 전진한 상태에서 좌우로 한 칸 움직일 수 있다.
        - [x] 다른 기물을 뛰어넘을 수 있다.
    - Rook
        - [x] 전후좌우 방향으로 여러 칸 움직일 수 있다.
    - Pawn
        - [x] 초기 상태에서 한 칸 혹은 두 칸 전진할 수 있다.
        - [x] 초기 상태가 아닌 경우, 한 칸만 전진할 수 있다.
        - [x] 후퇴할 수 없다.
        - [x] 전진해서 상대방 기물을 잡을 수 없다.
        - [x] 대각선 한 칸 이동해서 상대방 기물을 잡을 수 있다.

- 체스게임 (ChessGame)
    - [x] 입력 받은 위치의 말이 이동할 순서인지 확인한다.
        - [x] 이동할 순서가 아니면 예외가 발생한다.
    - [x] 말을 이동시킨다.
    - [x] 말이 이동하면, 다음 턴에 이동할 색을 변경한다.
    - [x] 게임은 시작하거나, 진행 중이거나, 끝날 수 있다.
    - [x] 왕이 잡히면 게임이 끝난다.
    - [x] 점수를 계산하고, 점수가 더 높은 색깔을 찾을 수 있다.

- 게임 명령어 (GameCommand)
    - [x] start: 게임 시작
    - [x] end: 게임 종료
    - [x] move: 말 이동
    - [x] status: 게임 진행 상황 확인

#### 뷰 기능 목록

#### 입력

- 게임 (제어) 명령어를 입력받는다.
    - move 다음 공백으로 구분하여 출발 위치와 도착 위치 입력

#### 출력

- [x] 게임 시작 안내 메시지를 출력한다
- [x] 체스판을 출력한다

```
> 체스 게임을 시작합니다.
> 게임 시작 : start
> 게임 종료 : end
> 게임 상황 : status
> 게임 이동 : move source위치 target위치 - 예. move b2 b3
```

- 게임이 시작된 경우 체스보드와 체스말을 출력한다.

```
RNBQKBNR
PPPPPPPP
........
........
........
........
pppppppp
rnbqkbnr
```

#### 데이터 베이스 CRUD

- [x] 체스게임을 저장한다
    - [x] 체스게임은 `체스말의 종류`, `파일`, `랭크`, `색상`, `현재 말을 움직일 색상`으로 구성된다.

- [x] 체스게임을 읽는다
    - [x] 체스게임 표에서 `체스말의 종류`, `파일`, `랭크`, `색상`, `현재 말을 움직일 색상`을 읽는다.
    - [x] 체스게임 객체를 생성한다.

- [x] 체스게임을 수정한다
    - [x] 본래의 체스게임을 지우고, 새롭게 저장한다.

- [x] 체스게임을 삭제한다

#### Mysql 정보

- MySQL 서버 주소 : "localhost:13306"
- MySQL DATABASE 이름 : "chess"
- MySQL 서버 아이디 : "root"
- MySQL 서버 비밀번호 : "root"
- URL : "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"

```sql
CREATE TABLE chess_game
(
    id          INT         NOT NULL AUTO_INCREMENT,
    piece_type  VARCHAR(10) NOT NULL,
    piece_file  VARCHAR(10) NOT NULL,
    piece_rank  VARCHAR(10) NOT NULL,
    piece_color VARCHAR(10) NOT NULL,
    turn        VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);
```