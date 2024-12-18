package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.CommunityPost;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.TimeConverter;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.HasID;
import com.example.sprintproject.model.TimeConverter;
import com.example.sprintproject.model.User;
import com.example.sprintproject.view.CommunityActivity;
import com.example.sprintproject.viewmodel.CommunityListViewModel;
import com.example.sprintproject.viewmodel.DestinationViewModel;
import com.example.sprintproject.viewmodel.DiningListViewModel;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.viewmodel.Observer;

import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Stack;

import android.content.Context;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private CommunityListViewModel communityListViewModel;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");



    @Before
    public void setUp() {
        //communityListViewModel = new CommunityListViewModel(null, new ArrayList<>());
    }



//
//    // Test to see if multiple accommodation will be added
//    // Raymond Chen
//    @Test
//    public void testMultipleAccommodations() throws Exception {
//        String hotelName = "Hotel1";
//        Date checkInDate = TimeConverter.stringToDate("11-09-2024");
//        Date checkOutDate = TimeConverter.stringToDate("11-10-2024");
//        String location = "Location1";
//        String numRooms = "2";
//        String roomType = "Suite";
//
//        Accommodation accommodation1 = new Accommodation(hotelName,
//                checkInDate, checkOutDate, location, numRooms, roomType);
//
//        assertEquals(hotelName, accommodation1.getHotelName());
//        assertEquals(location, accommodation1.getLocation());
//        assertEquals(numRooms, accommodation1.getNumsRooms());
//        assertEquals(roomType, accommodation1.getRoomType());
//        assertEquals(checkInDate, accommodation1.getCheckInDate());
//        assertEquals(checkOutDate, accommodation1.getCheckOutDate());
//
//        String hotelName2 = "Hotel2";
//        Date checkInDate2 = TimeConverter.stringToDate("08-02-2024");
//        Date checkOutDate2 = TimeConverter.stringToDate("09-10-2024");
//        String location2 = "Location2";
//        String numRooms2 = "5";
//        String roomType2 = "Family Room";
//
//        Accommodation accommodation2 = new Accommodation(hotelName2,
//                checkInDate2, checkOutDate2, location2, numRooms2, roomType2);
//
//        assertEquals(hotelName2, accommodation2.getHotelName());
//        assertEquals(location2, accommodation2.getLocation());
//        assertEquals(numRooms2, accommodation2.getNumsRooms());
//        assertEquals(roomType2, accommodation2.getRoomType());
//        assertEquals(checkInDate2, accommodation2.getCheckInDate());
//        assertEquals(checkOutDate2, accommodation2.getCheckOutDate());
//
//        String hotelName3 = "Hotel 3";
//        Date checkInDate3 = TimeConverter.stringToDate("01-09-2024");
//        Date checkOutDate3 = TimeConverter.stringToDate("12-30-2024");
//        String location3 = "Christmas Blah";
//        String numRooms3 = "3";
//        String roomType3 = "Double Room";
//
//        Accommodation accommodation3 = new Accommodation(hotelName3,
//                checkInDate3, checkOutDate3, location3, numRooms3, roomType3);
//
//        assertEquals(hotelName3, accommodation3.getHotelName());
//        assertEquals(location3, accommodation3.getLocation());
//        assertEquals(numRooms3, accommodation3.getNumsRooms());
//        assertEquals(roomType3, accommodation3.getRoomType());
//        assertEquals(checkInDate3, accommodation3.getCheckInDate());
//        assertEquals(checkOutDate3, accommodation3.getCheckOutDate());
//    }



    //Tests if the User constructor works with passing in dates that are objects
    //Faris Hamdi
//    public void testUserCanBeMadeViaObj() {
//        ArrayList<String> dest = new ArrayList<>();
//        dest.add("dest1");
//        dest.add("dest2");
//        User myUser = new User("uid", "email", new Date(124, 10, 25),
//                new Date(124, 10, 30), 5, dest);
//        assertEquals("uid", myUser.getUid());
//        assertEquals("email", myUser.getEmail());
//        assertEquals("11-25-2024", myUser.getStartDate());
//        assertEquals("11-30-2024", myUser.getEndDate());
//        assertEquals(5, myUser.getDuration());
//        assertEquals("dest1", myUser.getDestinations().get(0));
//        assertEquals("dest2", myUser.getDestinations().get(1));
//    }

    //Tests if the User class's constructors work properly
    //Faris Hamdi
