package Task;

import PlantsPack.Trees;
import PlantsPack.Shrubs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestuctTest {
    private static Trees trees;
    private static Shrubs shrubs;

    @BeforeAll
    public static void init() {
        trees = new Trees("Birch", 30);
        shrubs = new Shrubs("Elder", 10);
    }
    @Test
    public void test1() {
        assertEquals(4, trees.countConsonantsInThePlantName());
    }
    @Test
    public void test2() {
        assertEquals(3, shrubs.countConsonantsInThePlantName());
    }
}
