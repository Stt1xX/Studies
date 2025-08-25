package org.example;

import java.math.BigDecimal;

public class SysFunc implements Computable {
    private final Sin sinF;
    private final Cos cosF;
    private final Csc cscF;
    private final Sec secF;
    private final Cot cotF;

    private final Ln lnF;
    private final Log ln2F, ln5F, ln10F;

    public SysFunc(Sin sinF, Cos cosF, Csc cscF, Sec secF, Cot cotF, Ln lnF, Log ln2F, Log ln5F, Log ln10F) {
        this.sinF = sinF;
        this.cosF = cosF;
        this.cscF = cscF;
        this.secF = secF;
        this.cotF = cotF;
        this.lnF = lnF;
        this.ln2F = ln2F;
        this.ln5F = ln5F;
        this.ln10F = ln10F;
    }

    @Override
    public BigDecimal compute(double x) {
        if (x <= 0.0){
            var sin = sinF.compute(x).doubleValue();
            var cos = cosF.compute(x).doubleValue();
            var csc = cscF.compute(x).doubleValue();
            var sec = secF.compute(x).doubleValue();
            var cot = cotF.compute(x).doubleValue();
            return BigDecimal.valueOf((Math.pow((sec + cot), 2) * sec + sin * cos) * csc);
        } else {
            var ln = lnF.compute(x).doubleValue();
            var ln2 = ln2F.compute(x).doubleValue();
            var ln5 = ln5F.compute(x).doubleValue();
            var ln10 = ln10F.compute(x).doubleValue();
            return BigDecimal.valueOf((ln5 * ln2 * ln + ln2) * Math.pow(ln10, 6) + ln2);
        }
    }
}
