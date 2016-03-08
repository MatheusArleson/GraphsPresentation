package br.com.xavier.graphs.representation.util;

import java.math.BigDecimal;
import java.util.Random;

public class RandomBigDecimal {
	
	private static final Random RANDOM = new Random();
	
	//XXX CONSTRUCTOR
	private RandomBigDecimal(){}
	
	//XXX METHODS
	public static BigDecimal generateRandom() {
		int randomNum = RANDOM.nextInt((100 - 1) + 1) + 1;
		return new BigDecimal(randomNum);
	}
}
