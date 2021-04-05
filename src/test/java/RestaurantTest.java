import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant1 = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        Restaurant restaurant = Mockito.mock(Restaurant.class);
        LocalTime timeToCheck = LocalTime.of(11, 00, 00);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(timeToCheck);
        boolean checkRestaurantStatus = restaurant1.isRestaurantOpen(restaurant.getCurrentTime());
        assertTrue(checkRestaurantStatus);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant1 = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        Restaurant restaurant = Mockito.mock(Restaurant.class);
        LocalTime timeToCheck = LocalTime.of(8, 00, 00);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(timeToCheck);
        boolean checkRestaurantStatus = restaurant1.isRestaurantOpen(restaurant.getCurrentTime());
        assertFalse(checkRestaurantStatus);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Test Cases for Test Driven Developments
    @Test
    public void getOrderValue_for_sweetCornSoup_and_vegetableLasagabe_order_should_return_ordervalue_388() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        List<String> order = new ArrayList<>();
        order.add("Sweet corn soup");
        order.add("Vegetable lasagne");
        List<Item> finalOrder = restaurant.placeOrder(order);
        int orderValue = restaurant.getOrderValue(finalOrder);
        System.out.println("Order Value :::  " + orderValue);
        assertEquals(388, orderValue);
    }

   @Test
    public void getOrderValue_for_no_items_should_throw_exception() throws itemNotFoundException {
        Throwable exception = assertThrows(itemNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                LocalTime openingTime = LocalTime.parse("10:30:00");
                LocalTime closingTime = LocalTime.parse("22:00:00");
                restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
                restaurant.addToMenu("Sweet corn soup", 119);
                restaurant.addToMenu("Vegetable lasagne", 269);
                List<String> order = new ArrayList<>();
                List<Item> finalOrder = restaurant.placeOrder(order);
                int orderValue = restaurant.getOrderValue(finalOrder);


            }
        });
        assertEquals("No Items Are Added To Your Order", exception.getMessage());

    }
    @Test
    public void placeOrder_should_throw_exception_when_Item_Is_Not_In_Menu() throws itemNotFoundException {
        Throwable exception = assertThrows(itemNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                LocalTime openingTime = LocalTime.parse("10:30:00");
                LocalTime closingTime = LocalTime.parse("22:00:00");
                restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
                restaurant.addToMenu("Sweet corn soup", 119);
                restaurant.addToMenu("Vegetable lasagne", 269);
                List<String> order = new ArrayList<>();
                order.add("Fried Rice");
                order.add("Vegetable lasagne");
                List<Item> finalOrder = restaurant.placeOrder(order);

            }
        });
        assertEquals("The Item Ordered Is Not In Menu", exception.getMessage());

    }


}