/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package novelty;

import emo.Individual;
import emo.OptimizationProblem;
import parsing.IndividualEvaluator;

/**
 * OSY test problem
 *
 * @author Haitham Seada
 */
public class OSYWithNoveltyEvaluator extends IndividualEvaluator {

    @Override
    public void updateIndividualObjectivesAndConstraints(
            OptimizationProblem problem,
            Individual individual) {
        double[] x = individual.real;
        double obj0 = -(25 * (x[0] - 2) * (x[0] - 2) + (x[1] - 2) * (x[1] - 2) + (x[2] - 1) * (x[2] - 1) + (x[3] - 4) * (x[3] - 4) + (x[4] - 1) * (x[4] - 1));
        double obj1 = x[0] * x[0] + x[1] * x[1] + x[2] * x[2] + x[3] * x[3] + x[4] * x[4] ;
        double sparseness = InvertedNoveltyMetric.calcSparseness(individual);

        //2obj

        individual.setObjective(0, obj0+sparseness);
        individual.setObjective(1, obj1+sparseness);


        //3obj
/*
        individual.setObjective(0, obj0);
        individual.setObjective(1, obj1);
        individual.setObjective(2, sparseness);
*/
        // Announce that objective function values are valid
        individual.validObjectiveFunctionsValues = true;
        // Update constraint violations if constraints exist
        if (problem.constraints != null) {
            // Four constraints   
            double[] g = new double[6];
            g[0] = -1.0 + (x[0] + x[1]) / 2.0;
            g[1] = 1.0 - (x[0] + x[1]) / 6.0;
            g[2] = 1.0 + x[0] / 2.0 - x[1] / 2.0;
            g[3] = 1.0 - x[0] / 2.0 + 3 * x[1] / 2.0;
            g[4] = 1.0 - (x[2] - 3.0) * (x[2] - 3.0) / 4.0 - x[3] / 4.0;
            g[5] = -1.0 + (x[4] - 3.0) * (x[4] - 3.0) / 4.0 + x[5] / 4.0;
            // Set constraints violations
            for (int i = 0; i < g.length; i++) {
                if (g[i] < 0) {
                    individual.setConstraintViolation(i, g[i]);
                } else {
                    individual.setConstraintViolation(i, 0);
                }
            }
            // Announce that objective function values are valid
            individual.validConstraintsViolationValues = true;
        }
        // Increase Evaluations Count by One (counted per individual)
        funEvaCount++;
    }

    @Override
    public double[] getReferencePoint() {
        double[] refPoint = {-31.69076, 85.14825};
        return refPoint;
    }

    @Override
    public double[] getIdealPoint() {
        //double[] refPoint = {-273.88164, 4.00043};
        double[] refPoint = {-300, -0.05};
        return refPoint;
    }
}
