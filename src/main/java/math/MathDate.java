package math;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Entity class which represents MathDate table, stores information connected
 * with calculation date.
 *
 * @author Marek Kawalski
 * @version 1.1
 */
@Entity
@Table
public class MathDate implements Serializable {

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
     * Date of calculation
     */
    private String date;

    /**
     * Foreign key
     */
    private long MathId;

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the value of MathId
     *
     * @return the value of MathId
     */
    public long getMathId() {
        return MathId;
    }

    /**
     * Set the value of MathId
     *
     * @param MathId new value of MathId
     */
    public void setMathId(long MathId) {
        this.MathId = MathId;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof MathDate)) {
            return false;
        }
        MathDate other = (MathDate) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "math.MathDate[ id=" + id + " ]";
    }

}