//    @Test
//    public void userSetters () throws Exception {
//        User myUser = new User("uid", "email");
//
//        myUser.setStartDate(new Date(124, 10, 25));
//        myUser.setEndDate((124, 10, 30));
//        myUser.setDuration(5);
//
//        assertEquals("11-25-2024", myUser.getStartDate());
//        assertEquals("11-30-2024", myUser.getEndDate());
//        assertEquals(5, myUser.getDuration());
//    }

    /*
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    private static final SimpleDateFormat timeFormat =
            new SimpleDateFormat("HH:mm", Locale.US);

    // Tests if the Dining class's constructor works properly
    // Nitipon Trimaitreepituk
    @Test
    public void testDiningConstructor() throws ParseException {
        String restaurantName = "Restaurant A";
        String location = "Location A";
        String website = "website.com";
        Date reservationDate = dateFormat.parse("11-11-2024");
        Date reservationTime = timeFormat.parse("08:00");

        Dining dining = new Dining(restaurantName, location, reservationDate, reservationTime, website);

        assertEquals(restaurantName, dining.getRestaurantName());
        assertEquals(location, dining.getLocation());
        assertEquals(website, dining.getWebsite());
        assertEquals("11-11-2024", dining.getReservationDate());
        assertEquals("08:00", dining.getReservationTime());
    }

    // Tests if the Dining class's setters work properly
    // Nitipon Trimaitreepituk
    @Test
    public void testSetterMethods() throws ParseException {
        String restaurantName = "Restaurant A";
        String location = "Location A";
        String website = "website.com";

        Dining dining = new Dining("", "",
                new Date(), new Date(), ""); // Initialize with dummy Date values

        dining.setRestaurantName(restaurantName);
        dining.setLocation(location);
        dining.setWebsite(website);
        dining.setReservationDate("11-18-2024");
        dining.setReservationTime("08:00");

        assertEquals(restaurantName, dining.getRestaurantName());
        assertEquals(location, dining.getLocation());
        assertEquals(website, dining.getWebsite());
        assertEquals("11-18-2024", dining.getReservationDate());
        assertEquals("08:00", dining.getReservationTime());
    }
    */

    // Raymond Chen
    // Test if invalid input will be caught in accommodation
    @Test
    public void TestAllInvalidInput() throws ParseException {
        // Example invalid inputs
        String hotelName3 = "";
        Date checkInDate3 = TimeConverter.stringToDate("12-30-2024");
        Date checkOutDate3 = TimeConverter.stringToDate("1-2-2024");
        String location3 = "";
        String numRooms3 = "-3";
        String roomType3 = "Spaceship Suite";

        // Validate inputs
        boolean isValidHotelName = !hotelName3.isEmpty();
        boolean isValidDateRange = checkInDate3.before(checkOutDate3);
        boolean isValidLocation = !location3.isEmpty();
        boolean isValidNumRooms = numRooms3.matches("\\d+") && Integer.parseInt(numRooms3) > 0;
        boolean isValidRoomType = roomType3.equals("Single Room") || roomType3.equals("Double Room");

        assertFalse("Hotel name should be invalid.", isValidHotelName);
        assertFalse("Date range should be invalid.", isValidDateRange);
        assertFalse("Location should be invalid.", isValidLocation);
        assertFalse("Number of rooms should be invalid.", isValidNumRooms);
        assertFalse("Room type should be invalid.", isValidRoomType);
    }

    //Raymond Chen
    // Testing to see if invalid input will be caught in dining
    @Test
    public void testInvalidInputDining() throws ParseException {
        String restaurantName = "";
        String location = "";
        String website = "invalid-url";
        Date reservationDate = DATE_FORMAT.parse("11-11-2024");
        Date reservationTime = TIME_FORMAT.parse("08:00");

        Dining dining = new Dining(restaurantName, location, reservationDate, reservationTime, website);

        assertTrue("Restaurant name should be invalid.", restaurantName.isEmpty());
        assertTrue("Location should be invalid.", location.isEmpty());
        assertFalse("Website should be invalid.", website.matches("^(http|https)://.*"));
    }

    //test constructor will all parameters in trip class, including new notes functionality
    //Faris Hamdi sprint 4
    @Test
    public void tripConstructor() {
        List<String> use = new ArrayList<>();
        use.add("userID");
        use.add("seconduserID");

        List<String> dest = new ArrayList<>();
        dest.add("dest1ID");
        dest.add("dest2ID");

        List<String> din = new ArrayList<>();
        din.add("dining1ID");
        din.add("dining2ID");

        List<String> accom = new ArrayList<>();
        accom.add("accom1ID");
        accom.add("accom2ID");

        String notes = "These are the notes for my trip!";

        Trip trip = new Trip("tripID", notes, dest, accom, din, use);

        assertEquals(trip.getID(), "tripID");

        assertEquals(trip.getNotes(), "These are the notes for my trip!");

        List<String> users = trip.getUsers();
        assertEquals("userID", users.get(0));
        assertEquals("seconduserID", users.get(1));

        List<String> destinations = trip.getDestinations();
        assertEquals("dest1ID", destinations.get(0));
        assertEquals("dest2ID", destinations.get(1));

        List<String> dinings = trip.getDining();
        assertEquals("dining1ID", dinings.get(0));
        assertEquals("dining2ID", dinings.get(1));

        List<String> accoms = trip.getAccomidations();
        assertEquals("accom1ID", accoms.get(0));
        assertEquals("accom2ID", accoms.get(1));
    }

    //tests if list and notes setters works
    //Faris Hamdi Sprint 4
    @Test
    public void tripSetters() {
        Trip trip = new Trip("userID");
        trip.addUser("seconduserID");

        trip.addDestination("dest1ID");
        trip.addDestination("dest2ID");

        trip.addDining("dining1ID");
        trip.addDining("dining2ID");

        trip.addAccomidation("accom1ID");
        trip.addAccomidation("accom2ID");

        trip.setNotes("We got even more notes!");

        List<String> users = trip.getUsers();
        assertEquals("userID", users.get(0));
        assertEquals("seconduserID", users.get(1));

        List<String> destinations = trip.getDestinations();
        assertEquals("dest1ID", destinations.get(0));
        assertEquals("dest2ID", destinations.get(1));

        List<String> dinings = trip.getDining();
        assertEquals("dining1ID", dinings.get(0));
        assertEquals("dining2ID", dinings.get(1));

        List<String> accoms = trip.getAccomidations();
        assertEquals("accom1ID", accoms.get(0));
        assertEquals("accom2ID", accoms.get(1));

        assertEquals(trip.getNotes(), "We got even more notes!");
    }

