package entities;

import java.io.Serializable;
import java.util.Objects;

public class Departament implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;

    public Departament(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Departament that = (Departament) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Departament data:\n" +
                "id: " + id + "\n" +
                "name: " + name;
    }


}
