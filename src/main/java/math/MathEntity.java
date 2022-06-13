package math;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Entity class which represents MathEntity table, stores information connected
 * with previous calculations.
 *
 * @author Marek Kawalski
 * @version 1.1
 */
@Entity
@Table
public class MathEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Calculation basic form
     */
    private String calculationBase;

    /**
     * Equation after performing calculating value
     */
    private String calculationValue;

    /**
     * Equation after performing calculating first derivative
     */
    private String polynomialFirstDerivative;

    /**
     * Equation after calculating first derivative value
     */
    private String polynomialFirstDerivativeValue;

    /**
     * Foreign Key
     */
    private long dateId;

    /**
     * Get the value of dateId
     *
     * @return the value of dateId
     */
    public long getDateId() {
        return dateId;
    }

    /**
     * Set the value of dateId
     *
     * @param dateId new value of dateId
     */
    public void setDateId(long dateId) {
        this.dateId = dateId;
    }

    /**
     * Get the value of polynomialFirstDerivativeValue
     *
     * @return the value of polynomialFirstDerivativeValue
     */
    public String getPolynomialFirstDerivativeValue() {
        return polynomialFirstDerivativeValue;
    }

    /**
     * Set the value of polynomialFirstDerivativeValue
     *
     * @param polynomialFirstDerivativeValue new value of
     *                                       polynomialFirstDerivativeValue
     */
    public void setPolynomialFirstDerivativeValue(String polynomialFirstDerivativeValue) {
        this.polynomialFirstDerivativeValue = polynomialFirstDerivativeValue;
    }

    /**
     * Get the value of polynomialFirstDerivative
     *
     * @return the value of polynomialFirstDerivative
     */
    public String getPolynomialFirstDerivative() {
        return polynomialFirstDerivative;
    }

    /**
     * Set the value of polynomialFirstDerivative
     *
     * @param polynomialFirstDerivative new value of polynomialFirstDerivative
     */
    public void setPolynomialFirstDerivative(String polynomialFirstDerivative) {
        this.polynomialFirstDerivative = polynomialFirstDerivative;
    }

    /**
     * Get the value of calculationValue
     *
     * @return the value of calculationValue
     */
    public String getCalculationValue() {
        return calculationValue;
    }

    /**
     * Set the value of calculationValue
     *
     * @param calculationValue new value of calculationValue
     */
    public void setCalculationValue(String calculationValue) {
        this.calculationValue = calculationValue;
    }

    /**
     * Get the value of calculationBase
     *
     * @return the value of calculationBase
     */
    public String getCalculationBase() {
        return calculationBase;
    }

    /**
     * Set the value of calculationBase
     *
     * @param calculationBase new value of calculationBase
     */
    public void setCalculationBase(String calculationBase) {
        this.calculationBase = calculationBase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MathEntity)) {
            return false;
        }
        MathEntity other = (MathEntity) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "math.MathEntity[ id=" + id + " ]";
    }

}
