package com.kilhyunkim.DS;

import org.apache.commons.math3.stat.regression.GLSMultipleLinearRegression;

public class GLSMultipleRegressionTest {

	public static void main(String[] args) throws Exception 
	{
		double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
		double[][] x = new double[6][];
		double[][] omega = new double[6][];
		
		x[0] = new double[]{0, 0, 0, 0, 0};
		x[1] = new double[]{2.0, 0, 0, 0, 0};
		x[2] = new double[]{0, 3.0, 0, 0, 0};
		x[3] = new double[]{0, 0, 4.0, 0, 0};
		x[4] = new double[]{0, 0, 0, 5.0, 0};
		x[5] = new double[]{0, 0, 0, 0, 6.0};          
		
		omega[0] = new double[]{1.1, 0, 0, 0, 0, 0};
		omega[1] = new double[]{0, 2.2, 0, 0, 0, 0};
		omega[2] = new double[]{0, 0, 3.3, 0, 0, 0};
		omega[3] = new double[]{0, 0, 0, 4.4, 0, 0};
		omega[4] = new double[]{0, 0, 0, 0, 5.5, 0};
		omega[5] = new double[]{0, 0, 0, 0, 0, 6.6};  
		
		GLSMultipleRegressionTest test = new GLSMultipleRegressionTest();
		test.calculateGlsRegression(x, y, omega);

	}
	
	public void calculateGlsRegression(double[][] x, double[] y, double[][] omega)
	{
		GLSMultipleLinearRegression regression = new GLSMultipleLinearRegression();
		regression.newSampleData(y, x, omega);
		
		double[] beta = regression.estimateRegressionParameters();
		double[] residuals = regression.estimateResiduals();
		double[][] parametersVariance = regression.estimateRegressionParametersVariance();
		double regressandVariance = regression.estimateRegressandVariance();
		double sigma = regression.estimateRegressionStandardError();
		
		System.out.print("Beta : " );
		for(int i = 0; i < beta.length; i++)
			System.out.printf("%.2f ",beta[i]);
		
		System.out.print("\nResiduals : ");
		for(int i = 0; i < residuals.length; i++)
			System.out.printf("%.2f ", residuals[i]);
		
		
		System.out.println("\nParameterVariance : ");
		for(int i = 0; i < parametersVariance.length; i++)
		{
			for(int j = 0; j <parametersVariance[i].length; j++)
				System.out.printf("%.2f ", parametersVariance[j][i]);
			System.out.println();
			
		}
		
		System.out.printf("RegressAndVariance : %.2f\n", regressandVariance);
		System.out.printf("Sigma : %.2f\n", sigma);
	}

}
