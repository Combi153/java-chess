package chess.domain.board;

import chess.domain.Color;
import chess.domain.Position;
import chess.domain.piece.*;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static chess.domain.Color.*;
import static chess.domain.File.*;
import static chess.domain.Rank.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PiecesTest {

    @Test
    void 위치를_입력하면_말을_반환한다() {
        //given
        final Pieces pieces = new Pieces(List.of(
                new Pawn(A, SEVEN, BLACK)
        ));

        //when
        final Piece piece = pieces.findPieceOrThrow(new Position(A, SEVEN));

        //then
        assertThat(piece).extracting(Piece::getPosition, Piece::getColor, Object::getClass)
                .contains(new Position(A, SEVEN), BLACK, Pawn.class);
    }

    @Test
    void 입력된_위치에_말이_없으면_예외를_발생시킨다() {
        //given
        final Pieces pieces = new Pieces(List.of(
                new Pawn(A, SEVEN, BLACK)
        ));

        //when
        //then
        assertThatThrownBy(() -> pieces.findPieceOrThrow(new Position(B, SEVEN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 말이 존재하지 않습니다.");
    }

    @Test
    void 입력된_위치에_말이_없으면_빈_말을_반환한다() {
        //given
        final Pieces pieces = new Pieces(List.of(
                new Pawn(A, SEVEN, BLACK)
        ));

        //when
        final Piece piece = pieces.findPieceOrEmpty(new Position(B, SEVEN));

        //then
        assertThat(piece).extracting(Piece::getPosition, Piece::getColor, Object::getClass)
                .contains(new Position(B, SEVEN), EMPTY, EmptyPiece.class);
    }

    @ParameterizedTest
    @MethodSource("providePathPositions")
    void 입력된_경로에_말이_있는지_확인한다(final List<Position> pathPositions, final boolean expected) {
        //given
        final Pieces pieces = new Pieces(List.of(
                new Pawn(A, SEVEN, BLACK)
        ));

        //when
        final boolean actual = pieces.hasPiece(pathPositions);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> providePathPositions() {
        return Stream.of(
                Arguments.of(List.of(), false),
                Arguments.of(List.of(new Position(A, SEVEN)), true)
        );
    }

    @Test
    void 말을_더한다() {
        //given
        final Pieces pieces = new Pieces(new ArrayList<>());

        //when
        pieces.add(new Pawn(A, SEVEN, BLACK));

        //then
        assertSoftly(softly -> {
            final List<Piece> allPieces = pieces.getPieces();
            softly.assertThat(allPieces.size()).isEqualTo(1);
            softly.assertThat(allPieces.get(0)).extracting(Piece::getPosition, Piece::getColor, Object::getClass)
                    .contains(new Position(A, SEVEN), BLACK, Pawn.class);
        });
    }

    @Test
    void 말을_삭제한다() {
        //given
        final Piece pawn = new Pawn(A, SEVEN, BLACK);
        final Pieces pieces = new Pieces(new ArrayList<>(List.of(pawn)));

        //when
        pieces.remove(pawn);

        //then
        assertSoftly(softly -> {
            final List<Piece> allPieces = pieces.getPieces();
            softly.assertThat(allPieces.size()).isEqualTo(0);
        });
    }

    @ParameterizedTest
    @MethodSource("providePieces")
    void 비었는지_확인한다(final List<Piece> samplePieces, final boolean expected) {
        //given
        final Pieces pieces = new Pieces(samplePieces);

        //when
        final boolean actual = pieces.isEmpty();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> providePieces() {
        return Stream.of(
                Arguments.of(List.of(), true),
                Arguments.of(List.of(new Pawn(A, SEVEN, BLACK)), false)
        );
    }


    @ParameterizedTest
    @MethodSource("provideKingPieces")
    void 왕_체스말이_두_개인지_확인한다(final List<Piece> kings, final boolean expected) {
        //given
        final Pieces pieces = new Pieces(kings);

        //when
        final boolean actual = pieces.hasTwoKings();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideKingPieces() {
        return Stream.of(
                Arguments.of(List.of(new King(E, EIGHT, BLACK), new King(E, ONE, WHITE)), true),
                Arguments.of(List.of(), false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePiecesAndScore")
    void 점수를_계산한다(final List<Piece> allPieces, final Color color, final double expectedScore) {
        //given
        final Pieces pieces = new Pieces(allPieces);

        //when
        final double actualScore = pieces.calculateScore(color);

        //then
        assertThat(actualScore).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> providePiecesAndScore() {
        return Stream.of(
                Arguments.of(List.of(new King(E, EIGHT, BLACK)), BLACK, 0),
                Arguments.of(List.of(new Queen(E, EIGHT, BLACK)), BLACK, 9),
                Arguments.of(List.of(new Rook(E, ONE, WHITE)), WHITE, 5),
                Arguments.of(List.of(new Bishop(E, ONE, WHITE)), WHITE, 3),
                Arguments.of(List.of(new Knight(E, EIGHT, BLACK)), BLACK, 2.5),
                Arguments.of(List.of(new Pawn(D, SIX, BLACK), new Pawn(D, SEVEN, BLACK)), BLACK, 1),
                Arguments.of(List.of(new Pawn(E, SEVEN, BLACK)), BLACK, 1)
        );
    }
}