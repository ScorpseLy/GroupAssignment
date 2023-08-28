package GroupAssignment;

public class Container {
    private String containerType;
    private int id;
    private float weight;


    public Container(String containerType, int id, float weight){
        this.containerType = containerType;
        this.id = id;
        this.weight = weight;

    }

    public String getContainerType() {
        return containerType;
    }

    //Provide the available container types and force users to choose one
    public boolean setContainerType(String containerType) {
        String[] availableContainerType = {"Dry storage", "Open top", "Open side", "Refrigerated", "Liquid"};
        boolean matched = false;
        for (String type : availableContainerType){
            if (type.equals(containerType)){
                matched = true;
            }
        }

        if (matched){
            this.containerType = containerType;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Container{" +
                "containerType = '" + containerType + '\'' +
                ", id = " + id +
                ", weight = " + weight +
                '}';
    }
}