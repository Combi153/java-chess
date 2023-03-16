package chess.domain.piece;

import chess.domain.Color;
import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;

import java.util.List;
import java.util.Optional;

public class Knight extends Piece {

    private static final int MOVABLE_MANHATTAN_DISTANCE = 3;
    private static final List<Position> PASSING_POSITIONS_WITH_LEAPING = List.of();

    public Knight(final File file, final Rank rank, final Color color) {
        super(file, rank, color);
    }

    @Override
    protected boolean canMove(final Position targetPosition) {
        return !position.isInCrossPosition(targetPosition)
                && position.calculateManhattanDistance(targetPosition) == MOVABLE_MANHATTAN_DISTANCE;
    }

    @Override
    public Piece move(final Position targetPosition, final Optional<Piece> pieceContainerOfTargetPosition) {
        return null;
    }

    @Override
    public List<Position> getPassingPositions(final Position targetPosition) {
        validateSamePosition(targetPosition);
        validateDestination(targetPosition);
        return PASSING_POSITIONS_WITH_LEAPING;
    }
}
