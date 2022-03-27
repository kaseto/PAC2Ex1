package edu.uoc.pac2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class CarTest {

    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    Car car;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @BeforeAll
    void init() {
        try{
            Field field = Car.class.getDeclaredField("nextId");
            field.setAccessible(true);
            field.set(null, 0);
            car = new Car("Seat", "IBIZA", 2010, 'D', "9898HJC", 8500.50);
        }catch(Exception e) {
            fail("Parameterized constructor failed");
        }
    }


    @Test
    void testCarDefault() {
        Car carDefault = new Car();
        assertEquals(1, carDefault.getId());
        assertEquals("Lorem", carDefault.getMake());
        assertEquals("IPSUM", carDefault.getModel());
        assertEquals(2000, carDefault.getLicenseYear());
        assertEquals('P', carDefault.getFuel());
        assertEquals("0000CDV", carDefault.getLicensePlate());
        assertFalse(carDefault.getWarranty());
        assertEquals(12100, carDefault.getPrice());
    }

    @Test
    void testCar() {
        assertEquals(0, car.getId());
        assertEquals("Seat", car.getMake());
        assertEquals("IBIZA", car.getModel());
        assertEquals(2010, car.getLicenseYear());
        assertEquals('D', car.getFuel());
        assertEquals("9898HJC", car.getLicensePlate());
        assertFalse(car.getWarranty());
        assertEquals(10285.605, car.getPrice());
    }


    @Test
    void testGetId() {
        assertEquals(0, car.getId());
    }

    @Test
    void testGetMake() {
        assertEquals("Seat", car.getMake());
    }

    @Test
    void testSetMake() {
        car.setMake("auDI");
        assertEquals("Audi", car.getMake());
    }

    @Test
    void testSetMakeException() {
        car.setMake("Lorem ipsum dolo");
        assertEquals("[ERROR] Car's make cannot be longer than 15 characters", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
    }

    @Test
    void testGetModel() {
        assertEquals("IBIZA", car.getModel());
    }

    @Test
    void testSetModel() {
        car.setModel("ToleDo");
        assertEquals("TOLEDO", car.getModel());

        car.setModel("Ibiza Kit Car");
        assertEquals("IBIZA KIT CAR", car.getModel());
    }

    @Test
    void testSetModelException() {
        car.setModel("Lorem ipsum doll lore");
        assertEquals("[ERROR] Car's model cannot be longer than 20 characters", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
    }


    @Test
    void testGetLicenseYear() {
        assertEquals(2010, car.getLicenseYear());
    }

    @Test
    void testSetLicenseYear() {
        car.setLicenseYear(2019);
        assertEquals(2019, car.getLicenseYear());
    }

    @Test
    void testSetLicenseYearException() {

        car.setLicenseYear(2019);

        car.setLicenseYear(1999);
        assertEquals("[ERROR] Car's license year must be in range [2000, current year]", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));

        restoreStreams();

        car.setLicenseYear(2023);
        assertEquals("[ERROR] Car's license year must be in range [2000, current year]", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));

        restoreStreams();

        assertEquals(2019, car.getLicenseYear());
    }


    @Test
    void testGetFuel() {
        assertEquals('D', car.getFuel());
    }

    @Test
    void testSetFuel() {
        car.setFuel('P');
        assertEquals('P', car.getFuel());

        car.setFuel('E');
        assertEquals('E', car.getFuel());

        car.setFuel('D');
        assertEquals('D', car.getFuel());

        car.setFuel('H');
        assertEquals('H', car.getFuel());

    }

    @Test
    void testSetFuelException() {

        car.setFuel('H');

        car.setFuel('p');
        assertEquals("[ERROR] Car's fuel is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setFuel('N');
        assertEquals("[ERROR] Car's fuel is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setFuel('r');
        assertEquals("[ERROR] Car's fuel is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        assertEquals('H', car.getFuel());

    }

    @Test
    void testWarranty() {

        assertTrue(car.getWarranty());

        car.setLicenseYear(2015);
        assertFalse(car.getWarranty());

        car.setLicenseYear(2020);
        assertTrue(car.getWarranty());

        car.setLicenseYear(2010);
        assertFalse(car.getWarranty());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity - Methods definition")
    void checkMethodsSanity() {
        //check attribute fields
        assertEquals(10, Car.class.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("VAT_SPAIN").getModifiers()));
            assertTrue(Modifier.isStatic(Car.class.getDeclaredField("VAT_SPAIN").getModifiers()));
            assertTrue(Modifier.isFinal(Car.class.getDeclaredField("VAT_SPAIN").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("VAT_FRANCE").getModifiers()));
            assertTrue(Modifier.isStatic(Car.class.getDeclaredField("VAT_FRANCE").getModifiers()));
            assertTrue(Modifier.isFinal(Car.class.getDeclaredField("VAT_FRANCE").getModifiers()));

            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("id").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("nextId").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("make").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("model").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("licenseYear").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("fuel").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("licensePlate").getModifiers()));
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredField("price").getModifiers()));

        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }

        //check constructors
        assertEquals(2, Car.class.getDeclaredConstructors().length);

        try {
            assertTrue(Modifier.isPublic(Car.class.getDeclaredConstructor().getModifiers()));
            assertTrue(Modifier.isPublic(Car.class.getDeclaredConstructor(String.class, String.class, int.class, char.class, String.class, double.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("There is some problem with the definition of constructors");
            e.printStackTrace();
        }

        //assertEquals("setId", Arrays.stream(Car.class.getDeclaredMethods()).filter(m -> m.getName().equals("setId")).findFirst().get().getName());

        //check methods, parameters and return types
        try {
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getId").getModifiers()));
            assertEquals(int.class, Car.class.getDeclaredMethod("getId").getReturnType());
            assertTrue(Modifier.isPrivate(Car.class.getDeclaredMethod("setId").getModifiers()));
            assertTrue(Modifier.isStatic(Car.class.getDeclaredMethod("getNextId").getModifiers()));
            assertEquals(int.class, Car.class.getDeclaredMethod("getNextId").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the id attribute");
            e.printStackTrace();
        }

        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getMake").getModifiers()));
            assertEquals(String.class, Car.class.getDeclaredMethod("getMake").getReturnType());
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("setMake", String.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the make attribute");
            e.printStackTrace();
        }

        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getModel").getModifiers()));
            assertEquals(String.class, Car.class.getDeclaredMethod("getModel").getReturnType());
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("setModel", String.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the model attribute");
            e.printStackTrace();
        }

        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getLicenseYear").getModifiers()));
            assertEquals(int.class, Car.class.getDeclaredMethod("getLicenseYear").getReturnType());
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("setLicenseYear", int.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the matriculationYear attribute");
            e.printStackTrace();
        }

        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getFuel").getModifiers()));
            assertEquals(char.class, Car.class.getDeclaredMethod("getFuel").getReturnType());
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("setFuel", char.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the fuel attribute");
            e.printStackTrace();
        }

        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getWarranty").getModifiers()));
            assertEquals(boolean.class, Car.class.getDeclaredMethod("getWarranty").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the warranty attribute");
            e.printStackTrace();
        }

        //methods tested in private test (licensePlate, price)
        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getLicensePlate").getModifiers()));
            assertEquals(String.class, Car.class.getDeclaredMethod("getLicensePlate").getReturnType());
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("setLicensePlate", String.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the license plate attribute");
            e.printStackTrace();
        }
        try{
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("getPrice").getModifiers()));
            assertEquals(double.class, Car.class.getDeclaredMethod("getPrice").getReturnType());
            assertTrue(Modifier.isPublic(Car.class.getDeclaredMethod("setPrice", double.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods of the price attribute");
            e.printStackTrace();
        }
    }

    // tests realizados por el alumno:

    @Test
    void  testLicensePlate(){

        car.setLicensePlate("4578FHF");
        assertEquals("4578FHF", car.getLicensePlate());
        car. setPrice(10000);
        assertEquals(12100, car.getPrice());

        car.setLicensePlate("1234GTY");
        assertEquals("1234GTY", car.getLicensePlate());
        car. setPrice(15000);
        assertEquals(18150, car.getPrice());

        car.setLicensePlate("FG-123-GF");
        assertEquals("FG-123-GF", car.getLicensePlate());
        car. setPrice(20000);
        assertEquals(24300, car.getPrice());

        car.setLicensePlate("BH-667-AZ");
        assertEquals("BH-667-AZ", car.getLicensePlate());
        car. setPrice(30000);
        assertEquals(36450, car.getPrice());

        car.setLicensePlate("9898HJC");
        assertEquals("9898HJC", car.getLicensePlate());
        car. setPrice(8500.5);
        assertEquals(10285.605, car.getPrice());
    }

    @Test
    void testPrice(){

        car.setPrice(0);
        assertEquals("[ERROR] Car's price must be greater than 0", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setPrice(-10000);
        assertEquals("[ERROR] Car's price must be greater than 0", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setPrice(-30);
        assertEquals("[ERROR] Car's price must be greater than 0", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setPrice(-5.2);
        assertEquals("[ERROR] Car's price must be greater than 0", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();
    }
    @Test
    void  exceptionTestLicensePlate(){
        car.setLicensePlate("3456 KTD");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("1234fFH");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("7F67GTF");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("FF-34-GT");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("FF 345-GT");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("FF-3F4-GT");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("FF-344-G");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

        car.setLicensePlate("FF344HJ");
        assertEquals("[ERROR] Car's license plate pattern is incorrect", outContent.toString().replaceAll("\n|\r\n", System.getProperty("line.separator").trim()));
        restoreStreams();

    }

}
