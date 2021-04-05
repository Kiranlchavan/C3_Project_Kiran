import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    public LocalTime timeNow = getCurrentTime();
    private List<Item> menu = new ArrayList<Item>();


    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;

    }

    public boolean isRestaurantOpen(LocalTime timeNow) {
        LocalTime currenttime =timeNow;
        if (currenttime.isAfter(openingTime) && currenttime.isBefore(closingTime) ){
            return true;
        }
        else {
            return false;
        }


    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {

        return menu;
        //DELETE ABOVE RETURN STATEMENT AND WRITE CODE HERE
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }
    public List<Item> placeOrder(List<String> orderItem) throws itemNotFoundException {
        List<Item> orders = new ArrayList<>();
        List<Item> menu = getMenu();
        for (String order : orderItem)
            if(findItemByName(order)==null){
                throw new itemNotFoundException("The Item Ordered Is Not In Menu");
            }

        for (Item i : menu) {
            for (String order : orderItem) {
                String name = i.getName();

                if (name.equalsIgnoreCase(order)) {
                    orders.add(i);
                }
            }
        }

        return orders;

    }

    public int getOrderValue(List<Item> items) throws itemNotFoundException {
        int orderValue = 0;
        for (Item item : items) {
            orderValue = orderValue + item.getPrice();
        }
        if(orderValue==0){
            throw new itemNotFoundException("No Items Are Added To Your Order");
        }

        return  orderValue;
    }

}
