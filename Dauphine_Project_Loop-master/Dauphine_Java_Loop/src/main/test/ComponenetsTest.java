
import Code.Components.Orientation;
import Code.Components.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponenetsTest {
    @Test
    public static void rightOrientation(Piece p){
        Orientation o=p.getOrientation();
        switch(o){
            case NORTH -> assertEquals(o.getOpposedOrientation(), Orientation.SOUTH);
            case SOUTH -> assertEquals(o.getOpposedOrientation(), Orientation.NORTH);
            case WEST -> assertEquals(o.getOpposedOrientation(), Orientation.EAST);
            case EAST -> assertEquals(o.getOpposedOrientation(), Orientation.WEST);
        }
    }
    @Test
    public static void getOriFromValueTest(Piece p) {
        int value = p.getOrientation().getValue();
        switch (value) {
            case 0 -> assertEquals(p.getOrientation(), Orientation.NORTH);
            case 1 -> assertEquals(p.getOrientation(), Orientation.EAST);
            case 2 -> assertEquals(p.getOrientation(), Orientation.SOUTH);
            case 3 -> assertEquals(p.getOrientation(), Orientation.WEST);
        }
    }
    @Test
    public static void turn90Test(Piece p){
        Orientation o=p.getOrientation();
        switch(o){
            case NORTH -> assertEquals(o.turn90(), Orientation.EAST);
            case SOUTH -> assertEquals(o.turn90(), Orientation.WEST);
            case WEST -> assertEquals(o.turn90(), Orientation.NORTH);
            case EAST -> assertEquals(o.turn90(), Orientation.SOUTH);
        }
    }
}
