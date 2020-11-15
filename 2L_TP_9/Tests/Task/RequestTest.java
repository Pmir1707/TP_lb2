package Task;

import PlantsPack.Trees;
import PlantsPack.Shrubs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    @Test
    public void test1(){
        Trees trees = new Trees("Birch", 30);
        assertEquals(4, trees.countConsonantsInThePlantName());
    }
    @Test
    public void test2(){
        Shrubs shrubs = new Shrubs("Elder", 10);
        assertEquals(3, shrubs.countConsonantsInThePlantName());
    }
}
