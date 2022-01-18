
import Code.GUI.Grid;
import Code.Solve.Checker;
import Code.Solve.Generator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GeneratorTest {
    static Generator g;
    @Test
    static void solvableGenerator(String name, Grid grid){
        g=new Generator();
        Grid grid2 = Generator.generateLevel1(name,grid);
        assertEquals(Checker.isSolved(grid2),false);
    }
}
