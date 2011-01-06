package models;

import java.math.BigDecimal;
import java.math.MathContext;

public class CobalMathContext {
    public static final MathContext MC = MathContext.DECIMAL64;
    public static final BigDecimal PRECISION = BigDecimal.valueOf(0.01);
}