//    //Tests that the date format works correctly
//    //Aidan Criscione
//    @Test
//    public void testDateFormatValidation() {
//        String validDate = "12-25-2024";
//        String invalidDate = "05/12/2024";
//
//        String regex = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";
//
//        assertTrue(validDate.matches(regex));
//        assertFalse(invalidDate.matches(regex));
//    }
//
//    // Tests that the date format works correctly with some more complicated examples
//    // Aidan Criscione
//    @Test
//    public void testComplexDateFormat() {
//        String validDate = "12-25-2024";
//        String invalidDate = "05/12/2024";
//        String validDate2 = "01-20-2021";
//        String invalidDate2 = "09/10-2024";
//        String invalidDate3 = "1/1-10";
//
//        String regex = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";
//
//        assertTrue(validDate.matches(regex));
//        assertTrue(validDate2.matches(regex));
//        assertFalse(invalidDate.matches(regex));
//        assertFalse(invalidDate2.matches(regex));
//        assertFalse(invalidDate3.matches(regex));
//    }

    //Tests Trip getters and setters
    //Sebastian Veras
    @Test
    public void testTripGettersSetters() {
        Trip trip = new Trip();
        ArrayList<String> expectedUsers = new ArrayList<>();
        ArrayList<String> expectedDestinations = new ArrayList<>();
        ArrayList<String> expectedDining = new ArrayList<>();
        ArrayList<String> expectedAccommodations = new ArrayList<>();

        expectedUsers.add("userid");
        expectedUsers.add("userid2");
        expectedDestinations.add("dest1");
        expectedDestinations.add("dest2");
        expectedDining.add("McDonalds");
        expectedDining.add("Generic Steakhouse");
        expectedAccommodations.add("Holiday Inn");
        expectedAccommodations.add("Ritz-Carlton");

        trip.addUser("userid");
        trip.addUser("userid2");
        trip.addDestination("dest1");
        trip.addDestination("dest2");
        trip.addDining("McDonalds");
        trip.addDining("Generic Steakhouse");
        trip.addAccomidation("Holiday Inn");
        trip.addAccomidation("Ritz-Carlton");

        assertEquals(trip.getUsers(), expectedUsers);
        assertEquals(trip.getDestinations(), expectedDestinations);
        assertEquals(trip.getDining(), expectedDining);
        assertEquals(trip.getAccomidations(), expectedAccommodations);
    }

    //Tests the Trip constructor using a UID
    //Sebastian Veras
    @Test
    public void testTripConstructorWithUID() {
        User user1 = new User("userID", "sampleemail@gmail.com", "1-2-24", "1-5-24", 3, "tripid");
        Trip trip = new Trip(user1.getID());
        ArrayList<String> theUsers = new ArrayList<>();
        theUsers.add(user1.getID());
        assertEquals(trip.getUsers(), theUsers);
    }


    //
    //Sebastian Veras Sprint 4
    @Test
    public void testTripNotesMethods() {
        Trip trip = new Trip();
        String expectedNotes = "I am a note";
        trip.setNotes("I am a note");
        assertEquals(trip.getNotes(),expectedNotes);
    }

    //Test removeUser method in Trip
    //Sebastian Veras sprint 4
    @Test
    public void testTripRemoveUser() {
        Trip trip = new Trip("Tripid", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        User u1 = new User("id1", "test@gmail.com", trip.getID());
        User u2 = new User("id2", "test2@gmail.com", trip.getID());
        User u3 = new User("id3", "test3@gmail.com", trip.getID());
        ArrayList<String> expectedUsers = new ArrayList<>();
        expectedUsers.add("id1");
        expectedUsers.add("id2");
        expectedUsers.add("id3");
        trip.addUser("id1");
        trip.addUser("id2");
        trip.addUser("id3");

        assertEquals(expectedUsers, trip.getUsers());

        expectedUsers.remove("id2");
        trip.removeUser(u2);

        assertEquals(expectedUsers, trip.getUsers());

    }


    // Tests if classes implementing HasID interface work correctly
    // Jacob Paras
    @Test
    public void hasID() {
        HasID[] arr = new HasID[] {new Accommodation(), new Destination(), new Dining()};
        for (HasID obj: arr) {
            obj.setID("0");
            assertEquals(obj.getID(), "0");
        }
    }

    // Tests if TimeConverter works as expected
    // Jacob Paras
    @Test
    public void testTimeConverter() {
        String date = "";
        String time = "";
        try {
            date = TimeConverter.dateToString(TimeConverter.stringToDate("11-10-2024"));
            time = TimeConverter.timeToString(TimeConverter.stringToTime("07:00"));
        } catch (ParseException e) {
        }
        assertEquals(date, "11-10-2024");
        assertEquals(time, "07:00");
    }

    //Tests that the date format works correctly
    //Aidan Criscione
    @Test
    public void testDateFormatValidation() {
        String validDate = "12-25-2024";
        String invalidDate = "05/12/2024";

        String regex = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";

        assertTrue(validDate.matches(regex));
        assertFalse(invalidDate.matches(regex));
    }

    // Tests that the date format works correctly with some more complicated examples
    // Aidan Criscione
    @Test
    public void testComplexDateFormat() {
        String validDate = "12-25-2024";
        String invalidDate = "05/12/2024";
        String validDate2 = "01-20-2021";
        String invalidDate2 = "09/10-2024";
        String invalidDate3 = "1/1-10";

        String regex = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";

        assertTrue(validDate.matches(regex));
        assertTrue(validDate2.matches(regex));
        assertFalse(invalidDate.matches(regex));
        assertFalse(invalidDate2.matches(regex));
        assertFalse(invalidDate3.matches(regex));
    }

    //test CommunityPost constructor
    //Nitipon Trimaitreepituk Sprint 4
    @Test
    public void testCommunityPostConstructor() throws ParseException {
        String startDate = "03-15-2024";
        String endDate = "03-20-2024";
        String destination = "New York City";
        String accommodations = "Hotel";
        String diningReservations = "Pizza";
        String notes = "Spring Break Trip";

        CommunityPost post = new CommunityPost(notes, diningReservations, accommodations, destination, endDate, startDate, "userID");

        assertEquals("03-15-2024", post.getStart());
        assertEquals("03-20-2024", post.getEnd());
        assertEquals("New York City", post.getDestinations());
        assertEquals("Hotel", post.getAccommodations());
        assertEquals("Pizza", post.getDining());
        assertEquals("Spring Break Trip", post.getNotes());
    }

    //test adding CommunityPost
    //Nitipon Trimaitreepituk Sprint 4
    @Test
    public void testAddCommunityPostToCommunity() throws ParseException {
        String startDate = "07-01-2024";
        String endDate = "07-10-2024";
        String destination = "Tokyo";
        String accommodations = "Inn";
        String diningReservations = "Sushi";
        String notes = "Summer Vacation";

        CommunityPost post = new CommunityPost(notes, diningReservations, accommodations, destination, endDate, startDate, "userID");

        List<CommunityPost> communityPosts = new ArrayList<>();
        communityPosts.add(post);

        assertEquals(1, communityPosts.size());
        assertEquals("Tokyo", communityPosts.get(0).getDestinations());
        assertEquals("07-01-2024", communityPosts.get(0).getStart());
        assertEquals("07-10-2024", communityPosts.get(0).getEnd());
        assertEquals("Sushi", communityPosts.get(0).getDining());
        assertEquals("Summer Vacation", communityPosts.get(0).getNotes());
    }
}

