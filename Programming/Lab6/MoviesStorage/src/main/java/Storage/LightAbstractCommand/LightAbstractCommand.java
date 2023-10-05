package Storage.LightAbstractCommand;

import Storage.StorageData.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LightAbstractCommand implements Serializable {
    private String name;
    private transient PrimitiveTypes[] primitiveTypes;
    private transient ComplexTypes[] complexTypes;
    private transient String description;
    private String[] validArguments = new String[0];
    private Movie[] validMovies = new Movie[0];
    public LightAbstractCommand(String name, PrimitiveTypes[] primitiveTypes, ComplexTypes[] complexTypes, String description){
        this.name = name;
        this.primitiveTypes = primitiveTypes;
        this.complexTypes = complexTypes;
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void addValidArguments(String validArgument) {
        List<String> list = new ArrayList<>(List.of(validArguments));
        list.add(validArgument);
        validArguments = new String[list.size()];
        list.toArray(validArguments);
    }

    public void addValidMovie(Movie validMovie){
        List<Movie> list = new ArrayList<>(List.of(validMovie));
        list.add(validMovie);
        validMovies = new Movie[list.size()];
        list.toArray(validMovies);
    }


    public String[] getValidArguments() {
        return validArguments;
    }

    public Movie[] getValidMovie() {
        return validMovies;
    }

    public String getName() {
        return name;
    }

    public PrimitiveTypes[] getPrimitiveTypes() {
        return primitiveTypes;
    }

    public ComplexTypes[] getComplexTypes() {
        return complexTypes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrimitiveTypes(PrimitiveTypes[] primitiveTypes) {
        this.primitiveTypes = primitiveTypes;
    }

    public void setComplexTypes(ComplexTypes[] complexTypes) {
        this.complexTypes = complexTypes;
    }
}
