package com.kilhyunkim.DS;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class OLSRegressionTest {

	public static void main(String[] args) 
	{
		double[] y = new double[] {11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
		double[][] x = new double[6][];
		
    // 실습 데이터 입력
		x[0] = new double[] {0, 0, 0, 0, 0};
		x[1] = new double[] {2.0, 0, 0, 0, 0};
		x[2] = new double[] {0, 3.0, 0, 0, 0};
		x[3] = new double[] {0, 0, 4.0, 0, 0};
		x[4] = new double[] {0, 0, 0, 5.0, 0};
		x[5] = new double[] {0, 0, 0, 0, 6.0};
		
		OLSRegressionTest test = new OLSRegressionTest();
		
    test.calculateOlsRegression(x, y);

	}
	
	public void calculateOlsRegression(double[][] x, double[] y)
	{
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		regression.newSampleData(y, x);
		
		double[] beta = regression.estimateRegressionParameters();
		double[] residuals = regression.estimateResiduals();
		double[][] parameterVariance = regression.estimateRegressionParametersVariance();
		double regressandVariance = regression.estimateRegressandVariance();
		double rSquared = regression.calculateRSquared();
		double sigma = regression.estimateRegressionStandardError();
		
    // 측정값 출력
		System.out.print("Beta : " );
		for(int i = 0; i < beta.length; i++)
			System.out.printf("%.2f ",beta[i]);
		
		System.out.print("\nResiduals : ");
		for(int i = 0; i < residuals.length; i++)
			System.out.printf("%.2f ", residuals[i]);
		
		
		System.out.println("\nParameterVariance : ");
		for(int i = 0; i < parameterVariance.length; i++)
		{
			for(int j = 0; j <parameterVariance[i].length; j++)
				System.out.printf("%.2f ", parameterVariance[j][i]);
			System.out.println();
			
		}
		System.out.printf("\nRSquare : %.2f\n", rSquared);
		System.out.printf("RegressAndVariance : %.2f\n", regressandVariance);
		System.out.printf("Sigma : %.2f\n", sigma);
	}
}
