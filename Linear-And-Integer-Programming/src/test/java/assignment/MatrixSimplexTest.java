package assignment;


import assignment.MatrixSimplex;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatrixSimplexTest {

    @Test
    public void testLastColumnTableauHasNegativeTrue(){
        assertTrue(MatrixSimplex.lastColumnHasNegative(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        }));
    }
    @Test
    public void testLastColumnTableauHasNegativeFalse(){
        assertFalse(MatrixSimplex.lastColumnHasNegative(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {7,8,10,0,0,1,0}
        }));
    }
    @Test
    public void testLastColumnTableauGetSmallestValue(){
        assertEquals(MatrixSimplex.getPivotColumnIndex(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        }),2);
    }
    @Test
    public void testGetPivotRowIndex(){
        assertEquals(MatrixSimplex.getPivotRowIndex(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        },2),1);
    }
    @Test
    public void testGetPivotRowIndexUsingColumnSmallestValue(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.getPivotRowIndex(tableau,
                MatrixSimplex.getPivotColumnIndex(tableau)),
                1);
    }
    @Test
    public void testRowOperationMakePivotOne(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.makePivotOne(tableau,2,1),
        new double[][]{
                {2,3,2,1,0,0,1000},
                {1/2.,1/2.,1,0,1/2.,0,400},
                {-7,-8,-10,0,0,1,0}
        });
    }
    @Test
    public void testRowOperationMakePivotRowZero(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1/2.,1/2.,1,0,1/2.,0,400},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.makePivotRowZero(tableau,2,1),
                new double[][]{
                        {1,2,0,1,-1,0,200},
                        {1/2.,1/2.,1,0,1/2.,0,400},
                        {-2,-3,0,0,5,1,4000}
                });
    }
    @Test
    public void testSolutionToTableau() throws Exception {
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.solveSimplexTableau(tableau),
                new double[][]{
                        {1,2,0,1,-1,0,200},
                        {0,-1/2.,1,-1/2.,1,0,300},
                        {0,1,0,2,3,1,4400}
                });
    }
    @Test public void testCreateTablaue(){
        assertEquals(new MatrixSimplex(new String[][]{
                {"x1", "x2", "x3", "s1", "s2", "P"},
                {"x1", "x3", "P"}
            },new int[][]{
                    {2,3,2,1,0,0},
                    {1,1,2,0,1,0}
            },new int[]{1000,800},
            new int[]{-7,-8,-10,0,0,1}).createTableau(),
            new double[][]{
                    {2,3,2,1,0,0,1000},
                    {1,1,2,0,1,0,800},
                    {-7,-8,-10,0,0,1,0}
        });
    }
    @Test public void testSolve(){
        new MatrixSimplex(new String[][]{
                    {"x1", "x2", "x3", "s1", "s2", "P"},
                    {"x1", "x3", "P"}
            },new int[][]{
                    {2,3,2,1,0,0},
                    {1,1,2,0,1,0}
            },new int[]{1000,800},
                    new int[]{-7,-8,-10,0,0,1}).doSimplex();
    }

    @Test public void testNoSolution(){
        //if pivotColumn contains all 0s or negatives then no solution
        //test all 0s
        assertTrue(MatrixSimplex.isNoSolution(new double[][]{
                {2,3,2,0,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        },3));
        //test all negatives
        assertTrue(MatrixSimplex.isNoSolution(new double[][]{
                {-2,3,2,1,0,0,1000},
                {-1,1,-2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        },0));
        //test combo of 0s and negatives
        assertTrue(MatrixSimplex.isNoSolution(new double[][]{
                {-2,3,2,1,0,0,1000},
                {0,1,-2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        },0));
        //test it has a solution
        assertFalse(MatrixSimplex.isNoSolution(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,-2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        },0));
    }

    @Test public void testNoSolutionRepeatMatrix(){
        try{
            MatrixSimplex.solveSimplexTableau(
                    new double[][]{
                            {-1,-1,1,0,0,0,-3},
                            {-3,1,0,1,0,0,1},
                            {1,0,0,0,1,0,2},
                            {0,1,0,0,0,1,2},
                            {-3,-1,0,0,0,0,0}
                    }
            );
            assertFalse(true);
        }catch (Exception e){
            if(e.getMessage().equals("No Solution: Tableau got repeated too many times"))
                assertFalse(false);
            else
                assertFalse(true);
        }
    }

    /**
     * maximize z=3x1+x2 subject to
     *      x1+x2>=3
     *      3x1-x2>=-1
     *      x<=2
     *
     * results in:
     * z=8
     * x1=x2=2
     * x3=1
     * x4=5
     * x5=x6=0
     */
    @Test public void testAssignment3Problem2(){
        //TODO
        String v = new MatrixSimplex(new String[][]{
                {"x1", "x2", "x3", "x4", "x5","x6", "z"},
                {"x1", "x2", "x3","x4","z"}
        }, new int[][]{
                {-1, -1, 1, 0, 0, 0},
                {-3, 1, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1},
        }, new int[]{-3, 1, 2, 2},
                new int[]{-3,-1, 0, 0, 0}).getDoSimplexResults();
        String[] names = new String[]{"z","x1","x2","x3","x4","x5","x6"};
        Double[] ressies = new Double[]{8.,2.,2.,1.,5.,0.,0.};

        System.out.println(v);
        testResults(v,names,ressies);
    }
    /**
     * maximize z=10x1+9x2 subject to
     *      2x1+3x2<=72
     *      4x1+3x2<=108
     *      x1>=0,x2>=16
     *
     * results in:
     * x1 = 12.0
     * x2 = 18.0
     * P = 288.0
     * s2 = 0.0
     * s1 = 0.0
     */
    @Test public void testAssignment3Problem3(){
        String v = new MatrixSimplex(new String[][]{
                {"x1", "x2", "s1", "s2", "z"},
                {"x1", "x2", "P"}
        },new int[][]{
                {2,3,1,0},
                {4,3,0,1}
        },new int[]{72,108},
                new int[]{-10,-9,0,0,0}).getDoSimplexResults();
        String[] names = new String[]{"x1","x2","P","s2","s1"};
        Double[] ressies = new Double[]{12.,18.,288.,0.,0.};

        System.out.println(v);
        testResults(v,names,ressies);

    }

    private void testResults(String results, String[] names, Double[] ressies){
        HashMap<String,Double> res = new HashMap<>();
        for(String s : results.split("\n")) {
            String[] r = s.split(" = ");
            res.put(r[0],Double.parseDouble(r[1]));
        }
        for (int i = 0; i < names.length; i++) {
            //test that the variable exists and the variables value is correct
            assertTrue(res.containsKey(names[i]));
            assertEquals(ressies[i],res.get(names[i]));
        }
    }
}
