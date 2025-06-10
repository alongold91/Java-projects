public class ParkingLot {
    private final Semaphore parkingSpaces = new Semaphore(3); // 3 parking spaces
   
    public void parkCar(String carName) {
        try {
            parkingSpaces.acquire(); // Get a parking permit
            System.out.println(carName + " parked");
            Thread.sleep(2000); // Car stays parked for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(carName + " left");
            parkingSpaces.release(); // Release the parking space
        }
    }
}


// Usage
ParkingLot lot = new ParkingLot();
for (int i = 1; i <= 5; i++) {
    final int carNum = i;
    new Thread(() -> lot.parkCar("Car-" + carNum)).start();
}
