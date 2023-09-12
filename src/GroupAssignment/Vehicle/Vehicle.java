package Vehicle;

import Container.Container;
import Port.Port;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Vehicle {
    private String filePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Container.txt";
    //A vehicle has a name, id, and a carrier which hold a number of containers
    private String name;
    private String id;
    private double carryingCapacity;
    private double currentFuel;
    private double fuelCapacity;

    private double totalWeight; //not an atrribute in the constructor
    private ArrayList<Container> carrier; //not an atrribute in the constructor

    private String currentPort; //not included in constructor

    public Vehicle(String id, String name, double carryingCapacity, double currentFuel, double fuelCapacity, String currentPort) {
        //There is no parameter for carrier here. Every new vehicle has an empty carrier which is essentially an ArrayList
        this.id = id;
        this.name = name;
        this.carryingCapacity = carryingCapacity;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.totalWeight = 0;
        this.carrier = new ArrayList<Container>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public String getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
    }

    //If a container is found in the container list and the vehicle is still able to carry
    // the piles of container, it will add that container to the vehicle's carrier
    // I've made it so that it will not add the same container twice
    public boolean loadContainer() {
        //if container totalWeight + container weight <= carryingCapacity
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the container to load");
        System.out.print("Container ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();

        String searchID = Integer.toString(ID); // Replace with the ID you want to search for
        boolean found = false;

        // Read data from the text file
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s+"); // Use regex to split by comma and optional spaces
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String containerType = parts[1];
                    double weight = Double.parseDouble(parts[2]);

                    Container container = new Container(id, containerType, weight);

                    // Check if the current container matches the search ID & the vehicle still able to carry the next container
                    if (id.equals(searchID) && totalWeight + container.getWeight() <= carryingCapacity) {
                        carrier.add(container); // Add the found container to the list
                        found = true;
                        System.out.println("Container added ");
                        totalWeight += weight;
                    }
                    else{
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (found){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Failed to add container");
        }
        return true;
    }
    //unloading the containers if there is one. Avoiding duplicate ID
    public boolean unloadContainer(){
        // If there are containers to be removed
        if (carrier.size() > 0) {
            Container unloadedContainer = null; // Declare it here

            // Telling the ID of the container to be removed
            Scanner scanner = new Scanner(System.in);
            System.out.print("Unloading container ID: ");
            int ID = scanner.nextInt();
            scanner.nextLine();

            // Unloading the container with the aforementioned ID
            String searchID = Integer.toString(ID);

            Iterator<Container> iterator = carrier.iterator();
            while (iterator.hasNext()) {
                Container container = iterator.next();
                if (container.getId().equals(searchID)) {
                    unloadedContainer = container;
                    totalWeight-=container.getWeight();
                    iterator.remove(); // Remove the container
                }
            }

            // Check if a container was found and unloaded
            if (unloadedContainer != null) {
                // Write the container information into container.txt
                try {
                    // Open the file in append mode by creating a FileWriter with the 'true' parameter
                    FileWriter writer = new FileWriter(filePath, true);

                    // Write the container's attributes as plain text
                    writer.write("\n" + unloadedContainer.getId() + ", " + unloadedContainer.getContainerType() + ", " + unloadedContainer.getWeight());

                    // Close the FileWriter
                    writer.close();

                    // Print the success message
                    System.out.println("Container unloaded");
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
            else {
                System.out.println("Container with ID " + searchID + " not found.");
                return false;
            }
        }
        else {
            System.out.println("There is no container to be unloaded");
            return false;
        }
    }

    //Determining whether the vehicle can ship the containers to another port or not
    // Move the vehicle only if it is verified for transportation
    // I just made it moved to another port freely with no constraint. Update expected
    public boolean moveVehicle(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter vehicle ID: ");
        String vID = scanner.nextLine();
        System.out.print("Move to port number: ");
        int destinationPort = scanner.nextInt();
        scanner.nextLine();

        setCurrentPort(String.valueOf(destinationPort));
        return true;
        }

    //Display the general number of container
    public String displayGeneralNumberOfContainer(){
        return "Number of container: " + carrier.size();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                " id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                ", carryingCapacity = " + carryingCapacity +
                ", currentFuel = " + currentFuel +
                ", fuelCapacity = " + fuelCapacity +
                ", currentPort = " + currentPort +
                ", totalWeight = " + totalWeight +
                '}';
    }
}